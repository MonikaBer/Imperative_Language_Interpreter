package project.program.content.statements;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.Expression;

public class While extends Statement {

    private final Expression condition;
    private final Statement stmt;

    public While(Expression condition, Statement stmt) {
        this.condition = condition;
        this.stmt = stmt;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getStmt() {
        return stmt;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
