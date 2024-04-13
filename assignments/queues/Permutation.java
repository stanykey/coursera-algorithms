import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;


public class Permutation {
    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("usage: Permutation <visible items count>");
            return;
        }

        final int needToShow = Integer.parseInt(args[0]);

        int totalCount = 0;
        RandomizedQueue<String> strings = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            totalCount++;
            if (strings.size() == needToShow) {
                final int value = StdRandom.uniformInt(totalCount);
                if (value < needToShow) {
                    strings.dequeue();
                    strings.enqueue(item);
                }
            } else {
                strings.enqueue(item);
            }
        }

        Iterator<String> iterator = strings.iterator();
        for (int i = 0; i < needToShow; i++) {
            StdOut.println(iterator.next());
        }
    }
}
