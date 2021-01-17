package project.program.content.statements;

import project.interpreter.INodeVisitor;
import project.program.content.statements.expressions.Expression;

public class IfElse extends If {

    private final Statement elseStmt;

    public IfElse(Expression condition, Statement ifStmt, Statement elseStmt) {
        super(condition, ifStmt);
        this.elseStmt = elseStmt;
    }

    public Statement getElseStmt() {
        return elseStmt;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
