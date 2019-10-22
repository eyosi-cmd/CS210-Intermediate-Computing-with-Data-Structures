 import edu.princeton.cs.algs4.StdOut;


public class Ramanujan1 {

    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]);
        int b, anew, bnew;
        int c, d, cnew, dnew;
        int check;

        for (int a = 1; a <= N; a++) {
            anew = a*a*a;
            if (anew > N) {
                break;
            } 
            b = a;
            while (b <= N) {
                ++b;
                bnew = b*b*b;
                check = anew + bnew;
                if (check > N) {
                    break;
                }
                c = a + 1;
                while (c <= N) {
                    ++c;
                    cnew = c*c*c;
                    if (cnew > check) { 
                        break;
                    }
                    d = c;
                    while (d <= N) {
                        ++d;
                        dnew = d*d*d;
                        if (cnew + dnew > check) { 
                            break;
                        }
                        
                        if (cnew + dnew == check) {
                            cnew = anew + bnew;
                            StdOut.print(cnew + " = "); 
                            StdOut.print(a + "^3 + " + b + "^3 = ");
                            StdOut.print(c + "^3 + " + d + "^3");
                            StdOut.println();
                        }
                    }
                }
            }
        }
    }
}