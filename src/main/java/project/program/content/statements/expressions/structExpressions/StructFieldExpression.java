package project.program.content.statements.expressions.structExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.OrExpression;

public class StructFieldExpression extends StructExpression {

    private OrExpression structVarName;
    private StructExpression fieldName;

    public StructFieldExpression(OrExpression structVarName, StructExpression fieldName) {
        this.structVarName = structVarName;
        this.fieldName = fieldName;
    }

    public OrExpression getStructVarName() {
        return structVarName;
    }

    public StructExpression getFieldName() {
        return fieldName;
    }
}
