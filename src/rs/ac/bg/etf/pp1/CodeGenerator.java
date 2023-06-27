package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.LinkedList;
import java.util.Queue;

// TODO: videti da li treba da se dodaje i dodavanje bool konstanti u tabelu simbola

class Expressions {
    public int data;
    public int type;
    public static final int Constant = 0;
    public static final int Variable = 1;
}

public class CodeGenerator extends VisitorAdaptor {

    private int mainPc;
    private String designatorName = "";
    private Queue<Integer>[] operations = new LinkedList[256];
    private Queue<Integer> currentOperations;
    private boolean arrayCreation = false, exprEnter = false, oneOperandDeref = false,
            matrixCreation = false, exprEnterMatrix = false;
    private int dereference = 0, matrixDerederence = 0, indexing = 0;
    private Struct arrayType = null;
    private Obj arrayValue = null, arrayIndex = null;

    public CodeGenerator() {
        for (int i = 0; i < 256; i++) {
            operations[i] = new LinkedList<>();
        }
        currentOperations = operations[indexing];
    }

    public int getMainPc() {
        return mainPc;
    }

    private void afterStatementRestart() {
        arrayValue = null;
        arrayIndex = null;
        indexing = 0;
        dereference = 0;
    }

    public void visit(PrintStmt printStmt) {
        int kind = printStmt.getExpr().struct.getKind();
        if (kind == Struct.Array)
            kind = printStmt.getExpr().struct.getElemType().getKind();
        if (kind == Struct.Array)
            kind = printStmt.getExpr().struct.getElemType().getElemType().getKind();
        if (kind == Struct.Int) {
            Code.loadConst(5);
            Code.put(Code.print);
        } else {
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
        afterStatementRestart();
    }

    public void visit(FactorNum factorNum) {
        Obj con = Tab.insert(Obj.Con, "$", factorNum.struct);
        con.setLevel(0);
        con.setAdr(factorNum.getValue());
        Code.load(con);
        if (!currentOperations.isEmpty()) {
            Code.put(currentOperations.remove());
        }
    }

    public void visit(FactorChar factorChar) {
        Obj con = Tab.insert(Obj.Con, "$$", factorChar.struct);
        con.setLevel(0);
        con.setAdr(factorChar.getValue());
        Code.load(con);
    }

    public void visit(FactorDes factorDes) {
        if (!currentOperations.isEmpty()) {
            Code.put(currentOperations.remove());
        }
    }

    public void visit(MethodTypeName methodTypeName) {
        if ("main".equals(methodTypeName.getMetName())) {
            mainPc = Code.pc;
        }
        methodTypeName.obj.setAdr(Code.pc);

        SyntaxNode methodNode = methodTypeName.getParent();

        CounterVisitor.VarCounter varCounter = new CounterVisitor.VarCounter();
        methodNode.traverseBottomUp(varCounter);

        CounterVisitor.FormParamCounter formParamCounter = new CounterVisitor.FormParamCounter();
        methodNode.traverseBottomUp(formParamCounter);

        Code.put(Code.enter);
        Code.put(formParamCounter.getCount());
        Code.put(formParamCounter.getCount() + varCounter.getCount());
    }

    public void visit(MethodDecl methodDecl) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    public void visit(DesignatorStatementEqual designatorStatementEqual) {
        Obj index = Tab.insert(Obj.Var, "#index$", Tab.intType);
        if (arrayCreation) {
            arrayCreation = false;
            Code.put(Code.newarray);
            if (arrayType == Tab.intType)
                Code.put(1);
            else
                Code.put(0);
            Code.store(designatorStatementEqual.getDesignator().obj);
        } else if (matrixCreation) {
            matrixCreation = false;
            // rowNumber columnNumber ---- stanje na steku u ovom trenutku
            Code.store(index);                                                 // broj kolona - duzina pojedinacnog niza
            Obj index2 = Tab.insert(Obj.Var, "#index2$", Tab.intType);
            Obj curr = Tab.insert(Obj.Var, "#curr$", Tab.intType);
            Code.store(index2);                  // broj redova - duzina niza ciji je svaki elem niz duzine columnNumber
            Code.load(index2);
            Code.put(Code.newarray);
            Code.loadConst(1);
            Code.store(designatorStatementEqual.getDesignator().obj);                  // ne desava se ova linija u run

            Code.loadConst(0);
            Code.store(curr);
            int pocetak = Code.pc;
            Code.load(index2);
            Code.load(curr);
            Code.putFalseJump(Code.gt, 0);
            int falseJump = Code.pc - 2;
            Code.load(index);
            Code.put(Code.newarray);
            if (designatorStatementEqual.getDesignator().obj.getType().getElemType().getElemType().getKind() == Struct.Int)
                Code.loadConst(1);
            else
                Code.put(0);
            Obj arrayAddress = Tab.insert(Obj.Var, "#array$", Tab.intType);
            Code.store(arrayAddress);
            Code.load(designatorStatementEqual.getDesignator().obj);
            Code.load(curr);
            Code.load(arrayAddress);
            Code.put(Code.astore);
            Code.load(curr);
            Code.loadConst(1);
            Code.put(Code.add);
            Code.store(curr);
            Code.putJump(pocetak);
            Code.fixup(falseJump);
        } else if (dereference > 0) { // index value
            Code.load(designatorStatementEqual.getDesignator().obj);
            // index value addr
            Code.put(Code.dup_x2);
            Code.put(Code.pop);
            // addr index value
            Code.put(Code.astore);
            dereference--;
        } else if (matrixDerederence > 0) { // row column value
            matrixDerederence--;
            Code.put(Code.dup_x2);
            Code.put(Code.pop);
            // value row column
            Code.put(Code.dup_x2);
            Code.put(Code.pop);
            //column value row
            Code.load(designatorStatementEqual.getDesignator().obj);
            // column value row addr
            Code.put(Code.dup_x1);
            Code.put(Code.pop);
            // column value addr row
            Code.put(Code.aload);
            // column value addrArray
            Code.put(Code.dup_x2);
            Code.put(Code.pop);
            // addrArray column value
            Code.put(Code.astore);
        } else
            Code.store(designatorStatementEqual.getDesignator().obj);
    }

    public void visit(DesignatorExpression designator) {
        designatorName = designator.getName();
        SyntaxNode parent = designator.getParent();
        if ((DesignatorStatementPlusPlus.class != parent.getClass()
                && DesignatorStatementMinusMinus.class != parent.getClass()) && oneOperandDeref) {
            oneOperandDeref = false;

        }
        if (DesignatorStatementEqual.class != parent.getClass()) {
            if (dereference > 0 && exprEnter) {
                Code.load(designator.obj);
                Code.put(Code.dup_x1);
                Code.put(Code.pop);
                Code.put(Code.aload);
                if (!currentOperations.isEmpty()) {
                    Code.put(currentOperations.remove());
                }
                dereference--;
            } else if (matrixDerederence > 0 && exprEnterMatrix) {      // rowIndex columnIndex -- sadrzaj steka
                Code.put(Code.dup_x1);
                Code.put(Code.pop);
                Code.load(designator.obj);
                Code.put(Code.dup_x1);
                Code.put(Code.pop);
                Code.put(Code.aload);
                Code.put(Code.dup_x1);
                Code.put(Code.pop);
                Code.put(Code.aload);
                if (!currentOperations.isEmpty()) {
                    Code.put(currentOperations.remove());
                }
                matrixDerederence--;
            } else {
                Code.load(designator.obj);
            }
        }
        exprEnter = false;
        exprEnterMatrix = false;
    }

    public void visit(AddOperationPlus addOperationPlus) {
        currentOperations.add(Code.add);
    }

    public void visit(AddOperationMinus addOperationMinus) {
        currentOperations.add(Code.sub);
    }

    public void visit(MulOperationMul mulOperationMul) {
        currentOperations.add(Code.mul);
    }

    public void visit(MulOperationDiv mulOperationDiv) {
        currentOperations.add(Code.div);
    }

    public void visit(MulOperationPercent mulOperationPercent) {
        currentOperations.add(Code.rem);
    }

    public void visit(StatementRead statementRead) {
        Code.put(Code.read);
        Code.store(statementRead.getDesignator().obj);
        afterStatementRestart();
    }

    public void visit(DesignatorStatementPlusPlus designatorStatementPlusPlus) {
        // TODO: prepraviti ovo
        Obj con = Tab.insert(Obj.Con, "$", Tab.intType);
        con.setLevel(0);
        con.setAdr(1);
        Code.load(con);

        Code.put(Code.add);

        if (!oneOperandDeref)
            Code.store(designatorStatementPlusPlus.getDesignator().obj);
        else {
            arrayValue = Tab.insert(Obj.Var, "$$", Tab.intType);
            Code.store(arrayValue);
            Code.load(designatorStatementPlusPlus.getDesignator().obj);
            Code.load(arrayIndex);
            Code.load(arrayValue);
            Code.put(Code.astore);
        }
    }

    public void visit(DesignatorStatementMinusMinus designatorStatementMinusMinus) {
        // TODO: prepraviti ovo
        Obj con = Tab.insert(Obj.Con, "$", Tab.intType);
        con.setLevel(0);
        con.setAdr(1);
        Code.load(con);

        Code.put(Code.sub);

        if (!oneOperandDeref)
            Code.store(designatorStatementMinusMinus.getDesignator().obj);
        else {
            arrayValue = Tab.insert(Obj.Var, "$$", Tab.intType);
            Code.store(arrayValue);
            Code.load(designatorStatementMinusMinus.getDesignator().obj);
            Code.load(arrayIndex);
            Code.load(arrayValue);
            Code.put(Code.astore);
        }
    }

    public void visit(MinusExpression minusExpression) {
        currentOperations.add(Code.neg);
    }

    public void visit(FactorNewExpr factorNewExpr) {
        arrayCreation = true;
        arrayType = factorNewExpr.getType().struct;
    }

    public void visit(ExprListExpr exprListExpr) {
        dereference++;
        exprEnter = true;
        oneOperandDeref = true;
    }

    public void visit(ExprListExprMatrix exprListExprMatrix) {
        matrixDerederence++;
        exprEnterMatrix = true;
    }

    public void visit(FactorNewTypeExpr factorNewTypeExpr) {
        matrixCreation = true;
        arrayType = factorNewTypeExpr.getType().struct;
    }

    public void visit(StatementDesignator statementDesignator) {
        afterStatementRestart();
    }

    public void visit(LsquareClass lsquareClass){
        indexing++;
        currentOperations = operations[indexing];
    }

    public void visit(RsquareClass rsquareClass){
        indexing--;
        currentOperations = operations[indexing];
    }

}
