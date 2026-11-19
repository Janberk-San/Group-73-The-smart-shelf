import java.util.Objects;

public class ArrayBag<T> {
    private final Object[] items;
    private int currentSize;

    public ArrayBag(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.items = new Object[capacity];
    }

    public boolean add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null");
        }
        if (currentSize == items.length) {
            return false;
        }
        items[currentSize++] = item;
        return true;
    }

    public boolean remove(T item) {
        for (int i = 0; i < currentSize; i++) {
            if (Objects.equals(items[i], item)) {
                int remaining = currentSize - i - 1;
                if (remaining > 0) {
                    System.arraycopy(items, i + 1, items, i, remaining);
                }
                items[--currentSize] = null;
                return true;
            }
        }
        return false;
    }

    public boolean contains(T item) {
        for (int i = 0; i < currentSize; i++) {
            if (Objects.equals(items[i], item)) {
                return true;
            }
        }
        return false;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }
}
