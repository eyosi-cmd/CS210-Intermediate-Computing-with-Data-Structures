import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        ResizingArrayRandomQueue q = new ResizingArrayRandomQueue();
        int k = Integer.parseInt(args [0]);
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        for (int i = 0; i <= k-1; i++) {
            StdOut.println(q.dequeue());
        } 
    }
}