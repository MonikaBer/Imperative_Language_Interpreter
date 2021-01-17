package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.additionExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions.RelationExpression;


public abstract class AdditionExpression extends RelationExpression {

    public AdditionExpression(int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
