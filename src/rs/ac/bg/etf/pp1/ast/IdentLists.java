// generated with ast extension for cup
// version 0.8
// 27/5/2023 14:34:27


package rs.ac.bg.etf.pp1.ast;

public class IdentLists extends IdentList {

    private IdentList IdentList;
    private IdentTypeInLine IdentTypeInLine;

    public IdentLists (IdentList IdentList, IdentTypeInLine IdentTypeInLine) {
        this.IdentList=IdentList;
        if(IdentList!=null) IdentList.setParent(this);
        this.IdentTypeInLine=IdentTypeInLine;
        if(IdentTypeInLine!=null) IdentTypeInLine.setParent(this);
    }

    public IdentList getIdentList() {
        return IdentList;
    }

    public void setIdentList(IdentList IdentList) {
        this.IdentList=IdentList;
    }

    public IdentTypeInLine getIdentTypeInLine() {
        return IdentTypeInLine;
    }

    public void setIdentTypeInLine(IdentTypeInLine IdentTypeInLine) {
        this.IdentTypeInLine=IdentTypeInLine;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IdentList!=null) IdentList.accept(visitor);
        if(IdentTypeInLine!=null) IdentTypeInLine.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdentList!=null) IdentList.traverseTopDown(visitor);
        if(IdentTypeInLine!=null) IdentTypeInLine.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdentList!=null) IdentList.traverseBottomUp(visitor);
        if(IdentTypeInLine!=null) IdentTypeInLine.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentLists(\n");

        if(IdentList!=null)
            buffer.append(IdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdentTypeInLine!=null)
            buffer.append(IdentTypeInLine.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentLists]");
        return buffer.toString();
    }
}
