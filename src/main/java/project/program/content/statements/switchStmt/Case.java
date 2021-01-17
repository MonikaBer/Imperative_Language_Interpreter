package project.program.content.statements.switchStmt;

import project.interpreter.INodeVisitor;
import project.program.INode;
import project.program.content.statements.Statement;
import project.program.content.statements.expressions.Expression;

public class Case implements INode {

    private final Expression expression;
    private final Statement stmt;

    public Case(Expression expression, Statement stmt) {
        this.expression = expression;
        this.stmt = stmt;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getStmt() {
        return stmt;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
