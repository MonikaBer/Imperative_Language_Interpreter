package project.program.content.statements.expressions.structExpressions.orExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.AndExpression;

public class AlternativeExpression extends OrExpression {

    private AndExpression leftOperand;
    private AndExpression rightOperand;

    public AlternativeExpression(AndExpression leftOperand, AndExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public AndExpression getLeftOperand() {
        return leftOperand;
    }

    public AndExpression getRightOperand() {
        return rightOperand;
    }
}
