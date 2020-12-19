package project.program.content;

import project.program.content.types.Type;

import java.beans.Expression;

public class Variable {

    private Type type;
    private final String Name;
    private Expression expression;

    public Variable(Type type, String name, Expression expression) {
        this.type = type;
        Name = name;
        this.expression = expression;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
