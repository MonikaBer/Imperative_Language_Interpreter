package project.parser;

import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.Declaration;
import project.program.content.statements.Statement;
import project.program.content.statements.While;
import project.program.content.statements.expressions.boolExpressions.BoolExpression;
import project.token.StringToken;
import project.token.Token;

import java.util.ArrayList;

public class Parser {

    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Program ParseProgram() {
        ArrayList<Declaration> declarations = new ArrayList<>();
        ArrayList<FuncDef> funcDefs = new ArrayList<>();
        ArrayList<StructDef> structDefs = new ArrayList<>();

        Declaration declaration;
        FuncDef funcDef;
        StructDef structDef;

        while (lexer.getToken().getType() != Token.TokenType.EOT) {
            declaration = tryToParseDeclaration();
            if (declaration != null)
                declarations.add(declaration);

            funcDef = tryToParseFuncDef();
            if (funcDef != null)
                funcDefs.add(funcDef);

            structDef = tryToParseStructDef();
            if (structDef != null)
                structDefs.add(structDef);
        }

        return new Program(declarations, funcDefs, structDefs);
    }

    private Declaration tryToParseDeclaration() {
        Declaration declaration = null;

        return null;
    }

    private FuncDef tryToParseFuncDef() {
        FuncDef funcDef = null;

        return null;
    }

    private StructDef tryToParseStructDef() {
        StructDef structDef = null;

        return null;
    }

    private Statement tryToParseStatement() {
        Statement statement = null;

        if ((statement = tryToParseIfStatement()) != null)
            return statement;
        if ((statement = tryToParseIfElseStatement()) != null)
            return statement;
        if ((statement = tryToParseSwitchStatement()) != null)
            return statement;
        if ((statement = tryToParseWhileStatement()) != null)
            return statement;
        if ((statement = tryToParseBlockStatement()) != null)
            return statement;
        if ((statement = tryToParseDeclarationStatement()) != null)
            return statement;
        if ((statement = tryToParseEmptyStatement()) != null)
            return statement;
        if ((statement = tryToParseAssignmentOrFuncCallStatement()) != null)
            return statement;
        if ((statement = tryToParseExpressionStatement()) != null)
            return statement;

        return null;
    }

    private Statement tryToParseIfStatement() {
    }

    private Statement tryToParseIfElseStatement() {
    }

    private Statement tryToParseSwitchStatement() {
    }

    private Statement tryToParseWhileStatement() {
        if (lexer.getToken().getType() == Token.TokenType.WHILE) {
            lexer.nextToken();

            if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
                lexer.nextToken();

                BoolExpression condition;
                if ((condition = tryToParseBoolExpression()) != null) {

                    if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                        lexer.nextToken();

                        Statement statement;
                        if ((statement = tryToParseStatement()) != null) {
                            return new While(condition, statement);
                        }
                    }
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right parenthesis (after condition)");
                }
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No left parenthesis (after while keyword)");
        }

        return null;
    }

    private Statement tryToParseBlockStatement() {
    }

    private Statement tryToParseDeclarationStatement() {
    }

    private Statement tryToParseEmptyStatement() {
    }

    private Statement tryToParseAssignmentOrFuncCallStatement() {
        if (lexer.getToken().getType() == Token.TokenType.ID) {
            String id = ((StringToken) lexer.getToken()).getValue();
            lexer.nextToken();

            Statement statement;
            if ((statement = tryToParseFuncCallStatement(id)) != null)
                return statement;
            if ((statement = tryToParseAssignmentStatement(id)) != null)
                return statement;

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "Wrong usage of identifier");
        }

        return null;
    }

    private Statement tryToParseExpressionStatement() {
    }



    private BoolExpression tryToParseBoolExpression() {
    }

    private Statement tryToParseFuncCallStatement(String id) {
    }

    private Statement tryToParseAssignmentStatement(String id) {
    }
}
