// generated with ast extension for cup
// version 0.8
// 30/4/2023 14:53:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorListRec extends DesignatorListMany {

    private DesignatorListMany DesignatorListMany;
    private DesignatorList DesignatorList;

    public DesignatorListRec (DesignatorListMany DesignatorListMany, DesignatorList DesignatorList) {
        this.DesignatorListMany=DesignatorListMany;
        if(DesignatorListMany!=null) DesignatorListMany.setParent(this);
        this.DesignatorList=DesignatorList;
        if(DesignatorList!=null) DesignatorList.setParent(this);
    }

    public DesignatorListMany getDesignatorListMany() {
        return DesignatorListMany;
    }

    public void setDesignatorListMany(DesignatorListMany DesignatorListMany) {
        this.DesignatorListMany=DesignatorListMany;
    }

    public DesignatorList getDesignatorList() {
        return DesignatorList;
    }

    public void setDesignatorList(DesignatorList DesignatorList) {
        this.DesignatorList=DesignatorList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorListMany!=null) DesignatorListMany.accept(visitor);
        if(DesignatorList!=null) DesignatorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorListMany!=null) DesignatorListMany.traverseTopDown(visitor);
        if(DesignatorList!=null) DesignatorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorListMany!=null) DesignatorListMany.traverseBottomUp(visitor);
        if(DesignatorList!=null) DesignatorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorListRec(\n");

        if(DesignatorListMany!=null)
            buffer.append(DesignatorListMany.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorList!=null)
            buffer.append(DesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorListRec]");
        return buffer.toString();
    }
}
