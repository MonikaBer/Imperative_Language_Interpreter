package project.program.content.statements;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.Expression;

public class Assignment extends Statement {

    private final Expression id;
    private final Expression expression;

    public Assignment(Expression id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    public Expression getId() {
        return id;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
