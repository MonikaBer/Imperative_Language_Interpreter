package project.interpreter.evaluatedExpr;

import java.math.BigInteger;

public class EvalIntValue extends Value {

    private BigInteger value;

    public EvalIntValue(BigInteger value) {
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }
}
