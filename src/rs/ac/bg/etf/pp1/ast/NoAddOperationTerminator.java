// generated with ast extension for cup
// version 0.8
// 23/5/2023 1:1:30


package rs.ac.bg.etf.pp1.ast;

public class NoAddOperationTerminator extends AddopTerm {

    public NoAddOperationTerminator () {
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
        buffer.append("NoAddOperationTerminator(\n");

        buffer.append(tab);
        buffer.append(") [NoAddOperationTerminator]");
        return buffer.toString();
    }
}
