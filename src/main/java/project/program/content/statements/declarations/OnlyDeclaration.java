package project.program.content.statements.declarations;

import project.program.content.statements.expressions.structExpressions.orExpressions.andExpressions.relationExpressions.additionExpressions.multiplicationExpressions.negationExpressions.simpleExpressions.Identifier;
import project.program.content.types.Type;

public class OnlyDeclaration extends Declaration {

    public OnlyDeclaration(Type type, Identifier id) {
        super(type, id);
    }
}