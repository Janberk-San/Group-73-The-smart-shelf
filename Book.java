public class Book {

    // Book information
    private final int id;
    private final String title;
    private final String author;

    // Book status and reservation information
    private BookStatus status;
    private int reservedForId;
    private int borrowerId;

    // Each book has its own waitlist
    private final Waitlist waitlist;

    /**
     * Creates a new book.
     * A new book starts as AVAILABLE and has no borrower
     * or reservation.
     *
     * @param id unique ID of the book
     * @param title title of the book
     * @param author author of the book
     */
    public Book(int id, String title, String author) {

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Book title cannot be null or empty");
        }

        this.id = id;
        this.title = title;
        this.author = author;

        this.status = BookStatus.AVAILABLE;
        this.reservedForId = -1;
        this.borrowerId = -1;

        this.waitlist = new Waitlist();
    }

    /**
     * Returns the book's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the book's author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the current status of the book.
     */
    public BookStatus getStatus() {
        return status;
    }

    /**
     * Changes the book's status.
     */
    public void setStatus(BookStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                "Book status cannot be null");
        }

        this.status = status;
    }

    /**
     * Returns the ID of the user who reserved the book.
     * Returns -1 if the book is not reserved.
     */
    public int getReservedForId() {
        return reservedForId;
    }

    /**
     * Sets the ID of the user who reserved the book.
     */
    public void setReservedForId(int reservedForId) {
        this.reservedForId = reservedForId;
    }

    /**
     * Returns the ID of the user currently borrowing the book.
     * Returns -1 if there is no borrower.
     */
    public int getBorrowerId() {
        return borrowerId;
    }

    /**
     * Sets the ID of the current borrower.
     */
    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    /**
     * Returns this book's waitlist.
     */
    public Waitlist getWaitlist() {
        return waitlist;
    }
}
