package project.token;

public class Token {

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

    private final TokenType type;
    private final String value;
    private final int position;

    public Token(TokenType type, String value, int position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }
}
