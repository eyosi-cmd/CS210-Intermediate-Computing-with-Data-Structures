import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class KdTreePointST<Value> implements PointST<Value> {
    private Node root;
    private int N;
    
    // 2d-tree (generalization of a BST in 2d) representation.
    private class Node {
        private Point2D p;   // the point
        private Value val;   // the symbol table maps the point to this value
        private RectHV rect; // the axis-aligned rectangle corresponding to 
                             // this node
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree

        // Construct a node given the point, the associated value, and the 
        // axis-aligned rectangle corresponding to the node.
        Node(Point2D p, Value val, RectHV rect) {
            this.p = p;
            this.val = val;
            this.rect = rect;
        }
    }

    // Construct an empty symbol table of points.
    public KdTreePointST() {
        this.root = null;
        this.N = 0;
    }

    // Is the symbol table empty?
    public boolean isEmpty() { 
        return size() == 0;
    }

    // Number of points in the symbol table.
    public int size() {
        return this.N;
    }

    // Associate the value val with point p.
    public void put(Point2D p, Value val) {
        RectHV rightP = new RectHV(0, 0, 1, 1);
        root = put(root, p, val, rightP, true);
    }

    // Helper for put(Point2D p, Value val).
    private Node put(Node x, Point2D p, Value val, RectHV rect, boolean lr) {
        if (x == null) {
            N += 1;
            return new Node(p, val, rect);
        }
        
        if (x.p.equals(p)) {
            return x;
        }
        else if (lr && p.x() < x.p.x() || !lr && p.y() < x.p.y()) {
           double x1 = rect.xmin();
           double y1 = rect.ymin();
           double x2 = rect.xmax();
           double y2 = rect.ymax();
           RectHV sub;
            if (!lr) {

                sub = new RectHV(x1, y1, x2, x.p.y());
            }
            else {
                sub = new RectHV(x1, y1, x.p.x(), y2);                
             }
              x.lb = put(x.lb, p, val, sub, !lr);   
          }    
        else {
           double rx1 = rect.xmin();
           double ry1 = rect.ymin();
           double rx2 = rect.xmax();
           double ry2 = rect.ymax();
           RectHV sub;
           if (!lr) {
                sub = new RectHV(rx1, x.p.y(), rx2, ry2);
            } 
           else {
               sub = new RectHV(x.p.x(), ry1, rx2, ry2);
            }
            x.rt = put(x.rt, p, val, sub, !lr);   
        }
        return x;
    }

    // Value associated with point p.
    public Value get(Point2D p) {
        return get(root, p, true);
    }

    // Helper for get(Point2D p).
    private Value get(Node x, Point2D p, boolean lr) {
        if (x == null) { return null; }
        if (x.p.equals(p)) {
            return x.val;
        } 
        else if (lr && p.x() < x.p.x() || !lr && p.y() < x.p.y()) {
         
           return get(x.lb, p, !lr);   
        }    

        return get(x.rt, p, !lr);   
        
    }

    // Does the symbol table contain the point p?
    public boolean contains(Point2D p) {
        return get(p) != null;
    }

    // All points in the symbol table, in level order.
    public Iterable<Point2D> points() {
        Queue<Node> x = new Queue<Node>();
        Queue<Point2D> r = new Queue<Point2D>();
        Queue<Point2D> r2 = new Queue<Point2D>();
        x.enqueue(root);
        while (!x.isEmpty()) {
            Node q = x.dequeue();
            if (q.lb != null) {
                x.enqueue(q.lb);
            }
            if (q.rt != null) {
                x.enqueue(q.rt);
            }
            r.enqueue(q.p);
        }
        return r;
   
    
    }

    // All points in the symbol table that are inside the rectangle rect.
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }

    // Helper for public range(RectHV rect).
    private void range(Node x, RectHV rect, Queue<Point2D> q) {
        Node x1 = x;
        RectHV r1 = rect;
        if (x1 == null) { return; }
      
        if (r1.contains(x1.p)) 
              q.enqueue(x1.p);
        
        range(x1.lb, r1, q);
        range(x1.rt, r1, q);
    }

    // A nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D p) {
        double q  =  Double.POSITIVE_INFINITY;
        return nearest(root, p, null, q, true);
    }
    
    // Helper for public nearest(Point2D p).
    private Point2D nearest(Node x, Point2D p, Point2D nearest, 
                            double nearestDistance, boolean lr) {
        Point2D clst = nearest;
        double dist = nearestDistance;
        if (x == null) { return clst; }
        
        if (!x.p.equals(p)) {
            if (x.p.distanceSquaredTo(p) < dist) {

              dist = x.p.distanceSquaredTo(p);
              clst = x.p;

            }
        }
        
        Node first = x.rt;
        Node next = x.lb;
        
        if (lr && p.x() < x.p.x() || !lr && p.y() < x.p.y()) {
                first = x.lb;
                next = x.rt;
        }
        if (first != null) {
            if (first.rect.distanceSquaredTo(p) < dist) {
                clst = nearest(first, p, clst, dist, !lr);
                dist = clst.distanceSquaredTo(p);
           }
        }
        if (next != null) {
            if (next.rect.distanceSquaredTo(p) < dist) {
            clst = nearest(next, p, clst, dist, lr);
          }
        }
        return clst;
        
    }

    // k points that are clst to point p.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        MaxPQ<Point2D> pq = new MaxPQ<Point2D>(p.distanceToOrder());
        nearest(root, p, k, pq, true);
        return pq;
    }

    // Helper for public nearest(Point2D p, int k).
    private void nearest(Node x, Point2D p, int k, MaxPQ<Point2D> pq, 
                         boolean lr) {
         
        if (x == null || pq.size() >= k
        && pq.max().distanceSquaredTo(p) < x.rect.distanceSquaredTo(p)) {
            return;
        }
        if (!x.p.equals(p)) {
            pq.insert(x.p);
        }
        if (pq.size() > k) {
            pq.delMax();    
        }
        boolean leftBottom;
        if (lr && p.x() < x.p.x() || !lr && p.y() < x.p.y()) {
            nearest(x.lb, p, k, pq, !lr);
            leftBottom = true;
        } else {
            nearest(x.rt, p, k, pq, !lr);
            leftBottom = false;
        }
        nearest(leftBottom ? x.rt : x.lb, p, k, pq, !lr);
    }
    

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        KdTreePointST<Integer> st = new KdTreePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        double rx1 = Double.parseDouble(args[2]);
        double rx2 = Double.parseDouble(args[3]);
        double ry1 = Double.parseDouble(args[4]);
        double ry2 = Double.parseDouble(args[5]);
        int k = Integer.parseInt(args[6]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(rx1, ry1, rx2, ry2);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.println("First " + k + " values:");
        i = 0;
        for (Point2D p : st.points()) {
            StdOut.println("  " + st.get(p));
            if (i++ == k) {
                break;
            }
        }
        StdOut.println("st.contains(" + query + ")? " + st.contains(query));
        StdOut.println("st.range(" + rect + "):");
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.println("st.nearest(" + query + ") = " + st.nearest(query));
        StdOut.println("st.nearest(" + query + ", " + k + "):");
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
            
        }
        
    }
}