package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.AndExpression;


public abstract class RelationExpression extends AndExpression {

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
