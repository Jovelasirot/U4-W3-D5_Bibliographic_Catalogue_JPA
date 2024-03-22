package exeptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Element with the id: " + id + "is invalid.");
    }

    public NotFoundException(String isbn) {
        super("Element with the isbn: " + isbn + "is invalid.");
    }
}
