public class Waitlist 
{
    private final LinkedQueue<User> vipQueue = new LinkedQueue<>();
    private final LinkedQueue<User> regularQueue = new LinkedQueue<>();

    public void add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.isVIP()) 
        {
            vipQueue.enqueue(user);
        } 
        
        else 
        {
            regularQueue.enqueue(user);
        }
    }

    public User nextInLine() {
        if (!vipQueue.isEmpty()) {
            return vipQueue.dequeue();
        }
        if (!regularQueue.isEmpty()) {
            return regularQueue.dequeue();
        }
        throw new EmptyQueueException("Waitlist is empty");
    }

    public boolean isEmpty() {
        return vipQueue.isEmpty() && regularQueue.isEmpty();
    }

    public int size() {
        return vipQueue.size() + regularQueue.size();
    }

    public boolean removeUser(int userId) {
        LinkedQueue<User> remainingVip = new LinkedQueue<>();
        LinkedQueue<User> remainingRegular = new LinkedQueue<>();
        boolean removed = removeMatching(vipQueue, remainingVip, userId);
        removed = removeMatching(regularQueue, remainingRegular, userId) || removed;
        restore(vipQueue, remainingVip);
        restore(regularQueue, remainingRegular);
        return removed;
    }

    private boolean removeMatching(LinkedQueue<User> source,
                                   LinkedQueue<User> remaining,
                                   int userId) {
        boolean removed = false;
        while (!source.isEmpty()) {
            User user = source.dequeue();
            if (!removed && user.getId() == userId) {
                removed = true;
            } else {
                remaining.enqueue(user);
            }
        }
        return removed;
    }

    private void restore(LinkedQueue<User> destination,
                         LinkedQueue<User> remaining) {
        while (!remaining.isEmpty()) {
            destination.enqueue(remaining.dequeue());
        }
    }
}