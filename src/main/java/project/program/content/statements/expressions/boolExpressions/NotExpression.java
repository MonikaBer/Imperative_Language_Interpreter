package project.program.content.statements.expressions.boolExpressions;

import project.program.content.statements.expressions.boolExpressions.BoolExpression;

public class NotExpression extends BoolExpression {

    private final BoolExpression operand;

    public NotExpression(BoolExpression operand) {
        this.operand = operand;
    }

    public BoolExpression getOperand() {
        return operand;
    }
}
