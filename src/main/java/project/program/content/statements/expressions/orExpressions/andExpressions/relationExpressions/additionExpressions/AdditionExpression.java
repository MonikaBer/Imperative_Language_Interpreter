package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.RelationExpression;


public abstract class AdditionExpression extends RelationExpression {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
