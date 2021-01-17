package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegationExpression;


public abstract class SimpleExpression extends NegationExpression {

    protected final int lineNr;
    protected final int positionAtLine;

    public SimpleExpression(int lineNr, int positionAtLine) {
        this.lineNr = lineNr;
        this.positionAtLine = positionAtLine;
    }

    public int getLineNr() {
        return lineNr;
    }

    public int getPositionAtLine() {
        return positionAtLine;
    }
}
