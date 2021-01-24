package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.Expression;

public final class ParenthExpression extends SimpleExpression {

    private final Expression expression;

    public ParenthExpression(Expression expression, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
