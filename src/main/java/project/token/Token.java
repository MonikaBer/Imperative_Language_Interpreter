package project.token;

public abstract class Token implements IToken {

    public enum TokenType { ID, TEXT, INT_NUMBER, DOUBLE_NUMBER, TRUE, FALSE,
                            IF, ELSE, SWITCH, CASE, DEFAULT, WHILE,
                            ASSIGN, RETURN,
                            L_PARENTH, R_PARENTH, L_BRACE, R_BRACE,
                            INT, DOUBLE, BOOL, STRING, VOID, STRUCT,
                            EQ, NEQ, GEQT, GT, LEQT, LT, PLUS, MINUS, POSTINC, POSTDEC, MUL, DIV, MOD,
                            ALTERNATIVE, CONJUNCTION, NEGATION,
                            SEMICOLON, DOT, COLON, COMMA,
                            EOT, UNDEFINED
                            };

    protected TokenType type;
    protected int position;

    public Token(TokenType type, int position) {
        this.type = type;
        this.position = position;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public int getPosition() {
        return position;
    }
}
