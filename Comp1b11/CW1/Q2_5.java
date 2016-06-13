//N.Daheley 22/10/00
//Draws a pattern

class Q2_5
{
   public static void main(String[] args)
   {
      int line = 0 ;
      while (line++ < 5)
      {
         int spacer = 5 - line ;
         while (spacer-- >0)
         {
             System.out.print(" ") ;
         }
         int stars = line ;
         while (stars-- >0)
         {
             System.out.print("*") ;
         }
         System.out.println(" ") ;
      }
   }
}
