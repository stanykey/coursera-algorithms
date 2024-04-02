import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items = (Item[]) new Object[1];
    private int end = 0;

    public static void main(String[] args) {
        final int testSize = 32;
        final int quarterSize = testSize / 4;

        StdOut.printf("Create queue with size %d and enqueue range [1, %d]\n\n", testSize, testSize);
        RandomizedQueue<Integer> container = new RandomizedQueue<>();
        for (int i = 1; i <= testSize; i++) {
            container.enqueue(i);
        }
        StdOut.printf("Queue size is %d\n\n", container.size());

        StdOut.printf("Test sample method for quarter %d of queue size %d\n", quarterSize, testSize);
        for (int i = 1; i <= quarterSize; i++) {
            StdOut.println(container.sample());
        }
        StdOut.println();

        StdOut.printf("Dequeue 25%% of queue with size %d\n", container.size());
        for (int i = 1; i <= quarterSize; i++) {
            StdOut.printf("\t%d was dequeued\n", container.dequeue());
        }
        StdOut.printf("Current queue size is %d\n\n", container.size());

        StdOut.println("Let's iterate over our queue");
        for (Integer integer : container) {
            StdOut.println(integer);
        }
        StdOut.println();

        StdOut.println("Job is done.");
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public int size() {
        return end;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be a null");
        }

        if (size() == items.length) {
            resize(items.length << 1);
        }

        items[end++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }

        final int position = getRandomIndex();
        Item item = itemAt(position);
        items[position] = items[end - 1];
        items[--end] = null;
        if (size() < (items.length >> 2)) {
            resize(items.length >> 1);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }

        return itemAt(getRandomIndex());
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int needed) {
        Item[] temp = (Item[]) new Object[needed];
        System.arraycopy(items, 0, temp, 0, end);

        items = temp;
    }

    private Item itemAt(int index) {
        return items[index];
    }

    private int getRandomIndex() {
        return StdRandom.uniformInt(size());
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] indexes = StdRandom.permutation(size());
        private int cursor = 0;

        public boolean hasNext() {
            return (cursor != indexes.length);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            final int index = indexes[cursor++];
            return itemAt(index);
        }
    }
}
