package project.lexer;

import project.source.Source;
import project.token.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

public class Lexer {

    private final Source source;
    private Token token;
    private Buffer buffer;
    private final HashMap<String, Token.TokenType> keywordsMap;


    public Lexer(Source source) {
        this.source = source;
        token = new PrimitiveToken(null,-1, -1, -1);
        buffer = new Buffer();
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

    public void nextToken() {
        skipWhitespacesAndComments();

        if (token.getType() == Token.TokenType.UNDEFINED)
            return;

        if (buffer.length() == 0 && source.isEOT()) {
            token = new PrimitiveToken(Token.TokenType.EOT, source.getPosition(), source.getLineNr(), source.getPositionAtLine());
            return;
        }

        if (tryToBuildIdOrKeyword())
            return;
        if (tryToBuildIntegerOrDouble())
            return;
        if (tryToBuildToBuildStringConst())
            return;
        if (tryToBuildSingleOrDoubleChar())
            return;

        token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
    }

    public Token getToken() {
        return token;
    }

    private void skipWhitespacesAndComments() {
        boolean isWhitespaceOrComment = true;
        while (isWhitespaceOrComment) {
            if (Character.isWhitespace(getChar())) {
                advance();
                buffer.clear();
                continue;
            }

            if (getChar() == '#') {
                advance();
                while (getChar() != '\n') {
                    if (source.isEOT())
                        return;
                    advance();
                }
                advance();
                buffer.clear();
                continue;
            }

            if (getChar() == '/') {
                buffer.append(getChar());
                advance();

                if (getChar() == '/') {  // single line comment
                    advance();

                    while (getChar() != '\n') {
                        if (source.isEOT()) {
                            buffer.clear();
                            return;
                        }
                        advance();
                    }
                    advance();
                    buffer.clear();
                    continue;
                }
                else if (getChar() == '*') {  // multilinear comment
                    buffer.append(getChar());
                    advance();

                    boolean ifInsideMultilinearComment = true;
                    while (ifInsideMultilinearComment) {
                        if (source.isEOT()) {
                            token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                            buffer.clearReadPart();
                            return;
                        }

                        if (getChar() == '*') {
                            buffer.append(getChar());
                            advance();
                            if (getChar() == '/') {
                                advance();
                                ifInsideMultilinearComment = false;
                                buffer.clear();
                                continue;
                            }
                            continue;
                        }

                        // character is not '*'
                        buffer.append(getChar());
                        advance();
                        continue;
                    }
                    buffer.clear();
                    continue;
                }

                // it is not comment or whitespace
                return;
            }

            isWhitespaceOrComment = false;
        }
    }

    private boolean tryToBuildIdOrKeyword() {
        buffer.resetIdx();
        if (!Character.isLetter(getChar()) && getChar() != '_')
            return false;

        buffer.append(getChar());
        advance();

        while ((Character.isLetterOrDigit(getChar()) || getChar() == '_')) {
            buffer.append(getChar());
            advance();
        }


        if (keywordsMap.containsKey(buffer.get())) {
            token = new PrimitiveToken(keywordsMap.get(buffer.get()), source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
            buffer.clearReadPart();
            return true;
        }

        token = new StringToken(Token.TokenType.ID, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx(), buffer.get());
        buffer.clearReadPart();
        return true;
    }

    private boolean tryToBuildIntegerOrDouble() {
        buffer.resetIdx();
        if (!Character.isDigit(getChar()))
            return false;

        // 0...
        if (getChar() == '0') {
            buffer.append(getChar());
            advance();

            // integer -> 0
            if (getChar() != '.') {
                try {
                    BigInteger intNumber = new BigInteger(buffer.get());
                    token = new IntToken(Token.TokenType.INT_NUMBER, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx(), intNumber);
                } catch (NumberFormatException ex) {
                    token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            }

            // double -> 0. ...
            tryToBuildFraction();
            return true;
        }

        // [1-9]...
        while (Character.isDigit(getChar())) {
            buffer.append(getChar());
            advance();
        }

        // integer
        if (getChar() != '.') {
            try {
                BigInteger intNumber = new BigInteger(buffer.get());
                token = new IntToken(Token.TokenType.INT_NUMBER, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx(), intNumber);
            } catch (NumberFormatException ex) {
                token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
            }
            buffer.clearReadPart();
            return true;
        }

        // double -> integer. ...
        tryToBuildFraction();
        return true;
    }

    private void tryToBuildFraction() {
        buffer.append(getChar());
        advance();

        if (Character.isDigit(getChar())) {
            buffer.append(getChar());
            advance();
        } else {
            token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
            buffer.clearReadPart();
            return;
        }

        while (Character.isDigit(getChar())) {
            buffer.append(getChar());
            advance();
        }

        try {
            BigDecimal doubleNumber = new BigDecimal(buffer.get());
            token = new DoubleToken(Token.TokenType.DOUBLE_NUMBER, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx(), doubleNumber);
        } catch (NumberFormatException ex) {
            token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.toString().length());
        }
        buffer.clearReadPart();
    }

    private boolean tryToBuildToBuildStringConst() {
        buffer.resetIdx();
        boolean isSingleQuote;

        if (getChar() == '"') {
            isSingleQuote = false;
        } else if (getChar() == '\'') {
            isSingleQuote = true;
        } else {
            return false;
        }

        advance();

        while (getChar() != '"' && getChar() != '\'') {
            if (getChar() == '\n' || source.isEOT()) {
                token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx() - 1, source.getLineNr(), source.getPositionAtLine() - buffer.getIdx() - 1);
                buffer.clearReadPart();
                advance();
                return true;
            }
            if (getChar() == '\\') {
                advance();
                if (getChar() == 'n') {
                    buffer.append('\n');
                }
                else if (getChar() == '\'') {
                    buffer.append('\'');
                }
                else if (getChar() == '"') {
                    buffer.append('"');
                }
                else {
                    buffer.append('\\');
                    buffer.append(getChar());
                }
            } else {
                buffer.append(getChar());
            }
            advance();
        }

        if ((getChar() == '"' && !isSingleQuote) || (getChar() == '\'' && isSingleQuote)) {
            advance();
            token = new StringToken(Token.TokenType.TEXT, source.getPosition() - buffer.getIdx() - 2, source.getLineNr(), source.getPositionAtLine() - buffer.getIdx() - 2, buffer.get());
            buffer.clearReadPart();
            return true;
        }

        token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx() - 2, source.getLineNr(), source.getPositionAtLine() - buffer.getIdx() - 2);
        buffer.clearReadPart();
        return true;
    }

