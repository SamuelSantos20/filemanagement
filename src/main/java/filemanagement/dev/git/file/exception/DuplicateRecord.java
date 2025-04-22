package filemanagement.dev.git.file.exception;

public class DuplicateRecord extends RuntimeException {
    public DuplicateRecord(String message) {
        super(message);
    }
}
