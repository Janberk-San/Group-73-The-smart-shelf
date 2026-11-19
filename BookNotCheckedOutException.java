public class BookNotCheckedOutException extends Exception {
    private static final long serialVersionUID = 1L;
    public BookNotCheckedOutException(String message) { 
      super(message); 
    }
}
