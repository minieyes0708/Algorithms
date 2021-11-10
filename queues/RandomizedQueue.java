import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    /* **************** *
     * member variables *
     * **************** */
    private int count;
    private Item[] items;
    /* ******** *
     * Iterator *
     * ******** */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int index;
        private final int[] sequence;
        public RandomizedQueueIterator() {
            sequence = StdRandom.permutation(count);
        }
        public boolean hasNext() {
            return index != count;
        }
        public Item next() {
            if (index == count) throw new java.util.NoSuchElementException();
            Item item = items[sequence[index]];
            index = index + 1;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    /* ************** *
     * ADT Interfaces *
     * ************** */
    public RandomizedQueue() {
        count = 0;
        items = (Item[]) new Object[1];
    }
    public boolean isEmpty() {
        return count == 0;
    }
    public int size() {
        return count;
    }
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        items[count++] = item;
        if (count == items.length)
            resize(items.length * 2);
    }
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        // random index then swap to last
        int index = StdRandom.uniform(count);
        swap(index, count - 1);
        // extract last item then return
        Item item = items[count - 1];
        items[count - 1] = null;
        count--;
        return item;
    }
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(count);
        return items[index];
    }
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    /* ************** *
     * Helper Methods *
     * ************** */
    private void swap(int i, int j) {
        Item tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }
    private void resize(int newsize) {
        Item[] newItems = (Item[]) new Object[newsize];
        for (int i = 0; i < count; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }
    private void print() {
        StdOut.print("Queue Info: ");
        StdOut.printf("capacity = %d, size = %d, elements = ", items.length, size());
        for (Item i : this) StdOut.print(i + ",");
        StdOut.println();
    }
    /* **** *
     * Main *
     * **** */
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        StdOut.println("Initial empty");
        StdOut.println("isEmpty = " + queue.isEmpty());

        StdOut.println("Enqueue 1 to 5");
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.print();
        StdOut.println("isEmpty = " + queue.isEmpty());
        StdOut.println("size = " + queue.size());

        Iterator<Integer> iter = queue.iterator();
        while (iter.hasNext()) {
            StdOut.printf("Iterator got %d\n", iter.next());
        }

        StdOut.println("Dequeue " + queue.dequeue());
        StdOut.println("Dequeue " + queue.dequeue());
        StdOut.println("Dequeue " + queue.dequeue());
        StdOut.println("Dequeue " + queue.dequeue());
        StdOut.println("Dequeue " + queue.dequeue());
        queue.print();
    }
}
