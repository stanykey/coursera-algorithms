//  This program creates an initial board from each filename specified
//  on the command line and finds the minimum number of moves to
//  reach the goal state.
//
//  java PuzzleChecker puzzle*.txt
//  puzzle00.txt: 0
//  puzzle01.txt: 1
//  puzzle02.txt: 2
//  puzzle03.txt: 3
//  puzzle04.txt: 4
//  puzzle05.txt: 5
//  puzzle06.txt: 6
//  ...
//  puzzle3x3-impossible: -1
//  ...
//  puzzle42.txt: 42
//  puzzle43.txt: 43
//  puzzle44.txt: 44
//  puzzle45.txt: 45
//

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleChecker {
    public static void main(String[] args) {
        for (String filename : args) {
            final In in = new In(filename);
            final int dimension = in.readInt();
            final int[][] tiles = new int[dimension][dimension];
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            final Board initial = new Board(tiles);
            final Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
        }
    }
}
