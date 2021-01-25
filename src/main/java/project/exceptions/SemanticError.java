package project.exceptions;

public class SemanticError extends RuntimeException {

    private final int lineNr;
    private final int positionAtLine;
    private final String desc;
    private final String message;
    private final int retCode = -3;

    public SemanticError(int lineNr, int positionAtLine, String desc) {
        this.lineNr = lineNr;
        this.positionAtLine = positionAtLine;
        this.desc = desc;
        this.message = "(" + lineNr + ":" + positionAtLine + ") " + desc;
    }

    public int getLineNr() {
        return lineNr;
    }

    public int getPositionAtLine() {
        return positionAtLine;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getRetCode() {
        return retCode;
    }
}
