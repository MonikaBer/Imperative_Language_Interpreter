package project.program.content.statements.expressions.boolExpressions;

import project.program.content.statements.expressions.boolExpressions.BoolExpression;
import project.program.content.statements.expressions.numericalExpressions.NumericalExpression;

public class LesserEqualExpression extends BoolExpression {

    private final NumericalExpression leftOperand;
    private final NumericalExpression rightOperand;

    public LesserEqualExpression(NumericalExpression leftOperand, NumericalExpression rightOperand) {
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
