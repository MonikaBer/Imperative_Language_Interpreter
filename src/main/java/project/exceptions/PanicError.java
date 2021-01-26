package project.exceptions;

public class PanicError extends RuntimeException {

    private final String message;
    private final int retCode = -5;

    public PanicError(String desc) {
        this.message = "PanicError: " + desc;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getRetCode() {
        return retCode;
    }
}
