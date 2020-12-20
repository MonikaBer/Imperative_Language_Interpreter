package project.program.content.statements.declarations;

import project.program.content.statements.expressions.Expression;
import project.program.content.types.Type;

public class Initialisation extends Declaration {

    private Expression expression;


    public Initialisation(Type type, String name, Expression expression) {
        super(type, name);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
