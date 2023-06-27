// generated with ast extension for cup
// version 0.8
// 27/5/2023 18:25:42


package rs.ac.bg.etf.pp1.ast;

public class TypeIdentSquareListRec extends TypeIdentSquareList {

    private TypeIdentSquareList TypeIdentSquareList;
    private Type Type;
    private String identName;
    private SquareList SquareList;

    public TypeIdentSquareListRec (TypeIdentSquareList TypeIdentSquareList, Type Type, String identName, SquareList SquareList) {
        this.TypeIdentSquareList=TypeIdentSquareList;
        if(TypeIdentSquareList!=null) TypeIdentSquareList.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.identName=identName;
        this.SquareList=SquareList;
        if(SquareList!=null) SquareList.setParent(this);
    }

    public TypeIdentSquareList getTypeIdentSquareList() {
        return TypeIdentSquareList;
    }

    public void setTypeIdentSquareList(TypeIdentSquareList TypeIdentSquareList) {
        this.TypeIdentSquareList=TypeIdentSquareList;
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

    public SquareList getSquareList() {
        return SquareList;
    }

    public void setSquareList(SquareList SquareList) {
        this.SquareList=SquareList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TypeIdentSquareList!=null) TypeIdentSquareList.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(SquareList!=null) SquareList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypeIdentSquareList!=null) TypeIdentSquareList.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(SquareList!=null) SquareList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypeIdentSquareList!=null) TypeIdentSquareList.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(SquareList!=null) SquareList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypeIdentSquareListRec(\n");

        if(TypeIdentSquareList!=null)
            buffer.append(TypeIdentSquareList.toString("  "+tab));
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

        if(SquareList!=null)
            buffer.append(SquareList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeIdentSquareListRec]");
        return buffer.toString();
    }
}
