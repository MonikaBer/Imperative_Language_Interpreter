package project.program.content.statements;

import project.program.content.statements.expressions.boolExpressions.BoolExpression;

public class IfElse extends If {

    private final Statement elseStmt;

    public IfElse(BoolExpression condition, Statement ifStmt, Statement elseStmt) {
        super(condition, ifStmt);
        this.elseStmt = elseStmt;
    }

    public Statement getElseStmt() {
        return elseStmt;
    }
}
