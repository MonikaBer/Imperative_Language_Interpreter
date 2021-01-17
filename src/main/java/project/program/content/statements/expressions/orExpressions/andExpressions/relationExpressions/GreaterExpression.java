package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;

public class GreaterExpression extends RelationExpression {

    private AdditionExpression leftOperand;
    private RelationExpression rightOperand;

    public GreaterExpression(AdditionExpression leftOperand, RelationExpression rightOperand, int lineNr, int positionAtLine) {
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
