package project.interpreter.declaredObjects;

import project.program.content.statements.declarations.Declaration;

import java.util.ArrayList;

public class StructDefinition extends DeclaredObject {

    private final String name;
    private final ArrayList<Declaration> body;

    public StructDefinition(String name, ArrayList<Declaration> body) {
        this.name = name;
        this.body = body;
    }


    public String getName() {
        return name;
    }

    public ArrayList<Declaration> getBody() {
        return body;
    }
}
