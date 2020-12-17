package project.program.content.statements;

import project.program.content.types.Type;

public class Declaration {

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
