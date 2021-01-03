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
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.*;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.*;
import project.program.content.statements.expressions.orExpressions.AlternativeExpression;
import project.program.content.statements.expressions.orExpressions.OrExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.AndExpression;
import project.program.content.statements.expressions.orExpressions.andExpressions.ConjunctionExpression;
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
import project.program.content.statements.switchStmt.Case;
import project.program.content.statements.switchStmt.Switch;
import project.program.content.types.*;
import project.token.DoubleToken;
import project.token.IntToken;
import project.token.StringToken;
import project.token.Token;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Struct;
import java.util.ArrayList;

public class Parser {

    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Program parseProgram() {
        ArrayList<Declaration> declarations = new ArrayList<>();
        ArrayList<FuncDef> funcDefs = new ArrayList<>();
        ArrayList<StructDef> structDefs = new ArrayList<>();

        ArrayList<Statement> declarationsStatement;
        FuncDef funcDef;
        StructDef structDef;

        while (lexer.getToken().getType() != Token.TokenType.EOT) {
            declarationsStatement = tryToParseDeclarationsList();
            if (declarationsStatement != null) {
                for (Statement declaration : declarationsStatement) {
                    declarations.add((Declaration)declaration);
                }
            }

            funcDef = tryToParseFuncDef();
            if (funcDef != null)
                funcDefs.add(funcDef);

            structDef = tryToParseStructDef();
            if (structDef != null)
                structDefs.add(structDef);
        }

        return new Program(declarations, funcDefs, structDefs);
    }

    private FuncDef tryToParseFuncDef() {
        Type retType;
        if ((retType = tryToParseType()) != null) {

            if (lexer.getToken().getType() == Token.TokenType.ID) {
                String funcName = ((StringToken)lexer.getToken()).getValue();
                lexer.nextToken();

                if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
                    lexer.nextToken();

                    ArrayList<Declaration> args = tryToParseArgs();
                    if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                        lexer.nextToken();

                        Statement block;
                        if ((block = tryToParseBlockStatement()) != null) {
                            return new FuncDef(retType, new Identifier(funcName), args, (Block)block);
                        }
                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No block statement (after args in function definition)");
                    }
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ')' (after args in function definition)");
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '(' (after function name in function definition)");
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No function name (after returned type in function definition)");
        }

        return null;
    }

