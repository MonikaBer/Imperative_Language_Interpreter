package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegationExpression;

public final class DivExpression extends MultiplicationExpression {

    private final NegationExpression leftOperand;
    private final MultiplicationExpression rightOperand;

    public DivExpression(NegationExpression leftOperand, MultiplicationExpression rightOperand, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public NegationExpression getLeftOperand() {
        return leftOperand;
    }

    public MultiplicationExpression getRightOperand() {
        return rightOperand;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
