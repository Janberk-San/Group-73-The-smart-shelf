import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BookTest {

    private Book book;

    @Before
    public void setUp() {
        book = new Book(101, "The Hobbit", "J.R.R. Tolkien");
    }

    // Constructor tests

    @Test
    public void testConstructorInitializesBookInformation() {
        assertEquals(101, book.getId());
        assertEquals("The Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
    }

    @Test
    public void testConstructorSetsInitialStatusToAvailable() {
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test
    public void testConstructorInitializesBorrowerAndReservation() {
        assertEquals(-1, book.getBorrowerId());
        assertEquals(-1, book.getReservedForId());
    }

    @Test
    public void testConstructorCreatesEmptyWaitlist() {
        assertNotNull(book.getWaitlist());
        assertTrue(book.getWaitlist().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRejectsNullTitle() {
        new Book(102, null, "Unknown Author");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRejectsEmptyTitle() {
        new Book(103, "", "Unknown Author");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRejectsWhitespaceTitle() {
        new Book(104, "   ", "Unknown Author");
    }

    // Status tests

    @Test
    public void testSetStatusToCheckedOut() {
        book.setStatus(BookStatus.CHECKED_OUT);

        assertEquals(BookStatus.CHECKED_OUT, book.getStatus());
    }

    @Test
    public void testSetStatusToReserved() {
        book.setStatus(BookStatus.RESERVED);

        assertEquals(BookStatus.RESERVED, book.getStatus());
    }

    @Test
    public void testSetStatusBackToAvailable() {
        book.setStatus(BookStatus.CHECKED_OUT);
        book.setStatus(BookStatus.AVAILABLE);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStatusRejectsNull() {
        book.setStatus(null);
    }

    // Reservation tests

    @Test
    public void testSetReservedForId() {
        book.setReservedForId(25);

        assertEquals(25, book.getReservedForId());
    }

    @Test
    public void testClearReservation() {
        book.setReservedForId(25);
        book.setReservedForId(-1);

        assertEquals(-1, book.getReservedForId());
    }

    // Borrower tests

    @Test
    public void testSetBorrowerId() {
        book.setBorrowerId(50);

        assertEquals(50, book.getBorrowerId());
    }

    @Test
    public void testClearBorrowerId() {
        book.setBorrowerId(50);
        book.setBorrowerId(-1);

        assertEquals(-1, book.getBorrowerId());
    }

    // Waitlist tests

    @Test
    public void testEachBookHasItsOwnWaitlist() {
        Book anotherBook = new Book(
            105, "The Fellowship of the Ring", "J.R.R. Tolkien");

        assertNotSame(book.getWaitlist(), anotherBook.getWaitlist());
    }

    @Test
    public void testWaitlistStartsEmpty() {
        assertTrue(book.getWaitlist().isEmpty());
    }
}
