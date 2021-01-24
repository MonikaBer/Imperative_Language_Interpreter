package project.program.content.types;


public class VoidType extends Type {

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
}
