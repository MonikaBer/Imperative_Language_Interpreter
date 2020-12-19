package project.program.content.statements;

import project.program.content.statements.expressions.Expression;
import project.program.content.types.Type;

public class Declaration extends Statement {

    private Type type;
    private String name;
    private Expression expression;

    public Declaration(Type type, String name, Expression expression) {
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Expression getExpression() {
        return expression;
    }
}
