package project.interpreter;

import project.exceptions.SemanticError;
import project.interpreter.definitions.FuncDefinition;
import project.interpreter.definitions.StructDefinition;
import project.interpreter.definitions.Variable;
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


public class Interpreter implements INodeVisitor {

    private Program program;
    private Environment env;

    public Interpreter(Program program, Environment env) {
        this.program = program;
        this.env = env;
    }

    public void execute() {
        program.accept(this);
    }

    public Environment getEnv() {
        return env;
    }


    @Override
    public void visit(Program program) {
        for (ProgramContent programContent : program.getProgramContents()) {
            programContent.accept(this);
        }
    }

    @Override
    public void visit(ProgramContent programContent) {
        if (programContent instanceof OnlyDeclaration)
            ((OnlyDeclaration) programContent).accept(this);
        else if (programContent instanceof Initialisation)
            ((Initialisation) programContent).accept(this);
        else if (programContent instanceof FuncDef)
            ((FuncDef) programContent).accept(this);
        else if (programContent instanceof StructDef)
            ((StructDef) programContent).accept(this);
    }

    @Override
    public void visit(Declaration declaration) {
        if (declaration instanceof OnlyDeclaration)
            ((OnlyDeclaration)declaration).accept(this);
        else if (declaration instanceof Initialisation)
            ((Initialisation)declaration).accept(this);
    }

    @Override
    public void visit(OnlyDeclaration onlyDeclaration) {
        Type type = onlyDeclaration.getType();
        if (type instanceof VoidType) {
            String desc = "Type of variable in declaration is instance of void";
            throw new SemanticError(((VoidType)type).getLineNr(), ((VoidType)type).getPositionAtLine(), desc);
        }
        env.makeVar(new Variable(onlyDeclaration));
    }

    @Override
    public void visit(Initialisation initialisation) {
        Type type = initialisation.getType();
        if (type instanceof VoidType) {
            String desc = "Type of variable in definition is instance of void";
            throw new SemanticError(((VoidType)type).getLineNr(), ((VoidType)type).getPositionAtLine(), desc);
        }

        initialisation.getExpression().accept(this);
        Expression value = env.getLastResult();

        if (type instanceof IntType && !(value instanceof IntValue)) {
            String desc = "Wrong type of variable in initialisation expression, int is required";
            throw new SemanticError(value.getLineNr(), value.getPositionAtLine(), desc);
        }
        else if (type instanceof DoubleType && !(value instanceof DoubleValue)) {
            String desc = "Wrong type of variable in initialisation expression, double is required";
            throw new SemanticError(value.getLineNr(), value.getPositionAtLine(), desc);
        }
        else if (type instanceof StringType && !(value instanceof StringValue)) {
            String desc = "Wrong type of variable in initialisation expression, string is required";
            throw new SemanticError(value.getLineNr(), value.getPositionAtLine(), desc);
        }
        else if (type instanceof BoolType && !((value instanceof TrueExpression) || (value instanceof FalseExpression))) {
            String desc = "Wrong type of variable in initialisation expression, bool is required";
            throw new SemanticError(value.getLineNr(), value.getPositionAtLine(), desc);
        }
        else if (type instanceof StructType) {
            String desc = "Initialisation of variable of struct type is prohibited";
            throw new SemanticError(value.getLineNr(), value.getPositionAtLine(), desc);
        }

        env.makeVar(new Variable(initialisation));
    }

    @Override
    public void visit(FuncDef funcDef) {
        env.makeFuncDefinition(new FuncDefinition(funcDef));
    }

    @Override
    public void visit(StructDef structDef) {
        env.makeStructDefinition(new StructDefinition(structDef));
    }

    @Override
    public void visit(Statement statement) {

    }

    @Override
    public void visit(Assignment assignmentStmt) {

    }

    @Override
    public void visit(Block blockStmt) {
        env.makeBlockContext();

        for (Statement stmt : blockStmt.getStmts()) {
            stmt.accept(this);
        }

        env.deleteBlockContext();
    }

    @Override
    public void visit(Decrement decrementStmt) {

    }

    @Override
    public void visit(Increment incrementStmt) {

    }

