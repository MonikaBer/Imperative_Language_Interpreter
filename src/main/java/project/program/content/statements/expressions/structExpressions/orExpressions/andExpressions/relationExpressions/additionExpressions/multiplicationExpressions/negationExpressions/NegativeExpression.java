package project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.SimpleExpression;

public class NegativeExpression extends NegationExpression {

    private SimpleExpression expression;

    public NegativeExpression(SimpleExpression expression) {
        this.expression = expression;
    }

    public SimpleExpression getExpression() {
        return expression;
    }
}
