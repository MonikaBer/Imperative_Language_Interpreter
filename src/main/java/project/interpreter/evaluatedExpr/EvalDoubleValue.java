package project.interpreter.evaluatedExpr;

import java.math.BigDecimal;

public class EvalDoubleValue extends Value {

    private BigDecimal value;

    public EvalDoubleValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
