// generated with ast extension for cup
// version 0.8
// 27/5/2023 18:25:41


package rs.ac.bg.etf.pp1.ast;

public class NoMinusExpression extends Minus {

    public NoMinusExpression () {
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
        buffer.append("NoMinusExpression(\n");

        buffer.append(tab);
        buffer.append(") [NoMinusExpression]");
        return buffer.toString();
    }
}
