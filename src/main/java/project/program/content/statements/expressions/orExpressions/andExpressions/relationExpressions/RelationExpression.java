package project.program.content.statements.expressions.orExpressions.andExpressions.relationExpressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.orExpressions.andExpressions.AndExpression;


public abstract class RelationExpression extends AndExpression {

    public RelationExpression(int lineNr, int positionAtLine) {
        super(lineNr, positionAtLine);
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
