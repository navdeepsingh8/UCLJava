
import com.sun.java.util.collections.*;


/**
 * Rough benchmark of HashMap performance.
 */
public class PerfHashMap {


   public static void main(String args[]) {
   
      if (args.length != 2) {
         System.out.println(
            "Usage: java PerfHashMap NUMBER_OF_TRIALS NUMBER_OF_ELEMENTS");
         System.exit(-1);
      } // end if

      int numTrials = Integer.parseInt(args[0]);
      int numElements = Integer.parseInt(args[1]);
      long total = 0;

      for (int i = 0; i < numTrials; i++) {

         Map test = new HashMap();
         long start = System.currentTimeMillis();
         for (int j = 0; j < numElements; j++) {
            Integer val = new Integer(j);
            test.put(val, val);
         } // end for
         total += (System.currentTimeMillis() - start);

      } // end for

      System.out.println(
         "Average trial length: " + (total / numTrials) + " milliseconds.");
   
   }


}
