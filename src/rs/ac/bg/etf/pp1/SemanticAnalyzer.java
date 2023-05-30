package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

    int printCallCount = 0;
    int varDeclCount = 0;
    Obj currentMethod = null;
    boolean returnFound = false;
    boolean errorDetected = false;
    int nVars;
    Struct varDeclType = null;

    Logger log = Logger.getLogger(getClass());

    public void report_error(String message, SyntaxNode info) {
        errorDetected = true;
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.error(msg.toString());
    }

    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.info(msg.toString());
    }

    public void visit(VarDeclarationArray varDecl){
        varDeclCount++;
        report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
        Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
        varDeclType = null;
    }

    public void visit(ConstVarDeclaration varDecl){
        varDeclCount++;
        report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
        Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
        varDeclType = null;
    }

    public void visit(VarDeclarationEqual varDecl){
        varDeclCount++;
        report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
        Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
        varDeclType = null;
    }

    public void visit(IdentInLine varDecl){
        varDeclCount++;
        report_info("Deklarisana promenljiva "+ varDecl.getIdentName(), varDecl);
        Obj varNode = Tab.insert(Obj.Var, varDecl.getIdentName(), varDeclType);
    }

    public void visit(PrintStmt print) {
        printCallCount++;
    }

    public void visit(ProgName progName){
        progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
        Tab.openScope();
    }

    public void visit(Program program){
        nVars = Tab.currentScope.getnVars();
        Tab.chainLocalSymbols(program.getProgName().obj);
        Tab.closeScope();
    }

    public void visit(TypeIdent type){
        Obj typeNode = Tab.find(type.getTypeName());
        if(typeNode == Tab.noObj){
            report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
            type.struct = Tab.noType;
        }else{
            if(Obj.Type == typeNode.getKind()){
                type.struct = typeNode.getType();
                varDeclType = typeNode.getType();
            }else{
                report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
                type.struct = Tab.noType;
            }
        }
    }

    public void visit(MethodTypeName methodTypeName){
        currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMetName(), methodTypeName.getType().struct);
        methodTypeName.obj = currentMethod;
        Tab.openScope();
        report_info("Obradjuje se funkcija " + methodTypeName.getMetName(), methodTypeName);
    }

    public void visit(MethodDecl methodDecl){
        if(!returnFound && currentMethod.getType() != Tab.noType){
            report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " +
                    currentMethod.getName() + " nema return iskaz!", null);
        }
        Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();

        returnFound = false;
        currentMethod = null;
    }

    public void visit(DesignatorExpression designator){
        Obj obj = Tab.find(designator.getName());
        if(obj == Tab.noObj){
            report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getName() +
                                            " nije deklarisano! ", null);
        }
        designator.obj = obj;
    }

    public void visit(FormParams formParsLst){
        report_info("Parametar f-je "+ formParsLst.getIdentName(), formParsLst);
        Obj varNode = Tab.insert(Obj.Var, formParsLst.getIdentName(), formParsLst.getType().struct);
    }

    public void visit(IdentTypeInLine identTypeInLine){
        report_info("Parametar f-je "+ identTypeInLine.getIdentName(), identTypeInLine);
        Obj varNode = Tab.insert(Obj.Var, identTypeInLine.getIdentName(), identTypeInLine.getType().struct);
    }


    public void visit(FuncCall funcCall){                       // TODO: videti da li idu funkcije u ovu gramatiku
        Obj func = funcCall.getDesignator().obj;
        if(Obj.Meth == func.getKind()){
            report_info("Pronadjen poziv funkcije " + func.getName() +
                                            " na liniji " + funcCall.getLine(), null);
            funcCall.struct = func.getType();
        }else{
            report_error("Greska na liniji " + funcCall.getLine()+" : ime " +
                                            func.getName() + " nije funkcija!", null);
            funcCall.struct = Tab.noType;
        }
    }

    public void visit(Terminator term){
        term.struct = term.getFactor().struct;
    }

    public void visit(Expression termExpr){
        termExpr.struct = termExpr.getTerm().struct;
    }

    public void visit(AddOperationTerminatorRec addExpr){
        Struct te = addExpr.getAddopTerm().struct;
        Struct t = addExpr.getTerm().struct;
        if(te == null || t == null){
            return;
        }
        if(te.equals(t) && te == Tab.intType){
            addExpr.struct = te;
        }else{
            report_error("Greska na liniji "+ addExpr.getLine() +
                    " : nekompatibilni tipovi u izrazu za sabiranje.", null);
            addExpr.struct = Tab.noType;
        }
    }

    public void visit(FactorNum cnst){
        cnst.struct = Tab.intType;
    }

    public void visit(FactorDes var){
        var.struct = var.getDesignator().obj.getType();
    }

    public void visit(ReturnStatementExpr returnExpr){          // TODO: prepraviti ispitivanje tipa povratne vrednosti
        returnFound = true;
        Struct currMethType = currentMethod.getType();
        if(!currMethType.compatibleWith(returnExpr.getExpr().struct)){
            report_error("Greska na liniji " + returnExpr.getLine() + " : " +
                    "tip izraza u \"return\" naredbi ne slaze se sa tipom povratne vrednosti funkcije " +
                    currentMethod.getName(), null);
        }
    }

    public void visit(ReturnStatementOnly returnExpr){
        returnFound = true;
        Struct currMethType = currentMethod.getType();
        if(currMethType.getKind() != Struct.None){
            report_error("Greska na liniji " + returnExpr.getLine() + " : " +
                    "tip izraza u \"return\" naredbi ne slaze se sa tipom povratne vrednosti funkcije " +
                    currentMethod.getName(), null);
        }
    }

    public void visit(NoReturnStatement returnExpr){
        returnFound = true;
        Struct currMethType = currentMethod.getType();
        if(currMethType.getKind() != Struct.None){
            report_error("Greska na liniji " + returnExpr.getLine() + " : " +
                    "Nedostatak \"return\" naredbe u funkciji " + currentMethod.getName(), null);
        }
    }

    public void visit(DesignatorStatementEqual assignment){
        if(assignment.getExpr().struct == null){
            return;
        }
        if(!assignment.getExpr().struct.assignableTo(assignment.getDesignator().obj.getType()))
            report_error("Greska na liniji " + assignment.getLine() + " : " +
                            "nekompatibilni tipovi u dodeli vrednosti! ", null);
    }


    public boolean passed(){
        return !errorDetected;
    }

}
