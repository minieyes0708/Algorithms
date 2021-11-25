import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

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
    private class ManhattanComparator implements Comparator<BoardInfo> {
        public int compare(BoardInfo info1, BoardInfo info2) {
            if (info1.manhattan == -1) info1.manhattan = info1.board.manhattan();
            if (info2.manhattan == -1) info2.manhattan = info2.board.manhattan();
            return (info1.manhattan + info1.moves) - (info2.manhattan + info2.moves);
        }
    }
    private final int numOfMoves;
    private final BoardInfo brdSolution;
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<BoardInfo> pq = new MinPQ<BoardInfo>(new ManhattanComparator());
        MinPQ<BoardInfo> twinpq = new MinPQ<BoardInfo>(new ManhattanComparator());
        pq.insert(new BoardInfo(0, initial, null));
        twinpq.insert(new BoardInfo(0, initial.twin(), null));
        while (!pq.isEmpty()) {
            BoardInfo info = pq.delMin();
            BoardInfo twininfo = twinpq.delMin();
            if (twininfo.board.isGoal()) {
                break;
            }
            if (info.board.isGoal()) {
                brdSolution = buildSolution(info);
                numOfMoves = info.moves;
                return;
            }
            addNeighbors(info, pq);
            addNeighbors(twininfo, twinpq);
        }
        numOfMoves = -1;
        brdSolution = null;
    }
    private void addNeighbors(BoardInfo info, MinPQ<BoardInfo> pq) {
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
    private static BoardInfo buildSolution(BoardInfo info) {
        BoardInfo curr = info;
        BoardInfo next = null;
        BoardInfo prev = null;
        while (curr != null) {
            next = curr.parent;
            curr.parent = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    public boolean isSolvable() {
        return brdSolution != null;
    }
    public int moves() {
        return numOfMoves;
    }
    public Iterable<Board> solution() {
        Queue<Board> result = new Queue<Board>();
        BoardInfo curbrd = brdSolution;
        while (curbrd != null) {
            result.enqueue(curbrd.board);
            curbrd = curbrd.parent;
        }
        return result;
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
