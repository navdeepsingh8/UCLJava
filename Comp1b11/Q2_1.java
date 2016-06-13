// Written by N.Daheley, 22/10/00
// A while loop that displays the powers of 2 from 1 to 16

class Q2_1
{
   public static void main(String[] args)
   {
      KeyboardInput in = new KeyboardInput() ;
      int index = 0 ;
      int answer = 1 ;
      while (index < 16)
      {
         System.out.println("2^" + index + " = " + answer) ;
         answer = answer*2 ;
         index = index + 1 ;
         in.readString() ;
      }
   }
}
