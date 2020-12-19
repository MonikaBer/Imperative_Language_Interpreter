package project.program.content.statements;

import project.program.content.statements.expressions.Expression;

import java.util.ArrayList;

public class Switch extends Statement {

    private final Expression expression;
    private final ArrayList<Case> cases;
    private final Default def;

    public Switch(Expression expression, ArrayList<Case> cases, Default def) {
        this.expression = expression;
        this.cases = cases;
        this.def = def;
    }

    public Expression getExpression() {
        return expression;
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public Default getDef() {
        return def;
    }
}


class Case {

    private final Expression expression;
    private final Statement statement;

    public Case(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getStatement() {
        return statement;
    }
}


class Default {

    private final Statement statement;

    public Default(Statement statement) {
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }
}
