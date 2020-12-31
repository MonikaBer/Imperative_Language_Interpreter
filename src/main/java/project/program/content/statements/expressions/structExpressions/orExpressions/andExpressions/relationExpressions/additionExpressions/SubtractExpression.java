package project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultiplicationExpression;

public class SubtractExpression extends AdditionExpression {

    private MultiplicationExpression leftOperand;
    private MultiplicationExpression rightOperand;

    public SubtractExpression(MultiplicationExpression leftOperand, MultiplicationExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public MultiplicationExpression getLeftOperand() {
        return leftOperand;
    }

    public MultiplicationExpression getRightOperand() {
        return rightOperand;
    }
}
