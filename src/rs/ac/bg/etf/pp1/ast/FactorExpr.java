// generated with ast extension for cup
// version 0.8
// 27/5/2023 14:34:27


package rs.ac.bg.etf.pp1.ast;

public class FactorExpr extends Factor {

    private Lparen Lparen;
    private Expr Expr;
    private Rparen Rparen;

    public FactorExpr (Lparen Lparen, Expr Expr, Rparen Rparen) {
        this.Lparen=Lparen;
        if(Lparen!=null) Lparen.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.Rparen=Rparen;
        if(Rparen!=null) Rparen.setParent(this);
    }

    public Lparen getLparen() {
        return Lparen;
    }

    public void setLparen(Lparen Lparen) {
        this.Lparen=Lparen;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Rparen getRparen() {
        return Rparen;
    }

    public void setRparen(Rparen Rparen) {
        this.Rparen=Rparen;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Lparen!=null) Lparen.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(Rparen!=null) Rparen.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Lparen!=null) Lparen.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(Rparen!=null) Rparen.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Lparen!=null) Lparen.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(Rparen!=null) Rparen.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorExpr(\n");

        if(Lparen!=null)
            buffer.append(Lparen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Rparen!=null)
            buffer.append(Rparen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorExpr]");
        return buffer.toString();
    }
}
