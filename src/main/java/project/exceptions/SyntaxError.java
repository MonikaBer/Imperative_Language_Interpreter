package project.exceptions;

public class SyntaxError extends RuntimeException {

    private final int lineNr;
    private final int positionAtLine;
    private final String message;

    public SyntaxError(int lineNr, int positionAtLine, String message) {
        this.lineNr = lineNr;
        this.positionAtLine = positionAtLine;
        this.message = message;
    }

    public int getLineNr() {
        return lineNr;
    }

    public int getPositionAtLine() {
        return positionAtLine;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
