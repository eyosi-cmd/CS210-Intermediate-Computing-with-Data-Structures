import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ArrayST<Key, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] values;
    private int N;

    // Create a symbol table with INIT_CAPACITY.
    public ArrayST() {
        this.keys = (Key[]) new Object[INIT_CAPACITY];
        this.values = (Value[]) new Object[INIT_CAPACITY];
        this.N = 0;
    }

    // Create a symbol table with given capacity.
    public ArrayST(int capacity) {
        Key[] k = (Key[]) new Object[capacity];
        Value[] v = (Value[]) new Object[capacity];
       for (int i = 0; i < N; i++) {
            k[i] = keys[i];
       }
        for (int j = 0; j < N; j++) {
            v[j] = values[j];
        }
        keys = k;
        values = v;
    }

    // Return the number of key-value pairs in the table.
    public int size() {
        return N;
    }

    // Return true if the table is empty and false otherwise.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Return true if the table contains key and false otherwise.
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // Return the value associated with key, or null.
    public Value get(Key key) {
         if (isEmpty()) { return null; }
           for (int i = 0; i < N; i++) {
             if (key.equals(keys[i])) {
                return values[i];
            }
        }
        return null;

    }

    // Put the kev-value pair into the table; remove key from table 
    // if value is null.
    public void put(Key key, Value value) {
        delete(key);
        if (N >= values.length) {
            resize(2*N);
       }
        values[N] = value;
        keys[N] = key;
        N++;
    }

    // Remove key (and its value) from table.
    public void delete(Key key) {
        for (int k = 0; k < N; k++) {
            if (key.equals(keys[k])) {
                keys[k] = keys[N - 1];
                values[k] = values[N - 1];
                keys[N - 1] = null;
                values[N - 1] = null;
                N--;
                if (N > 0 && N == keys.length / 4) 
                    resize(keys.length / 2);
            }
        }
    }

    // Return all the keys in the table.
    public Iterable<Key> keys()  {
        Queue<Key> q = new Queue<Key>();
        for (int i = 0; i < N; i++)
            q.enqueue(keys[i]);
        return q;
    }

    // Resize the internal arrays to capacity.
    private void resize(int capacity) {
        Key[] key = (Key[]) new Object[capacity];
        Value[] val = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            key[i] = keys[i];
            val[i] = values[i];
        }
        values = val;
        keys = key;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ArrayST<String, Integer> st = new ArrayST<String, Integer>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            st.put(s, ++count);
        }
        for (String s : args) {
            st.delete(s);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
