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
    private final Statement stmt;

    public Case(Expression expression, Statement stmt) {
        this.expression = expression;
        this.stmt = stmt;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getStmt() {
        return stmt;
    }
}


class Default {

    private final Statement stmt;

    public Default(Statement stmt) {
        this.stmt = stmt;
    }

    public Statement getStmt() {
        return stmt;
    }
}
