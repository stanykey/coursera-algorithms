import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private final List<Board> solution = new ArrayList<>();

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null");
        }

        MinPQ<SearchNode> mainPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        mainPQ.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));
        while (!mainPQ.isEmpty() && !twinPQ.isEmpty()) {
            if (processNextNode(mainPQ, true) || processNextNode(twinPQ, false)) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        final int dimension = in.readInt();
        final int[][] tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        final Board initial = new Board(tiles);
        final Solver solver = new Solver(initial);
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

    private boolean processNextNode(MinPQ<SearchNode> pq, boolean isMain) {
        final SearchNode currentNode = pq.delMin();
        if (currentNode.board.isGoal()) {
            if (isMain) {
                for (SearchNode node = currentNode; node != null; node = node.previous) {
                    solution.add(0, node.board);
                }
            }
            return true;
        }

        for (final Board neighbor : currentNode.board.neighbors()) {
            if (currentNode.previous == null || !neighbor.equals(currentNode.previous.board)) {
                pq.insert(new SearchNode(neighbor, currentNode.moves + 1, currentNode));
            }
        }
        return false;
    }

    public boolean isSolvable() {
        return !solution.isEmpty();
    }

    public int moves() {
        return solution.isEmpty() ? -1 : solution.size() - 1;
    }

    public Iterable<Board> solution() {
        return solution.isEmpty() ? null : solution;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        final SearchNode previous;
        final Board board;
        final int moves;
        final int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.previous = previous;
            this.board = board;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }
}
