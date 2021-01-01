package project.program.content.statements;

import project.program.content.statements.expressions.Expression;

public class Return extends Statement {

    private final Expression expression;

    public Return(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
