package project.program.content.statements.expressions.numericalExpressions;

import project.program.content.statements.expressions.numericalExpressions.NumericalExpression;

public class ModExpression extends NumericalExpression {

    private final NumericalExpression leftOperand;
    private final NumericalExpression rightOperand;

    public ModExpression(NumericalExpression leftOperand, NumericalExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public NumericalExpression getLeftOperand() {
        return leftOperand;
    }

    public NumericalExpression getRightOperand() {
        return rightOperand;
    }
}
