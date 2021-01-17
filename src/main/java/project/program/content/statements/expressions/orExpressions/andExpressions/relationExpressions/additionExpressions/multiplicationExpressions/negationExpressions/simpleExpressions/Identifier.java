package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

public class Identifier extends SimpleExpression {

    private String name;

    public Identifier(String name, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
