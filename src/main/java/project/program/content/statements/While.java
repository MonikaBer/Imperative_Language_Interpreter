package project.program.content.statements;

import project.program.content.statements.expressions.boolExpressions.BoolExpression;

public class While extends Statement {

    private final BoolExpression condition;
    private final Statement stmt;

    public While(BoolExpression condition, Statement stmt) {
        this.condition = condition;
        this.stmt = stmt;
    }

    public BoolExpression getCondition() {
        return condition;
    }

    public Statement getStmt() {
        return stmt;
    }
}
