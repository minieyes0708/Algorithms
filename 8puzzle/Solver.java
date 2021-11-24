import java.util.Iterator;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

public class Solver {
    private class BoardInfo {
        public int moves;
        public Board board;
        public int manhattan;
        public BoardInfo parent;
        public BoardInfo(int m, Board bd, BoardInfo p) {
            moves = m;
            board = bd;
            parent = p;
            manhattan = -1;
        }
    }
    private class SolutionIterable implements Iterable<Board> {
        private class SolutionIterator implements Iterator<Board> {
            BoardInfo info = brdSolution;
            public boolean hasNext() {
                return info != null;
            }
            public Board next() {
                if (!hasNext()) throw new NoSuchElementException();
                Board brd = info.board;
                info = info.parent;
                return brd;
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
        public Iterator<Board> iterator() {
            return new SolutionIterator();
        }
    }
    private class ManhattanComparator implements Comparator<BoardInfo> {
        public int compare(BoardInfo info1, BoardInfo info2) {
            if (info1.manhattan == -1) info1.manhattan = info1.board.manhattan();
            if (info2.manhattan == -1) info2.manhattan = info2.board.manhattan();
            return (info1.manhattan + info1.moves) - (info2.manhattan + info2.moves);
        }
    }
    private final int numOfMoves;
    private final BoardInfo brdSolution;
    private final SolutionIterable iterSolution = new SolutionIterable();
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<BoardInfo> pq = new MinPQ<BoardInfo>(new ManhattanComparator());
        pq.insert(new BoardInfo(0, initial, null));
        while (!pq.isEmpty()) {
            // StdOut.println("==========");
            // for (BoardInfo ifo : pq) {
                // StdOut.printf("%s moves = %d, manhattan = %d\n",
                    // ifo.board.toString(),
                    // ifo.moves, ifo.manhattan);
            // }
            BoardInfo info = pq.delMin();
            if (info.board.isGoal())
            {
                numOfMoves = info.moves;

                BoardInfo curr = info;
                BoardInfo next = null;
                BoardInfo prev = null;
                while (curr != null) {
                    next = curr.parent;
                    curr.parent = prev;
                    prev = curr;
                    curr = next;
                }
                brdSolution = prev;
                return;
            }
            for (Board neighbor : info.board.neighbors()) {
                boolean found = false;
                BoardInfo curinfo = info;
                while (true) {
                    if (curinfo.parent == null) break;
                    if (neighbor.equals(curinfo.parent.board)) {
                        found = true;
                        break;
                    }
                    curinfo = curinfo.parent;
                }
                if (!found)
                    pq.insert(new BoardInfo(info.moves + 1, neighbor, info));
            }
        }
        numOfMoves = -1;
        brdSolution = null;
    }
    public boolean isSolvable() {
        return brdSolution != null;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return numOfMoves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return iterSolution;
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
