import edu.princeton.cs.algs4.StdOut;

public class PrimeCounter {
    // Is x prime?
    private static boolean isPrime(int x) {
        // For each 2 <= i <= sqrt(x), if x is divisible by
        // i, then x is not a prime; If no such i exists,
        // x is a prime
            if(x < 2){ return false; }
            for(int i = 2; i <= Math.sqrt(x); i++){
                if(x%i == 0) { return false; }
            }
                return true;
            }

    // The number of primes <= N.
    private static int primes(int N) {
        // For each 2 <= i <= N, use isPrime() to test if
        // i is prime, and if so increment a count; At the
        // end return count
        int count = 0;
        for( int i = 2; i <= N; i++){
            if(isPrime(i)){ 
                count++;
             }
         } 
        return count;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        StdOut.println(primes(N));
    }
}
