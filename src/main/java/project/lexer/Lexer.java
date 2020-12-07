package project.lexer;

import project.source.Source;
import project.token.*;
import project.types.Result;

import java.io.IOException;
import java.util.HashMap;

public class Lexer {

    private final int MAX_ID_LENGTH = 1048576;
    private final int MAX_STR_LENGTH = 1048576;
    private final int MAX_NUMBER_LENGTH = 50;

    private final Source source;
    private Token token;
    private int position;
    private int lineNr;
    private int positionAtLine;
    private int passedCharsCount;
    private final HashMap<String, Token.TokenType> keywordsMap;


    public Lexer(Source source) {
        this.source = source;
        token = new PrimitiveToken(Token.TokenType.UNDEFINED,-1, -1, -1);
        position = -1;
        lineNr = 0;
        positionAtLine = 0;
        keywordsMap = new HashMap<>();

        keywordsMap.put("true", Token.TokenType.TRUE);
        keywordsMap.put("false", Token.TokenType.FALSE);
        keywordsMap.put("if", Token.TokenType.IF);
        keywordsMap.put("else", Token.TokenType.ELSE);
        keywordsMap.put("switch", Token.TokenType.SWITCH);
        keywordsMap.put("case", Token.TokenType.CASE);
        keywordsMap.put("default", Token.TokenType.DEFAULT);
        keywordsMap.put("while", Token.TokenType.WHILE);
        keywordsMap.put("return", Token.TokenType.RETURN);
        keywordsMap.put("int", Token.TokenType.INT);
        keywordsMap.put("double", Token.TokenType.DOUBLE);
        keywordsMap.put("bool", Token.TokenType.BOOL);
        keywordsMap.put("string", Token.TokenType.STRING);
        keywordsMap.put("void", Token.TokenType.VOID);
        keywordsMap.put("struct", Token.TokenType.STRUCT);
    }

    public Result nextToken() {
        passedCharsCount = 0;

        try {
            if (!skipWhitespacesAndComments())
                return Result.OK;
        } catch (IOException ex) {
            System.out.println("[lexer] Problem during opening/reading from/closing file source");
            return Result.IO_ERROR;
        }

        position = source.getPosition();

        if (source.isEOT()) {
            token = new PrimitiveToken(Token.TokenType.EOT, position, lineNr, positionAtLine);
            return Result.OK;
        }

        try {

            if (tryToBuildIdOrKeyword())
                return Result.OK;;
            if (tryToBuildIntegerOrDouble())
                return Result.OK;;
            if (tryToBuildToBuildStringConst())
                return Result.OK;;
            if (tryToBuildSingleOrDoubleChar())
                return Result.OK;;

        } catch (IOException ex) {
            System.out.println("[lexer] Problem during opening/reading from/closing file source");
            return Result.IO_ERROR;
        }

        token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
        advancePositionAtLine();
        return Result.OK;
    }

    public Token getToken() {
        return token;
    }

    // returns false if DIV token found
    private boolean skipWhitespacesAndComments() throws IOException {
        boolean isWhitespaceOrComment = true;
        while (isWhitespaceOrComment) {
            if (Character.isWhitespace(source.getChar())) {
                if (source.getChar() == '\n') {
                    goToNewLineAndAdvanceSource();
                    continue;
                }
                advancePositionAtLineAndSource();
                continue;
            }

            if (source.getChar() == '#') {
                advancePositionAtLineAndSource();
                while (source.getChar() != '\n') {
                    if (source.isEOT())
                        return true;
                    advancePositionAtLineAndSource();
                }
                goToNewLineAndAdvanceSource();
                continue;
            }

            position = source.getPosition();

            if (source.getChar() == '/') {
                passOneCharAndAdvanceSource();

                if (source.getChar() == '/') {  // single line comment
                    advancePositionAtLineAndSource(1);

                    while (source.getChar() != '\n') {
                        if (source.isEOT())
                            return true;
                        advancePositionAtLineAndSource();
                    }
                    goToNewLineAndAdvanceSource();
                    continue;
                }
                else if (source.getChar() == '*') {  // multilinear comment
                    advancePositionAtLineAndSource(1);

                    boolean ifInsideMultilinearComment = true;
                    while (ifInsideMultilinearComment) {
                        if (source.isEOT())
                            return true;

                        if (source.getChar() != '*') {
                            if (source.getChar() == '\n') {
                                goToNewLineAndAdvanceSource();
                                continue;
                            }
                            advancePositionAtLineAndSource();
                            continue;
                        }

                        // character is '*'
                        advancePositionAtLineAndSource();
                        if (source.getChar() != '/') {
                            if (source.getChar() == '\n') {
                                goToNewLine();
                            }
                            continue;
                        }

                        // another character is '/'
                        advancePositionAtLineAndSource();
                        ifInsideMultilinearComment = false;
                    }
                    continue;
                }
                else {  // div token found
                    token = new PrimitiveToken(Token.TokenType.DIV, position, lineNr, positionAtLine);
                    advancePositionAtLine();
                    return false;
                }
            }

            isWhitespaceOrComment = false;
        }

        return true;
    }

