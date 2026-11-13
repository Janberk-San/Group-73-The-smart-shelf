import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for User.
 * Covers the constructor, the getters, and the book bag methods addBook,
 * removeBook and hasBook. No view, controller or Scanner is used.
 */
public class UserTest {
    private static final int CAPACITY = 3;

    private User user;

    @Before
    public void setUp() {
        user = new User("Alice", 10, false, CAPACITY);
    }

    // ---------- constructor and getters ----------

    @Test
    public void constructorStoresNameAndId() {
        assertEquals("Alice", user.getName());
        assertEquals(10, user.getId());
    }

    @Test
    public void regularUserIsNotVip() {
        assertFalse(user.isVIP());
    }

    @Test
    public void vipUserIsVip() {
        User vera = new User("Vera", 12, true, CAPACITY);

        assertTrue(vera.isVIP());
        assertEquals("Vera", vera.getName());
        assertEquals(12, vera.getId());
    }

    @Test
    public void newUserStartsWithEmptyBag() {
        assertNotNull(user.getBookBag());
        assertTrue(user.getBookBag().isEmpty());
        assertEquals(0, user.getBookBag().getCurrentSize());
    }

    @Test
    public void constructorRejectsNullName() {
        assertConstructorRejects(null, CAPACITY, "Name cannot be null or empty");
    }

    @Test
    public void constructorRejectsEmptyName() {
        assertConstructorRejects("", CAPACITY, "Name cannot be null or empty");
    }

    @Test
    public void constructorRejectsBlankName() {
        assertConstructorRejects("   ", CAPACITY, "Name cannot be null or empty");
    }

    @Test
    public void constructorRejectsNegativeInventorySize() {
        assertConstructorRejects("Alice", -1, "Inventory size cannot be negative");
    }

    // ---------- addBook ----------

    @Test
    public void addBookPutsIdInBag() throws InventoryFullException {
        user.addBook(5);

        assertTrue(user.hasBook(5));
        assertEquals(1, user.getBookBag().getCurrentSize());
    }

    @Test
    public void addBookFillsBagUpToTheLimit() throws InventoryFullException {
        user.addBook(1);
        user.addBook(2);
        user.addBook(3);

        assertTrue(user.hasBook(1));
        assertTrue(user.hasBook(2));
        assertTrue(user.hasBook(3));
        assertEquals(CAPACITY, user.getBookBag().getCurrentSize());
    }

    @Test
    public void addBookThrowsWhenBagIsFull() throws InventoryFullException {
        user.addBook(1);
        user.addBook(2);
        user.addBook(3);

        try {
            user.addBook(4);
            fail("Expected InventoryFullException when the bag is full.");
        } catch (InventoryFullException expected) {
            assertEquals("Alice has reached their book limit (3).", expected.getMessage());
        }

        // The failed add must not have changed the bag.
        assertFalse(user.hasBook(4));
        assertTrue(user.hasBook(1));
        assertTrue(user.hasBook(2));
        assertTrue(user.hasBook(3));
        assertEquals(CAPACITY, user.getBookBag().getCurrentSize());
    }

    @Test
    public void addBookKeepsThrowingWhileBagStaysFull() throws InventoryFullException {
        user.addBook(1);
        user.addBook(2);
        user.addBook(3);

        for (int bookId = 4; bookId <= 6; bookId++) {
            try {
                user.addBook(bookId);
                fail("Expected InventoryFullException for book " + bookId + ".");
            } catch (InventoryFullException expected) {
                assertFalse(user.hasBook(bookId));
            }
        }

        assertEquals(CAPACITY, user.getBookBag().getCurrentSize());
    }

    @Test
    public void userWithZeroCapacityCannotAddAnyBook() {
        User noRoom = new User("Bob", 11, false, 0);

        try {
            noRoom.addBook(1);
            fail("Expected InventoryFullException when the bag has no room at all.");
        } catch (InventoryFullException expected) {
            assertEquals("Bob has reached their book limit (0).", expected.getMessage());
        }

        assertFalse(noRoom.hasBook(1));
        assertTrue(noRoom.getBookBag().isEmpty());
    }

    // ---------- removeBook ----------

    @Test
    public void removeBookTakesIdOutOfBag() throws InventoryFullException {
        user.addBook(5);

        assertTrue(user.removeBook(5));

        assertFalse(user.hasBook(5));
        assertTrue(user.getBookBag().isEmpty());
    }

