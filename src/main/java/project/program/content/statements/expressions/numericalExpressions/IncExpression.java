package project.program.content.statements.expressions.numericalExpressions;

import project.program.content.statements.expressions.numericalExpressions.NumericalExpression;

public class IncExpression extends NumericalExpression {

    private final NumericalExpression operand;

    public IncExpression(NumericalExpression operand) {
        this.operand = operand;
    }

    public NumericalExpression getOperand() {
        return operand;
    }
}
