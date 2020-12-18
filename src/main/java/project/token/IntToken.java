package project.token;

import java.math.BigInteger;

public class IntToken extends Token {

    private final BigInteger value;

    public IntToken(TokenType type, int position, int lineNr, int positionAtLine, BigInteger value) {
        super(type, position, lineNr, positionAtLine);
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }
}
