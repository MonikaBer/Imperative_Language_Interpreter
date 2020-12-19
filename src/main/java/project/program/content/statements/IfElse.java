package project.program.content.statements;

public class IfElse extends Statement {

    private final Condition condition;
    private final Statement ifStatement;
    private final Statement elseStatement;

    public IfElse(Condition condition, Statement ifStatement, Statement elseStatement) {
        this.condition = condition;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    public Condition getCondition() {
        return condition;
    }

    public Statement getIfStatement() {
        return ifStatement;
    }

    public Statement getElseStatement() {
        return elseStatement;
    }
}