    @Test
    public void removeBookReturnsFalseWhenIdNotInBag() throws InventoryFullException {
        user.addBook(5);

        assertFalse(user.removeBook(9));

        // The bag is unchanged.
        assertTrue(user.hasBook(5));
        assertEquals(1, user.getBookBag().getCurrentSize());
    }

    @Test
    public void removeBookOnEmptyBagReturnsFalse() {
        assertFalse(user.removeBook(5));

        assertTrue(user.getBookBag().isEmpty());
    }

    @Test
    public void removeBookOnlyRemovesTheRequestedId() throws InventoryFullException {
        user.addBook(1);
        user.addBook(2);
        user.addBook(3);

        assertTrue(user.removeBook(2));

        assertTrue(user.hasBook(1));
        assertFalse(user.hasBook(2));
        assertTrue(user.hasBook(3));
        assertEquals(2, user.getBookBag().getCurrentSize());
    }

    @Test
    public void removeBookTwiceReturnsFalseTheSecondTime() throws InventoryFullException {
        user.addBook(5);

        assertTrue(user.removeBook(5));
        assertFalse(user.removeBook(5));
    }

    @Test
    public void removeBookFreesASlotInAFullBag() throws InventoryFullException {
        user.addBook(1);
        user.addBook(2);
        user.addBook(3);

        user.removeBook(1);
        user.addBook(4);

        assertFalse(user.hasBook(1));
        assertTrue(user.hasBook(4));
        assertEquals(CAPACITY, user.getBookBag().getCurrentSize());
    }

    @Test
    public void bagCanBeEmptiedAndRefilled() throws InventoryFullException {
        for (int bookId = 1; bookId <= CAPACITY; bookId++) {
            user.addBook(bookId);
        }
        for (int bookId = 1; bookId <= CAPACITY; bookId++) {
            assertTrue(user.removeBook(bookId));
        }
        assertTrue(user.getBookBag().isEmpty());

        for (int bookId = 7; bookId < 7 + CAPACITY; bookId++) {
            user.addBook(bookId);
        }

        assertTrue(user.hasBook(7));
        assertTrue(user.hasBook(8));
        assertTrue(user.hasBook(9));
        assertEquals(CAPACITY, user.getBookBag().getCurrentSize());
    }

    // ---------- hasBook ----------

    @Test
    public void hasBookIsTrueAfterAdding() throws InventoryFullException {
        user.addBook(5);

        assertTrue(user.hasBook(5));
    }

    @Test
    public void hasBookIsFalseForIdNeverAdded() throws InventoryFullException {
        assertFalse(user.hasBook(5));

        user.addBook(5);

        assertFalse(user.hasBook(6));
    }

    @Test
    public void hasBookIsFalseAfterRemoving() throws InventoryFullException {
        user.addBook(5);
        user.removeBook(5);

        assertFalse(user.hasBook(5));
    }

    // ---------- getBookBag ----------

    @Test
    public void getBookBagShowsAddsAndRemoves() throws InventoryFullException {
        user.addBook(5);
        user.addBook(6);
        assertTrue(user.getBookBag().contains(5));
        assertTrue(user.getBookBag().contains(6));
        assertEquals(2, user.getBookBag().getCurrentSize());

        user.removeBook(5);
        assertFalse(user.getBookBag().contains(5));
        assertTrue(user.getBookBag().contains(6));
        assertEquals(1, user.getBookBag().getCurrentSize());
    }

    @Test
    public void getBookBagReturnsSameBagEveryTime() {
        assertSame(user.getBookBag(), user.getBookBag());
    }

    @Test
    public void usersDoNotShareABag() throws InventoryFullException {
        User bob = new User("Bob", 11, false, CAPACITY);

        user.addBook(5);

        assertTrue(user.hasBook(5));
        assertFalse(bob.hasBook(5));
        assertNotSame(user.getBookBag(), bob.getBookBag());
        assertTrue(bob.getBookBag().isEmpty());
    }

    // ---------- helper ----------

    private void assertConstructorRejects(String name, int inventorySize, String expectedMessage) {
        try {
            new User(name, 12, false, inventorySize);
            fail("Expected IllegalArgumentException for name \"" + name
                    + "\" and inventory size " + inventorySize + ".");
        } catch (IllegalArgumentException expected) {
            assertEquals(expectedMessage, expected.getMessage());
        }
    }
}
