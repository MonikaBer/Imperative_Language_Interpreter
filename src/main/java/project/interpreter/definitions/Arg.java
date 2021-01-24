package project.interpreter.definitions;

import project.interpreter.evaluatedExpr.Value;
import project.program.content.types.Type;

public class Arg {

    private final Type type;
    private final String name;
    private final Value defValue;

    public Arg(Type type, String name, Value defValue) {
        this.type = type;
        this.name = name;
        this.defValue = defValue;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Value getDefValue() {
        return defValue;
    }
}
