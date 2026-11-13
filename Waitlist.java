public class Waitlist 
{
    private final LinkedQueue<User> vipQueue = new LinkedQueue<>();
    private final LinkedQueue<User> regularQueue = new LinkedQueue<>();

    public void add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.isVIP()) {
            vipQueue.enqueue(user);
        } else {
            regularQueue.enqueue(user);
        }
    }

    public boolean isEmpty() {
        return vipQueue.isEmpty() && regularQueue.isEmpty();
    }
}