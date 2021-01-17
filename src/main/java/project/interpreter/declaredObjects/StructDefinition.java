package project.interpreter.declaredObjects;

import project.program.content.StructDef;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;

import java.util.ArrayList;

public class StructDefinition extends DeclaredObject {

    private final Identifier id;
    private final ArrayList<Declaration> body;

    public StructDefinition(StructDef structDef) {
        this.id = structDef.getId();
        this.body = structDef.getBody();
    }

    public Identifier getId() {
        return id;
    }

    public ArrayList<Declaration> getBody() {
        return body;
    }
}
