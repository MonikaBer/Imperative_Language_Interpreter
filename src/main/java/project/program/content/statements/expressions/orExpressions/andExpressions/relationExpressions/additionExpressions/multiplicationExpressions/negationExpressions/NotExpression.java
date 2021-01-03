package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions;

import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.SimpleExpression;

public class NotExpression extends NegationExpression {

    private SimpleExpression expression;

    public NotExpression(SimpleExpression expression) {
        this.expression = expression;
    }

    public SimpleExpression getExpression() {
        return expression;
    }
}
