package project.program.content.statements.declarations;

import project.interpreter.INodeVisitor;
import project.program.content.statements.Statement;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.types.Type;

public abstract class Declaration extends Statement {

    private final Type type;
    private final Identifier id;

    public Declaration(Type type, Identifier id) {
        this.type = type;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public Identifier getId() {
        return id;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
