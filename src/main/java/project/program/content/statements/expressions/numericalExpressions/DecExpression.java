package project.program.content.statements.expressions.numericalExpressions;

import project.program.content.statements.expressions.numericalExpressions.NumericalExpression;

public class DecExpression extends NumericalExpression {

    private final NumericalExpression operand;

    public DecExpression(NumericalExpression operand) {
        this.operand = operand;
    }

    public NumericalExpression getOperand() {
        return operand;
    }
}
