package project.program.content.statements.declarations;

import project.program.content.statements.Statement;
import project.program.content.types.Type;

public abstract class Declaration extends Statement {

    private Type type;
    private String name;

    public Declaration(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
