package project.token;

public abstract class Token implements IToken {

    public enum TokenType { ID, TEXT, INT_NUMBER, DOUBLE_NUMBER, TRUE, FALSE,
                            IF, ELSE, SWITCH, CASE, DEFAULT, WHILE,
                            ASSIGN, RETURN,
                            L_PARENTH, R_PARENTH, L_BRACE, R_BRACE,
                            INT, DOUBLE, BOOL, STRING, VOID, STRUCT,
                            EQ, NEQ, GEQT, GT, LEQT, LT, PLUS, MINUS, PREINC, PREDEC, MUL, DIV, MOD,
                            ALTERNATIVE, CONJUNCTION, NEGATION,
                            SEMICOLON, DOT, COLON, COMMA,
                            EOT, UNDEFINED
                            };

    protected TokenType type;
    protected int position;
    protected int lineNr;
    protected int positionAtLine;

    public Token(TokenType type, int position, int lineNr, int positionAtLine) {
        this.type = type;
        this.position = position;
        this.lineNr = lineNr;
        this.positionAtLine = positionAtLine;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getLineNr() {
        return lineNr;
    }

    @Override
    public int getPositionAtLine() {
        return positionAtLine;
    }
}
