//Written by N.Daheley, November 2000
//Asks the user to input a string until the string stop is entered
class Q3_3
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		int counter = 10 ;
		while (counter > 0)
		{
			System.out.print("Enter a string: ") ;
			String word = in.readString() ;
			int compare = word.compareTo("stop") ;
			if (compare == 0)
			{
				break ;
			}
		}
		
		
	}
}