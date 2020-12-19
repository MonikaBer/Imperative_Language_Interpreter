package project.program.content.statements.expressions.numericalExpressions;

import project.program.content.statements.expressions.numericalExpressions.NumericalExpression;

public class NegativeExpression extends NumericalExpression {

    private final NumericalExpression operand;

    public NegativeExpression(NumericalExpression operand) {
        this.operand = operand;
    }

    public NumericalExpression getOperand() {
        return operand;
    }
}
