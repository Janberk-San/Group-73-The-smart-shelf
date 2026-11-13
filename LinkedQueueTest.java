import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the existing LinkedQueue<T> implementation.
 * Adding tests for methods in LinkedQueue<T> and different functionalities
 */
public class LinkedQueueTest {
    private LinkedQueue<Integer> queue;

    @Before
    public void setUp() {
        queue = new LinkedQueue<Integer>();
    }

    @Test
    public void newQueueStartsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void enqueueAddsOneItem() {
        queue.enqueue(10);

        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(Integer.valueOf(10), queue.peek());
    }

    @Test
    public void enqueueMultipleItemsPreservesFifoOrder() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertEquals(Integer.valueOf(10), queue.dequeue());
        assertEquals(Integer.valueOf(20), queue.dequeue());
        assertEquals(Integer.valueOf(30), queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void peekReturnsFrontWithoutRemovingIt() {
        queue.enqueue(7);
        queue.enqueue(8);

        assertEquals(Integer.valueOf(7), queue.peek());
        assertEquals(2, queue.size());
        assertEquals(Integer.valueOf(7), queue.dequeue());
    }

    @Test
    public void dequeueDecreasesSize() {
        queue.enqueue(1);
        queue.enqueue(2);

        queue.dequeue();

        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void removingLastItemMakesQueueEmpty() {
        queue.enqueue(42);

        assertEquals(Integer.valueOf(42), queue.dequeue());

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void enqueueAfterQueueBecomesEmptyWorks() {
        queue.enqueue(1);
        queue.dequeue();

        // This checks that tail is reset correctly when the final item is removed.
        queue.enqueue(2);

        assertEquals(1, queue.size());
        assertEquals(Integer.valueOf(2), queue.peek());
        assertEquals(Integer.valueOf(2), queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void enqueueRejectsNull() {
        try {
            queue.enqueue(null);
            fail("Expected IllegalArgumentException when enqueuing null.");
        } catch (IllegalArgumentException expected) {
            assertEquals("Cannot enqueue null", expected.getMessage());
        }

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void dequeueOnEmptyQueueThrowsException() {
        try {
            queue.dequeue();
            fail("Expected an exception when dequeuing an empty queue.");
        } catch (RuntimeException expected) {
            assertEquals("Queue is empty", expected.getMessage());
        }
    }

    @Test
    public void peekOnEmptyQueueThrowsException() {
        try {
            queue.peek();
            fail("Expected an exception when peeking at an empty queue.");
        } catch (RuntimeException expected) {
            assertEquals("Queue is empty", expected.getMessage());
        }
    }
}
