package project.program.content.statements;

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
}
