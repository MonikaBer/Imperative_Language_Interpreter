package project.token;

public class DoubleToken extends Token {

    private final double value;

    public DoubleToken(TokenType type, int position, int lineNr, int positionAtLine, double value) {
        super(type, position, lineNr, positionAtLine);
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
