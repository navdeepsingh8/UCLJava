//N.Daheley, 22/10/00
//Displays a 4 by 4 block of stars

class Q2_4
{
   public static void main(String[] args)
   {
      int line = 4 ;
      while (line-- > 0)
      {
         int digit = 4 ;
         while (digit-- > 0)
         {
            System.out.print("*") ;
         }
         System.out.println("") ;
      }
   }
}
