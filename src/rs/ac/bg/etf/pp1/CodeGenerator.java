package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FactorChar;
import rs.ac.bg.etf.pp1.ast.FactorNum;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPc;

    public int getMainPc() {
        return mainPc;
    }

    public void visit(PrintStmt printStmt) {
        if (printStmt.getExpr().struct == Tab.intType) {
            Code.loadConst(5);
            Code.put(Code.print);
        } else {
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
    }

    public void visit(FactorNum factorNum) {
        Obj con = Tab.insert(Obj.Con, "$", factorNum.struct);
        con.setLevel(0);
        con.setAdr(factorNum.getValue());
        Code.load(con);
    }

    public void visit(FactorChar factorChar) {
        Obj con = Tab.insert(Obj.Con, "$", factorChar.struct);
        con.setLevel(0);
        con.setAdr(factorChar.getValue());
        Code.load(con);
    }

}
