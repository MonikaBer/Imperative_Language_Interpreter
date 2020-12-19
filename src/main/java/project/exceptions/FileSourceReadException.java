package project.exceptions;

public class FileSourceReadException extends RuntimeException {

    private final String message;

    public FileSourceReadException() {
        message = "IOException occurred during reading from file source -> maybe you lost read permissions or meantime someone deleted the file";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
