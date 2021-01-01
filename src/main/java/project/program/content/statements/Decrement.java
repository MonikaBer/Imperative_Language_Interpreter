package project.program.content.statements;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;

public class Decrement extends Statement {

    private final Identifier identifier;

    public Decrement(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
