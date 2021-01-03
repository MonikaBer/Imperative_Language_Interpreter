package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions;

import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegationExpression;

public class ModExpression extends MultiplicationExpression {

    private NegationExpression leftOperand;
    private MultiplicationExpression rightOperand;

    public ModExpression(NegationExpression leftOperand, MultiplicationExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public NegationExpression getLeftOperand() {
        return leftOperand;
    }

    public MultiplicationExpression getRightOperand() {
        return rightOperand;
    }
}
