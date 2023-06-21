// generated with ast extension for cup
// version 0.8
// 21/5/2023 12:8:32


package rs.ac.bg.etf.pp1.ast;

public class FormParsMatrix extends FormPars {

    private Type Type;
    private String I2;
    private SquareList SquareList;
    private TypeIdentSquareList TypeIdentSquareList;

    public FormParsMatrix (Type Type, String I2, SquareList SquareList, TypeIdentSquareList TypeIdentSquareList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I2=I2;
        this.SquareList=SquareList;
        if(SquareList!=null) SquareList.setParent(this);
        this.TypeIdentSquareList=TypeIdentSquareList;
        if(TypeIdentSquareList!=null) TypeIdentSquareList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public SquareList getSquareList() {
        return SquareList;
    }

    public void setSquareList(SquareList SquareList) {
        this.SquareList=SquareList;
    }

    public TypeIdentSquareList getTypeIdentSquareList() {
        return TypeIdentSquareList;
    }

    public void setTypeIdentSquareList(TypeIdentSquareList TypeIdentSquareList) {
        this.TypeIdentSquareList=TypeIdentSquareList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(SquareList!=null) SquareList.accept(visitor);
        if(TypeIdentSquareList!=null) TypeIdentSquareList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(SquareList!=null) SquareList.traverseTopDown(visitor);
        if(TypeIdentSquareList!=null) TypeIdentSquareList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(SquareList!=null) SquareList.traverseBottomUp(visitor);
        if(TypeIdentSquareList!=null) TypeIdentSquareList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsMatrix(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(SquareList!=null)
            buffer.append(SquareList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypeIdentSquareList!=null)
            buffer.append(TypeIdentSquareList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsMatrix]");
        return buffer.toString();
    }
}
