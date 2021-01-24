package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.interpreter.INodeVisitor;

import java.math.BigInteger;

public final class IntValue extends SimpleExpression {

    private BigInteger value;

    public IntValue(BigInteger value, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
