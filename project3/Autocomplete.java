import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of 
// string and weights, using Term and BinarySearchDeluxe. 
public class Autocomplete {
    private Term[] terms;
    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        this.terms = new Term[terms.length];
        for (int k = 0; k < terms.length; k++) {
            this.terms[k] = terms[k];
        }
        Arrays.sort(this.terms);
        }
    

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
        Term c = new Term(prefix);
        int len = prefix.length();
        Comparator<Term> prefixOrder = Term.byPrefixOrder(len);
        int k = BinarySearchDeluxe.firstIndexOf(terms, c, prefixOrder);
        int j = numberOfMatches(prefix);
        Term[] matches = new Term[j];

        for (int i = 0; i < j; i++) {
            matches[i] = terms[i + k];
        }
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        int i = 0;
        if (i == -1) { 
            return 0;
        }
        else {
        Term c = new Term(prefix);
        int len = prefix.length();
        Comparator<Term> prefixOrder = Term.byPrefixOrder(len);
        i = BinarySearchDeluxe.firstIndexOf(terms, c, prefixOrder);

        int k = BinarySearchDeluxe.lastIndexOf(terms, c, prefixOrder);
        return (k + 1) - i;
            
        }
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}