    @Override
    public void visit(If ifStmt) {
        ifStmt.getCondition().accept(this);
        if (env.getLastResult() instanceof  TrueExpression) {
            ifStmt.getIfStmt().accept(this);
            return;
        }

        if (env.getLastResult() instanceof FalseExpression)
            return;

        String desc = "Condition in if statement is not a type of bool expression";
        int lineNr = ((Expression)env.getLastResult()).getLineNr();
        int posAtLine = ((Expression)env.getLastResult()).getPositionAtLine();
        throw new SemanticError(lineNr, posAtLine, desc);
    }

    @Override
    public void visit(IfElse ifElseStmt) {
        ifElseStmt.getCondition().accept(this);

        if (env.getLastResult() instanceof  TrueExpression) {
            ifElseStmt.getIfStmt().accept(this);
            return;
        }

        if (env.getLastResult() instanceof  FalseExpression) {
            ifElseStmt.getElseStmt().accept(this);
            return;
        }

        String desc = "Condition in if statement is not a type of bool expression";
        int lineNr = ((Expression)env.getLastResult()).getLineNr();
        int posAtLine = ((Expression)env.getLastResult()).getPositionAtLine();
        throw new SemanticError(lineNr, posAtLine, desc);
    }

    @Override
    public void visit(While whileStmt) {

    }

    @Override
    public void visit(Return returnStmt) {
        returnStmt.getExpression().accept(this);
    }

    @Override
    public void visit(VoidReturn voidReturnStmt) {

    }

    @Override
    public void visit(Empty emptyStmt) {

    }

    @Override
    public void visit(Switch switchStmt) {

    }

    @Override
    public void visit(Case caseStmt) {

    }

    @Override
    public void visit(Expression expression) {

    }

    @Override
    public void visit(OrExpression orExpression) {

    }

    @Override
    public void visit(AlternativeExpression alternativeExpression) {

    }

    @Override
    public void visit(AndExpression andExpression) {

    }

    @Override
    public void visit(ConjunctionExpression conjunctionExpression) {

    }

    @Override
    public void visit(RelationExpression relationExpression) {

    }

    @Override
    public void visit(NotEqualExpression notEqualExpression) {

    }

    @Override
    public void visit(LesserExpression lesserExpression) {

    }

    @Override
    public void visit(LesserEqualExpression lesserEqualExpression) {

    }

    @Override
    public void visit(GreaterExpression greaterExpression) {

    }

    @Override
    public void visit(GreaterEqualExpression greaterEqualExpression) {

    }

    @Override
    public void visit(EqualExpression equalExpression) {

    }

    @Override
    public void visit(AdditionExpression additionExpression) {

    }

    @Override
    public void visit(AddExpression addExpression) {

    }

    @Override
    public void visit(SubtractExpression subtractExpression) {

    }

    @Override
    public void visit(MultiplicationExpression multiplicationExpression) {

    }

    @Override
    public void visit(MultExpression multExpression) {

    }

    @Override
    public void visit(DivExpression divExpression) {

    }

    @Override
    public void visit(ModExpression modExpression) {

    }

    @Override
    public void visit(NegationExpression negationExpression) {

    }

    @Override
    public void visit(NotExpression notExpression) {

    }

    @Override
    public void visit(NegativeExpression negativeExpression) {

    }

    @Override
    public void visit(SimpleExpression simpleExpression) {

    }

    @Override
    public void visit(DoubleValue doubleValue) {

    }

    @Override
    public void visit(IntValue intValue) {

    }

    @Override
    public void visit(StringValue stringValue) {

    }

    @Override
    public void visit(TrueExpression trueExpression) {

    }

    @Override
    public void visit(FalseExpression falseExpression) {

    }

    @Override
    public void visit(Identifier identifier) {

    }

    @Override
    public void visit(StructFieldExpression structFieldExpression) {

    }

    @Override
    public void visit(ParenthExpression parenthExpression) {

    }

    @Override
    public void visit(FuncCall funcCall) {

    }

    @Override
    public void visit(Type type) {

    }

    @Override
    public void visit(NonVoidType nonVoidType) {

    }

    @Override
    public void visit(NumericalType numericalType) {

    }

    @Override
    public void visit(BoolType boolType) {

    }

    @Override
    public void visit(IntType intType) {

    }

    @Override
    public void visit(DoubleType doubleType) {

    }

    @Override
    public void visit(StringType stringType) {

    }

    @Override
    public void visit(StructType structType) {

    }

    @Override
    public void visit(VoidType voidType) {

    }
}
