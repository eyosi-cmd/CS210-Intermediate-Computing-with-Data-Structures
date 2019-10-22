import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Ramanujan2 {
    // A data type that encapsulates a pair of numbers (i, j) 
    // and the sum of their cubes, ie, i^3 + j^3.
    private static class Pair implements Comparable<Pair> {
        private int i;          // first element of the pair
        private int j;          // second element of the pair
        private int sumOfCubes; // i^3 + j^3

        // Construct a pair (i, j).
        Pair(int i, int j) {
            this.i = i;
            this.j = j;
            sumOfCubes = i * i * i + j * j * j;
        }

        // Compare this pair to the other by sumOfCubes.
        public int compareTo(Pair other) {
            return sumOfCubes - other.sumOfCubes;
        }
    }

    public static void main(String[] args) {
        Pair prev = new Pair(0, 0);
        int N = Integer.parseInt(args[0]);
        MinPQ<Pair> TAXI = new MinPQ<Pair>();
        for (int i = 1; i * i * i < N; i++) {
            TAXI.insert(new Pair(i, i+1));
        }
        while (!TAXI.isEmpty()) {
            Pair curr = TAXI.delMin(); 
            if ((prev.sumOfCubes == curr.sumOfCubes) && prev.sumOfCubes <= N) {
                StdOut.println(prev.sumOfCubes + " = " + prev.i+ "^3 + "  
                + prev.j+ "^3 = " + curr.i+ "^3 + " +curr.j+ "^3");
            }
            prev = curr;
            if (curr.j * curr.j * curr.j < N) {
                    TAXI.insert(new Pair(curr.i, curr.j + 1));
               
            }
        }
    }
}
