//N. Daheley 29/10/00
//A program that displays a pattern

class Q2_9
{
	public static void main(String[] args)
	{
	int line = 0 ;
	int stars = 0 ;
	int hashes = 0 ;
	int strings = 0 ;
	int ats = 0 ;
	
	do
	{
		if (line == 1 || line == 7)
		{
			for (stars = 8 ; stars > 0; stars--)
			{
				System.out.print("*") ;
		 	}
		 	System.out.println(" ") ;
		}
		
		if (line == 2 || line == 6)
		{
			for (stars = 1 ; stars > 0; stars--)
			{
				System.out.print("*") ;
			}
			for (hashes = 6 ; hashes > 0; hashes--)
			{
				System.out.print("#") ;
			}
			for (stars = 1 ; stars > 0; stars--)
			{
				System.out.print("*") ;
			}
			System.out.println(" ") ;
		}
		
		if (line == 3 || line == 5)
		{
			for (stars = 1 ; stars > 0; stars--)
			{
				System.out.print("*") ;
			}
			for (hashes = 1 ; hashes > 0; hashes--)
			{
				System.out.print("#") ;
			}
			for (strings = 4 ; strings > 0; strings--)
			{
				System.out.print("$") ;
			}
			for (hashes = 1 ; hashes > 0; hashes--)
			{
				System.out.print("#") ;
			}
			for (stars = 1 ; stars > 0; stars--)
			{
				System.out.print("*") ;
			}
			System.out.println(" ") ;
		}
				
		if (line == 4)
		{
			for (stars = 1 ; stars > 0; stars--)
			{
				System.out.print("*") ;
			}
			for (hashes = 1 ; hashes > 0; hashes--)
			{
				System.out.print("#") ;
			}
			for (strings = 1 ; strings > 0; strings--)
			{
				System.out.print("$") ;
			}
			for (ats = 2; ats > 0; ats--)
			{
				System.out.print("@") ;
			}
			for (strings = 1; strings > 0; strings--)
			{
				System.out.print("$") ;
			}
			for (hashes = 1 ; hashes > 0; hashes--)
			{
				System.out.print("#") ;
			}
			for (stars = 1; stars > 0; stars--)
			{
				System.out.print("*") ;
			}
			System.out.println(" ") ;
		}
	}
	while (line++ < 8) ;
}
}