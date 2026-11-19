import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LibraryTest {
    private Library library;
    private User alice, bob, carol;
    private Book book;

    @Before public void setUp() {
        library = new Library(2);
        book = new Book(100, "Dune", "Frank Herbert");
        library.addBook(book);
        alice = new User("Alice", 1, false, 2);
        bob = new User("Bob", 2, false, 2);
        carol = new User("Carol", 3, true, 2);
        library.addUser(alice); library.addUser(bob); library.addUser(carol);
    }

    @Test public void checkoutAndReturnMakesBookAvailable() throws Exception {
        library.checkOut(100, 1);
        assertEquals(BookStatus.CHECKED_OUT, book.getStatus());
        assertTrue(alice.hasBook(100));
        library.returnBook(100);
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertFalse(alice.hasBook(100));
    }

    @Test public void returnHandsBookToFirstWaitlistedUser() throws Exception {
        library.checkOut(100, 1);
        library.checkOut(100, 2);
        library.checkOut(100, 3);
        library.returnBook(100);
        assertEquals(BookStatus.CHECKED_OUT, book.getStatus());
        assertEquals(2, book.getBorrowerId());
        assertFalse(alice.hasBook(100));
        assertTrue(bob.hasBook(100));
    }

    @Test public void bagFullDoesNotChangeBookOrWaitlist() throws Exception {
        alice.addBook(999);
        alice.addBook(998);
        try { library.checkOut(100, 1); fail("Expected InventoryFullException"); }
        catch (InventoryFullException expected) { }
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test public void reserveThenCheckoutAndCancelReservation() throws Exception {
        library.reserve(100, 1);
        assertEquals(BookStatus.RESERVED, book.getStatus());
        library.checkOut(100, 1);
        assertEquals(BookStatus.CHECKED_OUT, book.getStatus());
        library.returnBook(100);
        library.reserve(100, 1);
        library.cancelReservation(100, 1);
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertFalse(alice.hasBook(100));
    }

    @Test public void userCanLeaveWaitlist() throws Exception {
        library.checkOut(100, 1);
        library.checkOut(100, 2);
        assertTrue(bob.hasBook(100));
        library.cancelWaitlist(100, 2);
        assertFalse(bob.hasBook(100));
        assertEquals(0, book.getWaitlist().size());
    }

    @Test public void titleSearchIsCaseInsensitiveAndPartial() {
        assertEquals(1, library.findBooksByTitle("duNe").size());
        assertTrue(library.findBooksByTitle("missing").isEmpty());
    }

    @Test(expected = BookNotFoundException.class)
    public void unknownBookThrows() throws Exception { library.findBook(999); }

    @Test(expected = BookNotCheckedOutException.class)
    public void cannotReturnAvailableBook() throws Exception { library.returnBook(100); }
