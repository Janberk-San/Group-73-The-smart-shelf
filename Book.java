/**
 * One book on the Smart Shelf.
 *
 * A Book only stores and reports its own state: its ID, title and author,
 * its status, who has it, who it is reserved for, and the line of users
 * waiting for it. The checkout rules live in Library.
 */
public class Book {
    /** Value of borrowerId and reservedForId when no user is attached. */
    public static final int NO_USER = -1;

    private final int id;
    private final String title;
    private final String author;
    private final Waitlist waitlist;
    private BookStatus status;
    private int borrowerId;
    private int reservedForId;

    /**
     * Creates a book that is AVAILABLE, with no borrower, no reservation and
     * an empty waitlist.
     *
     * @param id     the book's unique ID
     * @param title  the book's title
     * @param author the book's author
     * @throws IllegalArgumentException if the title or author is null or blank
     */
    public Book(int id, String title, String author) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        this.id = id;
        this.title = title;
        this.author = author;
        this.waitlist = new Waitlist();
        this.status = BookStatus.AVAILABLE;
        this.borrowerId = NO_USER;
        this.reservedForId = NO_USER;
    }

    /** @return the book's unique ID */
    public int getId() {
        return id;
    }

    /** @return the book's title */
    public String getTitle() {
        return title;
    }

    /** @return the book's author */
    public String getAuthor() {
        return author;
    }

    /** @return AVAILABLE, CHECKED_OUT or RESERVED */
    public BookStatus getStatus() {
        return status;
    }

    /**
     * Changes the book's status.
     *
     * @param status the new status
     * @throws IllegalArgumentException if status is null
     */
    public void setStatus(BookStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    /** @return the ID of the user who has the book, or NO_USER (-1) */
    public int getBorrowerId() {
        return borrowerId;
    }

    /** @param borrowerId the ID of the user who has the book, or NO_USER (-1) */
    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    /** @return the ID of the user holding the reservation, or NO_USER (-1) */
    public int getReservedForId() {
        return reservedForId;
    }

    /** @param reservedForId the ID of the user holding the reservation, or NO_USER (-1) */
    public void setReservedForId(int reservedForId) {
        this.reservedForId = reservedForId;
    }

    /** @return the line of users waiting for this book */
    public Waitlist getWaitlist() {
        return waitlist;
    }
}
