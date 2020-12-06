package project.token;

public class StringToken extends Token {

    private final String value;

    public StringToken(TokenType type, int position, String value) {
        super(type, position);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
