package project.program.content.statements.expressions.orExpressions.andExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.RelationExpression;

public final class ConjunctionExpression extends AndExpression {

    private final RelationExpression leftOperand;
    private final AndExpression rightOperand;

    public ConjunctionExpression(RelationExpression leftOperand, AndExpression rightOperand, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public RelationExpression getLeftOperand() {
        return leftOperand;
    }

    public AndExpression getRightOperand() {
        return rightOperand;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
