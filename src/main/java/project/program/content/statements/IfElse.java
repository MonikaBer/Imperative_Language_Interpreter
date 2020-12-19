package project.program.content.statements;

import project.program.content.statements.expressions.boolExpressions.BoolExpression;

public class IfElse extends Statement {

    private final BoolExpression condition;
    private final Statement ifStmt;
    private final Statement elseStmt;

    public IfElse(BoolExpression condition, Statement ifStmt, Statement elseStmt) {
        this.condition = condition;
        this.ifStmt = ifStmt;
        this.elseStmt = elseStmt;
    }

    public BoolExpression getCondition() {
        return condition;
    }

    public Statement getIfStmt() {
        return ifStmt;
    }

    public Statement getElseStmt() {
        return elseStmt;
    }
}
