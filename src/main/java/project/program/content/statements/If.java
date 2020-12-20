package project.program.content.statements;

import project.program.content.statements.expressions.boolExpressions.BoolExpression;

public class If extends Statement {

    private final BoolExpression condition;
    private final Statement ifStmt;

    public If(BoolExpression condition, Statement ifStmt) {
        this.condition = condition;
        this.ifStmt = ifStmt;
    }

    public BoolExpression getCondition() {
        return condition;
    }

    public Statement getIfStmt() {
        return ifStmt;
    }
}
