import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        int index = 0;
        while (!StdIn.isEmpty()) {
            index++;

            String word = StdIn.readString();
            if (StdRandom.bernoulli(1 / (double) index)) {
                champion = word;
            }
        }

        StdOut.println(champion);
    }
}
