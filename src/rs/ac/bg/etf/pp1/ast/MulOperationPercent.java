// generated with ast extension for cup
// version 0.8
// 25/4/2023 18:48:40


package rs.ac.bg.etf.pp1.ast;

public class MulOperationPercent extends Mulop {

    public MulOperationPercent () {
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
        buffer.append("MulOperationPercent(\n");

        buffer.append(tab);
        buffer.append(") [MulOperationPercent]");
        return buffer.toString();
    }
}
