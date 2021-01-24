package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.interpreter.INodeVisitor;

import java.math.BigDecimal;

public final class DoubleValue extends SimpleExpression {

    private BigDecimal value;

    public DoubleValue(BigDecimal value, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
