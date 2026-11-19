import static org.junit.Assert.*;
import org.junit.Test;

public class WaitlistTest {
    @Test public void servesVipUsersFirstAndPreservesFifoWithinEachPriority() {
        Waitlist line = new Waitlist();
        User regularFirst = new User("Regular First", 1, false, 2);
        User vipFirst = new User("VIP First", 2, true, 2);
        User regularSecond = new User("Regular Second", 3, false, 2);
        User vipSecond = new User("VIP Second", 4, true, 2);
        line.add(regularFirst);
        line.add(vipFirst);
        line.add(regularSecond);
        line.add(vipSecond);
        assertSame(vipFirst, line.nextInLine());
        assertSame(vipSecond, line.nextInLine());
        assertSame(regularFirst, line.nextInLine());
        assertSame(regularSecond, line.nextInLine());
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
