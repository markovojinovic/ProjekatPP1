// generated with ast extension for cup
// version 0.8
// 27/5/2023 14:34:27


package rs.ac.bg.etf.pp1.ast;

public class ListOfIdentInLine extends IdentInLineList {

    private IdentInLineList IdentInLineList;
    private IdentInLine IdentInLine;

    public ListOfIdentInLine (IdentInLineList IdentInLineList, IdentInLine IdentInLine) {
        this.IdentInLineList=IdentInLineList;
        if(IdentInLineList!=null) IdentInLineList.setParent(this);
        this.IdentInLine=IdentInLine;
        if(IdentInLine!=null) IdentInLine.setParent(this);
    }

    public IdentInLineList getIdentInLineList() {
        return IdentInLineList;
    }

    public void setIdentInLineList(IdentInLineList IdentInLineList) {
        this.IdentInLineList=IdentInLineList;
    }

    public IdentInLine getIdentInLine() {
        return IdentInLine;
    }

    public void setIdentInLine(IdentInLine IdentInLine) {
        this.IdentInLine=IdentInLine;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IdentInLineList!=null) IdentInLineList.accept(visitor);
        if(IdentInLine!=null) IdentInLine.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdentInLineList!=null) IdentInLineList.traverseTopDown(visitor);
        if(IdentInLine!=null) IdentInLine.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdentInLineList!=null) IdentInLineList.traverseBottomUp(visitor);
        if(IdentInLine!=null) IdentInLine.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListOfIdentInLine(\n");

        if(IdentInLineList!=null)
            buffer.append(IdentInLineList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdentInLine!=null)
            buffer.append(IdentInLine.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListOfIdentInLine]");
        return buffer.toString();
    }
}
