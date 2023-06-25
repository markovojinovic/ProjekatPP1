// generated with ast extension for cup
// version 0.8
// 25/5/2023 22:47:49


package rs.ac.bg.etf.pp1.ast;

public class NoTypeIdentSquareList extends TypeIdentSquareList {

    public NoTypeIdentSquareList () {
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
        buffer.append("NoTypeIdentSquareList(\n");

        buffer.append(tab);
        buffer.append(") [NoTypeIdentSquareList]");
        return buffer.toString();
    }
}
