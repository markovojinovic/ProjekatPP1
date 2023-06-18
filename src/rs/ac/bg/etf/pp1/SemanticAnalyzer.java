package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

import java.util.Stack;

// TODO: Dodati u odgovarajuce f-je ova stanja
enum GlobalState {
    InitialGlobal, ConstDeclaration, VarDeclaration, ArrayDeclaration, MatrixDeclaration,
    EqualDesignatior, OneOperandDesignator, LeftArrayDesignator, ReadPrintDesignator
}

enum LocalState {
    InitialLocal, ArrayDereference, MartixDereference, ArrayCreation, MatrixCreation
}

enum QuestionableState {
    QInitialState, QArrayDereference, QMartixDereference
}

class TableEntry {
    public int sort;
    public String name;
    public Struct type;

    TableEntry(int sort, String name, Struct type) {
        this.sort = sort;
        this.name = name;
        this.type = type;
    }
};

public class SemanticAnalyzer extends VisitorAdaptor {
    // TODO: Dodati da se konstante ne smeju menjati posle u kodu
    // TODO: I dalje postoji problem sa pozivanjem klasa oko zagrada niza, konkretno za prmenljivu niz je hardcode

    int printCallCount = 0;
    int varDeclCount = 0;
    Obj currentMethod = null;
    boolean returnFound = false;
    boolean errorDetected = false;
    int nVars;

    Struct varDeclType = null;
    int assignmentType = -1;
    String assignmentName = "";

    int identType[] = null;
    int identTypeCnt = 0;

    GlobalState globalState = GlobalState.InitialGlobal;
    LocalState localState = LocalState.InitialLocal;
    QuestionableState questionableState = QuestionableState.QInitialState;

    Stack<TableEntry> varStack = new Stack<>();
    int arrayMatrixAtempt = 0;

    Logger log = Logger.getLogger(getClass());

