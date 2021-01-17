package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.NegationExpression;


public abstract class SimpleExpression extends NegationExpression {

    public SimpleExpression(int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
    }

    public int getLineNr() {
        return lineNr;
    }

    public int getPositionAtLine() {
        return positionAtLine;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
