package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.LinkedList;
import java.util.Queue;

// TODO: videti da li treba da se dodaje i dodavanje bool konstanti u tabelu simbola
// TODO: idalje postoji bag kod linije niz[niz[jedan]] = niz[niz[0]] * 3;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPc;
    private String designatorName = "";
    private Queue<Integer>[] operations = new LinkedList[256];
    private Queue<Integer>[] expressions = new LinkedList[256];
    private Queue<Integer> currentOperations;
    private Queue<Integer> currentExpressions;
    private boolean arrayCreation = false, exprEnter = false, oneOperandDeref = false;
    private int indexing = 0, dereference = 0;
    private Struct arrayType = null;
    private Obj arrayValue = null, arrayIndex = null;

    public CodeGenerator() {
        for (int i = 0; i < 256; i++) {
            operations[i] = new LinkedList<>();
            expressions[i] = new LinkedList<>();
        }
        currentOperations = operations[indexing];
        currentExpressions = expressions[indexing];
    }

    public int getMainPc() {
        return mainPc;
    }

    private void afterStatementRestart() {
        arrayValue = null;
        arrayIndex = null;
    }

    public void visit(PrintStmt printStmt) {
        int kind = printStmt.getExpr().struct.getKind();
        if (kind == Struct.Array)
            kind = printStmt.getExpr().struct.getElemType().getKind();
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
        if (indexing > 0) {
            currentExpressions.add(con.getAdr());
        } else {
            Code.load(con);
            if (!currentOperations.isEmpty()) {
                Code.put(currentOperations.remove());
            }
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
        if (designatorStatementEqual.getLine() == 20) {
            boolean nesto = false;
        }
        Obj index = Tab.insert(Obj.Var, "#index$", Tab.intType);
        if (arrayCreation) {
            arrayCreation = false;
            Code.put(Code.newarray);
            if (arrayType == Tab.intType)
                Code.put(1);
            else
                Code.put(0);
            Code.store(designatorStatementEqual.getDesignator().obj);
        } else if (dereference > 0) {
            Obj arrayValue = Tab.insert(Obj.Var, "#value$", Tab.intType);
            Code.store(arrayValue);
            Code.store(index);
            Code.load(designatorStatementEqual.getDesignator().obj);
            Code.load(index);
            Code.load(arrayValue);
            Code.put(Code.astore);
            dereference--;
        } else
            Code.store(designatorStatementEqual.getDesignator().obj);
    }

    public void visit(DesignatorExpression designator) {
        designatorName = designator.getName();
        SyntaxNode parent = designator.getParent();
        boolean pass = false;
        if ((DesignatorStatementPlusPlus.class != parent.getClass()
                && DesignatorStatementMinusMinus.class != parent.getClass()) && oneOperandDeref) {
            oneOperandDeref = false;

        }
        if(DesignatorStatementPlusPlus.class == parent.getClass()
                || DesignatorStatementMinusMinus.class == parent.getClass())
            pass = true;
        if (DesignatorStatementEqual.class != parent.getClass()) {
            if (dereference > 0 && exprEnter) {
                Obj index = Tab.insert(Obj.Var, "#index$", Tab.intType);
                Code.store(index);
                if (pass) {
                    arrayIndex = Tab.insert(Obj.Var, "#index$", Tab.intType);
                    arrayIndex.setAdr(index.getAdr());
                }
                Code.load(designator.obj);
                Code.load(index);
                Code.put(Code.aload);
                if (indexing > 0) {
                    Obj curr = Tab.insert(Obj.Var, "$$", Tab.intType);      // ovde su problemi
                    Code.store(curr);
                    currentExpressions.add(curr.getAdr());
                } else if (!currentOperations.isEmpty())
                    Code.put(currentOperations.remove());
                dereference--;
            } else {
                if (indexing > 0)
                    currentExpressions.add(designator.obj.getAdr());
                else
                    Code.load(designator.obj);
            }
        }
        if (exprEnter)
            exprEnter = false;
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

    public void visit(FactorExpr factorExpr) {
        // TODO: dodati kod ovde za obradu izraza sa zagradama
    }

    public void visit(StatementRead statementRead) {
        Code.put(Code.read);
        Code.store(statementRead.getDesignator().obj);
        afterStatementRestart();
    }

    public void visit(DesignatorStatementPlusPlus designatorStatementPlusPlus) {
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

    public void visit(LsquareClass lsquareClass) {
        indexing++;
        currentOperations = operations[indexing];
        currentExpressions = expressions[indexing];
    }

    public void visit(RsquareClass rsquareClass) {
        if (currentExpressions.isEmpty())
            return;
        Obj con = Tab.insert(Obj.Con, "$", Tab.intType);
        con.setAdr(currentExpressions.remove());
        Code.load(con);
        while (!currentExpressions.isEmpty() && !currentOperations.isEmpty()) {
            con = Tab.insert(Obj.Con, "$", Tab.intType);
            con.setAdr(currentExpressions.remove());
            Code.load(con);
            Code.put(currentOperations.remove());
        }
        indexing--;
        currentOperations = operations[indexing];
        currentExpressions = expressions[indexing];
    }

    public void visit(ExprListExpr exprListExpr) {
        dereference++;
        exprEnter = true;
        oneOperandDeref = true;
    }

    public void visit(StatementDesignator statementDesignator) {
        afterStatementRestart();
    }

}
