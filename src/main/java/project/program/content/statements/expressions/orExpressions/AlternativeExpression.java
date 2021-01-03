package project.program.content.statements.expressions.orExpressions;

import project.program.content.statements.expressions.orExpressions.andExpressions.AndExpression;

public class AlternativeExpression extends OrExpression {

    private AndExpression leftOperand;
    private OrExpression rightOperand;

    public AlternativeExpression(AndExpression leftOperand, OrExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public AndExpression getLeftOperand() {
        return leftOperand;
    }

    public OrExpression getRightOperand() {
        return rightOperand;
    }
}
