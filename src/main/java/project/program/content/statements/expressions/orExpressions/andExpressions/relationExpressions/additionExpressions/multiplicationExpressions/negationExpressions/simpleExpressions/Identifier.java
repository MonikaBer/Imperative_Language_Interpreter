package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.interpreter.INodeVisitor;

public final class Identifier extends SimpleExpression {

    private final String name;

    public Identifier(String name, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
