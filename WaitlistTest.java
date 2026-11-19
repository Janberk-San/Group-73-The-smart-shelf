import static org.junit.Assert.*;
import org.junit.Test;

public class WaitlistTest {
    @Test public void servesUsersInFifoOrderEvenIfOneIsVip() {
        Waitlist line = new Waitlist();
        User regular = new User("Regular", 1, false, 2);
        User vip = new User("VIP", 2, true, 2);
        line.add(regular);
        line.add(vip);
        assertSame(regular, line.nextInLine());
        assertSame(vip, line.nextInLine());
        assertTrue(line.isEmpty());
    }

    @Test public void removesRequestedUserWithoutChangingOtherOrder() {
        Waitlist line = new Waitlist();
        User first = new User("First", 1, false, 2);
        User second = new User("Second", 2, false, 2);
        User third = new User("Third", 3, false, 2);
        line.add(first); line.add(second); line.add(third);
        assertTrue(line.removeUser(2));
        assertFalse(line.removeUser(99));
        assertSame(first, line.nextInLine());
        assertSame(third, line.nextInLine());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullUser() { new Waitlist().add(null); }

    @Test(expected = EmptyQueueException.class)
    public void emptyWaitlistThrows() { new Waitlist().nextInLine(); }
}
