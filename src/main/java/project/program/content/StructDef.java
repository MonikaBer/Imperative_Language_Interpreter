package project.program.content;

import project.program.content.statements.Declaration;
import project.program.content.types.Type;

import java.util.ArrayList;

public class StructDef extends Type {

    private ArrayList<Declaration> body;

    public StructDef(ArrayList<Declaration> body) {
        this.body = body;
    }

    public ArrayList<Declaration> getBody() {
        return body;
    }
}
