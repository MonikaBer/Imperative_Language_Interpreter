package project.exceptions;

public class SyntaxError extends RuntimeException {

    private final int lineNr;
    private final int positionAtLine;
    private final String desc;
    private final String message;

    public SyntaxError(int lineNr, int positionAtLine, String desc) {
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
}
