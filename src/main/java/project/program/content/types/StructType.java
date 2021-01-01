package project.program.content.types;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;

public class StructType extends NonVoidType {

    private final Identifier id;

    public StructType(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }
}
