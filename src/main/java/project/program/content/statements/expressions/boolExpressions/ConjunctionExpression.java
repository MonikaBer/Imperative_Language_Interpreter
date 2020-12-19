package project.program.content.statements.expressions.boolExpressions;

import project.program.content.statements.expressions.boolExpressions.BoolExpression;

public class ConjunctionExpression extends BoolExpression {

    private final BoolExpression leftOperand;
    private final BoolExpression rightOperand;

    public ConjunctionExpression(BoolExpression leftOperand, BoolExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public BoolExpression getLeftOperand() {
        return leftOperand;
    }

    public BoolExpression getRightOperand() {
        return rightOperand;
    }
}
