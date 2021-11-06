import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int openCount;
    private final int topRow;
    private final int topCol;
    private final int botRow;
    private final int botCol;
    private final int ufSize;
    private boolean[][] isOpenFlags;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufFull;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.topRow = 1;
        this.topCol = 0;
        this.botRow = n;
        this.botCol = n + 1;
        this.openCount = 0;
        this.isOpenFlags = new boolean[n + 2][n + 2];
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {
                this.isOpenFlags[i][j] = false;
            }
        }
        // with additional top & bottom virtual site
        this.ufSize = n * n + 2;
        this.uf = new WeightedQuickUnionUF(this.ufSize);
        this.ufFull = new WeightedQuickUnionUF(this.ufSize);
    }
    private void connect(int row1, int col1, int row2, int col2)
    {
        int ind1 = (row1 - 1) * n + (col1 - 1) + 1;
        int ind2 = (row2 - 1) * n + (col2 - 1) + 1;
        if (ind1 < 0 || ind1 >= ufSize) throw new IllegalArgumentException();
        if (ind2 < 0 || ind2 >= ufSize) throw new IllegalArgumentException();

        uf.union(ind1, ind2);
        if (ind1 != ufSize && ind2 != ufSize) ufFull.union(ind1, ind2);
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 1 || row > n) throw new IllegalArgumentException();
        if (col < 1 || col > n) throw new IllegalArgumentException();

        if (isOpenFlags[row][col]) return;

        openCount += 1;
        isOpenFlags[row][col] = true;

        if (col + 1 <= n && isOpenFlags[row + 0][col + 1])
            connect(row, col, row + 0, col + 1);
        if (col - 1 >= 1 && isOpenFlags[row + 0][col - 1])
            connect(row, col, row + 0, col - 1);
        if (row + 1 <= n && isOpenFlags[row + 1][col + 0])
            connect(row, col, row + 1, col + 0);
        if (row - 1 >= 1 && isOpenFlags[row - 1][col + 0])
            connect(row, col, row - 1, col + 0);
        if (row == 1)
            connect(row, col, topRow, topCol);
        if (row == n)
            connect(row, col, botRow, botCol);
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row < 1 || row > n) throw new IllegalArgumentException();
        if (col < 1 || col > n) throw new IllegalArgumentException();

        return isOpenFlags[row][col];
    }
    private boolean isConnected(int row1, int col1, int row2, int col2)
    {
        int ind1 = (row1 - 1) * n + (col1 - 1) + 1;
        int ind2 = (row2 - 1) * n + (col2 - 1) + 1;
        if (ind1 < 0 || ind1 >= ufSize) throw new IllegalArgumentException();
        if (ind2 < 0 || ind2 >= ufSize) throw new IllegalArgumentException();

        return uf.find(ind1) == uf.find(ind2);
    }
    private boolean isConnectedFull(int row1, int col1, int row2, int col2)
    {
        int ind1 = (row1 - 1) * n + (col1 - 1) + 1;
        int ind2 = (row2 - 1) * n + (col2 - 1) + 1;
        if (ind1 < 0 || ind1 >= ufSize) throw new IllegalArgumentException();
        if (ind2 < 0 || ind2 >= ufSize) throw new IllegalArgumentException();

        return ufFull.find(ind1) == ufFull.find(ind2);
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row < 1 || row > n) throw new IllegalArgumentException();
        if (col < 1 || col > n) throw new IllegalArgumentException();

        return isConnectedFull(row, col, topRow, topCol);
    }
    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openCount;
    }
    // does the system percolate?
    public boolean percolates()
    {
        return isConnected(botRow, botCol, topRow, topCol);
    }
    // test client (optional)
    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 1);
        System.out.println(String.format("isFull 3, 1 = %s", percolation.isFull(3, 1)));
    }
}