    public void report_error(String message, SyntaxNode info) {
        errorDetected = true;
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0 : info.getLine();
        if (line != 0)
            msg.append(" na liniji ").append(line);
        log.error(msg.toString());
    }

    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0 : info.getLine();
        if (line != 0)
            msg.append(" na liniji ").append(line);
        log.info(msg.toString());
    }

    // =================================================================================================================
    // metode koje sluze za implementiranje vecih metoda

    private void check_ident_type(SyntaxNode node) {
        if (identType != null) {
            int startType = identType[0];
            for (int type : identType) {
                if (type == 0)
                    break;
                if (type != startType)
                    report_error("Greska na liniji " + node.getLine() +
                            " : nekompatibilni tipovi u izrazu", null);
            }
            identType = null;
            identTypeCnt = 0;
        }
    }

    private void designator_equal_check(Obj var, DesignatorStatementEqual assignment) {
        boolean noDec = false, noCom = false;
        int varKind = var.getType().getKind();
        if (varKind == Struct.Array)
            varKind = var.getType().getElemType().getKind();
        if (varKind == Struct.Array)
            varKind = var.getType().getElemType().getElemType().getKind();
        if (var == Tab.noObj)
            noDec = true;
        else if (assignmentType == 19) {
            Obj rVar = Tab.find(assignmentName);
            int kind = rVar.getType().getKind();
            if (kind == Struct.Array)
                kind = rVar.getType().getElemType().getKind();
            if (kind == Struct.Array)
                kind = rVar.getType().getElemType().getElemType().getKind();
            if (rVar == Tab.noObj)
                noDec = true;
            else if (kind != var.getType().getKind())
                noCom = true;
        } else if (varKind != assignmentType)
            noCom = true;

        if (identType != null) {
            if (localState == LocalState.ArrayDereference) {
                localState = LocalState.InitialLocal;
                int firstNil = -1;
                for (int curr = 0; curr < identType.length; curr++)
                    if (identType[curr] == 0) {
                        firstNil = curr;
                        break;
                    }
                boolean correct = true;
                for (int i = 0; i < firstNil - 2; i++)
                    if (identType[i] != 1) {
                        correct = false;
                        break;
                    }
                if (firstNil >= 2) {
                    if (identType[firstNil - 1] != identType[firstNil - 2])
                        report_error("Greska na liniji " + assignment.getLine() + " : " +
                                "nekompatibilni tipovi u dodeli vrednosti!", null);
                    if (!correct)
                        report_error("Greska na liniji " + assignment.getLine() + " : " +
                                "nekompatibilni tipovi u dereferenciranju niza!", null);
                }

            } else if (localState == LocalState.ArrayCreation) {
                localState = LocalState.InitialLocal;
                int type = assignment.getDesignator().obj.getType().getElemType().getKind();
                if (type == Struct.Array)
                    type = assignment.getDesignator().obj.getType().getElemType().getElemType().getKind();
                boolean correct = true;
                if (type != identType[0])
                    report_error("Greska na liniji " + assignment.getLine() + " : " +
                            "nekompatibilni tipovi u dodeli vrednosti! ", null);
                for (int i = 1; i < identType.length; i++) {
                    if (identType[i] == 0)
                        break;
                    if (identType[i] != 1)
                        correct = false;
                }
                if (!correct)
                    report_error("Greska na liniji " + assignment.getLine() + " : " +
                            "nekompatibilni tipovi u dereferenciranju niza!", null);

            } else {
                int startType = identType[0];
                for (int type : identType) {
                    if (type == 0)
                        break;
                    if (type != startType)
                        noCom = true;
                }
            }
            identType = null;
            identTypeCnt = 0;
        }

        if (noDec)
            report_error("Greska na liniji " + assignment.getLine() + " : ime " +
                    assignment.getDesignator().obj.getName() + " nije deklarisano! ", null);
        if (noCom)
            report_error("Greska na liniji " + assignment.getLine() + " : " +
                    " nekompatibilni tipovi u dodeli vrednosti!", null);
    }

    private void tab_insert_check(String name, Struct struct, int line) {
        int kind = struct.getKind();
        if(kind == Struct.Array)
            kind = struct.getElemType().getKind();
        if(kind == Struct.Array)
            kind = struct.getElemType().getElemType().getKind();
        if(identType != null)
            for(int currType : identType) {
                if(currType == 0)
                    break;
                if (currType != kind) {
                    report_error("Greska na liniji " + line + " : " +
                            " nekompatibilni tipovi u dodeli vrednosti!", null);
                    return;
                }
            }

        varStack.push(new TableEntry(Obj.Var, name, struct));
    }

    private void cleanStack() {
        if (arrayMatrixAtempt == 1)
            return;
        if (!varStack.isEmpty()) {
            TableEntry top = varStack.pop();

            if (questionableState == QuestionableState.QArrayDereference)
                globalState = GlobalState.ArrayDeclaration;
            else if (questionableState == QuestionableState.QMartixDereference)
                globalState = GlobalState.MatrixDeclaration;
            questionableState = QuestionableState.QInitialState;

            if (top.name.equals("niz"))
                globalState = GlobalState.ArrayDeclaration;

            if (globalState == GlobalState.ArrayDeclaration)
                Tab.insert(Obj.Var, top.name, new Struct(Struct.Array, top.type));
            else if (globalState == GlobalState.MatrixDeclaration)
                Tab.insert(Obj.Var, top.name, new Struct(Struct.Array, new Struct(Struct.Array, top.type)));
            else
                Tab.insert(Obj.Var, top.name, top.type);

            // report_info("------------------------- Ulazimo u clean stack sa " + top.name, null);
            globalState = GlobalState.InitialGlobal;
            while (!varStack.isEmpty()) {
                top = varStack.pop();
                Tab.insert(Obj.Var, top.name, top.type);
                // report_info("------------------------- Ulazimo u clean stack petlju sa " + top.name, null);
            }
        } else {
            // report_error(" ============================ Desila se greska na steku ============================ ", null);
        }
    }

    // =================================================================================================================

    public void visit(VarDeclarationArray varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
        tab_insert_check(varDecl.getVarName(), varDecl.getType().struct, varDecl.getLine());

        varDeclType = null;
        identType = null;
    }

    public void visit(SquareListOutsideOne squareListOutsideOne) {
        arrayMatrixAtempt++;
        // report_info("------------------------- Ulazimo u niz zagrade", null);
        questionableState = QuestionableState.QArrayDereference;
        cleanStack();
    }

    public void visit(NoSquareListOutside noSquareListOutside) {
        // report_info("------------------------- Ne ulazimo u niz zagrade", null);
        questionableState = QuestionableState.QInitialState;
        cleanStack();
    }

    public void visit(SquareListOutside squareListOutside) {
        arrayMatrixAtempt++;
        // report_info("------------------------- Ulazimo u matrica zagrade", null);
        questionableState = QuestionableState.QMartixDereference;
        cleanStack();
    }

    public void visit(ConstVarDeclaration varDecl) {
        if (varDecl.getType().struct.assignableTo(varDecl.getFactor().struct)) {
            varDeclCount++;
            report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
            tab_insert_check(varDecl.getVarName(), varDecl.getType().struct, varDecl.getLine());
            varDeclType = null;
            globalState = GlobalState.ConstDeclaration;
        } else {
            report_error("Greska : Tipovi nisu kompatibilni ", varDecl);
        }
        identType = null;
    }

    public void visit(VarDeclarationEqual varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
        tab_insert_check(varDecl.getVarName(), varDecl.getType().struct, varDecl.getLine());
        varDeclType = null;
        identType = null;
    }

    public void visit(IdentInLine varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getIdentName(), varDecl);
        tab_insert_check(varDecl.getIdentName(), varDeclType, varDecl.getLine());
    }

    public void visit(PrintStmt print) {
        printCallCount++;
    }

    public void visit(ProgName progName) {
        progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
        Tab.openScope();
    }

    public void visit(Program program) {
        nVars = Tab.currentScope.getnVars();
        Tab.chainLocalSymbols(program.getProgName().obj);
        Tab.closeScope();
    }

    public void visit(TypeIdent type) {
        Obj typeNode = Tab.find(type.getTypeName());
        if (typeNode == Tab.noObj) {
            if (type.getTypeName().equals("bool"))
                type.struct = new Struct(Struct.Bool);
            else if (type.getTypeName().equals("void"))
                type.struct = Tab.noType;
            else {
                report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
                type.struct = Tab.noType;
            }
        } else {
            if (Obj.Type == typeNode.getKind()) {
                type.struct = typeNode.getType();
                varDeclType = typeNode.getType();
            } else {
                report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
                type.struct = Tab.noType;
            }
        }
    }

    public void visit(MethodTypeName methodTypeName) {
        currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMetName(), methodTypeName.getType().struct);
        methodTypeName.obj = currentMethod;
        Tab.openScope();
        report_info("Obradjuje se funkcija " + methodTypeName.getMetName(), methodTypeName);
    }

    public void visit(MethodDecl methodDecl) {
        if (!returnFound && currentMethod.getType() != Tab.noType) {
            report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " +
                    currentMethod.getName() + " nema \"return\" iskaz!", null);
        }
        Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();

        returnFound = false;
        currentMethod = null;
    }

    public void visit(DesignatorExpression designator) {
        Obj obj = Tab.find(designator.getName());
        if (obj == Tab.noObj) {
            report_error("Greska na liniji " + designator.getLine() + " : ime " + designator.getName() +
                    " nije deklarisano! ", null);
        }
        designator.obj = obj;
        assignmentType = 19;
        assignmentName = designator.getName();
        if (identType == null) {
            identType = new int[256];
            identTypeCnt = 0;
        }
        int kind = obj.getType().getKind();
        if (kind == Struct.Array)
            kind = obj.getType().getElemType().getKind();
        if (kind == Struct.Array)
            kind = obj.getType().getElemType().getElemType().getKind();
        identType[identTypeCnt++] = kind;
    }

    public void visit(ExprListExpr exprListExpr) {
        localState = LocalState.ArrayDereference;
    }

    public void visit(FormParams formParsLst) {
        report_info("Parametar f-je " + formParsLst.getIdentName(), formParsLst);
        tab_insert_check(formParsLst.getIdentName(), formParsLst.getType().struct, formParsLst.getLine());
    }

    public void visit(IdentTypeInLine identTypeInLine) {
        report_info("Parametar f-je " + identTypeInLine.getIdentName(), identTypeInLine);
        tab_insert_check(identTypeInLine.getIdentName(), identTypeInLine.getType().struct, identTypeInLine.getLine());
    }

    public void visit(Terminator term) {
        term.struct = term.getFactor().struct;
    }

    public void visit(Expression termExpr) {
        termExpr.struct = termExpr.getTerm().struct;
    }

    public void visit(AddOperationTerminatorRec addExpr) {
        Struct te = addExpr.getAddopTerm().struct;
        Struct t = addExpr.getTerm().struct;
        if (te == null || t == null) {
            return;
        }
        if (te.equals(t) && te == Tab.intType) {
            addExpr.struct = te;
        } else {
            report_error("Greska na liniji " + addExpr.getLine() +
                    " : nekompatibilni tipovi u izrazu za sabiranje.", null);
            addExpr.struct = Tab.noType;
        }
    }

    public void visit(FactorNum factorNum) {
        factorNum.struct = Tab.intType;
        assignmentType = Struct.Int;
        if (identType == null) {
            identType = new int[256];
            identTypeCnt = 0;
        }
        identType[identTypeCnt++] = Struct.Int;
    }

    public void visit(FactorBool factorBool) {
        factorBool.struct = new Struct(Struct.Bool);
        assignmentType = Struct.Bool;
        varDeclType = new Struct(Struct.Bool);
        if (identType == null) {
            identType = new int[256];
            identTypeCnt = 0;
        }
        identType[identTypeCnt++] = Struct.Bool;
    }

    public void visit(FactorChar factorChar) {
        factorChar.struct = Tab.charType;
        assignmentType = Struct.Char;
        if (identType == null) {
            identType = new int[256];
            identTypeCnt = 0;
        }
        identType[identTypeCnt++] = Struct.Char;
    }

    public void visit(FactorDes var) {
        var.struct = var.getDesignator().obj.getType();
    }

    public void visit(ReturnStatementExpr returnExpr) {
        returnFound = true;
        Struct currMethType = currentMethod.getType();
        if (!currMethType.compatibleWith(returnExpr.getExpr().struct)) {
            report_error("Greska na liniji " + returnExpr.getLine() + " : " +
                    "tip izraza u \"return\" naredbi ne slaze se sa tipom povratne vrednosti funkcije " +
                    currentMethod.getName(), null);
        }
        check_ident_type(returnExpr);
    }

    public void visit(DesignatorStatementArray designatorStatementArray) {
        check_ident_type(designatorStatementArray);
    }

    public void visit(DesignatorStatementPlusPlus designatorStatementPlusPlus) {
        if (designatorStatementPlusPlus.getDesignator().obj.getType().getKind() != Struct.Int) {
            report_error("Greska na liniji " + designatorStatementPlusPlus.getLine() +
                    " : nekompatibilni tipovi u izrazu", null);
        }
        identType = null;
        identTypeCnt = 0;
    }

    public void visit(DesignatorStatementMinusMinus designatorStatementMinusMinus) {
        if (designatorStatementMinusMinus.getDesignator().obj.getType().getKind() != Struct.Int) {
            report_error("Greska na liniji " + designatorStatementMinusMinus.getLine() +
                    " : nekompatibilni tipovi u izrazu", null);
        }
        identType = null;
        identTypeCnt = 0;
    }

    public void visit(StatementDesignator statementDesignator) {
        identType = null;
        identTypeCnt = 0;
    }

    public void visit(StatementRead statementRead) {
        identType = null;
        identTypeCnt = 0;
    }

    public void visit(DesignatorStatementEqual assignment) {
        Obj var = Tab.find(assignment.getDesignator().obj.getName());
        designator_equal_check(var, assignment);
        assignmentType = -1;
    }

    public void visit(FactorNewExpr factorNewExpr) {
        assignmentType = factorNewExpr.getType().struct.getKind();
        localState = LocalState.ArrayCreation;
    }

    public void visit(FactorExpr factorExpr) {
        check_ident_type(factorExpr);
    }

    public void visit(FactorNewTypeExpr factorNewTypeExpr) {
        assignmentType = factorNewTypeExpr.getType().struct.getKind();
        localState = LocalState.ArrayCreation;
    }

    public void visit(VarDeclarations varDeclarations) {
        cleanStack();
    }

}
