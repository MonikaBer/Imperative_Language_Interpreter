package project.program.content.statements.expressions.stringExpressions;

public class EqualStringExpression extends StringExpression {

    private final StringValue leftOperand;
    private final StringValue rightOperand;

    public EqualStringExpression(StringValue leftOperand, StringValue rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public StringValue getLeftOperand() {
        return leftOperand;
    }

    public StringValue getRightOperand() {
        return rightOperand;
    }
}
