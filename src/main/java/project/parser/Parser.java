package project.parser;

import project.exceptions.SyntaxError;
import project.lexer.Lexer;
import project.program.Program;
import project.program.content.FuncDef;
import project.program.content.ProgramContent;
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
        ArrayList<ProgramContent> programContents = new ArrayList<>();

        ArrayList<Statement> declarationsStatement;
        FuncDef funcDef;
        StructDef structDef;

        while (lexer.getToken().getType() != Token.TokenType.EOT) {
            if ((structDef = tryToParseStructDef()) != null) {
                structDefs.add(structDef);
                programContents.add(structDef);
                continue;
            }

            Type type;
            if ((type = tryToParseType()) != null) {

                String desc = "Unknown language construction";
                StringToken token = (StringToken)consumeToken(Token.TokenType.ID, desc);
                String id = token.getValue();
                int lineNr = token.getLineNr();
                int posAtLine = token.getPositionAtLine();

                if ((funcDef = tryToParseFuncDef(type, id, lineNr, posAtLine)) != null) {
                    funcDefs.add(funcDef);
                    programContents.add(funcDef);
                    continue;
                }

                if ((declarationsStatement = tryToParseDeclarationsList(type, id, lineNr, posAtLine)) == null)
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);

                for (Statement declaration : declarationsStatement) {
                    declarations.add((Declaration) declaration);
                    programContents.add(declaration);
                }
            }
        }

        return new Program(declarations, funcDefs, structDefs, programContents);
    }

    private FuncDef tryToParseFuncDef(Type retType, String funcName, int lineNr, int posAtLine) {
        if (expectToken(Token.TokenType.L_PARENTH) == null)
            return null;

        ArrayList<Declaration> args = tryToParseArgs();
        consumeToken(Token.TokenType.R_PARENTH, "No ')' (after args in function definition)");

        Statement block;
        if ((block = tryToParseBlockStatement()) != null) {
            Identifier id = new Identifier(funcName, lineNr, posAtLine);
            return new FuncDef(retType, id, args, (Block)block);
        }

        String desc = "No block statement (after args in function definition)";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private StructDef tryToParseStructDef() {
        if (expectToken(Token.TokenType.STRUCT) == null)
            return null;

        Token token = consumeToken(Token.TokenType.ID, "No name of struct type (in struct definition)");
        String structTypeName = ((StringToken) token).getValue();
        int lineNr = token.getLineNr();
        int posAtLine = token.getPositionAtLine();

        consumeToken(Token.TokenType.L_BRACE, "No '{' (after name of struct type in struct definition)");

        ArrayList<Declaration> structTypeBody;
        if ((structTypeBody = tryToParseStructTypeBody()) != null) {

            consumeToken(Token.TokenType.R_BRACE, "No '}' (at the end of struct definition)");
            Identifier id = new Identifier(structTypeName, lineNr, posAtLine);
            return new StructDef(id, structTypeBody);
        }

        String desc = "No fields (in struct definition)";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private ArrayList<Statement> tryToParseStatement(String errorDesc) {
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
        if ((statement = tryToParseIncrementStatement()) != null) {
            statements.add(statement);
            return statements;
        }
        if ((statement = tryToParseDecrementStatement()) != null) {
            statements.add(statement);
            return statements;
        }
        if ((statements = tryToParseFuncCallOrAssignmentOrDeclarationStatement()) != null) {
            return statements;
        }

        if (errorDesc == null)
            return null;

        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), errorDesc);
    }

    private Statement tryToParseIfOrIfElseStatement() {
        if (expectToken(Token.TokenType.IF) == null)
            return null;

        consumeToken(Token.TokenType.L_PARENTH, "No '(' (after 'if' keyword)");

        String desc;
        Expression condition;
        if ((condition = tryToParseExpression()) != null) {

            consumeToken(Token.TokenType.R_PARENTH, "No ')' (after condition)");

            Statement ifStatement;
            ArrayList<Statement> stmts;
            stmts = tryToParseStatement("No statement (after 'if' keyword)");
            if (stmts.size() != 1) {
                desc = "No if body (after right parenthesis in if statement)";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }

            ifStatement = stmts.get(0);
            if (expectToken(Token.TokenType.ELSE) == null)
                return new If(condition, ifStatement);

            Statement elseStatement;
            stmts = tryToParseStatement("No statement (after right parenthesis)");
            if (stmts.size() != 1) {
                desc = "No else body (after 'else' keyword in ifElse statement)";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }

            elseStatement = stmts.get(0);
            return new IfElse(condition, ifStatement, elseStatement);
        }

        desc = "No condition (after left parenthesis)";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private Statement tryToParseSwitchStatement() {
        if (expectToken(Token.TokenType.SWITCH) == null)
            return null;

        consumeToken(Token.TokenType.L_PARENTH, "No '(' (after 'switch' keyword)");

        String desc;
        Expression switchExp;
        if ((switchExp = tryToParseExpression()) != null) {

            consumeToken(Token.TokenType.R_PARENTH, "No ')' (after expression)");
            consumeToken(Token.TokenType.L_BRACE, "No '{' (after right parenthesis)");

            ArrayList<Statement> stmts;
            ArrayList<Case> cases = new ArrayList<>();

            while (expectToken(Token.TokenType.CASE) != null) {

                Expression caseExp;
                if ((caseExp = tryToParseExpression()) != null) {

                    consumeToken(Token.TokenType.COLON, "No ':' (after case expression)");

                    stmts = tryToParseStatement("No statement (after colon in case)");

                    if (stmts.size() != 1) {
                        desc = "No statement (after expression in case in switch statement)";
                        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
                    }

                    cases.add(new Case(caseExp, stmts.get(0)));
                    continue;
                }

                desc = "No expression (after 'case' keyword)";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }

            if (cases.isEmpty()) {
                desc = "No case (in switch statement)";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }

            consumeToken(Token.TokenType.DEFAULT, "No 'default' keyword (after list of cases in switch)");
            consumeToken(Token.TokenType.COLON, "No ':' (after 'default' keyword)");

            Statement defaultStmt;
            stmts = tryToParseStatement("No statement (after colon in default)");

            if (stmts.size() != 1) {
                desc = "No statement (after 'default' keyword in switch statement)";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }

            defaultStmt = stmts.get(0);

            consumeToken(Token.TokenType.R_BRACE, "No '}' (after default statement)");

            return new Switch(switchExp, cases, defaultStmt);
        }

        desc = "No expression (after left parenthesis)";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private Statement tryToParseWhileStatement() {
        if (expectToken(Token.TokenType.WHILE) == null)
            return null;

        consumeToken(Token.TokenType.L_PARENTH, "No '(' (after 'while' keyword)");

        String desc;
        Expression condition;
        if ((condition = tryToParseExpression()) != null) {

            consumeToken(Token.TokenType.R_PARENTH, "No ')' (after condition)");

            ArrayList<Statement> stmts;
            stmts = tryToParseStatement("No statement (after right parenthesis)");

            if (stmts.size() != 1) {
                desc = "No while body (after condition)";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }

            return new While(condition, stmts.get(0));
        }

        desc = "No condition (after left parenthesis)";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private Statement tryToParseReturnStatement() {
        if (expectToken(Token.TokenType.RETURN) == null)
            return null;

        Expression expression = tryToParseExpression();
        consumeToken(Token.TokenType.SEMICOLON, "No ';' (after expression in return statement)");

        if (expression != null)
            return new Return(expression);

        return new VoidReturn();
    }

    private Statement tryToParseBlockStatement() {
        if (expectToken(Token.TokenType.L_BRACE)  == null)
            return null;

        ArrayList<Statement> statements = new ArrayList<>();
        ArrayList<Statement> stmts;

        while ((stmts = tryToParseStatement(null)) != null)
            statements.addAll(stmts);

        consumeToken(Token.TokenType.R_BRACE, "Incorrect statement in block");
        if (statements.isEmpty())
            statements = null;

        return new Block(statements);
    }

    private Statement tryToParseEmptyStatement() {
        if (expectToken((Token.TokenType.SEMICOLON)) == null)
            return null;

        return new Empty();
    }

    private Statement tryToParseIncrementStatement() {
        if (expectToken(Token.TokenType.INC) == null)
            return null;

        if (lexer.getToken().getType() == Token.TokenType.ID) {
            Expression expression = tryToParseExpression();
            consumeToken(Token.TokenType.SEMICOLON, "No ';' (at the end of increment statement)");

            return new Increment(expression);
        }

        String desc = "No identifier (after '++' in increment statement)";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private Statement tryToParseDecrementStatement() {
        if (expectToken(Token.TokenType.DEC) == null)
            return null;

        if (lexer.getToken().getType() == Token.TokenType.ID) {
            Expression expression = tryToParseExpression();
            consumeToken(Token.TokenType.SEMICOLON, "No ';' (at the end of decrement statement)");

            return new Decrement(expression);
        }

        String desc = "No identifier (after '--' in decrement statement)";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private ArrayList<Statement> tryToParseFuncCallOrAssignmentOrDeclarationStatement() {
        ArrayList<Statement> stmts = new ArrayList<>();
        Statement stmt;

        if (lexer.getToken().getType() == Token.TokenType.ID) {
            Expression expression = tryToParseExpression();

            if (expression instanceof FuncCall) {
                consumeToken(Token.TokenType.SEMICOLON, "No ';' (after function calling)");
                stmts.add(expression);
                return stmts;
            }

            if ((stmt = tryToParseAssignmentStatement(expression)) != null) {
                stmts.add(stmt);
                return stmts;
            }

            if (expression instanceof Identifier) {
                if ((stmts = tryToParseDeclarationsList(
                                            new StructType((Identifier)expression), null, -1, -1)
                                            ) != null)
                    return stmts;
            }

            String desc = "Wrong usage of identifier";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }

        return tryToParseDeclarationsList();
    }

    private Statement tryToParseAssignmentStatement(Expression id) {
        if (expectToken(Token.TokenType.ASSIGN) == null)
            return null;

        Expression expression;
        if ((expression = tryToParseExpression()) != null) {
            consumeToken(Token.TokenType.SEMICOLON, "No ';' (at the end of assignment statement)");

            return new Assignment(id, expression);
        }

        String desc = "No expression on the right side of assignment";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private Statement tryToParseDeclarationStatement() {
        Declaration declaration;
        if ((declaration = tryToParseDeclaration()) != null) {
            consumeToken(Token.TokenType.SEMICOLON, "No ';' (at the end of declaration statement)");

            return declaration;
        }

        return null;
    }

    private Declaration tryToParseDeclaration() {
        NonVoidType type;
        if ((type = tryToParseNonVoidType()) != null) {

            String desc = "No identifier (after type in declaration)";
            StringToken idToken = (StringToken)consumeToken(Token.TokenType.ID, desc);
            String id = idToken.getValue();
            int lineNr = idToken.getLineNr();
            int posAtLine = idToken.getPositionAtLine();

            if (expectToken(Token.TokenType.ASSIGN) == null)
                return new OnlyDeclaration(type, new Identifier(id, lineNr, posAtLine));

            Expression expression;
            if ((expression = tryToParseExpression()) != null)
                return new Initialisation(type, new Identifier(id, lineNr, posAtLine), expression);

            desc = "No expression (after '=' in initialisation)";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }

        return null;
    }

    private ArrayList<Statement> tryToParseDeclarationsList() {
        ArrayList<Statement> declarations = new ArrayList<>();

        String desc;
        Declaration declaration;
        if ((declaration = tryToParseDeclaration()) != null) {
            declarations.add(declaration);

            while (expectToken(Token.TokenType.COMMA) != null) {
                if ((declaration = tryToParseDeclaration(declaration.getType(), null, -1, -1)) != null) {
                    declarations.add(declaration);
                } else {
                    desc = "No variable name (after ',' in list of declarations)";
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
                }
            }

            consumeToken(Token.TokenType.SEMICOLON, "No ';' (at the end of list of declarations)");

            return declarations;
        }

        return null;
    }

    private ArrayList<Statement> tryToParseDeclarationsList(Type type, String id, int lineNr, int posAtLine) {
        ArrayList<Statement> declarations = new ArrayList<>();

        String desc;
        Declaration declaration;
        if ((declaration = tryToParseDeclaration(type, id, lineNr, posAtLine)) != null) {
            declarations.add(declaration);

            while (expectToken(Token.TokenType.COMMA) != null) {
                if ((declaration = tryToParseDeclaration(type, null, -1, -1)) != null) {
                    declarations.add(declaration);
                } else {
                    desc = "No variable name (after ',' in list of declarations)";
                    throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
                }
            }

            consumeToken(Token.TokenType.SEMICOLON, "No ';' (at the end of list of declarations)");

            return declarations;
        }

        return null;
    }

    private Declaration tryToParseDeclaration(Type type, String id, int lineNr, int posAtLine) {
        if (id == null) {
            if (lexer.getToken().getType() != Token.TokenType.ID)
                return null;

            StringToken idToken = (StringToken) consumeToken(Token.TokenType.ID, null);
            id = idToken.getValue();
            lineNr = idToken.getLineNr();
            posAtLine = idToken.getPositionAtLine();
        }

        if (expectToken(Token.TokenType.ASSIGN) == null)
            return new OnlyDeclaration(type, new Identifier(id, lineNr, posAtLine));

        Expression expression;
        if ((expression = tryToParseExpression()) != null)
            return new Initialisation(type, new Identifier(id, lineNr, posAtLine), expression);

        String desc = "No expression (after '=' in initialisation)";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
    }

    private NonVoidType tryToParseNonVoidType() {
        if (expectToken(Token.TokenType.BOOL) != null)
            return new BoolType();

        if (expectToken(Token.TokenType.STRING) != null)
            return new StringType();

        if (expectToken(Token.TokenType.INT) != null)
            return new IntType();

        if (expectToken(Token.TokenType.DOUBLE) != null)
            return new DoubleType();

        Token id;
        if ((id = expectToken(Token.TokenType.ID)) != null) {
            String typeName = ((StringToken) id).getValue();
            return new StructType(new Identifier(typeName, id.getLineNr(), id.getPositionAtLine()));
        }

        return null;
    }

    private ArrayList<Expression> tryToParseParams() {
        ArrayList<Expression> params = new ArrayList<>();

        Expression param;
        while ((param = tryToParseExpression()) != null) {
            params.add(param);

           if (expectToken(Token.TokenType.COMMA) == null)
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

        Token token;
        if ((token = expectToken(Token.TokenType.VOID)) != null)
            return new VoidType(token.getLineNr(), token.getPositionAtLine());

        return null;
    }

    private ArrayList<Declaration> tryToParseArgs() {
        ArrayList<Declaration> args = new ArrayList<>();

        Declaration arg;
        while ((arg = tryToParseDeclaration()) != null) {
            args.add(arg);

            if (expectToken(Token.TokenType.COMMA) == null)
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
        if (expectToken(Token.TokenType.ALTERNATIVE) == null)
           return null;

        OrExpression rightOperand;
        if ((rightOperand = tryToParseOrExpression()) != null)
            return new AlternativeExpression(leftOperand, rightOperand, leftOperand.getLineNr(), leftOperand.getPositionAtLine());

        String desc = "No right operand (after '||' in alternative expression";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
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
        if (expectToken(Token.TokenType.CONJUNCTION) == null)
            return null;

        AndExpression rightOperand;
        if ((rightOperand = tryToParseAndExpression()) != null) {
            return new ConjunctionExpression(leftOperand, rightOperand, leftOperand.getLineNr(), leftOperand.getPositionAtLine());
        }

        String desc = "No right operand (after '&&' in conjunction expression";
        throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
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

        String desc;
        if (expectToken(Token.TokenType.EQ) != null) {
            if ((rightOperand = tryToParseRelationExpression()) != null)
                return new EqualExpression(leftOperand, rightOperand, leftOperand.getLineNr(), leftOperand.getPositionAtLine());

            desc = "No right operand (after '==' in 'equal' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }
        else if (expectToken(Token.TokenType.NEQ) != null) {
            if ((rightOperand = tryToParseRelationExpression()) != null)
                return new NotEqualExpression(leftOperand, rightOperand, leftOperand.getLineNr(), leftOperand.getPositionAtLine());

            desc = "No right operand (after '!=' in 'not equal' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }
        else if (expectToken(Token.TokenType.GEQT) != null) {
            if ((rightOperand = tryToParseRelationExpression()) != null)
                return new GreaterEqualExpression(leftOperand, rightOperand, leftOperand.getLineNr(), leftOperand.getPositionAtLine());

            desc = "No right operand (after '>=' in 'greater equal' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }
        else if (expectToken(Token.TokenType.GT) != null) {
            if ((rightOperand = tryToParseRelationExpression()) != null)
                return new GreaterExpression(leftOperand, rightOperand, leftOperand.getLineNr(), leftOperand.getPositionAtLine());

            desc = "No right operand (after '>' in 'greater' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }
        else if (expectToken(Token.TokenType.LEQT) != null) {
            if ((rightOperand = tryToParseRelationExpression()) != null)
                return new LesserEqualExpression(leftOperand, rightOperand, leftOperand.getLineNr(), leftOperand.getPositionAtLine());

            desc = "No right operand (after '<=' in 'lesser equal' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }
        else if (expectToken(Token.TokenType.LT) != null) {
            if ((rightOperand = tryToParseRelationExpression()) != null)
                return new LesserExpression(leftOperand, rightOperand, leftOperand.getLineNr(), leftOperand.getPositionAtLine());

            desc = "No right operand (after '<' in 'lesser' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }

        return null;
    }


    private AdditionExpression tryToParseAdditionExpression() {
        MultiplicationExpression multiplicationExpression;
        if ((multiplicationExpression = tryToParseMultiplicationExpression()) != null) {
            int lineNr = multiplicationExpression.getLineNr();
            int posAtLine = multiplicationExpression.getPositionAtLine();

            String desc;
            AdditionExpression rightOperand;
            if (expectToken(Token.TokenType.PLUS) != null) {
                if ((rightOperand = tryToParseAdditionExpression()) != null) {
                    return new AddExpression(multiplicationExpression, rightOperand, lineNr, posAtLine);
                }

                desc = "No right operand (after '+' in 'add' expression";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }
            else if (expectToken(Token.TokenType.MINUS) != null) {
                if ((rightOperand = tryToParseAdditionExpression()) != null)
                    return new SubtractExpression(multiplicationExpression, rightOperand, lineNr, posAtLine);

                desc = "No right operand (after '-' in 'subtract' expression";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }

            return multiplicationExpression;
        }

        return null;
    }


    private MultiplicationExpression tryToParseMultiplicationExpression() {
        NegationExpression negationExpression;
        if ((negationExpression = tryToParseNegationExpression()) != null) {
            int lineNr = negationExpression.getLineNr();
            int posAtLine = negationExpression.getPositionAtLine();

            String desc;
            MultiplicationExpression rightOperand;
            if (expectToken(Token.TokenType.MUL) != null) {
                if ((rightOperand = tryToParseMultiplicationExpression()) != null)
                    return new MultExpression(negationExpression, rightOperand, lineNr, posAtLine);

                desc = "No right operand (after '*' in 'mult' expression";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }
            else if (expectToken(Token.TokenType.DIV) != null) {
                if ((rightOperand = tryToParseMultiplicationExpression()) != null)
                    return new DivExpression(negationExpression, rightOperand, lineNr, posAtLine);

                desc = "No right operand (after '/' in 'div' expression";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }
            else if (expectToken(Token.TokenType.MOD) != null) {
                if ((rightOperand = tryToParseMultiplicationExpression()) != null)
                    return new ModExpression(negationExpression, rightOperand, lineNr, posAtLine);

                desc = "No right operand (after '%' in 'mod' expression";
                throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
            }

            return negationExpression;
        }

        return null;
    }


    private NegationExpression tryToParseNegationExpression() {
        SimpleExpression simpleExpression;
        String desc;

        Token token;
        if ((token = expectToken(Token.TokenType.NEGATION)) != null) {
            if ((simpleExpression = tryToParseSimpleExpression()) != null)
                return new NotExpression(simpleExpression, token.getLineNr(), token.getPositionAtLine());

            desc = "No simple expression (after '!' in 'not' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }
        else if ((token = expectToken(Token.TokenType.MINUS)) != null) {
            if ((simpleExpression = tryToParseSimpleExpression()) != null)
                return new NegativeExpression(simpleExpression, token.getLineNr(), token.getPositionAtLine());

            desc = "No simple expression (after '-' in 'negative' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }
        else if ((simpleExpression = tryToParseSimpleExpression()) != null) {
            return simpleExpression;
        }

        return null;
    }


    private SimpleExpression tryToParseSimpleExpression() {
        String desc;
        Token token;

        if (lexer.getToken().getType() == Token.TokenType.TEXT) {
            token = consumeToken(Token.TokenType.TEXT, null);
            String text = ((StringToken)token).getValue();
            return new StringValue(text, token.getLineNr(), token.getPositionAtLine());
        }
        else if ((token = expectToken(Token.TokenType.FALSE)) != null) {
            return new FalseExpression(token.getLineNr(), token.getPositionAtLine());
        }
        else if ((token = expectToken(Token.TokenType.TRUE)) != null) {
            return new TrueExpression(token.getLineNr(), token.getPositionAtLine());
        }
        else if ((token = expectToken(Token.TokenType.INT_NUMBER)) != null) {
            BigInteger intValue = ((IntToken) token).getValue();
            return new IntValue(intValue, token.getLineNr(), token.getPositionAtLine());
        }
        else if ((token = expectToken(Token.TokenType.DOUBLE_NUMBER)) != null) {
            BigDecimal doubleValue = ((DoubleToken) token).getValue();
            return new DoubleValue(doubleValue, token.getLineNr(), token.getPositionAtLine());
        }
        else if ((token = expectToken(Token.TokenType.L_PARENTH)) != null) {
            Expression expression;
            if ((expression = tryToParseExpression()) != null) {
                desc = "No ')' (after expression in simple 'parenth' expression";
                consumeToken(Token.TokenType.R_PARENTH, desc);

                return new ParenthExpression(expression, token.getLineNr(), token.getPositionAtLine());
            }

            desc = "No expression (after '(' in simple 'parenth' expression";
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);
        }
        else if ((token = expectToken(Token.TokenType.ID)) != null) {
            String id = ((StringToken) token).getValue();
            int lineNr = token.getLineNr();
            int posAtLine = token.getPositionAtLine();

            FuncCall funcCall;
            if ((funcCall = tryToParseFuncCall(new Identifier(id, lineNr, posAtLine))) != null)
                return funcCall;

            StructFieldExpression structFieldExpression;
            if ((structFieldExpression = tryToParseStructFieldExpression(new Identifier(id, lineNr, posAtLine))) != null)
                return structFieldExpression;

            return new Identifier(id, lineNr, posAtLine);
        }

        return null;
    }

    private FuncCall tryToParseFuncCall(Identifier id) {
        if (expectToken(Token.TokenType.L_PARENTH) == null)
            return null;

        ArrayList<Expression> params = tryToParseParams();
        consumeToken(Token.TokenType.R_PARENTH, "No ')' (after list of params in function calling)");

        return new FuncCall(id, params, id.getLineNr(), id.getPositionAtLine());
 }

    private StructFieldExpression tryToParseStructFieldExpression(Identifier structVarName) {
        if (expectToken(Token.TokenType.DOT) == null)
            return null;

        String desc = "No field name (after '.' in struct type variable";
        StringToken token = (StringToken)consumeToken(Token.TokenType.ID, desc);
        String id = token.getValue();
        int lineNr = token.getLineNr();
        int posAtLine = token.getPositionAtLine();

        StructFieldExpression structFieldExpression;
        if ((structFieldExpression = tryToParseStructFieldExpression(new Identifier(id, lineNr, posAtLine))) != null)
            return new StructFieldExpression(structVarName, structFieldExpression,
                                            structVarName.getLineNr(), structVarName.getPositionAtLine());

        return new StructFieldExpression(structVarName, new Identifier(id, lineNr, posAtLine),
                                        structVarName.getLineNr(), structVarName.getPositionAtLine());
    }


    private Token expectToken(Token.TokenType tokenType) {
        if (lexer.getToken().getType() != tokenType)
            return null;

        Token token = lexer.getToken();
        lexer.nextToken();
        return token;
    }

    private Token consumeToken(Token.TokenType tokenType, String desc) {
        if (lexer.getToken().getType() != tokenType)
            throw new SyntaxError(lexer.getToken().getLineNr(), lexer.getToken().getPositionAtLine(), desc);

        Token token = lexer.getToken();
        lexer.nextToken();
        return token;
    }
}
