package project.program.content.statements.expressions.orExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.AndExpression;

public class AlternativeExpression extends OrExpression {

    private AndExpression leftOperand;
    private OrExpression rightOperand;

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
