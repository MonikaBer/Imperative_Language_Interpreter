package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.MultiplicationExpression;


public abstract class NegationExpression extends MultiplicationExpression {

    public NegationExpression(int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
