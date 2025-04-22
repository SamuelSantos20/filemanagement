package filemanagement.dev.git.file.exception;

public class OperationNotAllowed extends RuntimeException {
    public OperationNotAllowed(String message) {
        super(message);
    }
}
