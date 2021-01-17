package project.program.content.statements;

import project.interpreter.INodeVisitor;

import java.util.ArrayList;

public class Block extends Statement {

    private final ArrayList<Statement> stmts;

    public Block(ArrayList<Statement> stmts) {
        this.stmts = stmts;
    }

    public ArrayList<Statement> getStmts() {
        return stmts;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
