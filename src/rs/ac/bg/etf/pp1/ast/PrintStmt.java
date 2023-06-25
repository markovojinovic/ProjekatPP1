// generated with ast extension for cup
// version 0.8
// 25/5/2023 22:47:49


package rs.ac.bg.etf.pp1.ast;

public class PrintStmt extends Statement {

    private Expr Expr;
    private NumberInPrint NumberInPrint;

    public PrintStmt (Expr Expr, NumberInPrint NumberInPrint) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.NumberInPrint=NumberInPrint;
        if(NumberInPrint!=null) NumberInPrint.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public NumberInPrint getNumberInPrint() {
        return NumberInPrint;
    }

    public void setNumberInPrint(NumberInPrint NumberInPrint) {
        this.NumberInPrint=NumberInPrint;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(NumberInPrint!=null) NumberInPrint.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(NumberInPrint!=null) NumberInPrint.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(NumberInPrint!=null) NumberInPrint.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintStmt(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NumberInPrint!=null)
            buffer.append(NumberInPrint.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintStmt]");
        return buffer.toString();
    }
}
