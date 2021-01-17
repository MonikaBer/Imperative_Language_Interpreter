package project.program.content.statements.expressions.orExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.Expression;


public abstract class OrExpression extends Expression {

    public OrExpression(int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
