package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.interpreter.INodeVisitor;

public final class StructFieldExpression extends SimpleExpression {

    private final Identifier structVarName;
    private final SimpleExpression fieldName;

    public StructFieldExpression(Identifier structVarName, SimpleExpression fieldName, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.structVarName = structVarName;
        this.fieldName = fieldName;
    }

    public Identifier getStructVarName() {
        return structVarName;
    }

    public SimpleExpression getFieldName() {
        return fieldName;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
