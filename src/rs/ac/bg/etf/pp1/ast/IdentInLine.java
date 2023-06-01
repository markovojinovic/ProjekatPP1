// generated with ast extension for cup
// version 0.8
// 1/5/2023 23:34:22


package rs.ac.bg.etf.pp1.ast;

public class IdentInLine implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String identName;

    public IdentInLine (String identName) {
        this.identName=identName;
    }

    public String getIdentName() {
        return identName;
    }

    public void setIdentName(String identName) {
        this.identName=identName;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("IdentInLine(\n");

        buffer.append(" "+tab+identName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentInLine]");
        return buffer.toString();
    }
}
