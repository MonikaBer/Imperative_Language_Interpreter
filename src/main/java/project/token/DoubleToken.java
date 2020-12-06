package project.token;

public class DoubleToken extends Token {

    private final double value;

    public DoubleToken(TokenType type, int position, double value) {
        super(type, position);
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
