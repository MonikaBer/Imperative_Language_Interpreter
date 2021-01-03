package project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;

public class GreaterEqualExpression extends RelationExpression {

    private AdditionExpression leftOperand;
    private RelationExpression rightOperand;

    public GreaterEqualExpression(AdditionExpression leftOperand, RelationExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public AdditionExpression getLeftOperand() {
        return leftOperand;
    }

    public RelationExpression getRightOperand() {
        return rightOperand;
    }
}
