package project.program.content.statements;

import project.program.content.statements.expressions.Expression;

public class Decrement extends Statement {

    private final Expression expression;

    public Decrement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() { return expression; }
}
