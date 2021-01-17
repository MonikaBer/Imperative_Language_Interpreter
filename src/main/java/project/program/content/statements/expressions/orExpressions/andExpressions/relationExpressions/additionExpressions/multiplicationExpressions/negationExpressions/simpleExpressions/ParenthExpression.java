package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions;

import project.program.content.statements.expressions.Expression;

public class ParenthExpression extends SimpleExpression {

    private Expression expression;

    public ParenthExpression(Expression expression, int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
