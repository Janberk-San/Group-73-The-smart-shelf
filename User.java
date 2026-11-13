/**
 * One person who uses the Smart Shelf.
 *
 * The book bag holds the ID of every book the user has checked out, has
 * reserved, or is waiting for, so the limit covers all three. Joining a
 * waitlist takes a slot up front, which means the user always has room when
 * the book is handed to them.
 */
public class User {
    private final String name;
    private final int id;
    private final boolean vip;
    private final int inventorySize;
    private final ArrayBag<Integer> bookBag;

    /**
     * Creates a user with an empty book bag.
     *
     * @param name          the user's name
     * @param id            the user's unique ID
     * @param vip           true if the user goes ahead of regular users on a waitlist
     * @param inventorySize the most book IDs the bag can hold
     * @throws IllegalArgumentException if the name is null or blank, or
     *                                  inventorySize is negative
     */
    public User(String name, int id, boolean vip, int inventorySize) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (inventorySize < 0) {
            throw new IllegalArgumentException("Inventory size cannot be negative");
        }
        this.name = name;
        this.id = id;
        this.vip = vip;
        this.inventorySize = inventorySize;
        this.bookBag = new ArrayBag<>(inventorySize);
    }

    /** @return the user's unique ID */
    public int getId() {
        return id;
    }

    /** @return the user's name */
    public String getName() {
        return name;
    }

    /** @return true if the user has VIP priority on waitlists */
    public boolean isVIP() {
        return vip;
    }

    /**
     * Returns the bag itself, not a copy. Read from it, but change it only
     * through addBook and removeBook.
     *
     * @return the bag of book IDs the user has checked out, reserved or is waiting for
     */
    public ArrayBag<Integer> getBookBag() {
        return bookBag;
    }

    /**
     * Adds a book ID to the bag. ArrayBag.add returns false when the bag is
     * full instead of throwing, so this method checks that and throws.
     *
     * @param bookId the ID of the book to add
     * @throws InventoryFullException if the bag is already full
     */
    public void addBook(int bookId) throws InventoryFullException {
        if (!bookBag.add(bookId)) {
            throw new InventoryFullException(
                    name + " has reached their book limit (" + inventorySize + ").");
        }
    }

    /**
     * Removes a book ID from the bag.
     *
     * @param bookId the ID of the book to remove
     * @return true if the ID was removed, false if it was not in the bag
     */
    public boolean removeBook(int bookId) {
        return bookBag.remove(Integer.valueOf(bookId));
    }

    /**
     * @param bookId the ID of the book to look for
     * @return true if the ID is in the bag
     */
    public boolean hasBook(int bookId) {
        return bookBag.contains(bookId);
    }
}
