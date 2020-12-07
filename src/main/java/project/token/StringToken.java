package project.token;

public class StringToken extends Token {

    private final String value;

    public StringToken(TokenType type, int position, int lineNr, int positionAtLine, String value) {
        super(type, position, lineNr, positionAtLine);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
