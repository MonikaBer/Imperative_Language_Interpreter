package project.exceptions;

public class InterpreterError extends RuntimeException {

    private final int lineNr;
    private final int positionAtLine;
    private final String desc;
    private final String message;
    private final int retCode = -4;

    public InterpreterError(String desc) {
        this.lineNr = -1;
        this.positionAtLine = -1;
        this.desc = desc;
        this.message = desc;
    }

    public InterpreterError(int lineNr, int positionAtLine, String desc) {
        this.lineNr = lineNr;
        this.positionAtLine = positionAtLine;
        this.desc = desc;
        this.message = "InterpreterError: (" + lineNr + ":" + positionAtLine + ") " + desc;
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
