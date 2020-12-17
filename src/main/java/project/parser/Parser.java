package project.parser;

import project.lexer.Lexer;
import project.program.Program;
import project.program.content.Declaration;
import project.program.content.instructions.Instruction;
import project.program.content.instructions.While;
import project.program.content.others.Block;
import project.program.content.others.Condition;
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
        ArrayList<Instruction> instructions = new ArrayList<>();

        Declaration declaration = tryToParseDeclaration();
        while (declaration != null) {
            declarations.add(declaration);
            declaration = tryToParseDeclaration();
        }

        Instruction instruction = tryToParseInstruction();
        while (instruction != null) {
            instructions.add(instruction);
            instruction = tryToParseInstruction();
        }

        // eventually check if EOF
        return new Program(declarations, instructions);
    }

    private Instruction tryToParseInstruction() {
        Instruction instruction = null;

        if ((instruction = tryToParseIfInstruction()) != null)
            return instruction;
        if ((instruction = tryToParseIfElseInstruction()) != null)
            return instruction;
        if ((instruction = tryToParseSwitchInstruction()) != null)
            return instruction;
        if ((instruction = tryToParseWhileInstruction()) != null)
            return instruction;
        if ((instruction = tryToParseAssignmentOrFuncCallInstruction()) != null)
            return instruction;

        return null;
    }

    private Instruction tryToParseIfInstruction() {
        Instruction instruction = null;
        return null;
    }

    private Instruction tryToParseIfElseInstruction() {
        Instruction instruction = null;
        return null;
    }

    private Instruction tryToParseSwitchInstruction() {
        Instruction instruction = null;
        return null;
    }

    private Instruction tryToParseWhileInstruction() {
        if (lexer.getToken().getType() == Token.TokenType.WHILE) {
            lexer.nextToken();

            if (lexer.getToken().getType() == Token.TokenType.L_PARENTH) {
                lexer.nextToken();

                Condition condition;
                if ((condition = tryToParseCondition()) != null) {

                    if (lexer.getToken().getType() == Token.TokenType.R_PARENTH) {
                        lexer.nextToken();

                        if (lexer.getToken().getType() == Token.TokenType.L_BRACE) {
                            lexer.nextToken();

                            Block block;
                            if ((block = tryToParseBlock()) != null) {

                                if (lexer.getToken().getType() == Token.TokenType.R_BRACE) {
                                    lexer.nextToken();
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
                //return error
            }
            //return error
        }

        return null;
    }

    private Instruction tryToParseAssignmentOrFuncCallInstruction() {
        if (lexer.getToken().getType() == Token.TokenType.ID) {
            String id = ((StringToken) lexer.getToken()).getValue();
            lexer.nextToken();

            Instruction instruction;
            if ((instruction = tryToParseFuncCallInstruction(id)) != null)
                return instruction;

            if ((instruction = tryToParseAssignmentInstruction(id)) != null)
                return instruction;

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

    private Instruction tryToParseFuncCallInstruction(String id) {
        Instruction instruction = null;
        return null;
    }

    private Instruction tryToParseAssignmentInstruction(String id) {
        Instruction instruction = null;
        return null;
    }

    private Declaration tryToParseDeclaration() {
        Declaration declaration = null;

        return null;
    }
}
