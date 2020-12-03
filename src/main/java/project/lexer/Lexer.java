package project.lexer;

import project.source.Source;
import project.token.Token;

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
        token = new Token(Token.TokenType.UNDEFINED, null,-1);
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

    public void nextToken() throws IOException {
        if (!skipWhitespacesAndComments())
            return;

        position = source.getPosition();

        if (source.isEOT()) {
            token = new Token(Token.TokenType.EOT, null, position);
            return;
        }

        if (tryToBuildIdOrKeyword())
            return;
        if (tryToBuildInteger())
            return;
        if (tryToBuildDouble())
            return;
        if (tryToBuildToBuildStringConst())
            return;
        if (tryToBuildSingleOrDoubleChar())
            return;

        token = new Token(Token.TokenType.UNDEFINED, null, position);
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
                        token = new Token(Token.TokenType.UNDEFINED, null, position);
                        return false;
                    }
                }
                else {  // div token found
                    token = new Token(Token.TokenType.DIV, null, position);
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
            token = new Token(keywordsMap.get(builder.toString()), null, position);
            return true;
        }

        token = new Token(Token.TokenType.ID, builder.toString(), position);
        return true;
    }

    private boolean tryToBuildInteger() throws IOException {
        if (source.getChar() == '0') {
            token = new Token(Token.TokenType.INT_NUMBER, String.valueOf(source.getChar()), position);
            source.advance();
            return true;
        }

        if (Character.isDigit(source.getChar())) {
            StringBuilder builder = new StringBuilder();
            builder.append(source.getChar());
            source.advance();

            while (Character.isDigit(source.getChar()) && builder.length() < MAX_NUMBER_LENGTH) {
                builder.append(source.getChar());
                source.advance();
            }

            token = new Token(Token.TokenType.INT_NUMBER, builder.toString(), position);
            return true;
        }

        return false;
    }

    private boolean tryToBuildDouble() throws IOException {
        if (!Character.isDigit(source.getChar()))
            return false;

        StringBuilder builder = new StringBuilder();

        // 0.(...)
        if (source.getChar() == '0') {
            builder.append(source.getChar());
            source.advance();

            if (source.getChar() == '.') {
                builder.append(source.getChar());
                source.advance();

                if (Character.isDigit(source.getChar())) {
                    builder.append(source.getChar());
                    source.advance();
                } else {
                    return false;
                }

                while (Character.isDigit(source.getChar()) && builder.length() < MAX_NUMBER_LENGTH) {
                    builder.append(source.getChar());
                    source.advance();
                }

                token = new Token(Token.TokenType.DOUBLE_NUMBER, builder.toString(), position);
                return true;
            }

            return false;
        }

        // integer.(...)
        while (Character.isDigit(source.getChar()) && builder.length() < MAX_NUMBER_LENGTH) {
            builder.append(source.getChar());
            source.advance();
        }

        if (source.getChar() == '.'  && builder.length() < MAX_NUMBER_LENGTH) {
            builder.append(source.getChar());
            source.advance();
        } else {
            return false;
        }

        if (Character.isDigit(source.getChar()) && builder.length() < MAX_NUMBER_LENGTH) {
            builder.append(source.getChar());
            source.advance();
        } else {
            return false;
        }

        while (Character.isDigit(source.getChar()) && builder.length() < MAX_NUMBER_LENGTH) {
            builder.append(source.getChar());
            source.advance();
        }

        token = new Token(Token.TokenType.DOUBLE_NUMBER, builder.toString(), position);

        return true;
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
            token = new Token(Token.TokenType.TEXT, builder.toString(), position);
            return true;
        } else if (source.getChar() == '\'' && isSingleQuote) {
            source.advance();
            token = new Token(Token.TokenType.TEXT, builder.toString(), position);
            return true;
        }

        return false;
    }

    private boolean tryToBuildSingleOrDoubleChar() throws IOException {
        switch (source.getChar()) {
            case '(':
                source.advance();
                token = new Token(Token.TokenType.L_PARENTH, null, position);
                return true;
            case ')':
                source.advance();
                token = new Token(Token.TokenType.R_PARENTH, null, position);
                return true;
            case '{':
                source.advance();
                token = new Token(Token.TokenType.L_BRACE, null, position);
                return true;
            case '}':
                source.advance();
                token = new Token(Token.TokenType.R_BRACE, null, position);
                return true;
            case '=':
                source.advance();
                if (source.getChar() == '=') {
                    source.advance();
                    token = new Token(Token.TokenType.EQ, null, position);
                } else {
                    token = new Token(Token.TokenType.ASSIGN, null, position);
                }
                return true;
            case '+':
                source.advance();
                if (source.getChar() == '+') {
                    source.advance();
                    token = new Token(Token.TokenType.POSTINC, null, position);
                } else {
                    token = new Token(Token.TokenType.PLUS, null, position);
                }
                return true;
            case '-':
                source.advance();
                if (source.getChar() == '-') {
                    source.advance();
                    token = new Token(Token.TokenType.POSTDEC, null, position);
                } else {
                    token = new Token(Token.TokenType.MINUS, null, position);
                }
                return true;
            case '*':
                source.advance();
                token = new Token(Token.TokenType.MUL, null, position);
                return true;
            case '%':
                source.advance();
                token = new Token(Token.TokenType.MOD, null, position);
                return true;
            case '>':
                source.advance();
                if (source.getChar() == '=') {
                    source.advance();
                    token = new Token(Token.TokenType.GEQT, null, position);
                } else {
                    token = new Token(Token.TokenType.GT, null, position);
                }
                return true;
            case '<':
                source.advance();
                if (source.getChar() == '=') {
                    source.advance();
                    token = new Token(Token.TokenType.LEQT, null, position);
                } else {
                    token = new Token(Token.TokenType.LT, null, position);
                }
                return true;
            case '!':
                source.advance();
                if (source.getChar() == '=') {
                    source.advance();
                    token = new Token(Token.TokenType.NEQ, null, position);
                } else {
                    token = new Token(Token.TokenType.NEGATION, null, position);
                }
                return true;
            case '|':
                source.advance();
                if (source.getChar() == '|') {
                    source.advance();
                    token = new Token(Token.TokenType.ALTERNATIVE, null, position);
                } else {
                    token = new Token(Token.TokenType.UNDEFINED, null, position);
                }
                return true;
            case '&':
                source.advance();
                if (source.getChar() == '&') {
                    source.advance();
                    token = new Token(Token.TokenType.CONJUNCTION, null, position);
                } else {
                    token = new Token(Token.TokenType.UNDEFINED, null, position);
                }
                return true;
            case ';':
                source.advance();
                token = new Token(Token.TokenType.SEMICOLON, null, position);
                return true;
            case '.':
                source.advance();
                token = new Token(Token.TokenType.DOT, null, position);
                return true;
            case ',':
                source.advance();
                token = new Token(Token.TokenType.COMMA, null, position);
                return true;
            case ':':
                source.advance();
                token = new Token(Token.TokenType.COLON, null, position);
                return true;
            default:;
        }

        return false;
    }
}
