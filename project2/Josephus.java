import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Josephus {
    public static void main(String[] args) {
            // Get M and N from command line as ints.
            int N = Integer.parseInt(args[0]);
            int M = Integer.parseInt(args[1]);
            Queue<Integer> q = new Queue<Integer>();
            // Create a queue q and enqueue integers
            // 0, 1, ... N - 1.
            for (int i = 0; i < N; i++) {
                q.enqueue(i);
            }
            // As long as q is not empty: increment i;
            // dequeue an element pos; if M divides i,
            // write pos, otherwise enqueue pos.
            String pos = new String();
            while (!q.isEmpty()) {
                 for (int i = 0; i < M-1; i++) {
                    q.enqueue(q.dequeue());
                }
            // Dequeue and write the remaining element
            // from q.
               pos += q.dequeue().toString() + " ";

            }
            
           StdOut.println(pos.substring(0, pos.length()-1));
      
    }
}