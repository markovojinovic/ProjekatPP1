// generated with ast extension for cup
// version 0.8
// 23/5/2023 22:59:23


package rs.ac.bg.etf.pp1.ast;

public class AddOperationPlus extends Addop {

    public AddOperationPlus () {
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
        buffer.append("AddOperationPlus(\n");

        buffer.append(tab);
        buffer.append(") [AddOperationPlus]");
        return buffer.toString();
    }
}
