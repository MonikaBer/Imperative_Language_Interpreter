package project.program.content;

import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.types.Type;

import java.util.ArrayList;

public class StructDef extends Type {

    private final Identifier id;
    private final ArrayList<Declaration> body;

    public StructDef(Identifier id, ArrayList<Declaration> body) {
        this.id = id;
        this.body = body;
    }

    public Identifier getId() {
        return id;
    }

    public ArrayList<Declaration> getBody() {
        return body;
    }
}
