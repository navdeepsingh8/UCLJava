
import java.util.*;


/**
 * Rough benchmark of ArrayList performance.
 */
public class PerfArrayList {


   public static void main(String args[]) {
   
      if (args.length != 2) {
         System.out.println(
            "Usage: java PerfArrayList NUMBER_OF_TRIALS NUMBER_OF_ELEMENTS");
         System.exit(-1);
      } // end if

      int numTrials = Integer.parseInt(args[0]);
      int numElements = Integer.parseInt(args[1]);
      long total = 0;

      for (int i = 0; i < numTrials; i++) {

         List test = new ArrayList();
         long start = System.currentTimeMillis();
         for (int j = 0; j < numElements; j++) {
            test.add(new Integer(j));
         } // end for
         total += (System.currentTimeMillis() - start);

      } // end for

      System.out.println(
         "Average trial length: " + (total / numTrials) + " milliseconds.");
   
   }


}
