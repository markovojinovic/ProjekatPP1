package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
    // TODO: Dodati da se konstante ne smeju menjati posle u kodu

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
    boolean arrayDereference = false, newArrayDereference = false;

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

    public void visit(VarDeclarationArray varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
        Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
        varDeclType = null;
    }

    public void visit(ConstVarDeclaration varDecl) {                    // TODO: samo bool const i int dodela ne detektuje
        if (varDecl.getType().struct.assignableTo(varDecl.getFactor().struct)) {
            varDeclCount++;
            report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
            Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
            varDeclType = null;
        } else {
            report_error("Greska : Tipovi nisu kompatibilni ", varDecl);
        }
    }

    public void visit(VarDeclarationEqual varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
        Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
        varDeclType = null;
    }

    public void visit(IdentInLine varDecl) {
        varDeclCount++;
        report_info("Deklarisana promenljiva " + varDecl.getIdentName(), varDecl);
        Obj varNode = Tab.insert(Obj.Var, varDecl.getIdentName(), varDeclType);
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
            if (type.getTypeName().equals("bool"))                     // TODO: proveriti ove dve konverzije ispod
                type.struct = Tab.intType;
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
        identType[identTypeCnt++] = obj.getType().getKind();
    }

    public void visit(ExprListExpr exprListExpr) {
        arrayDereference = true;
    }

    public void visit(FormParams formParsLst) {
        report_info("Parametar f-je " + formParsLst.getIdentName(), formParsLst);
        Obj varNode = Tab.insert(Obj.Var, formParsLst.getIdentName(), formParsLst.getType().struct);
    }

    public void visit(IdentTypeInLine identTypeInLine) {
        report_info("Parametar f-je " + identTypeInLine.getIdentName(), identTypeInLine);
        Obj varNode = Tab.insert(Obj.Var, identTypeInLine.getIdentName(), identTypeInLine.getType().struct);
    }

    public void visit(Terminator term) {
        term.struct = term.getFactor().struct;
    }

    public void visit(Expression termExpr) {
        termExpr.struct = termExpr.getTerm().struct;
    }

    public void visit(AddOperationTerminatorRec addExpr) {        // TODO: proveriti da li radi ova provera
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
        if (identType != null)
            identType[identTypeCnt++] = 1;
    }

    public void visit(FactorBool factorBool) {               // TODO: naci resenje za bool vrednosti
        factorBool.struct = new Struct(Struct.Bool);
        assignmentType = Struct.Bool;
    }

    public void visit(FactorChar factorChar) {
        factorChar.struct = Tab.charType;
        assignmentType = Struct.Char;
        if (identType != null)
            identType[identTypeCnt++] = 2;
    }

    public void visit(FactorDes var) {
        var.struct = var.getDesignator().obj.getType();
    }

    public void visit(ReturnStatementExpr returnExpr) {          // TODO: prepraviti ispitivanje tipa povratne vrednosti
        returnFound = true;
        Struct currMethType = currentMethod.getType();
        if (!currMethType.compatibleWith(returnExpr.getExpr().struct)) {
            report_error("Greska na liniji " + returnExpr.getLine() + " : " +
                    "tip izraza u \"return\" naredbi ne slaze se sa tipom povratne vrednosti funkcije " +
                    currentMethod.getName(), null);
        }
        if (identType != null) {
            int startType = identType[0];
            for (int type : identType) {
                if (type == 0)
                    break;
                if (type != startType)
                    report_error("Greska na liniji " + returnExpr.getLine() +
                            " : nekompatibilni tipovi u izrazu", null);
            }
            identType = null;
            identTypeCnt = 0;
        }
    }

    public void visit(DesignatorStatementArray designatorStatementArray) {
        if (identType != null) {
            int startType = identType[0];
            for (int type : identType) {
                if (type == 0)
                    break;
                if (type != startType)
                    report_error("Greska na liniji " + designatorStatementArray.getLine() +
                            " : nekompatibilni tipovi u izrazu", null);
            }
            identType = null;
            identTypeCnt = 0;
        }
    }

    public void visit(DesignatorStatementPlusPlus designatorStatementPlusPlus) {
        if (designatorStatementPlusPlus.getDesignator().obj.getType().getKind() != 1) {
            report_error("Greska na liniji " + designatorStatementPlusPlus.getLine() +
                    " : nekompatibilni tipovi u izrazu", null);
        }
        identType = null;
        identTypeCnt = 0;
    }

    public void visit(DesignatorStatementMinusMinus designatorStatementMinusMinus) {
        if (designatorStatementMinusMinus.getDesignator().obj.getType().getKind() != 1) {
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

    public void visit(DesignatorStatementEqual assignment) {    // TODO: ne radi kako treba za matricu
        boolean noDec = false, noCom = false;
        Obj var = Tab.find(assignment.getDesignator().obj.getName());
        if (var == Tab.noObj)
            noDec = true;
        else if (assignmentType == 19) {
            Obj rVar = Tab.find(assignmentName);
            if (rVar == Tab.noObj)
                noDec = true;
            else if (rVar.getType().getKind() != var.getType().getKind())
                noCom = true;
        } else if (var.getType().getKind() != assignmentType)
            noCom = true;

        if (identType != null) {
            if (arrayDereference) {
                if (assignment.getLine() == 53) {
                    boolean nesto = false;
                }
                arrayDereference = false;
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
                if (identType[firstNil - 1] != identType[firstNil - 2])
                    report_error("Greska na liniji " + assignment.getLine() + " : " +
                            "nekompatibilni tipovi u dodeli vrednosti! ", null);
                if (!correct)
                    report_error("Greska na liniji " + assignment.getLine() + " : " +
                            "nekompatibilni tipovi u dereferenciranju niza!", null);

            } else if (newArrayDereference) {
                newArrayDereference = false;
                int type = assignment.getDesignator().obj.getType().getKind();
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
                    "nekompatibilni tipovi u dodeli vrednosti! ", null);

        assignmentType = -1;
    }

    public void visit(FactorNewExpr factorNewExpr) {
        assignmentType = factorNewExpr.getType().struct.getKind();
        newArrayDereference = true;
        arrayDereference = false;
    }

    public void visit(FactorExpr factorExpr) {
        if (identType != null) {
            int startType = identType[0];
            for (int type : identType) {
                if (type == 0)
                    break;
                if (type != startType)
                    report_error("Greska na liniji " + factorExpr.getLine() +
                            " : nekompatibilni tipovi u izrazu", null);
            }
            identType = null;
            identTypeCnt = 0;
        }
    }

    public void visit(FactorNewTypeExpr factorNewTypeExpr) {
        assignmentType = factorNewTypeExpr.getType().struct.getKind();
        newArrayDereference = true;
        arrayDereference = false;
        if (identType != null) {
            int startType = identType[0];
            for (int type : identType) {
                if (type == 0)
                    break;
                if (type != startType)
                    report_error("Greska na liniji " + factorNewTypeExpr.getLine() +
                            " : nekompatibilni tipovi u izrazu", null);
            }
            identType = null;
            identTypeCnt = 0;
        }
    }

}
