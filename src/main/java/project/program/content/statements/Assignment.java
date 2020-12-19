package project.program.content.statements;

import project.program.content.Variable;
import project.program.content.statements.expressions.Expression;

public class Assignment extends Statement {

    private final Variable variable;
    private final Expression expression;

    public Assignment(Variable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public Variable getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
