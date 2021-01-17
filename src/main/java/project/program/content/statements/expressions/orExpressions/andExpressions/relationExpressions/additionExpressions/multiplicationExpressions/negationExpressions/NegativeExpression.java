package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.SimpleExpression;

public class NegativeExpression extends NegationExpression {

    private SimpleExpression expression;

    public NegativeExpression(SimpleExpression expression, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.expression = expression;
    }

    public SimpleExpression getExpression() {
        return expression;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
