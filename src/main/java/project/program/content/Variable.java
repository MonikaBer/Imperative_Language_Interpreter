package project.program.content;

import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.types.Type;

import java.beans.Expression;

public class Variable {

    private Type type;
    private final Identifier id;
    private Expression expression;

    public Variable(Type type, Identifier id, Expression expression) {
        this.type = type;
        this.id = id;
        this.expression = expression;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Identifier getName() {
        return id;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
