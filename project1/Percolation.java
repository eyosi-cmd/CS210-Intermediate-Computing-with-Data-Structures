import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class Percolation {
    private int N;
    private boolean[][] open_Location;
    private int sitesOpen;
    private int Source;
    private int sink;
    WeightedQuickUnionUF UF;
 
    // Create an N-by-N grid, with all sites blocked.
    public Percolation(int N) {
        this.N = N;
        open_Location = new boolean[this.N][this.N];
        UF = new WeightedQuickUnionUF(this.N * this.N + 2);
        sink = this.N * this.N + 1;
        Source = 0;
        for (int i = 0; i <= N; i++) {
            UF.union(encode(0, i), Source);
            UF.union(encode(this.N - 1, i), sink);
        }
    }

    // Open site (i, j) if it is not open_Location already.
    public void open(int i, int j) {

        if (j < 0 || j > this.N - 1) {
            throw new IndexOutOfBoundsException();
        }
         
        if (!isOpen(i, j)) {
            open_Location[i][j] = true;
            sitesOpen += 1;
        }
        if (i + 1 < N && open_Location[i + 1][j]) {
            UF.union(encode(i, j), encode(i + 1, j)); 
        }
        if (i - 1 >= 0 && open_Location[i-1][j]) {
            UF.union(encode(i, j), encode(i - 1, j));
        }
        if (j + 1 < N && open_Location[i][j + 1]) {
            UF.union(encode(i, j), encode(i, j + 1));
        }
        if (j - 1 >= 0 && open_Location[i][j - 1]) {
            UF.union(encode(i, j), encode(i, j - 1));
        }
    }

    // Is site (i, j) open_Location?
    public boolean isOpen(int i, int j) {
        if (open_Location[i][j]) {
            return true;
        }
        return false;
    }

    // Is site (i, j) full?
    public boolean isFull(int i, int j) {
        if (open_Location[i][j] && UF.connected(encode(i, j), Source)) {
            return true;
        }
        return false;
    }

    // Number of open_Location sites.
    public int numberOfOpenSites() {
        return sitesOpen;
    }

    // Does the system percolate?
    public boolean percolates() {
        if (UF.connected(Source, sink)) {
            return true;
        }
        return false;
    }

    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
        return (i * N + j + 1);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
       
        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}

