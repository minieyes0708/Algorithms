import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class Board {
    private class NeighborIterator implements Iterator<Board> {
        private class Offset {
            int row, col;
            public Offset(int r, int c) {
                this.row = r;
                this.col = c;
            }
        }
        private int offsetIndex;
        private int offsetCount;
        private int[] offsetRow;
        private int[] offsetCol;
        public NeighborIterator() {
            offsetRow = new int[4];
            offsetCol = new int[4];
            Offset[] options = new Offset[] {
                new Offset( 0, +1), // right
                new Offset(+1,  0), // bottom
                new Offset( 0, -1), // left
                new Offset(-1,  0), // top
            };
            for (Offset offset : options) {
                int newRow = holeRow + offset.row;
                int newCol = holeCol + offset.col;
                if (newRow >= 0 && newRow < dim && newCol >= 0 && newCol < dim) {
                    offsetRow[offsetCount] = offset.row;
                    offsetCol[offsetCount] = offset.col;
                    offsetCount++;
                }
            }
        }
        public boolean hasNext() {
            return offsetIndex != offsetCount;
        }
        public Board next() {
            if (!hasNext()) throw new NoSuchElementException();
            int _offsetRow = offsetRow[offsetIndex];
            int _offsetCol = offsetCol[offsetIndex];
            offsetIndex++;
            return move(_offsetRow, _offsetCol);
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private class NeighborIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new NeighborIterator();
        }
    }

    private int dim;
    private int[][] tiles;
    private int holeRow, holeCol;
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        this.tiles = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        this.tiles = tiles;
        this.dim = tiles.length;
        this.holeRow = findRow(0);
        this.holeCol = findCol(0);
    }
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%d\n", dim));
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                builder.append(String.format("%d ", tiles[i][j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
    public int dimension() {
        return dim;
    }
    private int findRow(int num) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (tiles[i][j] == num)
                    return i;
            }
        }
        return -1;
    }
    private int findCol(int num) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (tiles[i][j] == num)
                    return j;
            }
        }
        return -1;
    }
    private int ansx(int num) {
        return (num - 1) % dim;
    }
    private int ansy(int num) {
        return (num - 1) / dim;
    }
    private int[][] copyTile() {
        int[][] newtile = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                newtile[i][j] = tiles[i][j];
            }
        }
        return newtile;
    }
    private Board move(int offsetRow, int offsetCol) {
        int[][] newtile = copyTile();
        int value = newtile[holeRow + offsetRow][holeCol + offsetCol];
        newtile[holeRow + offsetRow][holeCol + offsetCol] = 0;
        newtile[holeRow][holeCol] = value;
        return new Board(newtile);
    }
    private Board swap(int num1, int num2) {
        int row1 = findRow(num1);
        int col1 = findCol(num1);
        int row2 = findRow(num2);
        int col2 = findCol(num2);
        int[][] newtile = copyTile();
        int value = newtile[row1][col1];
        newtile[row1][col1] = newtile[row2][col2];
        newtile[row2][col2] = value;
        return new Board(newtile);
    }
    public int hamming() {
        int dist = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int num = tiles[i][j];
                if (num != 0) {
                    int tarx = ansx(num);
                    int tary = ansy(num);
                    if (i == tary && j == tarx) continue;
                    dist++;
                }
            }
        }
        return dist;
    }
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int num = tiles[i][j];
                if (num != 0) {
                    int tarx = ansx(num);
                    int tary = ansy(num);
                    if (i == tary && j == tarx) continue;
                    dist += Math.abs(i - tary) + Math.abs(j - tarx);
                }
            }
        }
        return dist;
    }
    public boolean isGoal() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int num = tiles[i][j];
                if (num != 0) {
                    int tarx = ansx(num);
                    int tary = ansy(num);
                    if (i == tary && j == tarx) continue;
                    return false;
                }
            }
        }
        return true;
    }
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board)y;
        if (that.dimension() != dim) return false;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (that.tiles[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public Iterable<Board> neighbors() {
        return new NeighborIterable();
    }
    public Board twin() {
        int[] nums = StdRandom.permutation(dim * dim - 1, 2);
        return swap(nums[0] + 1, nums[1] + 1);
    }
    public static void main(String[] args) {
        int[][] tiles = new int[][] {
            { 8, 1, 3 },
            { 4, 0, 2 },
            { 7, 6, 5 },
        };
        Board board = new Board(tiles);
        StdOut.println("=== Current Board ===");
        StdOut.println(board.toString());
        StdOut.printf("hamming = %d\n", board.hamming());
        StdOut.printf("manhattan = %d\n", board.manhattan());

        StdOut.println("=== Neighbors ===");
        for (Board b : board.neighbors()) {
            StdOut.println(b.toString());
        }

        StdOut.println("=== Twins ===");
        for (int i = 0; i < 3; i++) {
            StdOut.println(board.twin().toString());
        }
    }
}
