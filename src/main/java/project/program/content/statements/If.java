package project.program.content.statements;

public class If extends Statement {

    private final Condition condition;
    private final Statement statement;

    public If(Condition condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    public Condition getCondition() {
        return condition;
    }

    public Statement getStatement() {
        return statement;
    }
}
