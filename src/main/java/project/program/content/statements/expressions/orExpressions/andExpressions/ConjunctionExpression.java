package project.program.content.statements.expressions.orExpressions.andExpressions;

import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.RelationExpression;

public class ConjunctionExpression extends AndExpression {

    private RelationExpression leftOperand;
    private AndExpression rightOperand;

    public ConjunctionExpression(RelationExpression leftOperand, AndExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public RelationExpression getLeftOperand() {
        return leftOperand;
    }

    public AndExpression getRightOperand() {
        return rightOperand;
    }
}
