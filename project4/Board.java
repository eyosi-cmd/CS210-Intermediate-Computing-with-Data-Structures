import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdOut;

// Models a board in the 8-puzzle game or its generalization.
public class Board {
    private int N;
    private final int [][] tiles;
    private int hamming;
    private int mahattan;
    // Construct a board from an N-by-N array of tiles, where 
    // tiles[i][j] = tile at row i and column j, and 0 represents the blank 
    // square.
    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = tiles;
        hamming = 0;
        mahattan = 0;
        int X, Y;
        int pos, distance;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != i * N + j + 1 
                    && this.tiles[i][j] != 0) {
                    hamming++;
                    pos = tiles[i][j];
                    X = (pos-1) / N;
                    Y = (pos -1) % N;
                    distance = Math.abs(i - X) + Math.abs(j - Y);
                    mahattan += distance;
                }
            }
        }
    }
    // Tile at row i and column j.
    public int tileAt(int i, int j) {
        return this.tiles[i][j];
    }
    
    // Size of this board.
    public int size() {
        return N;
    }

    // Number of tiles out of place.
    public int hamming() {
        return hamming;
    }

    // Sum of Manhattan distances between tiles and goal.
    public int manhattan() {
        return mahattan;
    }

    // Is this board the goal board?
    public boolean isGoal() {
        return (hamming == 0); 
    }

    // Is this board solvable?
    public boolean isSolvable() {
      int i = (blankPos()) / N;
      return (N % 2 == 1) ? inversions() % 2 == 0
                       : (inversions() + i) % 2 != 0;
    }

    // Does this board equal that?
   public boolean equals(Board that) {
         for (int i = 0; i < N; i++) {
           for (int j = 0; j < N; j++) {
               if (this.tiles[i][j] != that.tileAt(i, j)) {
                   return false;
                }
            }
        }
        return true;
      
    }

    // All neighboring boards.
    public Iterable<Board> neighbors() {
      LinkedQueue<Board> q = new LinkedQueue<Board>();
      int i = blankPos() / N;
      int j = blankPos() % N;
      int [][]board = new int [N][N];
        

        if (i > 0) {
           board = cloneTiles();
           int temp = board[i-1][j];
           board[i-1][j] = board[i][j];
           board[i][j] = temp;
           Board b = new Board(board);
           q.enqueue(b);
        }
       
       
        if (j < size() - 1) {
           board = cloneTiles();
           int temp = board[i][j+1];
           board[i][j+1] = board[i][j];
           board[i][j] = temp;
           Board b = new Board(board);
           q.enqueue(b); 
        }
         if (i < size() -1) {
           board = cloneTiles();
           int temp = board[i+1][j];
           board[i+1][j] = board[i][j];
           board[i][j] = temp;
           Board b = new Board(board);
           q.enqueue(b);
        }
       
         if (j > 0) {
           board = cloneTiles();
           int temp = board[i][j-1];
           board[i][j-1] = board[i][j];
           board[i][j] = temp;
           Board b = new Board(board);
           q.enqueue(b);
            
        }
       
        return q;
    }

    // String representation of this board.
    public String toString() {
        String result = new String();
        result += N + "\n";
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                result += String.format("%2d", tileAt(i, j));
                if (j != N - 1)
                result += " ";
            }
            if (i != N - 1)
            result += "\n";
        }
        return result;
    }

    // Helper method that returns the position (in row-major order) of the 
    // blank (zero) tile.
    private int blankPos() {
        int b = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == 0) {
                    b = i * N + j;
                }
            }
        }   
        return b;
    }

    // Helper method that returns the number of inversions.
    private int inversions() {
        int inv = 0;
        int b = 0;
        int [] a = new int [N * N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                  a[b] = tileAt(i, j);
                  b++;
                  
            }
        }
        for (int i = 0; i < N*N; i++) {
           for (int j = i + 1; j < N * N; j++) {
             if (a[i] > a[j] && a[i] != 0 && a[j] != 0)
                 inv++;
           }
       }
       return inv;
    }

    // Helper method that clones the tiles[][] array in this board and 
    // returns it.
    private int[][] cloneTiles() {
        int [][]clone = new int [N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
              int x = tiles[i][j];
                clone[i][j] = x;
            }
        }
        return clone;

    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }
    }
}
