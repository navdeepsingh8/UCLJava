//N.Daheley 22/10/00
//Draws a pattern

class Q2_7
{
   public static void main(String[] args)
   {
       int line = 0 ;
       while (line++ < 3)
	   {
	       int counter1 = 0 ;
		   while (counter1++ < 3)
		       { System.out.print("*") ;
		       System.out.print("#") ;
			     }
	       
		   System.out.println(" ") ;
	       
		   int counter2 = 0 ;
		   while (counter2++ < 3)
		       { System.out.print("#") ;
		       System.out.print("*") ;
		       }
		   
		   System.out.println(" ") ;
	   }
   }
}
