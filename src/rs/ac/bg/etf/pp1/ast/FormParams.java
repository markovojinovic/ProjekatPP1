// generated with ast extension for cup
// version 0.8
// 30/4/2023 14:53:4


package rs.ac.bg.etf.pp1.ast;

public class FormParams extends FormParsLst {

    private FormParsLst FormParsLst;
    private Type Type;
    private String identName;
    private IdentList IdentList;

    public FormParams (FormParsLst FormParsLst, Type Type, String identName, IdentList IdentList) {
        this.FormParsLst=FormParsLst;
        if(FormParsLst!=null) FormParsLst.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.identName=identName;
        this.IdentList=IdentList;
        if(IdentList!=null) IdentList.setParent(this);
    }

    public FormParsLst getFormParsLst() {
        return FormParsLst;
    }

    public void setFormParsLst(FormParsLst FormParsLst) {
        this.FormParsLst=FormParsLst;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getIdentName() {
        return identName;
    }

    public void setIdentName(String identName) {
        this.identName=identName;
    }

    public IdentList getIdentList() {
        return IdentList;
    }

    public void setIdentList(IdentList IdentList) {
        this.IdentList=IdentList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsLst!=null) FormParsLst.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(IdentList!=null) IdentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsLst!=null) FormParsLst.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(IdentList!=null) IdentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsLst!=null) FormParsLst.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(IdentList!=null) IdentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParams(\n");

        if(FormParsLst!=null)
            buffer.append(FormParsLst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+identName);
        buffer.append("\n");

        if(IdentList!=null)
            buffer.append(IdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParams]");
        return buffer.toString();
    }
}
