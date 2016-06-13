//Written by N.Daheley, November 2000
//Asks the user for ten strings, which are alphabetically sorted.
class Q3_8
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		String[] words = new String[10] ;
		
		for (int input = 0 ; input < 10 ; input++)
		{
			System.out.print("Enter word " + input + " : ") ;
			words[input] = in.readString() ;
		}
		
		for (int counter = 0 ; counter < 10 ; counter++)
		{
			for (int cycle = 0 ; cycle < 9 ; cycle++)
			{
				String first = words[cycle] ;
				String second = words[cycle + 1] ;
				int n = first.compareTo(second) ;
				
				if (n > 1)
				{
					words[cycle] = second ;
					words[cycle + 1] = first ;
				}
			}
		}
		
		for (int output = 0 ; output < 10 ; output++)
		{
			System.out.println("Ordered word " + output + " : " + words[output]) ;
		}
	}
}