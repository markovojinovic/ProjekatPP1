// generated with ast extension for cup
// version 0.8
// 25/4/2023 19:21:43


package rs.ac.bg.etf.pp1.ast;

public class MinusExpression extends Minus {

    public MinusExpression () {
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
        buffer.append("MinusExpression(\n");

        buffer.append(tab);
        buffer.append(") [MinusExpression]");
        return buffer.toString();
    }
}