package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;

public class LesserEqualExpression extends RelationExpression {

    private AdditionExpression leftOperand;
    private RelationExpression rightOperand;

    public LesserEqualExpression(AdditionExpression leftOperand,RelationExpression rightOperand) {
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
