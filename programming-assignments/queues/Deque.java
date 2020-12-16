import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Node next;
        Node prev;
        Item data;
    }

    private Node head;
    private Node tail;
    private int  size;

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be a null");
        }

        Node node = new Node();
        node.data = item;
        if (head != null) {
            head.prev = node;
            node.next = head;
        }
        head = node;

        if (tail == null) {
            tail = node;
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be a null");
        }

        Node node = new Node();
        node.data = item;
        if (tail != null) {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;

        if (head == null) {
            head = node;
        }

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }

        Item item = head.data;
        size--;
        if (isEmpty()) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }

        Item item = tail.data;
        size--;
        if (isEmpty()) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        final int testSize = 32;

        StdOut.printf("Create deque with size %d and add range [1, %d] with next rules:\n", testSize, testSize);
        StdOut.println("\todd number will add into tail");
        StdOut.println("\teven number will add into head");
        Deque<Integer> container = new Deque<>();
        for (int i = 1; i <= testSize; i++) {
            if (i % 2 > 0) container.addLast(i);
            else           container.addFirst(i);
        }
        StdOut.printf("Deque size is %d\n\n", container.size());

        StdOut.println("Let's iterate over our deque:");
        for (Integer integer : container) {
            StdOut.printf("%d ", integer);
        }
        StdOut.println("\n");

        StdOut.println("Test pop-methodsLet's test pop-methods: take head than tail then head and so on...");
        boolean takeHead = true;
        while (!container.isEmpty()) {
            StdOut.println(takeHead
                    ? container.removeFirst()
                    : container.removeLast());
            takeHead = !takeHead;
        }
        StdOut.println();

        StdOut.println("Job is done.");
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return (current != null);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.data;
            current = current.next;
            return item;
        }
    }
}
