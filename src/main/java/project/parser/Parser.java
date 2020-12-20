package project.parser;

import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.StructDef;
import project.program.content.statements.*;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.boolExpressions.BoolExpression;
import project.program.content.statements.switchStmt.Case;
import project.program.content.statements.switchStmt.Switch;
import project.program.content.types.NonVoidType;
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

        if ((statement = tryToParseIfOrIfElseStatement()) != null)
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

    private Statement tryToParseIfOrIfElseStatement() {
        if (lexer.getToken().getType() == Token.TokenType.IF) {
            lexer.nextToken();

            if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
                lexer.nextToken();

                BoolExpression condition;
                if ((condition = tryToParseBoolExpression()) != null) {

                    if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                        lexer.nextToken();

                        Statement ifStatement;
                        if ((ifStatement = tryToParseStatement()) != null) {

                            if (lexer.getToken().getType() != Token.TokenType.ELSE) {
                                return new If(condition, ifStatement);
                            }
                            lexer.nextToken();

                            Statement elseStatement;
                            if ((elseStatement = tryToParseStatement()) != null) {
                                return new IfElse(condition, ifStatement, elseStatement);
                            }
                            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No statement (after right parenthesis)");
                        }
                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No statement (after 'if' keyword)");
                    }
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ')' (after condition)");
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No condition (after left parenthesis)");
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '(' (after 'if' keyword)");
        }

        return null;
    }

    private Statement tryToParseSwitchStatement() {
        if (lexer.getToken().getType() == Token.TokenType.SWITCH) {
            lexer.nextToken();

            if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
                lexer.nextToken();

                Expression switchExp;
                if ((switchExp = tryToParseExpression()) != null) {

                    if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                        lexer.nextToken();

                        if (lexer.getToken().getType() == Token.TokenType.L_BRACE) {
                            lexer.nextToken();

                            ArrayList<Case> cases = new ArrayList<>();
                            while (lexer.getToken().getType() == Token.TokenType.CASE) {
                                lexer.nextToken();

                                Expression caseExp;
                                if ((caseExp = tryToParseExpression()) != null) {

                                    if (lexer.getToken().getType() == Token.TokenType.COLON) {
                                        lexer.nextToken();

                                        Statement statement;
                                        if ((statement = tryToParseStatement()) != null) {
                                            cases.add(new Case(caseExp, statement));
                                            continue;
                                        }
                                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No statement (after colon in case)");
                                    }
                                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ':' (after case expression)");
                                }
                                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No expression (after 'case' keyword)");
                            }

                            if (lexer.getToken().getType() == Token.TokenType.DEFAULT) {
                                lexer.nextToken();

                                if (lexer.getToken().getType() == Token.TokenType.COLON) {
                                    lexer.nextToken();

                                    Statement defaultStmt;
                                    if ((defaultStmt = tryToParseStatement()) != null) {

                                        if (lexer.getToken().getType() == Token.TokenType.R_BRACE) {
                                            lexer.nextToken();

                                            return new Switch(switchExp, cases, defaultStmt);
                                        }
                                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '}' (after default statement)");
                                    }
                                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No statement (after colon in default)");
                                }
                                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ':' (after 'default' keyword)");
                            }
                            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No 'default' keyword (after list of cases in switch)");
                        }
                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '{' (after right parenthesis)");
                    }
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ')' (after expression)");
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No expression (after left parenthesis)");
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '(' (after 'switch' keyword)");
        }

        return null;
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
                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No statement (after right parenthesis)");
                    }
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ')' (after condition)");
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No condition (after left parenthesis)");
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '(' (after 'while' keyword)");
        }

        return null;
    }

    private Statement tryToParseBlockStatement() {
        if (lexer.getToken().getType() == Token.TokenType.L_BRACE) {
            lexer.nextToken();

            ArrayList<Statement> statements = new ArrayList<>();
            Statement statement;
            while ((statement = tryToParseStatement()) != null) {
                statements.add(statement);
            }

            if (lexer.getToken().getType() == Token.TokenType.R_BRACE) {
                lexer.nextToken();

                return new Block(statements);
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '}' (after list of statements in block)");
        }

        return null;
    }

    private Statement tryToParseDeclarationStatement() {
        NonVoidType type;
        if ((type = tryToParseNonVoidType()) != null) {

            if (lexer.getToken().getType() == Token.TokenType.ID) {
                String id = ((StringToken) lexer.getToken()).getValue();
                lexer.nextToken();

                if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
                    lexer.nextToken();

                    return new OnlyDeclaration(type, id);
                }
                else if (lexer.getToken().getType() == Token.TokenType.ASSIGN) {
                    lexer.nextToken();

                    Expression expression;
                    if ((expression = tryToParseExpression()) != null) {

                        if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
                            lexer.nextToken();

                            return new Initialisation(type, id, expression);
                        }
                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ';' (after expression in initialisation)");
                    }
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No expression (after '=' in initialisation)");
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '=' or ';' (after identifier of declaring variable)");
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No identifier (after type in declaration)");
        }

        return null;
    }

    private Statement tryToParseEmptyStatement() {
        if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
            lexer.nextToken();

            return new Empty();
        }

        return null;
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



    private Expression tryToParseExpression() {
    }

    private BoolExpression tryToParseBoolExpression() {
    }

    private Statement tryToParseFuncCallStatement(String id) {
    }

    private Statement tryToParseAssignmentStatement(String id) {
    }

    private NonVoidType tryToParseNonVoidType() {
    }
}
