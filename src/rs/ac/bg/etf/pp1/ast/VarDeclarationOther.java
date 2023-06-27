// generated with ast extension for cup
// version 0.8
// 27/5/2023 20:19:6


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationOther extends VarDecl {

    private Type Type;
    private OneVarDeclarationList OneVarDeclarationList;

    public VarDeclarationOther (Type Type, OneVarDeclarationList OneVarDeclarationList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OneVarDeclarationList=OneVarDeclarationList;
        if(OneVarDeclarationList!=null) OneVarDeclarationList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public OneVarDeclarationList getOneVarDeclarationList() {
        return OneVarDeclarationList;
    }

    public void setOneVarDeclarationList(OneVarDeclarationList OneVarDeclarationList) {
        this.OneVarDeclarationList=OneVarDeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OneVarDeclarationList!=null) OneVarDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OneVarDeclarationList!=null) OneVarDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OneVarDeclarationList!=null) OneVarDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationOther(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneVarDeclarationList!=null)
            buffer.append(OneVarDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationOther]");
        return buffer.toString();
    }
}
