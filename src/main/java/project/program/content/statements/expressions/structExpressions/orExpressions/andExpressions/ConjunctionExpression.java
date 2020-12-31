package project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.RelationExpression;

public class ConjunctionExpression extends AndExpression {

    private RelationExpression leftOperand;
    private RelationExpression rightOperand;

    public ConjunctionExpression(RelationExpression leftOperand, RelationExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public RelationExpression getLeftOperand() {
        return leftOperand;
    }

    public RelationExpression getRightOperand() {
        return rightOperand;
    }
}
