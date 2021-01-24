package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;

public final class EqualExpression extends RelationExpression {

    private final AdditionExpression leftOperand;
    private final RelationExpression rightOperand;

    public EqualExpression(AdditionExpression leftOperand, RelationExpression rightOperand, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public AdditionExpression getLeftOperand() {
        return leftOperand;
    }

    public RelationExpression getRightOperand() {
        return rightOperand;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
