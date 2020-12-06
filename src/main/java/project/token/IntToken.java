package project.token;

public class IntToken extends Token {

    private final int value;

    public IntToken(TokenType type, int position, int value) {
        super(type, position);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
