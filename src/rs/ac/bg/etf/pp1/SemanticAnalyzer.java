package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

import java.util.Stack;

enum State {
    ConstDeclaration, VarDeclaration, ArrayDeclaration, MatrixDeclaration
}

enum LocalState {
    InitialLocal, ArrayDereference, MartixDereference, ArrayCreation
}

class TableEntry {
    public int sort;
    public String name;
    public Struct type;
    public State declaration;

    TableEntry(int sort, String name, Struct type, State declaration) {
        this.sort = sort;
        this.name = name;
        this.type = type;
        this.declaration = declaration;
    }
}

public class SemanticAnalyzer extends VisitorAdaptor {

    private static final int variable = 1, arrayDereference = 2, matrixDereference = 3;

    int printCallCount = 0;
    int varDeclCount = 0;
    private Obj currentMethod = null;
    private boolean returnFound = false;
    boolean errorDetected = false;
    int nVars;

    private Struct varDeclType = null;
    private int assignmentType = -1;
    private String assignmentName = "";

    private int identType[] = null, varType[] = null, derefType[] = null;
    private int identTypeCnt = 0, varTypeCnt = 0, derefTypeCnt = 0;

    private LocalState localState = LocalState.InitialLocal;

    private Stack<TableEntry> varStack = new Stack<>();

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

    private boolean check_deref_level() {       // TODO: zavrsiti f-ju
        if (derefType == null)
            return true;

        for (int i = 0; i < derefTypeCnt; i++)
            if (varType[i] != derefType[i])
                return false;

        // proveriti da li se razlika sa leve strane jednakosti izmedju odgovarajuceg derefType clana i varType clana
        // poklapa sa svakom razlikom paralelnih clanova sa suprotnih strana

        // pozicija leve promenljive u nizu je kad se izokidaju sva njena deref, primer matrica[1][2] je treca pozicija
        // treba gledati sve pre nje da su deref razlike 0, jer svi moraju biti tipa int, a sve kasnije na druge razlike

        return true;
    }

    private int getKind(Obj var) {
        int varKind = var.getType().getKind();
        if (varKind == Struct.Array)
            varKind = var.getType().getElemType().getKind();
        if (varKind == Struct.Array)
            varKind = var.getType().getElemType().getElemType().getKind();
        return varKind;
    }

    private int getKindDept(Obj var) {
        int varKind = var.getType().getKind();
        if (varKind != Struct.Array)
            return 1;

        varKind = var.getType().getElemType().getKind();
        if (varKind != Struct.Array)
            return 2;
        else
            return 3;
    }

