package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultiplicationExpression;

public class AddExpression extends AdditionExpression {

    private MultiplicationExpression leftOperand;
    private AdditionExpression rightOperand;

    public AddExpression(MultiplicationExpression leftOperand, AdditionExpression rightOperand, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public MultiplicationExpression getLeftOperand() {
        return leftOperand;
    }

    public AdditionExpression getRightOperand() {
        return rightOperand;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
