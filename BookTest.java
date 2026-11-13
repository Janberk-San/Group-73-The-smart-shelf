import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Book.
 * Covers the constructor, every getter and setter, and the waitlist each
 * book owns. No view, controller or Scanner is used.
 */
public class BookTest {
    private Book book;

    @Before
    public void setUp() {
        book = new Book(1, "Dune", "Frank Herbert");
    }

    // ---------- constructor ----------

    @Test
    public void constructorStoresIdTitleAndAuthor() {
        assertEquals(1, book.getId());
        assertEquals("Dune", book.getTitle());
        assertEquals("Frank Herbert", book.getAuthor());
    }

    @Test
    public void newBookStartsAvailable() {
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test
    public void newBookHasNoBorrowerOrReservation() {
        assertEquals(-1, book.getBorrowerId());
        assertEquals(-1, book.getReservedForId());
    }

    @Test
    public void newBookStartsWithEmptyWaitlist() {
        assertNotNull(book.getWaitlist());
        assertTrue(book.getWaitlist().isEmpty());
    }

    @Test
    public void noUserConstantIsMinusOne() {
        // Library writes -1 directly, so the constant has to stay -1.
        assertEquals(-1, Book.NO_USER);
    }

    @Test
    public void titleMayContainDigits() {
        Book numbers = new Book(2, "1984", "George Orwell");

        assertEquals("1984", numbers.getTitle());
    }

    @Test
    public void constructorRejectsNullTitle() {
        assertConstructorRejects(null, "Frank Herbert", "Title cannot be null or empty");
    }

    @Test
    public void constructorRejectsEmptyTitle() {
        assertConstructorRejects("", "Frank Herbert", "Title cannot be null or empty");
    }

    @Test
    public void constructorRejectsBlankTitle() {
        assertConstructorRejects("   ", "Frank Herbert", "Title cannot be null or empty");
    }

    @Test
    public void constructorRejectsNullAuthor() {
        assertConstructorRejects("Dune", null, "Author cannot be null or empty");
    }

    @Test
    public void constructorRejectsEmptyAuthor() {
        assertConstructorRejects("Dune", "", "Author cannot be null or empty");
    }

    @Test
    public void constructorRejectsBlankAuthor() {
        assertConstructorRejects("Dune", "   ", "Author cannot be null or empty");
    }

    // ---------- setStatus ----------

    @Test
    public void setStatusChangesStatus() {
        book.setStatus(BookStatus.CHECKED_OUT);

        assertEquals(BookStatus.CHECKED_OUT, book.getStatus());
    }

    @Test
    public void setStatusCanReachEveryStatus() {
        for (BookStatus status : BookStatus.values()) {
            book.setStatus(status);

            assertEquals(status, book.getStatus());
        }
    }

    @Test
    public void setStatusRejectsNull() {
        book.setStatus(BookStatus.RESERVED);

        try {
            book.setStatus(null);
            fail("Expected IllegalArgumentException when setting a null status.");
        } catch (IllegalArgumentException expected) {
            assertEquals("Status cannot be null", expected.getMessage());
        }

        // The failed call must not have changed anything.
        assertEquals(BookStatus.RESERVED, book.getStatus());
    }

    @Test
    public void setStatusLeavesBorrowerAndReservationAlone() {
        book.setBorrowerId(10);
        book.setReservedForId(11);

        book.setStatus(BookStatus.CHECKED_OUT);

        assertEquals(10, book.getBorrowerId());
        assertEquals(11, book.getReservedForId());
    }

    // ---------- borrower ----------

    @Test
    public void setBorrowerIdStoresBorrower() {
        book.setBorrowerId(10);

        assertEquals(10, book.getBorrowerId());
    }

    @Test
    public void setBorrowerIdCanGoBackToNoUser() {
        book.setBorrowerId(10);

        book.setBorrowerId(Book.NO_USER);

        assertEquals(-1, book.getBorrowerId());
    }

    @Test
    public void setBorrowerIdLeavesStatusAndReservationAlone() {
        // Book only stores state. Library sets each field itself.
        book.setBorrowerId(10);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertEquals(-1, book.getReservedForId());
    }

    // ---------- reservation ----------

    @Test
    public void setReservedForIdStoresReservation() {
        book.setReservedForId(12);

        assertEquals(12, book.getReservedForId());
    }

    @Test
    public void setReservedForIdCanGoBackToNoUser() {
        book.setReservedForId(12);

        book.setReservedForId(Book.NO_USER);

        assertEquals(-1, book.getReservedForId());
    }

    @Test
    public void setReservedForIdLeavesStatusAndBorrowerAlone() {
        book.setReservedForId(12);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertEquals(-1, book.getBorrowerId());
    }

    // ---------- waitlist ----------

    @Test
    public void getWaitlistReturnsSameWaitlistEveryTime() {
        assertSame(book.getWaitlist(), book.getWaitlist());
    }

    @Test
    public void usersAddedToWaitlistStayWithTheBook() {
        User alice = new User("Alice", 10, false, 3);

        book.getWaitlist().add(alice);

        assertFalse(book.getWaitlist().isEmpty());
        assertSame(alice, book.getWaitlist().nextInLine());
        assertTrue(book.getWaitlist().isEmpty());
    }

    @Test
    public void eachBookOwnsItsOwnWaitlist() {
        Book other = new Book(2, "1984", "George Orwell");

        book.getWaitlist().add(new User("Alice", 10, false, 3));

        assertNotSame(book.getWaitlist(), other.getWaitlist());
        assertFalse(book.getWaitlist().isEmpty());
        assertTrue(other.getWaitlist().isEmpty());
    }

    // ---------- whole book ----------

    @Test
    public void idTitleAndAuthorNeverChange() {
        book.setStatus(BookStatus.CHECKED_OUT);
        book.setBorrowerId(10);
        book.setReservedForId(11);
        book.getWaitlist().add(new User("Bob", 11, false, 3));

        assertEquals(1, book.getId());
        assertEquals("Dune", book.getTitle());
        assertEquals("Frank Herbert", book.getAuthor());
    }

    @Test
    public void bookCanBeReservedCheckedOutAndReturned() {
        // The same field changes Library makes, in order.
        book.setStatus(BookStatus.RESERVED);
        book.setReservedForId(10);
        assertEquals(BookStatus.RESERVED, book.getStatus());
        assertEquals(10, book.getReservedForId());
        assertEquals(-1, book.getBorrowerId());

        book.setStatus(BookStatus.CHECKED_OUT);
        book.setBorrowerId(10);
        book.setReservedForId(-1);
        assertEquals(BookStatus.CHECKED_OUT, book.getStatus());
        assertEquals(10, book.getBorrowerId());
        assertEquals(-1, book.getReservedForId());

        book.setStatus(BookStatus.AVAILABLE);
        book.setBorrowerId(-1);
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertEquals(-1, book.getBorrowerId());
        assertEquals(-1, book.getReservedForId());
    }

    // ---------- helper ----------

    private void assertConstructorRejects(String title, String author, String expectedMessage) {
        try {
            new Book(2, title, author);
            fail("Expected IllegalArgumentException for title \"" + title
                    + "\" and author \"" + author + "\".");
        } catch (IllegalArgumentException expected) {
            assertEquals(expectedMessage, expected.getMessage());
        }
    }
}