    private boolean tryToBuildIdOrKeyword() throws IOException {
        if (!Character.isLetter(source.getChar()) && source.getChar() != '_')
            return false;

        StringBuilder builder = new StringBuilder();
        appendBuilderAndPassOneCharAndAdvanceSource(builder);
        while ((Character.isLetterOrDigit(source.getChar()) || source.getChar() == '_')) {
            if (builder.length() >= MAX_ID_LENGTH) {
                token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            }
            appendBuilderAndPassOneCharAndAdvanceSource(builder);
        }

        if (keywordsMap.containsKey(builder.toString())) {
            token = new PrimitiveToken(keywordsMap.get(builder.toString()), position, lineNr, positionAtLine);
            advancePositionAtLine();
            return true;
        }

        token = new StringToken(Token.TokenType.ID, position, lineNr, positionAtLine, builder.toString());
        advancePositionAtLine();

        return true;
    }

    private boolean tryToBuildIntegerOrDouble() throws IOException {
        if (!Character.isDigit(source.getChar()))
            return false;

        StringBuilder builder = new StringBuilder();

        // 0...
        if (source.getChar() == '0') {
            appendBuilderAndPassOneCharAndAdvanceSource(builder);

            // integer -> 0
            if (source.getChar() != '.') {
                try {
                    int intNumber = Integer.parseInt(builder.toString());
                    token = new IntToken(Token.TokenType.INT_NUMBER, position, lineNr, positionAtLine, intNumber);
                } catch (NumberFormatException ex) {
                    token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            }

            // double -> 0. ...
            tryToBuildFraction(builder);
            return true;
        }

        // [1-9]...
        while (Character.isDigit(source.getChar())) {
            if (builder.length() >= MAX_NUMBER_LENGTH) {
                token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            }
            appendBuilderAndPassOneCharAndAdvanceSource(builder);
        }

        // integer
        if (source.getChar() != '.') {
            try {
                int intNumber = Integer.parseInt(builder.toString());
                token = new IntToken(Token.TokenType.INT_NUMBER, position, lineNr, positionAtLine, intNumber);
            } catch (NumberFormatException ex) {
                token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
            }
            advancePositionAtLine();
            return true;
        }

        // double -> integer. ...
        tryToBuildFraction(builder);
        return true;
    }

    private void tryToBuildFraction(StringBuilder builder) throws IOException {
        appendBuilderAndPassOneCharAndAdvanceSource(builder);

        if (Character.isDigit(source.getChar())) {
            appendBuilderAndPassOneCharAndAdvanceSource(builder);
        } else {
            token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
            advancePositionAtLine();
            return;
        }

        while (Character.isDigit(source.getChar())) {
            if (builder.length() >= MAX_NUMBER_LENGTH) {
                token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return;
            }
            appendBuilderAndPassOneCharAndAdvanceSource(builder);
        }

        try {
            double doubleNumber = Double.parseDouble(builder.toString());
            token = new DoubleToken(Token.TokenType.DOUBLE_NUMBER, position, lineNr, positionAtLine, doubleNumber);
        } catch (NumberFormatException ex) {
            token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
        }

        advancePositionAtLine();
    }

    private boolean tryToBuildToBuildStringConst() throws IOException {
        boolean isSingleQuote;

        if (source.getChar() == '"') {
            isSingleQuote = false;
        } else if (source.getChar() == '\'') {
            isSingleQuote = true;
        } else {
            return false;
        }

        StringBuilder builder = new StringBuilder();
        passOneCharAndAdvanceSource();

        while (source.getChar() != '"' && source.getChar() != '\'') {
            if (builder.length() >= MAX_STR_LENGTH) {
                token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            }
            appendBuilderAndPassOneCharAndAdvanceSource(builder);
        }

        if (source.getChar() == '"' && !isSingleQuote) {
            passOneCharAndAdvanceSource();
            token = new StringToken(Token.TokenType.TEXT, position, lineNr, positionAtLine, builder.toString());
            advancePositionAtLine();
            return true;
        } else if (source.getChar() == '\'' && isSingleQuote) {
            passOneCharAndAdvanceSource();
            token = new StringToken(Token.TokenType.TEXT, position, lineNr, positionAtLine, builder.toString());
            advancePositionAtLine();
            return true;
        }

        token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
        advancePositionAtLine();
        return true;
    }

    private boolean tryToBuildSingleOrDoubleChar() throws IOException {
        switch (source.getChar()) {
            case '(':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.L_PARENTH, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case ')':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.R_PARENTH, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case '{':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.L_BRACE, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case '}':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.R_BRACE, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case '=':
                passOneCharAndAdvanceSource();
                if (source.getChar() == '=') {
                    passOneCharAndAdvanceSource();
                    token = new PrimitiveToken(Token.TokenType.EQ, position, lineNr, positionAtLine);
                } else {
                    token = new PrimitiveToken(Token.TokenType.ASSIGN, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            case '+':
                passOneCharAndAdvanceSource();
                if (source.getChar() == '+') {
                    passOneCharAndAdvanceSource();
                    token = new PrimitiveToken(Token.TokenType.POSTINC, position, lineNr, positionAtLine);
                } else {
                    token = new PrimitiveToken(Token.TokenType.PLUS, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            case '-':
                passOneCharAndAdvanceSource();
                if (source.getChar() == '-') {
                    passOneCharAndAdvanceSource();
                    token = new PrimitiveToken(Token.TokenType.POSTDEC, position, lineNr, positionAtLine);
                } else {
                    token = new PrimitiveToken(Token.TokenType.MINUS, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            case '*':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.MUL, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case '%':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.MOD, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case '>':
                passOneCharAndAdvanceSource();
                if (source.getChar() == '=') {
                    passOneCharAndAdvanceSource();
                    token = new PrimitiveToken(Token.TokenType.GEQT, position, lineNr, positionAtLine);
                } else {
                    token = new PrimitiveToken(Token.TokenType.GT, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            case '<':
                passOneCharAndAdvanceSource();
                if (source.getChar() == '=') {
                    passOneCharAndAdvanceSource();
                    token = new PrimitiveToken(Token.TokenType.LEQT, position, lineNr, positionAtLine);
                } else {
                    token = new PrimitiveToken(Token.TokenType.LT, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            case '!':
                passOneCharAndAdvanceSource();
                if (source.getChar() == '=') {
                    passOneCharAndAdvanceSource();
                    token = new PrimitiveToken(Token.TokenType.NEQ, position, lineNr, positionAtLine);
                } else {
                    token = new PrimitiveToken(Token.TokenType.NEGATION, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            case '|':
                passOneCharAndAdvanceSource();
                if (source.getChar() == '|') {
                    passOneCharAndAdvanceSource();
                    token = new PrimitiveToken(Token.TokenType.ALTERNATIVE, position, lineNr, positionAtLine);
                } else {
                    token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            case '&':
                passOneCharAndAdvanceSource();
                if (source.getChar() == '&') {
                    passOneCharAndAdvanceSource();
                    token = new PrimitiveToken(Token.TokenType.CONJUNCTION, position, lineNr, positionAtLine);
                } else {
                    token = new PrimitiveToken(Token.TokenType.UNDEFINED, position, lineNr, positionAtLine);
                }
                advancePositionAtLine();
                return true;
            case ';':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.SEMICOLON, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case '.':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.DOT, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case ',':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.COMMA, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            case ':':
                passOneCharAndAdvanceSource();
                token = new PrimitiveToken(Token.TokenType.COLON, position, lineNr, positionAtLine);
                advancePositionAtLine();
                return true;
            default:;
        }

        return false;
    }


    private void advancePositionAtLine() {
        positionAtLine += passedCharsCount;
        passedCharsCount = 0;
    }

    private void advancePositionAtLineAndSource(int number) throws IOException {
        positionAtLine += passedCharsCount + number;
        passedCharsCount = 0;
        source.advance();
    }

    private void passOneCharAndAdvanceSource() throws IOException {
        ++passedCharsCount;
        source.advance();
    }

    private void appendBuilderAndPassOneCharAndAdvanceSource(StringBuilder builder) throws IOException {
        builder.append(source.getChar());
        ++passedCharsCount;
        source.advance();
    }

    private void advancePositionAtLineAndSource() throws IOException {
        ++positionAtLine;
        source.advance();
    }

    private void goToNewLineAndAdvanceSource() throws IOException {
        ++lineNr;
        positionAtLine = 0;
        source.advance();
    }

    private void goToNewLine() {
        ++lineNr;
        positionAtLine = 0;
    }
}
