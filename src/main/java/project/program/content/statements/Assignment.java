package project.program.content.statements;

import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;

public class Assignment extends Statement {

    private final Identifier identifier;
    private final Expression expression;

    public Assignment(Identifier identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }
}
