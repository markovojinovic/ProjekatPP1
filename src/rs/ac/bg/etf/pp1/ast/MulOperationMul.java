// generated with ast extension for cup
// version 0.8
// 18/5/2023 15:16:15


package rs.ac.bg.etf.pp1.ast;

public class MulOperationMul extends Mulop {

    public MulOperationMul () {
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
        buffer.append("MulOperationMul(\n");

        buffer.append(tab);
        buffer.append(") [MulOperationMul]");
        return buffer.toString();
    }
}
