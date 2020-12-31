package project.program.content.statements.expressions.structExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.OrExpression;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;

public class StructFieldExpression extends StructExpression {

    private OrExpression structVarName;
    private Identifier fieldName;

    public StructFieldExpression(OrExpression structVarName, Identifier fieldName) {
        this.structVarName = structVarName;
        this.fieldName = fieldName;
    }

    public OrExpression getStructVarName() {
        return structVarName;
    }

    public Identifier getFieldName() {
        return fieldName;
    }
}
