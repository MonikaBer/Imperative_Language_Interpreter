package project.program.content.statements;

import project.program.content.statements.expressions.Expression;

public class If extends Statement {

    private final Expression condition;
    private final Statement ifStmt;

    public If(Expression condition, Statement ifStmt) {
        this.condition = condition;
        this.ifStmt = ifStmt;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getIfStmt() {
        return ifStmt;
    }
}
