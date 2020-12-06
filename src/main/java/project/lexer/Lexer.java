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

    private Source source;
    private Token token;
    private int position;
    private HashMap<String, Token.TokenType> keywordsMap;


    public Lexer(Source source) {
        this.source = source;
        token = new PrimitiveToken(Token.TokenType.UNDEFINED,-1);
        position = -1;
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
        try {
            if (!skipWhitespacesAndComments())
                return Result.OK;
        } catch (IOException ex) {
            System.out.println("[lexer] Problem during opening/reading from/closing file source");
            return Result.IO_ERROR;
        }


        position = source.getPosition();

        if (source.isEOT()) {
            token = new PrimitiveToken(Token.TokenType.EOT, position);
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

        token = new PrimitiveToken(Token.TokenType.UNDEFINED, position);
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
                source.advance();
                continue;
            }

            if (source.getChar() == '#') {
                source.advance();
                while (source.getChar() != '\n') {
                    if (source.isEOT())
                        return true;
                    source.advance();
                }
                source.advance();
                continue;
            }

            position = source.getPosition();

            if (source.getChar() == '/') {
                source.advance();

                if (source.getChar() == '/') {  // single line comment
                    source.advance();
                    while (source.getChar() != '\n')
                        if (source.isEOT())
                            return true;
                        source.advance();
                    source.advance();
                    continue;
                }
                else if (source.getChar() == '*') {  // multi lines comment
                    source.advance();
                    while (source.getChar() != '*') {
                        source.advance();
                        if (source.isEOT())
                            return true;
                    }
                    source.advance();
                    if (source.getChar() == '/') {
                        source.advance();
                        continue;
                    } else {
                        if (source.isEOT())
                            return true;
                        token = new PrimitiveToken(Token.TokenType.UNDEFINED, position);
                        return false;
                    }
                }
                else {  // div token found
                    token = new PrimitiveToken(Token.TokenType.DIV, position);
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
        builder.append(source.getChar());
        source.advance();
        while ((Character.isLetterOrDigit(source.getChar()) || source.getChar() == '_')
                && builder.length() < MAX_ID_LENGTH) {

            builder.append(source.getChar());
            source.advance();
        }

        if (keywordsMap.containsKey(builder.toString())) {
            token = new PrimitiveToken(keywordsMap.get(builder.toString()), position);
            return true;
        }

        token = new StringToken(Token.TokenType.ID, position, builder.toString());
        return true;
    }

    private boolean tryToBuildIntegerOrDouble() throws IOException {
        if (!Character.isDigit(source.getChar()))
            return false;

        StringBuilder builder = new StringBuilder();

        // 0...
        if (source.getChar() == '0') {
            builder.append(source.getChar());
            source.advance();

            // integer -> 0
            if (source.getChar() != '.') {
                int intNumber = Integer.parseInt(builder.toString());
                token = new IntToken(Token.TokenType.INT_NUMBER, position, intNumber);
                return true;
            }

            // double -> 0. ...
            tryToBuildFraction(builder);
            return true;
        }

        // [1-9]...
        while (Character.isDigit(source.getChar()) && builder.length() < MAX_NUMBER_LENGTH) {
            builder.append(source.getChar());
            source.advance();
        }

        // integer
        if (source.getChar() != '.') {
            int intNumber = Integer.parseInt(builder.toString());
            token = new IntToken(Token.TokenType.INT_NUMBER, position, intNumber);
            return true;
        }

        // double -> integer. ...
        tryToBuildFraction(builder);
        return true;
    }

    private void tryToBuildFraction(StringBuilder builder) throws IOException {
        builder.append(source.getChar());
        source.advance();

        if (Character.isDigit(source.getChar())) {
            builder.append(source.getChar());
            source.advance();
        } else {
            token = new PrimitiveToken(Token.TokenType.UNDEFINED, position);
        }

        while (Character.isDigit(source.getChar()) && builder.length() < MAX_NUMBER_LENGTH) {
            builder.append(source.getChar());
            source.advance();
        }

        try {
            double doubleNumber = Double.parseDouble(builder.toString());
            token = new DoubleToken(Token.TokenType.DOUBLE_NUMBER, position, doubleNumber);
        } catch (Exception ex) {
            token = new PrimitiveToken(Token.TokenType.UNDEFINED, position);
        }
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
        source.advance();

        while (source.getChar() != '"' && source.getChar() != '\'' && builder.length() < MAX_STR_LENGTH) {
            builder.append(source.getChar());
            source.advance();
        }

        if (source.getChar() == '"' && !isSingleQuote) {
            source.advance();
            token = new StringToken(Token.TokenType.TEXT, position, builder.toString());
            return true;
        } else if (source.getChar() == '\'' && isSingleQuote) {
            source.advance();
            token = new StringToken(Token.TokenType.TEXT, position, builder.toString());
            return true;
        }

        token = new PrimitiveToken(Token.TokenType.UNDEFINED, position);
        return true;
    }

    private boolean tryToBuildSingleOrDoubleChar() throws IOException {
        switch (source.getChar()) {
            case '(':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.L_PARENTH, position);
                return true;
            case ')':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.R_PARENTH, position);
                return true;
            case '{':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.L_BRACE, position);
                return true;
            case '}':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.R_BRACE, position);
                return true;
            case '=':
                source.advance();
                if (source.getChar() == '=') {
                    source.advance();
                    token = new PrimitiveToken(Token.TokenType.EQ, position);
                } else {
                    token = new PrimitiveToken(Token.TokenType.ASSIGN, position);
                }
                return true;
            case '+':
                source.advance();
                if (source.getChar() == '+') {
                    source.advance();
                    token = new PrimitiveToken(Token.TokenType.POSTINC, position);
                } else {
                    token = new PrimitiveToken(Token.TokenType.PLUS, position);
                }
                return true;
            case '-':
                source.advance();
                if (source.getChar() == '-') {
                    source.advance();
                    token = new PrimitiveToken(Token.TokenType.POSTDEC, position);
                } else {
                    token = new PrimitiveToken(Token.TokenType.MINUS, position);
                }
                return true;
            case '*':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.MUL, position);
                return true;
            case '%':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.MOD, position);
                return true;
            case '>':
                source.advance();
                if (source.getChar() == '=') {
                    source.advance();
                    token = new PrimitiveToken(Token.TokenType.GEQT, position);
                } else {
                    token = new PrimitiveToken(Token.TokenType.GT, position);
                }
                return true;
            case '<':
                source.advance();
                if (source.getChar() == '=') {
                    source.advance();
                    token = new PrimitiveToken(Token.TokenType.LEQT, position);
                } else {
                    token = new PrimitiveToken(Token.TokenType.LT, position);
                }
                return true;
            case '!':
                source.advance();
                if (source.getChar() == '=') {
                    source.advance();
                    token = new PrimitiveToken(Token.TokenType.NEQ, position);
                } else {
                    token = new PrimitiveToken(Token.TokenType.NEGATION, position);
                }
                return true;
            case '|':
                source.advance();
                if (source.getChar() == '|') {
                    source.advance();
                    token = new PrimitiveToken(Token.TokenType.ALTERNATIVE, position);
                } else {
                    token = new PrimitiveToken(Token.TokenType.UNDEFINED, position);
                }
                return true;
            case '&':
                source.advance();
                if (source.getChar() == '&') {
                    source.advance();
                    token = new PrimitiveToken(Token.TokenType.CONJUNCTION, position);
                } else {
                    token = new PrimitiveToken(Token.TokenType.UNDEFINED, position);
                }
                return true;
            case ';':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.SEMICOLON, position);
                return true;
            case '.':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.DOT, position);
                return true;
            case ',':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.COMMA, position);
                return true;
            case ':':
                source.advance();
                token = new PrimitiveToken(Token.TokenType.COLON, position);
                return true;
            default:;
        }

        return false;
    }
}
