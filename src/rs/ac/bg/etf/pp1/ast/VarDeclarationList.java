// generated with ast extension for cup
// version 0.8
// 27/5/2023 13:7:33


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationList extends OneVarDeclarationList {

    private OneVarDeclarationList OneVarDeclarationList;
    private OptionalComma OptionalComma;
    private OneVarDeclaration OneVarDeclaration;

    public VarDeclarationList (OneVarDeclarationList OneVarDeclarationList, OptionalComma OptionalComma, OneVarDeclaration OneVarDeclaration) {
        this.OneVarDeclarationList=OneVarDeclarationList;
        if(OneVarDeclarationList!=null) OneVarDeclarationList.setParent(this);
        this.OptionalComma=OptionalComma;
        if(OptionalComma!=null) OptionalComma.setParent(this);
        this.OneVarDeclaration=OneVarDeclaration;
        if(OneVarDeclaration!=null) OneVarDeclaration.setParent(this);
    }

    public OneVarDeclarationList getOneVarDeclarationList() {
        return OneVarDeclarationList;
    }

    public void setOneVarDeclarationList(OneVarDeclarationList OneVarDeclarationList) {
        this.OneVarDeclarationList=OneVarDeclarationList;
    }

    public OptionalComma getOptionalComma() {
        return OptionalComma;
    }

    public void setOptionalComma(OptionalComma OptionalComma) {
        this.OptionalComma=OptionalComma;
    }

    public OneVarDeclaration getOneVarDeclaration() {
        return OneVarDeclaration;
    }

    public void setOneVarDeclaration(OneVarDeclaration OneVarDeclaration) {
        this.OneVarDeclaration=OneVarDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OneVarDeclarationList!=null) OneVarDeclarationList.accept(visitor);
        if(OptionalComma!=null) OptionalComma.accept(visitor);
        if(OneVarDeclaration!=null) OneVarDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OneVarDeclarationList!=null) OneVarDeclarationList.traverseTopDown(visitor);
        if(OptionalComma!=null) OptionalComma.traverseTopDown(visitor);
        if(OneVarDeclaration!=null) OneVarDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OneVarDeclarationList!=null) OneVarDeclarationList.traverseBottomUp(visitor);
        if(OptionalComma!=null) OptionalComma.traverseBottomUp(visitor);
        if(OneVarDeclaration!=null) OneVarDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationList(\n");

        if(OneVarDeclarationList!=null)
            buffer.append(OneVarDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalComma!=null)
            buffer.append(OptionalComma.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneVarDeclaration!=null)
            buffer.append(OneVarDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationList]");
        return buffer.toString();
    }
}
