package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

public class StringValue extends SimpleExpression {

    private String value;

    public StringValue(String value, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
