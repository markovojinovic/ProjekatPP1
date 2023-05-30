// generated with ast extension for cup
// version 0.8
// 30/4/2023 14:53:5


package rs.ac.bg.etf.pp1.ast;

public class FuncCall extends Statement {

    private Designator Designator;
    private FormParsLst FormParsLst;

    public FuncCall (Designator Designator, FormParsLst FormParsLst) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.FormParsLst=FormParsLst;
        if(FormParsLst!=null) FormParsLst.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public FormParsLst getFormParsLst() {
        return FormParsLst;
    }

    public void setFormParsLst(FormParsLst FormParsLst) {
        this.FormParsLst=FormParsLst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(FormParsLst!=null) FormParsLst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(FormParsLst!=null) FormParsLst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(FormParsLst!=null) FormParsLst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsLst!=null)
            buffer.append(FormParsLst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncCall]");
        return buffer.toString();
    }
}
