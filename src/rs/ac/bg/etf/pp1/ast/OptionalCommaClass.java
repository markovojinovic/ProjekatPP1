// generated with ast extension for cup
// version 0.8
// 21/5/2023 12:8:32


package rs.ac.bg.etf.pp1.ast;

public class OptionalCommaClass extends OptionalComma {

    public OptionalCommaClass () {
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
        buffer.append("OptionalCommaClass(\n");

        buffer.append(tab);
        buffer.append(") [OptionalCommaClass]");
        return buffer.toString();
    }
}