    private StructDef tryToParseStructDef() {
        if (lexer.getToken().getType() == Token.TokenType.STRUCT) {
            lexer.nextToken();

            if (lexer.getToken().getType() == Token.TokenType.ID) {
                String structTypeName = ((StringToken)lexer.getToken()).getValue();
                lexer.nextToken();

                if (lexer.getToken().getType() == Token.TokenType.L_BRACE) {
                    lexer.nextToken();

                    ArrayList<Declaration> structTypeBody;
                    if ((structTypeBody = tryToParseStructTypeBody()) != null) {

                        if (lexer.getToken().getType() == Token.TokenType.R_BRACE) {
                            lexer.nextToken();
                            return new StructDef(new Identifier(structTypeName), structTypeBody);
                        }
                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '}' (at the end of struct definition)");
                    }
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No fields (in struct definition)");
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '{' (after name of struct type in struct definition)");
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No name of struct type (in struct definition)");
        }

        return null;
    }

    private ArrayList<Statement> tryToParseStatement() {
        ArrayList<Statement> statements = new ArrayList<>();
        Statement statement;

        if ((statement = tryToParseIfOrIfElseStatement()) != null) {
            statements.add(statement);
            return statements;
        }
        if ((statement = tryToParseSwitchStatement()) != null) {
            statements.add(statement);
            return statements;
        }
        if ((statement = tryToParseWhileStatement()) != null) {
            statements.add(statement);
            return statements;
        }
        if ((statement = tryToParseReturnStatement()) != null) {
            statements.add(statement);
            return statements;
        }
        if ((statement = tryToParseBlockStatement()) != null) {
            statements.add(statement);
            return statements;
        }
        if ((statement = tryToParseEmptyStatement()) != null) {
            statements.add(statement);
            return statements;
        }

        return tryToParseFuncCallOrAssignmentOrDeclarationStatement();
    }

    private Statement tryToParseIfOrIfElseStatement() {
        if (lexer.getToken().getType() == Token.TokenType.IF) {
            lexer.nextToken();

            if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
                lexer.nextToken();

                Expression condition;
                if ((condition = tryToParseExpression()) != null) {

                    if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                        lexer.nextToken();

                        Statement ifStatement;
                        ArrayList<Statement> stmts;
                        if ((stmts = tryToParseStatement()) != null) {

                            if (stmts.size() != 1)
                                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No if body (after right parenthesis in if statement)");

                            ifStatement = stmts.get(0);
                            if (lexer.getToken().getType() != Token.TokenType.ELSE) {
                                return new If(condition, ifStatement);
                            }
                            lexer.nextToken();

                            Statement elseStatement;
                            if ((stmts = tryToParseStatement()) != null) {
                                if (stmts.size() != 1)
                                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No else body (after 'else' keyword in ifElse statement)");

                                elseStatement = stmts.get(0);
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
                            ArrayList<Statement> stmts;

                            ArrayList<Case> cases = new ArrayList<>();
                            while (lexer.getToken().getType() == Token.TokenType.CASE) {
                                lexer.nextToken();

                                Expression caseExp;
                                if ((caseExp = tryToParseExpression()) != null) {

                                    if (lexer.getToken().getType() == Token.TokenType.COLON) {
                                        lexer.nextToken();

                                        if ((stmts = tryToParseStatement()) != null) {

                                            if (stmts.size() != 1)
                                                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No statement (after expression in case in switch statement)");

                                            cases.add(new Case(caseExp, stmts.get(0)));
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
                                    if ((stmts = tryToParseStatement()) != null) {

                                        if (stmts.size() != 1)
                                            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No statement (after 'default' keyword in switch statement)");

                                        defaultStmt = stmts.get(0);
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

                Expression condition;
                if ((condition = tryToParseExpression()) != null) {

                    if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                        lexer.nextToken();

                        ArrayList<Statement> stmts;
                        if ((stmts = tryToParseStatement()) != null) {

                            if (stmts.size() != 1)
                                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No while body (after condition)");

                            return new While(condition, stmts.get(0));
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

    private Statement tryToParseReturnStatement() {
        if (lexer.getToken().getType() == Token.TokenType.RETURN) {
            lexer.nextToken();

            Expression expression = tryToParseExpression();
            if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
                lexer.nextToken();

                return new Return(expression);
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ';' (after expression in return statement)");
        }

        return null;
    }

    private Statement tryToParseBlockStatement() {
        if (lexer.getToken().getType() == Token.TokenType.L_BRACE) {
            lexer.nextToken();

            ArrayList<Statement> statements = new ArrayList<>();

            ArrayList<Statement> stmts;
            while ((stmts = tryToParseStatement()) != null)
                statements.addAll(stmts);

            if (lexer.getToken().getType() == Token.TokenType.R_BRACE) {
                lexer.nextToken();

                return new Block(statements);
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No '}' (after list of statements in block)");
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

    private ArrayList<Statement> tryToParseFuncCallOrAssignmentOrDeclarationStatement() {
        ArrayList<Statement> stmts = new ArrayList<>();
        Statement stmt;

        if (lexer.getToken().getType() == Token.TokenType.ID) {
            Expression expression = tryToParseExpression();

            if (expression instanceof FuncCall) {
                if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
                    lexer.nextToken();
                    stmts.add(expression);
                    return stmts;
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ';' (after function calling)");
            }

            if ((stmt = tryToParseAssignmentStatement(expression)) != null) {
                stmts.add(stmt);
                return stmts;
            }

            if (expression instanceof Identifier) {

                if ((stmts = tryToParseDeclarationsList(new StructType((Identifier)expression))) != null)
                    return stmts;
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "Wrong usage of identifier");
        }

        return tryToParseDeclarationsList();
    }

    private Statement tryToParseAssignmentStatement(Expression id) {
        if (lexer.getToken().getType() == Token.TokenType.ASSIGN) {
            lexer.nextToken();

            Expression expression;
            if ((expression = tryToParseExpression()) != null) {

                if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
                    lexer.nextToken();
                    return new Assignment(id, expression);
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ';' (at the end of assignment statement)");
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No expression on the right side of assignment");
        }

        return null;
    }

    private Statement tryToParseDeclarationStatement() {
        Declaration declaration;
        if ((declaration = tryToParseDeclaration()) != null) {

            if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
                lexer.nextToken();
                return declaration;
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ';' (at the end of declaration statement)");
        }

        return null;
    }

    private Declaration tryToParseDeclaration() {
        NonVoidType type;
        if ((type = tryToParseNonVoidType()) != null) {

            if (lexer.getToken().getType() == Token.TokenType.ID) {
                String id = ((StringToken) lexer.getToken()).getValue();
                lexer.nextToken();

                if (lexer.getToken().getType() != Token.TokenType.ASSIGN) {
                    return new OnlyDeclaration(type, new Identifier(id));
                }

                lexer.nextToken();

                Expression expression;
                if ((expression = tryToParseExpression()) != null) {
                    return new Initialisation(type, new Identifier(id), expression);
                }
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No expression (after '=' in initialisation)");
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No identifier (after type in declaration)");
        }

        return null;
    }

    private ArrayList<Statement> tryToParseDeclarationsList() {
        ArrayList<Statement> declarations = new ArrayList<>();

        Declaration declaration;
        if ((declaration = tryToParseDeclaration()) != null) {
            declarations.add(declaration);

            while (lexer.getToken().getType() == Token.TokenType.COMMA) {
                lexer.nextToken();
                if ((declaration = tryToParseDeclaration(declaration.getType())) != null)
                    declarations.add(declaration);
                else
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No variable name (after ',' in list of declarations)");
            }

            if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
                lexer.nextToken();
                return declarations;
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ';' (at the end of list of declarations)");
        }

        return null;
    }

    private ArrayList<Statement> tryToParseDeclarationsList(Type type) {
        ArrayList<Statement> declarations = new ArrayList<>();

        Declaration declaration;
        if ((declaration = tryToParseDeclaration(type)) != null) {
            declarations.add(declaration);

            while (lexer.getToken().getType() == Token.TokenType.COMMA) {
                lexer.nextToken();
                if ((declaration = tryToParseDeclaration(type)) != null)
                    declarations.add(declaration);
                else
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No variable name (after ',' in list of declarations)");
            }

            if (lexer.getToken().getType() == Token.TokenType.SEMICOLON) {
                lexer.nextToken();
                return declarations;
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ';' (at the end of list of declarations)");
        }

        return null;
    }

    private Declaration tryToParseDeclaration(Type type) {
        if (lexer.getToken().getType() == Token.TokenType.ID) {
            String id = ((StringToken) lexer.getToken()).getValue();
            lexer.nextToken();

            if (lexer.getToken().getType() != Token.TokenType.ASSIGN) {
                return new OnlyDeclaration(type, new Identifier(id));
            }

            lexer.nextToken();

            Expression expression;
            if ((expression = tryToParseExpression()) != null) {
                return new Initialisation(type, new Identifier(id), expression);
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No expression (after '=' in initialisation)");
        }

        return null;
    }

    private NonVoidType tryToParseNonVoidType() {
        if (lexer.getToken().getType() == Token.TokenType.BOOL) {
            lexer.nextToken();
            return new BoolType();
        }
        else if (lexer.getToken().getType() == Token.TokenType.STRING) {
            lexer.nextToken();
            return new StringType();
        }
        else if (lexer.getToken().getType() == Token.TokenType.INT) {
            lexer.nextToken();
            return new IntType();
        }
        else if (lexer.getToken().getType() == Token.TokenType.DOUBLE) {
            lexer.nextToken();
            return new DoubleType();
        }
        else if (lexer.getToken().getType() == Token.TokenType.ID) {
            String typeName = ((StringToken)lexer.getToken()).getValue();
            lexer.nextToken();
            return new StructType(new Identifier(typeName));
        }

        return null;
    }

    private ArrayList<Expression> tryToParseParams() {
        ArrayList<Expression> params = new ArrayList<>();

        Expression param;
        while ((param = tryToParseExpression()) != null) {
            params.add(param);

            if (lexer.getToken().getType() == Token.TokenType.COMMA)
                lexer.nextToken();
            else
                break;
        }

        if (params.isEmpty())
            return null;
        return params;
    }

    private Type tryToParseType() {
        Type type;
        if ((type = tryToParseNonVoidType()) != null)
            return type;

        if (lexer.getToken().getType() == Token.TokenType.VOID) {
            lexer.nextToken();
            return new VoidType();
        }

        return null;
    }

    private ArrayList<Declaration> tryToParseArgs() {
        ArrayList<Declaration> args = new ArrayList<>();

        Declaration arg;
        while ((arg = tryToParseDeclaration()) != null) {
            args.add(arg);

            if (lexer.getToken().getType() == Token.TokenType.COMMA)
                lexer.nextToken();
            else
                break;
        }

        if (args.isEmpty())
            return null;
        return args;
    }

    private ArrayList<Declaration> tryToParseStructTypeBody() {
        ArrayList<Declaration> fields = new ArrayList<>();

        Statement field;
        while((field = tryToParseDeclarationStatement()) != null) {
            fields.add((Declaration)field);
        }

        if (fields.isEmpty())
            return null;
        return fields;
    }



    private Expression tryToParseExpression() {
        return tryToParseOrExpression();
    }


    private OrExpression tryToParseOrExpression() {
        AndExpression andExpression;
        if ((andExpression = tryToParseAndExpression()) != null) {

            AlternativeExpression alternativeExpression;
            if ((alternativeExpression = tryToParseAlternativeExpression(andExpression)) != null) {
                return alternativeExpression;
            }

            return andExpression;
        }

        return null;
    }


    private AlternativeExpression tryToParseAlternativeExpression(AndExpression leftOperand) {
        if (lexer.getToken().getType() == Token.TokenType.ALTERNATIVE) {
            lexer.nextToken();

            OrExpression rightOperand;
            if ((rightOperand = tryToParseOrExpression()) != null) {
                return new AlternativeExpression(leftOperand, rightOperand);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '||' in alternative expression");
        }

        return null;
    }

    private AndExpression tryToParseAndExpression() {
        RelationExpression relationExpression;
        if ((relationExpression = tryToParseRelationExpression()) != null) {

            ConjunctionExpression conjunctionExpression;
            if ((conjunctionExpression = tryToParseConjunctionExpression(relationExpression)) != null) {
                return conjunctionExpression;
            }

            return relationExpression;
        }

        return null;
    }


    private ConjunctionExpression tryToParseConjunctionExpression(RelationExpression leftOperand) {
        if (lexer.getToken().getType() == Token.TokenType.CONJUNCTION) {
            lexer.nextToken();

            AndExpression rightOperand;
            if ((rightOperand = tryToParseAndExpression()) != null) {
                return new ConjunctionExpression(leftOperand, rightOperand);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '&&' in conjunction expression");
        }

        return null;
    }


    private RelationExpression tryToParseRelationExpression() {
        AdditionExpression additionExpression;
        if ((additionExpression = tryToParseAdditionExpression()) != null) {

            RelationExpression relationExpression;
            if ((relationExpression = tryToParseRelation(additionExpression)) != null) {
                return relationExpression;
            }

            return additionExpression;
        }

        return null;
    }

    private RelationExpression tryToParseRelation(AdditionExpression leftOperand) {
        RelationExpression rightOperand;

        if (lexer.getToken().getType() == Token.TokenType.EQ) {
            lexer.nextToken();

            if ((rightOperand = tryToParseRelationExpression()) != null) {
                return new EqualExpression(leftOperand, rightOperand);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '==' in 'equal' expression");
        }
        else if (lexer.getToken().getType() == Token.TokenType.NEQ) {
            lexer.nextToken();

            if ((rightOperand = tryToParseRelationExpression()) != null) {
                return new NotEqualExpression(leftOperand, rightOperand);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '!=' in 'not equal' expression");
        }
        else if (lexer.getToken().getType() == Token.TokenType.GEQT) {
            lexer.nextToken();

            if ((rightOperand = tryToParseRelationExpression()) != null) {
                return new GreaterEqualExpression(leftOperand, rightOperand);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '>=' in 'greater equal' expression");
        }
        else if (lexer.getToken().getType() == Token.TokenType.GT) {
            lexer.nextToken();

            if ((rightOperand = tryToParseRelationExpression()) != null) {
                return new GreaterExpression(leftOperand, rightOperand);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '>' in 'greater' expression");
        }
        else if (lexer.getToken().getType() == Token.TokenType.LEQT) {
            lexer.nextToken();

            if ((rightOperand = tryToParseRelationExpression()) != null) {
                return new LesserEqualExpression(leftOperand, rightOperand);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '<=' in 'lesser equal' expression");
        }
        else if (lexer.getToken().getType() == Token.TokenType.LT) {
            lexer.nextToken();

            if ((rightOperand = tryToParseRelationExpression()) != null) {
                return new LesserExpression(leftOperand, rightOperand);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '<' in 'lesser' expression");
        }

        return null;
    }


    private AdditionExpression tryToParseAdditionExpression() {
        MultiplicationExpression multiplicationExpression;
        if ((multiplicationExpression = tryToParseMultiplicationExpression()) != null) {

            AdditionExpression rightOperand;
            if (lexer.getToken().getType() == Token.TokenType.PLUS) {
                lexer.nextToken();

                if ((rightOperand = tryToParseAdditionExpression()) != null) {
                    return new AddExpression(multiplicationExpression, rightOperand);
                }

                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '+' in 'add' expression");
            }
            else if (lexer.getToken().getType() == Token.TokenType.MINUS) {
                lexer.nextToken();

                if ((rightOperand = tryToParseAdditionExpression()) != null) {
                    return new SubtractExpression(multiplicationExpression, rightOperand);
                }

                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '-' in 'subtract' expression");
            }

            return multiplicationExpression;
        }

        return null;
    }


    private MultiplicationExpression tryToParseMultiplicationExpression() {
        NegationExpression negationExpression;
        if ((negationExpression = tryToParseNegationExpression()) != null) {

            MultiplicationExpression rightOperand;
            if (lexer.getToken().getType() == Token.TokenType.MUL) {
                lexer.nextToken();

                if ((rightOperand = tryToParseMultiplicationExpression()) != null) {
                    return new MultExpression(negationExpression, rightOperand);
                }

                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '*' in 'mult' expression");
            }
            else if (lexer.getToken().getType() == Token.TokenType.DIV) {
                lexer.nextToken();

                if ((rightOperand = tryToParseMultiplicationExpression()) != null) {
                    return new DivExpression(negationExpression, rightOperand);
                }

                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '/' in 'div' expression");
            }
            else if (lexer.getToken().getType() == Token.TokenType.MOD) {
                lexer.nextToken();

                if ((rightOperand = tryToParseMultiplicationExpression()) != null) {
                    return new ModExpression(negationExpression, rightOperand);
                }

                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No right operand (after '%' in 'mod' expression");
            }

            return negationExpression;
        }

        return null;
    }


    private NegationExpression tryToParseNegationExpression() {
        SimpleExpression simpleExpression;

        if (lexer.getToken().getType() == Token.TokenType.NEGATION) {
            lexer.nextToken();

            if ((simpleExpression = tryToParseSimpleExpression()) != null) {
                return new NotExpression(simpleExpression);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No simple expression (after '!' in 'not' expression");
        }
        else if (lexer.getToken().getType() == Token.TokenType.MINUS) {
            lexer.nextToken();

            if ((simpleExpression = tryToParseSimpleExpression()) != null) {
                return new NegativeExpression(simpleExpression);
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No simple expression (after '-' in 'negative' expression");
        }
        else if ((simpleExpression = tryToParseSimpleExpression()) != null) {
            return simpleExpression;
        }

        return null;
    }


    private SimpleExpression tryToParseSimpleExpression() {
        if (lexer.getToken().getType() == Token.TokenType.TEXT) {
            String text = ((StringToken)lexer.getToken()).getValue();
            lexer.nextToken();
            return new StringValue(text);
        }
        else if (lexer.getToken().getType() == Token.TokenType.FALSE) {
            lexer.nextToken();
            return new FalseExpression();
        }
        else if (lexer.getToken().getType() == Token.TokenType.TRUE) {
            lexer.nextToken();
            return new TrueExpression();
        }
        else if (lexer.getToken().getType() == Token.TokenType.INT_NUMBER) {
            BigInteger intValue = ((IntToken)lexer.getToken()).getValue();
            lexer.nextToken();
            return new IntValue(intValue);
        }
        else if (lexer.getToken().getType() == Token.TokenType.DOUBLE_NUMBER) {
            BigDecimal doubleValue = ((DoubleToken)lexer.getToken()).getValue();
            lexer.nextToken();
            return new DoubleValue(doubleValue);
        }
        else if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
            lexer.nextToken();

            Expression expression;
            if ((expression = tryToParseExpression()) != null) {

                if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                    lexer.nextToken();
                    return new ParenthExpression(expression);
                }

                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ')' (after expression in simple 'parenth' expression");
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No expression (after '(' in simple 'parenth' expression");
        }
        else if (lexer.getToken().getType() == Token.TokenType.ID) {
            String id = ((StringToken)lexer.getToken()).getValue();
            lexer.nextToken();

            FuncCall funcCall;
            if ((funcCall = tryToParseFuncCall(new Identifier(id))) != null) {
                return funcCall;
            }

            StructFieldExpression structFieldExpression;
            if ((structFieldExpression = tryToParseStructFieldExpression(new Identifier(id))) != null) {
                return structFieldExpression;
            }

            return new Identifier(id);
        }

        return null;
    }

    private FuncCall tryToParseFuncCall(Identifier id) {
        if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
            lexer.nextToken();

            ArrayList<Expression> params = tryToParseParams();
            if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                lexer.nextToken();
                return new FuncCall(id, params);
            }
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No ')' (after list of params in function calling)");
        }

        return null;
    }

    private StructFieldExpression tryToParseStructFieldExpression(Identifier structVarName) {
        if (lexer.getToken().getType() == Token.TokenType.DOT) {
            lexer.nextToken();

            if (lexer.getToken().getType() == Token.TokenType.ID) {
                String id = ((StringToken)lexer.getToken()).getValue();
                lexer.nextToken();

                StructFieldExpression structFieldExpression;
                if ((structFieldExpression = tryToParseStructFieldExpression(new Identifier(id))) != null) {
                    return new StructFieldExpression(structVarName, structFieldExpression);
                }

                return new StructFieldExpression(structVarName, new Identifier(id));
            }

            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), "No field name (after '.' in struct type variable");
        }

        return null;
    }
}
