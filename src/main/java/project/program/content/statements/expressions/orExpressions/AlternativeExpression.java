package project.program.content.statements.expressions.orExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.AndExpression;

public final class AlternativeExpression extends OrExpression {

    private final AndExpression leftOperand;
    private final OrExpression rightOperand;

    public AlternativeExpression(AndExpression leftOperand, OrExpression rightOperand, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public AndExpression getLeftOperand() {
        return leftOperand;
    }

    public OrExpression getRightOperand() {
        return rightOperand;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
