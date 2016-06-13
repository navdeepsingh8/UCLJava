// Written by N.Daheley, 22/10/00
// A while loop that displays the powers of 2 from 1 to 16

class Q2_2
{
   public static void main(String[] args)
    {   
      int index = 1 ;
      int answer = 1 ;
      do
	{
	  answer = answer*2 ;
          System.out.println("2^" + index + " = " + answer) ;
	}
      while (index++ < 16) ;
   }
}
