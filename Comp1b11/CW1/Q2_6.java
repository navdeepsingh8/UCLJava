//N.Daheley 22/10/00
//Draws a pattern

class Q2_6
{
    public static void main(String[] args)
    {
	int line = 0 ;
        while (line++ < 7)
	    {
		if (line < 5)
		    {
			int spacer = line ;
			    while (spacer-- > 0)
				{
				    System.out.print(" ") ;
				}
			    int stars = 0 ;
				while (stars++ < 5)
				    {
					System.out.print("*") ;
				    }
		    }
		if (line >= 5)
		{
		    int spacer = 8 - line ;
		    while (spacer-- > 0)
		    {
			System.out.print(" ") ;
			    }
		    int stars = 0 ;
		    while (stars++ < 5)
		    {
			System.out.print("*") ;
		    }
		}
		System.out.println(" ") ;
	    }
    }
}
