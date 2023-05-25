// generated with ast extension for cup
// version 0.8
// 25/4/2023 19:21:43


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationArray extends VarDecl {

    private Type Type;
    private SquareList SquareList;
    private IdentSquareList IdentSquareList;

    public VarDeclarationArray (Type Type, SquareList SquareList, IdentSquareList IdentSquareList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.SquareList=SquareList;
        if(SquareList!=null) SquareList.setParent(this);
        this.IdentSquareList=IdentSquareList;
        if(IdentSquareList!=null) IdentSquareList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public SquareList getSquareList() {
        return SquareList;
    }

    public void setSquareList(SquareList SquareList) {
        this.SquareList=SquareList;
    }

    public IdentSquareList getIdentSquareList() {
        return IdentSquareList;
    }

    public void setIdentSquareList(IdentSquareList IdentSquareList) {
        this.IdentSquareList=IdentSquareList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(SquareList!=null) SquareList.accept(visitor);
        if(IdentSquareList!=null) IdentSquareList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(SquareList!=null) SquareList.traverseTopDown(visitor);
        if(IdentSquareList!=null) IdentSquareList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(SquareList!=null) SquareList.traverseBottomUp(visitor);
        if(IdentSquareList!=null) IdentSquareList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationArray(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SquareList!=null)
            buffer.append(SquareList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdentSquareList!=null)
            buffer.append(IdentSquareList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationArray]");
        return buffer.toString();
    }
}
