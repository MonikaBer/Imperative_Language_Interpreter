package project.interpreter.definitions;

import project.program.content.statements.declarations.Initialisation;
import project.program.content.statements.declarations.OnlyDeclaration;
import project.program.content.statements.expressions.Expression;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.types.Type;

public class Variable extends Definition {

    private Type type;
    private Identifier id;
    private Expression value;

    public Variable(OnlyDeclaration onlyDeclaration) {
        this.type = onlyDeclaration.getType();
        this.id = onlyDeclaration.getId();
        this.value = null;
    }

    public Variable(Initialisation initialisation) {
        this.type = initialisation.getType();
        this.id = initialisation.getId();
        this.value = initialisation.getExpression();
    }

    public Type getType() {
        return type;
    }

    public Identifier getId() {
        return id;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