    private boolean tryToBuildSingleOrDoubleChar() {
        buffer.resetIdx();
        switch (getChar()) {
            case '(':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.L_PARENTH, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case ')':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.R_PARENTH, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case '{':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.L_BRACE, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case '}':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.R_BRACE, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case '=':
                buffer.append(getChar());
                advance();
                if (getChar() == '=') {
                    buffer.append(getChar());
                    advance();
                    token = new PrimitiveToken(Token.TokenType.EQ, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                } else {
                    token = new PrimitiveToken(Token.TokenType.ASSIGN, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            case '+':
                buffer.append(getChar());
                advance();
                if (getChar() == '+') {
                    buffer.append(getChar());
                    advance();
                    token = new PrimitiveToken(Token.TokenType.INC, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                } else {
                    token = new PrimitiveToken(Token.TokenType.PLUS, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            case '-':
                buffer.append(getChar());
                advance();
                if (getChar() == '-') {
                    buffer.append(getChar());
                    advance();
                    token = new PrimitiveToken(Token.TokenType.DEC, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                } else {
                    token = new PrimitiveToken(Token.TokenType.MINUS, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            case '*':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.MUL, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case '/':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.DIV, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case '%':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.MOD, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case '>':
                buffer.append(getChar());
                advance();
                if (getChar() == '=') {
                    buffer.append(getChar());
                    advance();
                    token = new PrimitiveToken(Token.TokenType.GEQT, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                } else {
                    token = new PrimitiveToken(Token.TokenType.GT, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            case '<':
                buffer.append(getChar());
                advance();
                if (getChar() == '=') {
                    buffer.append(getChar());
                    advance();
                    token = new PrimitiveToken(Token.TokenType.LEQT, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                } else {
                    token = new PrimitiveToken(Token.TokenType.LT, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            case '!':
                buffer.append(getChar());
                advance();
                if (getChar() == '=') {
                    buffer.append(getChar());
                    advance();
                    token = new PrimitiveToken(Token.TokenType.NEQ, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                } else {
                    token = new PrimitiveToken(Token.TokenType.NEGATION, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            case '|':
                buffer.append(getChar());
                advance();
                if (getChar() == '|') {
                    buffer.append(getChar());
                    advance();
                    token = new PrimitiveToken(Token.TokenType.ALTERNATIVE, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                } else {
                    token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            case '&':
                buffer.append(getChar());
                advance();
                if (getChar() == '&') {
                    buffer.append(getChar());
                    advance();
                    token = new PrimitiveToken(Token.TokenType.CONJUNCTION, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                } else {
                    token = new PrimitiveToken(Token.TokenType.UNDEFINED, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                }
                buffer.clearReadPart();
                return true;
            case ';':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.SEMICOLON, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case '.':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.DOT, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case ',':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.COMMA, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            case ':':
                buffer.append(getChar());
                advance();
                token = new PrimitiveToken(Token.TokenType.COLON, source.getPosition() - buffer.getIdx(), source.getLineNr(), source.getPositionAtLine() - buffer.getIdx());
                buffer.clearReadPart();
                return true;
            default:;
        }

        return false;
    }

    private char getChar() {
        char character = buffer.getChar();
        if (character != 0x03)
            return character;

        return source.getChar();
    }

    private void advance() {
        if (buffer.getIdx() < buffer.length()) {
            buffer.incrementIdx();
            return;
        }
        source.advance();
    }
}
