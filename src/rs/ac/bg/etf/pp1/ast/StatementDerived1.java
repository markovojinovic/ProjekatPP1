// generated with ast extension for cup
// version 0.8
// 1/5/2023 23:34:22


package rs.ac.bg.etf.pp1.ast;

public class StatementDerived1 extends Statement {

    public StatementDerived1 () {
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
        buffer.append("StatementDerived1(\n");

        buffer.append(tab);
        buffer.append(") [StatementDerived1]");
        return buffer.toString();
    }
}
