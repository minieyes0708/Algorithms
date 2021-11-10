import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    /* **************** *
     * member variables *
     * **************** */
    private int count;
    private ListItem first, last;
    /* **************** *
     * Linked List Item *
     * **************** */
    private class ListItem {
        public Item item;
        public ListItem next;
        public ListItem prev;
    }
    /* ******** *
     * Iterator *
     * ******** */
    private class DequeIterator implements Iterator<Item> {
        private ListItem current;
        public DequeIterator() {
            current = first;
        }
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (current == null) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    /* ************** *
     * ADT Interfaces *
     * ************** */
    public Deque() {
        count = 0;
        last = null;
        first = null;
    }
    public boolean isEmpty() {
        return count == 0;
    }
    public int size() {
        return count;
    }
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        ListItem newitem = new ListItem();
        newitem.item = item;
        newitem.next = first;
        first = newitem;
        if (first.next != null)
            first.next.prev = first;
        if (isEmpty())
            last = first;
        count++;
    }
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        ListItem newitem = new ListItem();
        newitem.item = item;
        newitem.prev = last;
        last = newitem;
        if (last.prev != null)
            last.prev.next = last;
        if (isEmpty())
            first = last;
        count++;
    }
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null)
            first.prev = null;
        count--;
        if (isEmpty())
            last = first;
        return item;
    }
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        if (last != null)
            last.next = null;
        count--;
        if (isEmpty())
            first = last;
        return item;
    }
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    /* ************** *
     * Helper Methods *
     * ************** */
    private void print() {
        StdOut.printf("Deque Info: isEmpty = %s, size = %d, Contents =  ", isEmpty() ? "yes" : "no", size());
        for (Item i : this) StdOut.print(i + ",");
        StdOut.println();
    }
    /* **** *
     * Main *
     * **** */
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        StdOut.println("Initial empty");
        StdOut.println("isEmpty = " + dq.isEmpty());

        StdOut.println("AddLast 1 to 5");
        dq.addLast(1);
        dq.addLast(2);
        dq.addLast(3);
        dq.addLast(4);
        dq.addLast(5);
        dq.print();
        StdOut.println("isEmpty = " + dq.isEmpty());
        StdOut.println("size = " + dq.size());

        Iterator<Integer> iter = dq.iterator();
        while (iter.hasNext()) {
            StdOut.printf("Iterator got %d\n", iter.next());
        }

        StdOut.println("AddFirst 1 to 5");
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addFirst(3);
        dq.addFirst(4);
        dq.addFirst(5);
        dq.print();

        StdOut.println("Removing Last " + dq.removeLast());
        StdOut.println("Removing Last " + dq.removeLast());
        StdOut.println("Removing Last " + dq.removeLast());
        StdOut.println("Removing Last " + dq.removeLast());
        StdOut.println("Removing Last " + dq.removeLast());
        dq.print();

        StdOut.println("Removing First " + dq.removeFirst());
        StdOut.println("Removing First " + dq.removeFirst());
        StdOut.println("Removing First " + dq.removeFirst());
        StdOut.println("Removing First " + dq.removeFirst());
        StdOut.println("Removing First " + dq.removeFirst());
        dq.print();

        StdOut.println("AddFirst 1 to 5");
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addFirst(3);
        dq.addFirst(4);
        dq.addFirst(5);
        dq.print();

        StdOut.println("AddLast 1 to 5");
        dq.addLast(1);
        dq.addLast(2);
        dq.addLast(3);
        dq.addLast(4);
        dq.addLast(5);
        dq.print();
    }
}
