// generated with ast extension for cup
// version 0.8
// 17/5/2023 0:43:47


package rs.ac.bg.etf.pp1.ast;

public class MulOperationDiv extends Mulop {

    public MulOperationDiv () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MulOperationDiv(\n");

        buffer.append(tab);
        buffer.append(") [MulOperationDiv]");
        return buffer.toString();
    }
}
