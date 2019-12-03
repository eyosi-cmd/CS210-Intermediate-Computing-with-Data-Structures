import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Spell {

    public static void main(String[] args) {

ArrayST<String, String> st = new ArrayST<String, String>();

        In in = new In(args[0]);

        String[] key = in.readAllStrings();

        String a = "";

        String b = "";

       for (int i = 0; i < key.length; i++) {     

            for (int j = 0; j < key[i].length() - 1; j++) 

            {         

                if (key[i].substring(j, j + 1).equals(",")) {            

                 a = key[i].substring(0, j);            

                 b = key[i].substring(j + 1, key[i].length());         

                }     

            }

          st.put(a, b);  

       } 
         int k = 0;
        String [] str;        

        String line = null;
        while (!StdIn.isEmpty()) {

           line = StdIn.readLine();
           str = line.split("\\b");
           for (int i = 0; i < str.length; i++) {
              String c = str[i];
              if(st.contains(c)){

                  StdOut.println(str[i]+":"+k+" -> "+st.get(str[i]));
                  
              }
           }
        k++;
        str = null;
        in.close();
        }

    }


}