package project.program.content.statements;

import project.program.content.statements.expressions.Expression;

public class Increment extends Statement {

    private final Expression expression;

    public Increment(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
