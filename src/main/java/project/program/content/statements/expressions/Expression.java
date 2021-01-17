package project.program.content.statements.expressions;

import project.interpreter.INodeVisitor;
import project.program.content.statements.Statement;


public abstract class Expression extends Statement {

    protected final int lineNr;
    protected final int positionAtLine;

    public Expression(int lineNr, int positionAtLine) {
        this.lineNr = lineNr;
        this.positionAtLine = positionAtLine;
    }

    public int getLineNr() {
        return lineNr;
    }

    public int getPositionAtLine() {
        return positionAtLine;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visit(this);
    }
}
