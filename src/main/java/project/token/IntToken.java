package project.token;

public class IntToken extends Token {

    private final int value;

    public IntToken(TokenType type, int position, int lineNr, int positionAtLine, int value) {
        super(type, position, lineNr, positionAtLine);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
