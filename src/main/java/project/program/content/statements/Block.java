package project.program.content.statements;

import java.util.ArrayList;

public class Block extends Statement {

    private final ArrayList<Statement> statements;

    public Block(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }
}
