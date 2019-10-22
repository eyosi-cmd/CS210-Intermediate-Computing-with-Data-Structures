import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

// A solver based on the A* algorithm for the 8-puzzle and its generalizations.
public class Solver {
     private LinkedStack<Board> solution;
     private int moves;

    // Helper search node class.
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode previous;

        SearchNode(Board board, int moves, SearchNode previous) {
                this.board = board;
                this.moves = moves;
                this.previous = previous; 
        }
    }
     
    // Find a solution to the initial board (using the A* algorithm).
    public Solver(Board initial) {
        this.moves = 0;
        if (initial == null) {
             throw new NullPointerException();
         }
        if (!initial.isSolvable()) {
              throw new IllegalArgumentException();
          }
        MinPQ<SearchNode> a = new MinPQ<SearchNode>(new ManhattanOrder());
        this.solution = new LinkedStack<Board>();
        SearchNode node = new SearchNode(initial, 0, null);
        a.insert(node);
        
        while (!a.isEmpty()) {
           node = a.delMin();
           if (node.board.isGoal()) {
               moves = node.moves;
               
                while (node.previous != null) {
                       solution.push(node.board);
                       node = node.previous;
                }
              break;
           }
         
            for (Board neighbor: node.board.neighbors()) {
                 if (node.previous != null) {
                    if (!neighbor.equals(node.previous.board)) {
                        
                    
                  SearchNode node1 = new SearchNode(neighbor, 
                  node.moves+1, node);
                  a.insert(node1);
                }
            }
                else if (node.previous == null) {
                    SearchNode node1 = new SearchNode(neighbor,
                     node.moves + 1, node);
                    a.insert(node1);
            }
        }
    }
}
        


    // The minimum number of moves to solve the initial board.
    public int moves() {
        return moves;
    }
    // Sequence of boards in a shortest solution.
    public Iterable<Board> solution() {
        return solution;
    }

    // Helper hamming priority function comparator.
    private static class HammingOrder implements Comparator<SearchNode> {
        private int x, y;
        public int compare(SearchNode a, SearchNode b) {
             x = a.board.hamming() + a.moves;
             y = b.board.hamming() + b.moves;
            return this.x - this.y;  
        }      
    }
       
    // Helper manhattan priority function comparator.
    private static class ManhattanOrder implements Comparator<SearchNode> {
        private int x, y;
        public int compare(SearchNode a, SearchNode b) {
             x = a.board.manhattan() + a.moves;
             y = b.board.manhattan() + b.moves;
            return this.x - this.y;  
        }
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
        else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
