package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.AdditionExpression;


public abstract class MultiplicationExpression extends AdditionExpression {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
