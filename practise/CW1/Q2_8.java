//N.Daheley 22/10/00
//Draws a pattern

class Q2_8
{
    public static void main(String[] args)
    {
	int line = 0 ;
	do 
	  {
	    int loutside = 0 ;
	    
	    if (line <= 3)
	      { 
	        loutside = 3 - line ;
	      }
	    else
	      { 
	        loutside = line - 3 ;
	      }
	    
	    int inside = 7 - 2 - 2*loutside ;
	    
	    for ( ; loutside > 0; loutside--)
	      	{
			System.out.print(" ") ;
		}
	    
	    System.out.print("*") ;

	    if (line == 0 || line == 6)
	      	{
			System.out.println(" ") ;
	     	}
	    else
	      	{
			for ( ; inside > 0; inside-- )
		  		{
		    	System.out.print(" ") ;
		  		}
			System.out.print("*") ;
			System.out.println(" ") ;
			}
	      	
	  }
	  while (line++ < 6) ;
    }
}
