// generated with ast extension for cup
// version 0.8
// 23/5/2023 22:59:23


package rs.ac.bg.etf.pp1.ast;

public class FactorNewTypeExpr extends Factor {

    private Type Type;
    private Lsqare Lsqare;
    private Expr Expr;
    private Rsqare Rsqare;
    private Lsqare Lsqare1;
    private Expr Expr2;
    private Rsqare Rsqare3;

    public FactorNewTypeExpr (Type Type, Lsqare Lsqare, Expr Expr, Rsqare Rsqare, Lsqare Lsqare1, Expr Expr2, Rsqare Rsqare3) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.Lsqare=Lsqare;
        if(Lsqare!=null) Lsqare.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.Rsqare=Rsqare;
        if(Rsqare!=null) Rsqare.setParent(this);
        this.Lsqare1=Lsqare1;
        if(Lsqare1!=null) Lsqare1.setParent(this);
        this.Expr2=Expr2;
        if(Expr2!=null) Expr2.setParent(this);
        this.Rsqare3=Rsqare3;
        if(Rsqare3!=null) Rsqare3.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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

    public Lsqare getLsqare1() {
        return Lsqare1;
    }

    public void setLsqare1(Lsqare Lsqare1) {
        this.Lsqare1=Lsqare1;
    }

    public Expr getExpr2() {
        return Expr2;
    }

    public void setExpr2(Expr Expr2) {
        this.Expr2=Expr2;
    }

    public Rsqare getRsqare3() {
        return Rsqare3;
    }

    public void setRsqare3(Rsqare Rsqare3) {
        this.Rsqare3=Rsqare3;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(Lsqare!=null) Lsqare.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(Rsqare!=null) Rsqare.accept(visitor);
        if(Lsqare1!=null) Lsqare1.accept(visitor);
        if(Expr2!=null) Expr2.accept(visitor);
        if(Rsqare3!=null) Rsqare3.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(Lsqare!=null) Lsqare.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(Rsqare!=null) Rsqare.traverseTopDown(visitor);
        if(Lsqare1!=null) Lsqare1.traverseTopDown(visitor);
        if(Expr2!=null) Expr2.traverseTopDown(visitor);
        if(Rsqare3!=null) Rsqare3.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(Lsqare!=null) Lsqare.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(Rsqare!=null) Rsqare.traverseBottomUp(visitor);
        if(Lsqare1!=null) Lsqare1.traverseBottomUp(visitor);
        if(Expr2!=null) Expr2.traverseBottomUp(visitor);
        if(Rsqare3!=null) Rsqare3.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorNewTypeExpr(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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

        if(Lsqare1!=null)
            buffer.append(Lsqare1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr2!=null)
            buffer.append(Expr2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Rsqare3!=null)
            buffer.append(Rsqare3.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNewTypeExpr]");
        return buffer.toString();
    }
}
