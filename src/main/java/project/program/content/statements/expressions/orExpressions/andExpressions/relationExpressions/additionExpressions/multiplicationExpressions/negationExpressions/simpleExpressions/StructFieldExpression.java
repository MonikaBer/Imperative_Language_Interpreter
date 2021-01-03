package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

public class StructFieldExpression extends SimpleExpression {

    private Identifier structVarName;
    private SimpleExpression fieldName;

    public StructFieldExpression(Identifier structVarName, SimpleExpression fieldName) {
        this.structVarName = structVarName;
        this.fieldName = fieldName;
    }

    public Identifier getStructVarName() {
        return structVarName;
    }

    public SimpleExpression getFieldName() {
        return fieldName;
    }
}