    private int getKindDept(String name) {
        Obj var = Tab.find(name);
        if (var == Tab.noObj) {
            report_error("===============Nepostojeca promenljiva pustena u tabelu u okviru kontrole", null);
            return -1;
        }
        int varKind = var.getType().getKind();
        if (varKind != Struct.Array)
            return 1;

        varKind = var.getType().getElemType().getKind();
        if (varKind != Struct.Array)
            return 2;
        else
            return 3;
    }

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
            varType = null;
            varTypeCnt = 0;
            derefType = null;
            derefTypeCnt = 0;
        }
    }

    private void designator_equal_check(Obj var, DesignatorStatementEqual assignment) {
        if (var.getKind() == Obj.Con) {
            report_error("Greska na liniji: " + assignment.getLine() +
                    " - Ne sme se menjati vrednost konstanti", null);
            return;
        }
        if (localState != LocalState.ArrayCreation)
            if (!check_deref_level()) {
                report_error("Greska na liniji " + assignment.getLine() + " : " +
                        "nekompatibilni tipovi u dodeli vrednosti!", null);
                return;
            }
        boolean noDec = false, noCom = false;
        int varKind = getKind(var);
        if (var == Tab.noObj)
            noDec = true;
        else if (assignmentType == 19) {
            Obj rVar = Tab.find(assignmentName);
            int kind = getKind(rVar);
            if (rVar == Tab.noObj)
                noDec = true;
            else if (kind != varKind)
                noCom = true;
        } else if (varKind != assignmentType)
            noCom = true;

        if (identType != null) {
            if (localState == LocalState.ArrayDereference || localState == LocalState.MartixDereference) {
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
                    boolean jump = false;
                    if (identType[firstNil - 1] != identType[firstNil - 2]) {
                        report_error("Greska na liniji " + assignment.getLine() + " : " +
                                "nekompatibilni tipovi u dodeli vrednosti!", null);
                        jump = true;
                    }
                    if (!correct) {
                        report_error("Greska na liniji " + assignment.getLine() + " : " +
                                "nekompatibilni tipovi u dereferenciranju niza!", null);
                        jump = true;
                    }
                    if (jump)
                        return;
                }

            } else if (localState == LocalState.ArrayCreation) {
                localState = LocalState.InitialLocal;
                int type = getKind(assignment.getDesignator().obj);
                boolean correct = true, jump = false;
                if (type != identType[0]) {
                    report_error("Greska na liniji " + assignment.getLine() + " : " +
                            "nekompatibilni tipovi u dodeli vrednosti! ", null);
                    jump = true;
                }
                for (int i = 1; i < identType.length; i++) {
                    if (identType[i] == 0)
                        break;
                    if (identType[i] != 1)
                        correct = false;
                }
                if (!correct) {
                    report_error("Greska na liniji " + assignment.getLine() + " : " +
                            "nekompatibilni tipovi u dereferenciranju niza!", null);
                    jump = true;
                }
                if (jump)
                    return;

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
            varType = null;
            varTypeCnt = 0;
            derefType = null;
            derefTypeCnt = 0;
        }

        if (noDec)
            report_error("Greska na liniji " + assignment.getLine() + " : ime " +
                    assignment.getDesignator().obj.getName() + " nije deklarisano! ", null);
        if (noCom)
            report_error("Greska na liniji " + assignment.getLine() + " : " +
                    " nekompatibilni tipovi u dodeli vrednosti!", null);
    }

    private void tab_insert_check(String name, Struct struct, int line, State declaration) {
        int kind = struct.getKind();
        if (kind == Struct.Array)
            kind = struct.getElemType().getKind();
        if (kind == Struct.Array)
            kind = struct.getElemType().getElemType().getKind();
        if (identType != null)
            for (int currType : identType) {
                if (currType == 0)
                    break;
                if (currType != kind) {
                    report_error("Greska na liniji " + line + " : " +
                            " nekompatibilni tipovi kod dodele vrednosti!", null);
                    return;
                }
            }
        if (declaration == State.ConstDeclaration)
            Tab.insert(Obj.Con, name, struct);
        else
            Tab.insert(Obj.Var, name, struct);
    }

    private void stack_insert_check(String name, State declaration) {
        if (declaration == State.ConstDeclaration)
            varStack.push(new TableEntry(Obj.Con, name, null, declaration));
        else
            varStack.push(new TableEntry(Obj.Var, name, null, declaration));
    }

    private void cleanStack(Struct struct) {
        if (!varStack.isEmpty()) {
            TableEntry top = varStack.pop();
            if (top.type == null)
                top.type = struct;

            if (top.declaration == State.ArrayDeclaration)
                Tab.insert(Obj.Var, top.name, new Struct(Struct.Array, top.type));
            else if (top.declaration == State.MatrixDeclaration)
                Tab.insert(Obj.Var, top.name, new Struct(Struct.Array, new Struct(Struct.Array, top.type)));
            else if (top.declaration == State.VarDeclaration)
                Tab.insert(Obj.Var, top.name, top.type);

            while (!varStack.isEmpty()) {
                top = varStack.pop();
                if (top.type == null)
                    top.type = struct;

                if (top.declaration == State.ArrayDeclaration)
                    Tab.insert(Obj.Var, top.name, new Struct(Struct.Array, top.type));
                else if (top.declaration == State.MatrixDeclaration)
                    Tab.insert(Obj.Var, top.name, new Struct(Struct.Array, new Struct(Struct.Array, top.type)));
                else if (top.declaration == State.VarDeclaration)
                    Tab.insert(Obj.Var, top.name, top.type);
            }
        }
    }

    // =================================================================================================================

    public void visit(VarDeclarationArray varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
        stack_insert_check(varDecl.getVarName(), State.ArrayDeclaration);

        varDeclType = null;
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
    }

    public void visit(VarDeclarationMatrix varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
        stack_insert_check(varDecl.getVarName(), State.MatrixDeclaration);

        varDeclType = null;
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
    }

    public void visit(VarDeclaration varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
        stack_insert_check(varDecl.getVarName(), State.VarDeclaration);

        varDeclType = null;
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
    }

    public void visit(VarDeclarationOther varDeclarationOther) {
        cleanStack(varDeclarationOther.getType().struct);
    }

    public void visit(ConstVarDeclaration varDecl) {
        if (varDecl.getType().struct.getKind() == varDecl.getFactor().struct.getKind()) {
            varDeclCount++;
            report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
            tab_insert_check(varDecl.getVarName(),
                    varDecl.getType().struct, varDecl.getLine(), State.ConstDeclaration);
            varDeclType = null;
        } else {
            report_error("Greska : Tipovi nisu kompatibilni ", varDecl);
        }
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
    }

    public void visit(IdentInLine varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getIdentName(), varDecl);
        tab_insert_check(varDecl.getIdentName(), varDeclType, varDecl.getLine(), State.VarDeclaration);
    }

    public void visit(PrintStmt print) {
//        if(print.getExpr().struct != Tab.intType && print.getExpr().struct != Tab.charType){
//            report_error("Greska na liniji " + print.getLine() + " : Operand mora biti char ili int", null);
//        }
        printCallCount++;
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
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
        if (Tab.find(methodTypeName.getMetName()) != Tab.noObj) {
            report_error("Greska na liniji " +
                    methodTypeName.getLine() + " : funkcija sa imenom " +
                    methodTypeName.getMetName() + " vec postoji", null);
        } else {
            currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMetName(), methodTypeName.getType().struct);
            methodTypeName.obj = currentMethod;
            Tab.openScope();
            report_info("Obradjuje se funkcija " + methodTypeName.getMetName(), methodTypeName);
        }
    }

    public void visit(MethodDecl methodDecl) {
        if (currentMethod == null)
            return;
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
        int kind = getKind(obj);
        identType[identTypeCnt++] = kind;
        if (varType == null) {
            varType = new int[256];
            varTypeCnt = 0;
        }
        int kindDept = getKindDept(obj);
        varType[varTypeCnt++] = kindDept;
    }

    public void visit(ExprListExpr exprListExpr) {
        localState = LocalState.ArrayDereference;
        if (derefType == null) {
            derefType = new int[256];
            derefTypeCnt = 0;
        }
        derefType[derefTypeCnt++] = arrayDereference;
    }

    public void visit(ExprListExprMatrix exprListExprMatrix) {
        localState = LocalState.MartixDereference;
        if (derefType == null) {
            derefType = new int[256];
            derefTypeCnt = 0;
        }
        derefType[derefTypeCnt++] = matrixDereference;
    }

    public void visit(NoExprListExpr noExprListExpr) {
        if (derefType == null) {
            derefType = new int[256];
            derefTypeCnt = 0;
        }
        derefType[derefTypeCnt++] = variable;
    }

    public void visit(FormParams formParsLst) {
        report_info("Parametar f-je " + formParsLst.getIdentName(), formParsLst);
        tab_insert_check(formParsLst.getIdentName(), formParsLst.getType().struct,
                formParsLst.getLine(), State.VarDeclaration);
    }

    public void visit(IdentTypeInLine identTypeInLine) {
        report_info("Parametar f-je " + identTypeInLine.getIdentName(), identTypeInLine);
        tab_insert_check(identTypeInLine.getIdentName(),
                identTypeInLine.getType().struct, identTypeInLine.getLine(), State.VarDeclaration);
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
        if (varType == null) {
            varType = new int[256];
            varTypeCnt = 0;
        }
        varType[varTypeCnt++] = variable;
        if (derefType == null) {
            derefType = new int[256];
            derefTypeCnt = 0;
        }
        derefType[derefTypeCnt++] = variable;
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
        if (varType == null) {
            varType = new int[256];
            varTypeCnt = 0;
        }
        varType[varTypeCnt++] = variable;
        if (derefType == null) {
            derefType = new int[256];
            derefTypeCnt = 0;
        }
        derefType[derefTypeCnt++] = variable;
    }

    public void visit(FactorChar factorChar) {
        factorChar.struct = Tab.charType;
        assignmentType = Struct.Char;
        if (identType == null) {
            identType = new int[256];
            identTypeCnt = 0;
        }
        identType[identTypeCnt++] = Struct.Char;
        if (varType == null) {
            varType = new int[256];
            varTypeCnt = 0;
        }
        varType[varTypeCnt++] = variable;
        if (derefType == null) {
            derefType = new int[256];
            derefTypeCnt = 0;
        }
        derefType[derefTypeCnt++] = variable;
    }

    public void visit(FactorDes var) {
        var.struct = var.getDesignator().obj.getType();
    }

    public void visit(ReturnStatementExpr returnExpr) {
        if (currentMethod == null)
            return;
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
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
    }

    public void visit(DesignatorStatementMinusMinus designatorStatementMinusMinus) {
        if (designatorStatementMinusMinus.getDesignator().obj.getType().getKind() != Struct.Int) {
            report_error("Greska na liniji " + designatorStatementMinusMinus.getLine() +
                    " : nekompatibilni tipovi u izrazu", null);
        }
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
    }

    public void visit(StatementDesignator statementDesignator) {
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
    }

    public void visit(StatementRead statementRead) {
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
    }

    public void visit(DesignatorStatementEqual assignment) {
        Obj var = Tab.find(assignment.getDesignator().obj.getName());
        designator_equal_check(var, assignment);
        assignmentType = -1;
        identType = null;
        identTypeCnt = 0;
        varType = null;
        varTypeCnt = 0;
        derefType = null;
        derefTypeCnt = 0;
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


}
