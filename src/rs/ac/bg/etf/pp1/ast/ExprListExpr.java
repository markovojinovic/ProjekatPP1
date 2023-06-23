// generated with ast extension for cup
// version 0.8
// 23/5/2023 1:1:30


package rs.ac.bg.etf.pp1.ast;

public class ExprListExpr extends ExprList {

    private Lsqare Lsqare;
    private Expr Expr;
    private Rsqare Rsqare;

    public ExprListExpr (Lsqare Lsqare, Expr Expr, Rsqare Rsqare) {
        this.Lsqare=Lsqare;
        if(Lsqare!=null) Lsqare.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.Rsqare=Rsqare;
        if(Rsqare!=null) Rsqare.setParent(this);
    }

    public Lsqare getLsqare() {
        return Lsqare;
    }

    public void setLsqare(Lsqare Lsqare) {
        this.Lsqare=Lsqare;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Rsqare getRsqare() {
        return Rsqare;
    }

    public void setRsqare(Rsqare Rsqare) {
        this.Rsqare=Rsqare;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Lsqare!=null) Lsqare.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(Rsqare!=null) Rsqare.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Lsqare!=null) Lsqare.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(Rsqare!=null) Rsqare.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Lsqare!=null) Lsqare.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(Rsqare!=null) Rsqare.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprListExpr(\n");

        if(Lsqare!=null)
            buffer.append(Lsqare.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Rsqare!=null)
            buffer.append(Rsqare.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprListExpr]");
        return buffer.toString();
    }
}
