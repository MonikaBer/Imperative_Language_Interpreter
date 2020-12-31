package project.program.content;

import project.program.content.statements.declarations.Declaration;
import project.program.content.types.Type;

import java.util.ArrayList;

public class StructDef extends Type {

    private String name;
    private ArrayList<Declaration> body;

    public StructDef(String name, ArrayList<Declaration> body) {
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
