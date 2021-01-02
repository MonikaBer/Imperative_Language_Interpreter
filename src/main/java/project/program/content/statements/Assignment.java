package project.program.content.statements;

import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;

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
}
