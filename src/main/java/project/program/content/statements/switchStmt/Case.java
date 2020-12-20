package project.program.content.statements.switchStmt;

import project.program.content.statements.Statement;
import project.program.content.statements.expressions.Expression;

public class Case {

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
