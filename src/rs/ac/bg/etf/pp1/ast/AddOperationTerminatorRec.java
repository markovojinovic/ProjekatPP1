// generated with ast extension for cup
// version 0.8
// 27/5/2023 14:34:27


package rs.ac.bg.etf.pp1.ast;

public class AddOperationTerminatorRec extends AddopTerm {

    private AddopTerm AddopTerm;
    private Addop Addop;
    private Term Term;

    public AddOperationTerminatorRec (AddopTerm AddopTerm, Addop Addop, Term Term) {
        this.AddopTerm=AddopTerm;
        if(AddopTerm!=null) AddopTerm.setParent(this);
        this.Addop=Addop;
        if(Addop!=null) Addop.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public AddopTerm getAddopTerm() {
        return AddopTerm;
    }

    public void setAddopTerm(AddopTerm AddopTerm) {
        this.AddopTerm=AddopTerm;
    }

    public Addop getAddop() {
        return Addop;
    }

    public void setAddop(Addop Addop) {
        this.Addop=Addop;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddopTerm!=null) AddopTerm.accept(visitor);
        if(Addop!=null) Addop.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddopTerm!=null) AddopTerm.traverseTopDown(visitor);
        if(Addop!=null) Addop.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddopTerm!=null) AddopTerm.traverseBottomUp(visitor);
        if(Addop!=null) Addop.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddOperationTerminatorRec(\n");

        if(AddopTerm!=null)
            buffer.append(AddopTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Addop!=null)
            buffer.append(Addop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddOperationTerminatorRec]");
        return buffer.toString();
    }
}
