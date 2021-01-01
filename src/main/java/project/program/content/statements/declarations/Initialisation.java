package project.program.content.statements.declarations;

import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.types.Type;

public class Initialisation extends Declaration {

    private final Expression expression;


    public Initialisation(Type type, Identifier id, Expression expression) {
        super(type, id);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
