package project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegationExpression;

public class DivExpression extends MultiplicationExpression {

    private NegationExpression leftOperand;
    private NegationExpression rightOperand;

    public DivExpression(NegationExpression leftOperand, NegationExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public NegationExpression getLeftOperand() {
        return leftOperand;
    }

    public NegationExpression getRightOperand() {
        return rightOperand;
    }
}
