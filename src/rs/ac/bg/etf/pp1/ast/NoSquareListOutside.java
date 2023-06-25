// generated with ast extension for cup
// version 0.8
// 25/5/2023 21:46:37


package rs.ac.bg.etf.pp1.ast;

public class NoSquareListOutside extends SquareList {

    public NoSquareListOutside () {
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
        buffer.append("NoSquareListOutside(\n");

        buffer.append(tab);
        buffer.append(") [NoSquareListOutside]");
        return buffer.toString();
    }
}
