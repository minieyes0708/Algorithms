import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private class Offset {
        int row, col;
        public Offset(int r, int c) {
            this.row = r;
            this.col = c;
        }
    }
    private final int[][] tiles;
    private final int[] twinIndex = new int[2];
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        this.tiles = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

        int[] tmpIndex = StdRandom.permutation(dimension() * dimension() - 1, 2);
        twinIndex[0] = tmpIndex[0];
        twinIndex[1] = tmpIndex[1];
    }
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%d\n", dimension()));
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                builder.append(String.format("%d ", tiles[i][j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
    public int dimension() {
        return tiles.length;
    }
    private int findRow(int num) {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (tiles[i][j] == num)
                    return i;
            }
        }
        return -1;
    }
    private int findCol(int num) {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (tiles[i][j] == num)
                    return j;
            }
        }
        return -1;
    }
    private int ansx(int num) {
        return (num - 1) % dimension();
    }
    private int ansy(int num) {
        return (num - 1) / dimension();
    }
    private int[][] copyTile() {
        int[][] newtile = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                newtile[i][j] = tiles[i][j];
            }
        }
        return newtile;
    }
    private Board move(int offsetRow, int offsetCol) {
        int[][] newtile = copyTile();
        int value = newtile[findRow(0) + offsetRow][findCol(0) + offsetCol];
        newtile[findRow(0) + offsetRow][findCol(0) + offsetCol] = 0;
        newtile[findRow(0)][findCol(0)] = value;
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
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
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
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
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
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
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

        Board that = (Board) y;
        if (that.dimension() != dimension()) return false;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (that.tiles[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public Iterable<Board> neighbors() {
        Offset[] options = new Offset[] {
            new Offset(0, 1), // right
            new Offset(1, 0), // bottom
            new Offset(0, -1), // left
            new Offset(-1, 0), // top
        };
        Queue<Board> result = new Queue<Board>();
        for (Offset offset : options) {
            int newRow = findRow(0) + offset.row;
            int newCol = findCol(0) + offset.col;
            if (newRow >= 0 && newRow < dimension() && newCol >= 0 && newCol < dimension()) {
                result.enqueue(move(offset.row, offset.col));
            }
        }
        return result;
    }
    public Board twin() {
        return swap(twinIndex[0] + 1, twinIndex[1] + 1);
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
