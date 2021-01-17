package project.exceptions;

public class SemanticError extends RuntimeException {

    private final String message;

    public SemanticError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
