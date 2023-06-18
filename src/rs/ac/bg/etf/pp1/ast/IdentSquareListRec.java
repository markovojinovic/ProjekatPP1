// generated with ast extension for cup
// version 0.8
// 17/5/2023 0:43:47


package rs.ac.bg.etf.pp1.ast;

public class IdentSquareListRec extends IdentSquareList {

    private IdentSquareList IdentSquareList;
    private IdentInLine IdentInLine;
    private SquareList SquareList;

    public IdentSquareListRec (IdentSquareList IdentSquareList, IdentInLine IdentInLine, SquareList SquareList) {
        this.IdentSquareList=IdentSquareList;
        if(IdentSquareList!=null) IdentSquareList.setParent(this);
        this.IdentInLine=IdentInLine;
        if(IdentInLine!=null) IdentInLine.setParent(this);
        this.SquareList=SquareList;
        if(SquareList!=null) SquareList.setParent(this);
    }

    public IdentSquareList getIdentSquareList() {
        return IdentSquareList;
    }

    public void setIdentSquareList(IdentSquareList IdentSquareList) {
        this.IdentSquareList=IdentSquareList;
    }

    public IdentInLine getIdentInLine() {
        return IdentInLine;
    }

    public void setIdentInLine(IdentInLine IdentInLine) {
        this.IdentInLine=IdentInLine;
    }

    public SquareList getSquareList() {
        return SquareList;
    }

    public void setSquareList(SquareList SquareList) {
        this.SquareList=SquareList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IdentSquareList!=null) IdentSquareList.accept(visitor);
        if(IdentInLine!=null) IdentInLine.accept(visitor);
        if(SquareList!=null) SquareList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdentSquareList!=null) IdentSquareList.traverseTopDown(visitor);
        if(IdentInLine!=null) IdentInLine.traverseTopDown(visitor);
        if(SquareList!=null) SquareList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdentSquareList!=null) IdentSquareList.traverseBottomUp(visitor);
        if(IdentInLine!=null) IdentInLine.traverseBottomUp(visitor);
        if(SquareList!=null) SquareList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentSquareListRec(\n");

        if(IdentSquareList!=null)
            buffer.append(IdentSquareList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdentInLine!=null)
            buffer.append(IdentInLine.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SquareList!=null)
            buffer.append(SquareList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentSquareListRec]");
        return buffer.toString();
    }
}
