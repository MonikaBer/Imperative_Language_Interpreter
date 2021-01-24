package project.interpreter;

import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.ProgramContent;
import project.program.content.StructDef;
import project.program.content.statements.*;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.orExpressions.AlternativeExpression;
import project.program.content.statements.expressions.orExpressions.OrExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.AndExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.ConjunctionExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.*;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AddExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.SubtractExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.DivExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.ModExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultiplicationExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegationExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegativeExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NotExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.*;
import project.program.content.statements.switchStmt.Case;
import project.program.content.statements.switchStmt.Switch;
import project.program.content.types.*;

public interface INodeVisitor {
    void visit(Program program);
    void visit(ProgramContent programContent);

    void visit(Declaration declaration);
    void visit(OnlyDeclaration onlyDeclaration);
    void visit(Initialisation initialisation);
    void visit(FuncDef funcDef);
    void visit(StructDef structDef);

    void visit(Statement statement);
    void visit(Assignment assignmentStmt);
    void visit(Block blockStmt);
    void visit(Decrement decrementStmt);
    void visit(Increment incrementStmt);
    void visit(If ifStmt);
    void visit(IfElse ifElseStmt);
    void visit(While whileStmt);
    void visit(Return returnStmt);
    void visit(VoidReturn voidReturnStmt);
    void visit(Empty emptyStmt);
    void visit(Switch switchStmt);

    void visit(Expression expression);
    void visit(OrExpression orExpression);
    void visit(AlternativeExpression alternativeExpression);

    void visit(AndExpression andExpression);
    void visit(ConjunctionExpression conjunctionExpression);

    void visit(RelationExpression relationExpression);
    void visit(NotEqualExpression notEqualExpression);
    void visit(LesserExpression lesserExpression);
    void visit(LesserEqualExpression lesserEqualExpression);
    void visit(GreaterExpression greaterExpression);
    void visit(GreaterEqualExpression greaterEqualExpression);
    void visit(EqualExpression equalExpression);

    void visit(AdditionExpression additionExpression);
    void visit(AddExpression addExpression);
    void visit(SubtractExpression subtractExpression);

    void visit(MultiplicationExpression multiplicationExpression);
    void visit(MultExpression multExpression);
    void visit(DivExpression divExpression);
    void visit(ModExpression modExpression);

    void visit(NegationExpression negationExpression);
    void visit(NotExpression notExpression);
    void visit(NegativeExpression negativeExpression);

    void visit(SimpleExpression simpleExpression);
    void visit(DoubleValue doubleValue);
    void visit(IntValue intValue);
    void visit(StringValue stringValue);
    void visit(TrueExpression trueExpression);
    void visit(FalseExpression falseExpression);
    void visit(Identifier identifier);
    void visit(StructFieldExpression structFieldExpression);
    void visit(ParenthExpression parenthExpression);
    void visit(FuncCall funcCall);
}
