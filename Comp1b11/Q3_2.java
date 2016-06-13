//Written by N.Daheley, November 2000
//Asks the user to input a character until the character Q is entered
class Q3_2
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		int counter = 10 ;
		while (counter > 0)
		{
			System.out.print("Enter a character: ") ;
			char letter = in.readCharacter() ;
			if (letter == 'Q')
			{
				break ;
			}
			
			letter = in.readCharacter() ;
			letter = in.readCharacter() ;
		}
		
		
	}
}