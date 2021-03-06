package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.interpreter.INodeVisitor;

public final class StringValue extends SimpleExpression {

    private final String value;

    public StringValue(String value, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
