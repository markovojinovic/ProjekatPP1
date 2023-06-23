// generated with ast extension for cup
// version 0.8
// 23/5/2023 1:1:30


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(OptionalComma OptionalComma);
    public void visit(Constant Constant);
    public void visit(NumberInPrint NumberInPrint);
    public void visit(OneVarDeclarationList OneVarDeclarationList);
    public void visit(StatementList StatementList);
    public void visit(SquareList SquareList);
    public void visit(MulopFactor MulopFactor);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(TypeIdentSquareList TypeIdentSquareList);
    public void visit(FunctionParameters FunctionParameters);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(FormParsLst FormParsLst);
    public void visit(IdentList IdentList);
    public void visit(IdentSquareList IdentSquareList);
    public void visit(ExprList ExprList);
    public void visit(ReturnStatement ReturnStatement);
    public void visit(Lsqare Lsqare);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(DesignatorList DesignatorList);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Rsqare Rsqare);
    public void visit(OneVarDeclaration OneVarDeclaration);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(AddopTerm AddopTerm);
    public void visit(Type Type);
    public void visit(Minus Minus);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(DesignatorListMany DesignatorListMany);
    public void visit(FormPars FormPars);
    public void visit(RsquareClass RsquareClass);
    public void visit(LsquareClass LsquareClass);
    public void visit(TypeIdent TypeIdent);
    public void visit(NoSquareListOutside NoSquareListOutside);
    public void visit(SquareListOutsideOne SquareListOutsideOne);
    public void visit(SquareListOutside SquareListOutside);
    public void visit(NoTypeIdentSquareList NoTypeIdentSquareList);
    public void visit(TypeIdentSquareListRec TypeIdentSquareListRec);
    public void visit(FormParsMatrix FormParsMatrix);
    public void visit(IdentTypeInLine IdentTypeInLine);
    public void visit(IdentInLineList IdentInLineList);
    public void visit(ListOfIdentInLine ListOfIdentInLine);
    public void visit(IdentInLine IdentInLine);
    public void visit(NoIdentSquareList NoIdentSquareList);
    public void visit(IdentSquareListRec IdentSquareListRec);
    public void visit(MulOperationPercent MulOperationPercent);
    public void visit(MulOperationDiv MulOperationDiv);
    public void visit(MulOperationMul MulOperationMul);
    public void visit(AddOperationMinus AddOperationMinus);
    public void visit(AddOperationPlus AddOperationPlus);
    public void visit(NoExprListExpr NoExprListExpr);
    public void visit(ExprListExprMatrix ExprListExprMatrix);
    public void visit(ExprListExpr ExprListExpr);
    public void visit(DesignatorExpression DesignatorExpression);
    public void visit(FactorBool FactorBool);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNum FactorNum);
    public void visit(FactorNewTypeExpr FactorNewTypeExpr);
    public void visit(FactorDes FactorDes);
    public void visit(FactorNewExpr FactorNewExpr);
    public void visit(FactorExpr FactorExpr);
    public void visit(Consts Consts);
    public void visit(NoMulopFactor NoMulopFactor);
    public void visit(MulopFactorRec MulopFactorRec);
    public void visit(Terminator Terminator);
    public void visit(NoMinusExpression NoMinusExpression);
    public void visit(MinusExpression MinusExpression);
    public void visit(NoAddOperationTerminator NoAddOperationTerminator);
    public void visit(AddOperationTerminatorRec AddOperationTerminatorRec);
    public void visit(Expression Expression);
    public void visit(NoNumberInPrintNumber NoNumberInPrintNumber);
    public void visit(NumberInPrintNumber NumberInPrintNumber);
    public void visit(PrintStmt PrintStmt);
    public void visit(StatementRead StatementRead);
    public void visit(StatementDerived1 StatementDerived1);
    public void visit(StatementDesignator StatementDesignator);
    public void visit(NoDesignatorListDesignator NoDesignatorListDesignator);
    public void visit(DesignatorListDesignator DesignatorListDesignator);
    public void visit(NoDesignatorList NoDesignatorList);
    public void visit(DesignatorListRec DesignatorListRec);
    public void visit(DesignatorStatementMinusMinus DesignatorStatementMinusMinus);
    public void visit(DesignatorStatementPlusPlus DesignatorStatementPlusPlus);
    public void visit(DesignatorStatementEqual DesignatorStatementEqual);
    public void visit(NoIdentLists NoIdentLists);
    public void visit(IdentLists IdentLists);
    public void visit(NoFormParams NoFormParams);
    public void visit(FormParams FormParams);
    public void visit(NoStmt NoStmt);
    public void visit(Statements Statements);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(NoReturnStatement NoReturnStatement);
    public void visit(ReturnStatementExpr ReturnStatementExpr);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(NoOptionalCommaClass NoOptionalCommaClass);
    public void visit(OptionalCommaClass OptionalCommaClass);
    public void visit(NoVarDeclarationList NoVarDeclarationList);
    public void visit(VarDeclarationList VarDeclarationList);
    public void visit(OneVarDeclarationDerived1 OneVarDeclarationDerived1);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(VarDeclarationMatrix VarDeclarationMatrix);
    public void visit(VarDeclarationArray VarDeclarationArray);
    public void visit(VarDeclDerived1 VarDeclDerived1);
    public void visit(ConstVarDeclaration ConstVarDeclaration);
    public void visit(VarDeclarationOther VarDeclarationOther);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
