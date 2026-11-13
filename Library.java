import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library {
    private final int inventorySize;
    private final HashMap<Integer, Book> books = new HashMap<>();
    private final HashMap<Integer, User> users = new HashMap<>();
    private final List<LibraryListener> listeners = new ArrayList<>();

    public Library(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID: " + book.getId());
        }
        books.put(book.getId(), book);
    }

    public void addUser(User user) {
        if (users.containsKey(user.getId())) {
            throw new IllegalArgumentException("Duplicate user ID: " + user.getId());
        }
        users.put(user.getId(), user);
    }

    public Book findBook(int bookId) throws BookNotFoundException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book " + bookId + " is not in the library.");
        }
        return book;
    }

    private User findUser(int userId) throws UserNotFoundException {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User " + userId + " was not found.");
        }
        return user;
    }

    public void addListener(LibraryListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        listeners.add(listener);
    }

    private void notifyListeners(Book book, String message) {
        for (LibraryListener l : listeners) {
            l.bookChanged(book.getId(), book.getStatus(), message);
        }
    }

    public void checkOut(int bookId, int userId)
            throws BookNotFoundException, UserNotFoundException,
                   AlreadyOnWaitlistException, InventoryFullException {
        Book book = findBook(bookId);
        User user = findUser(userId);

        if (book.getStatus() == BookStatus.RESERVED && book.getReservedForId() == userId) {
            book.setStatus(BookStatus.CHECKED_OUT);
            book.setBorrowerId(userId);
            book.setReservedForId(-1);
            notifyListeners(book, user.getName() + " checked out \"" + book.getTitle() + "\".");
            return;
        }
        if (user.hasBook(bookId)) {
            throw new AlreadyOnWaitlistException(
                    user.getName() + " already has this book or is already in line.");
        }
        if (book.getStatus() != BookStatus.AVAILABLE) {
            joinWaitlist(bookId, userId);
            return;
        }
        user.addBook(bookId);
        book.setStatus(BookStatus.CHECKED_OUT);
        book.setBorrowerId(userId);
        notifyListeners(book, user.getName() + " checked out \"" + book.getTitle() + "\".");
    }

    public void joinWaitlist(int bookId, int userId)
            throws BookNotFoundException, UserNotFoundException, InventoryFullException {
        Book book = findBook(bookId);
        User user = findUser(userId);
        user.addBook(bookId);
        book.getWaitlist().add(user);
        notifyListeners(book, user.getName() + " joined the waitlist for \"" + book.getTitle() + "\".");
    }

    public void reserve(int bookId, int userId)
            throws BookNotFoundException, UserNotFoundException,
                   BookUnavailableException, InventoryFullException {
        Book book = findBook(bookId);
        User user = findUser(userId);
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new BookUnavailableException("\"" + book.getTitle() + "\" can't be reserved right now.");
        }
        user.addBook(bookId);
        book.setStatus(BookStatus.RESERVED);
        book.setReservedForId(userId);
        notifyListeners(book, "\"" + book.getTitle() + "\" reserved for " + user.getName() + ".");
    }

    public void returnBook(int bookId) throws BookNotFoundException, BookNotCheckedOutException {
        Book book = findBook(bookId);
        if (book.getStatus() != BookStatus.CHECKED_OUT) 
        
        {
            throw new BookNotCheckedOutException("\"" + book.getTitle() + "\" is already on the Smart Shelf.");
        }
        users.get(book.getBorrowerId()).removeBook(bookId);

        if (!book.getWaitlist().isEmpty()) {
            User next = book.getWaitlist().nextInLine();
            book.setBorrowerId(next.getId());   // ID is already in next's bag
            notifyListeners(book, "\"" + book.getTitle() + "\" was returned and handed to " + next.getName() + ".");
        } else {
            book.setStatus(BookStatus.AVAILABLE);
            book.setBorrowerId(-1);
            notifyListeners(book, "\"" + book.getTitle() + "\" was returned and is now available.");
        }
    }
}