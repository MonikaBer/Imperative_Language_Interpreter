package project.program.content.types;


import project.interpreter.INodeVisitor;
import project.program.INode;

public class VoidType extends Type implements INode {

    private final int lineNr;
    private final int positionAtLine;

    public VoidType(int lineNr, int positionAtLine) {
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
