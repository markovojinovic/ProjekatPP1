// generated with ast extension for cup
// version 0.8
// 25/4/2023 18:48:40


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private FunctionType FunctionType;
    private FormParsLst FormParsLst;
    private VarDeclList VarDeclList;
    private StatementList StatementList;

    public MethodDecl (FunctionType FunctionType, FormParsLst FormParsLst, VarDeclList VarDeclList, StatementList StatementList) {
        this.FunctionType=FunctionType;
        if(FunctionType!=null) FunctionType.setParent(this);
        this.FormParsLst=FormParsLst;
        if(FormParsLst!=null) FormParsLst.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public FunctionType getFunctionType() {
        return FunctionType;
    }

    public void setFunctionType(FunctionType FunctionType) {
        this.FunctionType=FunctionType;
    }

    public FormParsLst getFormParsLst() {
        return FormParsLst;
    }

    public void setFormParsLst(FormParsLst FormParsLst) {
        this.FormParsLst=FormParsLst;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FunctionType!=null) FunctionType.accept(visitor);
        if(FormParsLst!=null) FormParsLst.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FunctionType!=null) FunctionType.traverseTopDown(visitor);
        if(FormParsLst!=null) FormParsLst.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FunctionType!=null) FunctionType.traverseBottomUp(visitor);
        if(FormParsLst!=null) FormParsLst.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(FunctionType!=null)
            buffer.append(FunctionType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsLst!=null)
            buffer.append(FormParsLst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
