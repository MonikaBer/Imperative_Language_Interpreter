package project.parser;

import project.lexer.Lexer;
import project.program.Program;
import project.program.content.statements.Declaration;
import project.program.content.statements.Statement;
import project.program.content.statements.While;
import project.program.content.statements.Block;
import project.program.content.statements.Condition;
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
        ArrayList<Statement> statements = new ArrayList<>();

        Declaration declaration = tryToParseDeclaration();
        while (declaration != null) {
            declarations.add(declaration);
            declaration = tryToParseDeclaration();
        }

        Statement statement = tryToParseInstruction();
        while (statement != null) {
            statements.add(statement);
            statement = tryToParseInstruction();
        }

        // eventually check if EOF
        return new Program(declarations, statements);
    }

    private Statement tryToParseInstruction() {
        Statement statement = null;

        if ((statement = tryToParseIfInstruction()) != null)
            return statement;
        if ((statement = tryToParseIfElseInstruction()) != null)
            return statement;
        if ((statement = tryToParseSwitchInstruction()) != null)
            return statement;
        if ((statement = tryToParseWhileInstruction()) != null)
            return statement;
        if ((statement = tryToParseAssignmentOrFuncCallInstruction()) != null)
            return statement;

        return null;
    }

    private Statement tryToParseIfInstruction() {
        Statement statement = null;
        return null;
    }

    private Statement tryToParseIfElseInstruction() {
        Statement statement = null;
        return null;
    }

    private Statement tryToParseSwitchInstruction() {
        Statement statement = null;
        return null;
    }

    private Statement tryToParseWhileInstruction() {
        if (lexer.getToken().getType() == Token.TokenType.WHILE) {
            lexer.nextToken();

            if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
                lexer.nextToken();

                Condition condition;
                if ((condition = tryToParseCondition()) != null) {

                    if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                        lexer.nextToken();

                        Block block;
                        if ((block = tryToParseBlock()) != null) {
                            return new While(condition, block);
                        }
                        //return error
                    }
                    //return error
                }
                //return error
            }
            //return error
        }

        return null;
    }

    private Statement tryToParseAssignmentOrFuncCallInstruction() {
        if (lexer.getToken().getType() == Token.TokenType.ID) {
            String id = ((StringToken) lexer.getToken()).getValue();
            lexer.nextToken();

            Statement statement;
            if ((statement = tryToParseFuncCallInstruction(id)) != null)
                return statement;

            if ((statement = tryToParseAssignmentInstruction(id)) != null)
                return statement;

            //throw new SyntaxError();
        }

        return null;
    }

    private Condition tryToParseCondition() {
        Condition condition = null;
        return null;
    }

    private Block tryToParseBlock() {
        Block block = null;
        return null;
    }

    private Statement tryToParseFuncCallInstruction(String id) {
        Statement statement = null;
        return null;
    }

    private Statement tryToParseAssignmentInstruction(String id) {
        Statement statement = null;
        return null;
    }

    private Declaration tryToParseDeclaration() {
        Declaration declaration = null;

        return null;
    }
}
