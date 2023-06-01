// generated with ast extension for cup
// version 0.8
// 1/5/2023 23:34:22


package rs.ac.bg.etf.pp1.ast;

public class DesignatorExpression extends Designator {

    private String name;
    private ExprList ExprList;

    public DesignatorExpression (String name, ExprList ExprList) {
        this.name=name;
        this.ExprList=ExprList;
        if(ExprList!=null) ExprList.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public ExprList getExprList() {
        return ExprList;
    }

    public void setExprList(ExprList ExprList) {
        this.ExprList=ExprList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprList!=null) ExprList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprList!=null) ExprList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprList!=null) ExprList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorExpression(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(ExprList!=null)
            buffer.append(ExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorExpression]");
        return buffer.toString();
    }
}
