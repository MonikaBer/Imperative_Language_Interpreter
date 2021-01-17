package project.program.content;

import project.interpreter.INodeVisitor;
import project.program.content.statements.declarations.Declaration;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;

import java.util.ArrayList;

public class StructDef extends ProgramContent {

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

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
