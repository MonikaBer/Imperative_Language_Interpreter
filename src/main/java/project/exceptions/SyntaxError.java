package project.exceptions;

public class SyntaxError extends RuntimeException {

    private final int lineNr;
    private final int positionAtLine;
    private final String desc;
    private final String message;
    private final int retCode = -2;

    public SyntaxError(int lineNr, int positionAtLine, String desc) {
        this.lineNr = lineNr;
        this.positionAtLine = positionAtLine;
        this.desc = desc;
        this.message = "SyntaxError: (" + lineNr + ":" + positionAtLine + ") " + desc;
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
