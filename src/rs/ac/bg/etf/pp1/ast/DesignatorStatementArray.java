// generated with ast extension for cup
// version 0.8
// 1/5/2023 23:28:53


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementArray extends DesignatorStatement {

    private DesignatorList DesignatorList;
    private DesignatorListMany DesignatorListMany;
    private Designator Designator;

    public DesignatorStatementArray (DesignatorList DesignatorList, DesignatorListMany DesignatorListMany, Designator Designator) {
        this.DesignatorList=DesignatorList;
        if(DesignatorList!=null) DesignatorList.setParent(this);
        this.DesignatorListMany=DesignatorListMany;
        if(DesignatorListMany!=null) DesignatorListMany.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public DesignatorList getDesignatorList() {
        return DesignatorList;
    }

    public void setDesignatorList(DesignatorList DesignatorList) {
        this.DesignatorList=DesignatorList;
    }

    public DesignatorListMany getDesignatorListMany() {
        return DesignatorListMany;
    }

    public void setDesignatorListMany(DesignatorListMany DesignatorListMany) {
        this.DesignatorListMany=DesignatorListMany;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorList!=null) DesignatorList.accept(visitor);
        if(DesignatorListMany!=null) DesignatorListMany.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorList!=null) DesignatorList.traverseTopDown(visitor);
        if(DesignatorListMany!=null) DesignatorListMany.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorList!=null) DesignatorList.traverseBottomUp(visitor);
        if(DesignatorListMany!=null) DesignatorListMany.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementArray(\n");

        if(DesignatorList!=null)
            buffer.append(DesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorListMany!=null)
            buffer.append(DesignatorListMany.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementArray]");
        return buffer.toString();
    }
}
