package project.program.content.statements;

import project.program.content.statements.expressions.boolExpressions.BoolExpression;

public class While extends Statement {

    private final BoolExpression condition;
    private final Statement statement;

    public While(BoolExpression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    public BoolExpression getCondition() {
        return condition;
    }

    public Statement getStatement() {
        return statement;
    }
}
