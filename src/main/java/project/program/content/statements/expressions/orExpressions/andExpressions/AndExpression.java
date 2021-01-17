package project.program.content.statements.expressions.orExpressions.andExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.OrExpression;


public abstract class AndExpression extends OrExpression {

    public AndExpression(int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
