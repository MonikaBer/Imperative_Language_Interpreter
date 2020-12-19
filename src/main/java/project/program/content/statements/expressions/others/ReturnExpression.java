package project.program.content.statements.expressions.others;

import project.program.content.statements.expressions.Expression;

public class ReturnExpression extends Expression {

    private final Expression expression;

    public ReturnExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
