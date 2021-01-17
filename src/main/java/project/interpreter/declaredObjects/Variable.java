package project.interpreter.declaredObjects;

import project.program.content.statements.expressions.Expression;
import project.program.content.types.Type;

public class Variable extends DeclaredObject {

    private Type type;
    private String name;
    private Expression value;

    public Variable(Type type, String name, Expression value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
