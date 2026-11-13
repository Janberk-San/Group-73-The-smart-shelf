public interface LibraryListener 

{
    void bookChanged(int bookId, BookStatus status, String message);
}
