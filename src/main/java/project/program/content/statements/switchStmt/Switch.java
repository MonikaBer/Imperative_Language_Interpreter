package project.program.content.statements.switchStmt;

import project.program.content.statements.Statement;
import project.program.content.statements.expressions.Expression;

import java.util.ArrayList;

public class Switch extends Statement {

    private final Expression expression;
    private final ArrayList<Case> cases;
    private final Statement defaultStmt;

    public Switch(Expression expression, ArrayList<Case> cases, Statement defaultStmt) {
        this.expression = expression;
        this.cases = cases;
        this.defaultStmt = defaultStmt;
    }

    public Expression getExpression() {
        return expression;
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public Statement getDefaultStmt() {
        return defaultStmt;
    }
}
