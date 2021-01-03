package project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultiplicationExpression;

public class SubtractExpression extends AdditionExpression {

    private MultiplicationExpression leftOperand;
    private AdditionExpression rightOperand;

    public SubtractExpression(MultiplicationExpression leftOperand, AdditionExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public MultiplicationExpression getLeftOperand() {
        return leftOperand;
    }

    public AdditionExpression getRightOperand() {
        return rightOperand;
    }
}
