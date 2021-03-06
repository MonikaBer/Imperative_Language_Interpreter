package project.program.content.statements;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.Expression;

public class Return extends Statement {

    private final Expression expression;

    public Return(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
