package project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;

public class EqualExpression extends RelationExpression {

    private AdditionExpression leftOperand;
    private AdditionExpression rightOperand;

    public EqualExpression(AdditionExpression leftOperand, AdditionExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public AdditionExpression getLeftOperand() {
        return leftOperand;
    }

    public AdditionExpression getRightOperand() {
        return rightOperand;
    }
}
