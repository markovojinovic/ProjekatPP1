// generated with ast extension for cup
// version 0.8
// 1/5/2023 23:28:53


package rs.ac.bg.etf.pp1.ast;

public class AddOperationMinus extends Addop {

    public AddOperationMinus () {
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
        buffer.append("AddOperationMinus(\n");

        buffer.append(tab);
        buffer.append(") [AddOperationMinus]");
        return buffer.toString();
    }
}
