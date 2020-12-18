package project.token;

import java.math.BigDecimal;

public class DoubleToken extends Token {

    private final BigDecimal value;

    public DoubleToken(TokenType type, int position, int lineNr, int positionAtLine, BigDecimal value) {
        super(type, position, lineNr, positionAtLine);
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
