//N.Daheley January 2001

class countchars
{
	public void output(int chars)
	{
		System.out.println("There are " + chars + " characters in this string.") ;
	}
	
	public int count(String inputString)
	{
		String dictionary = "abcdefghijklmnopqrstuvwxyz1234567890" ;
		int chars = 0 ;
		for (int a = 0; a < inputString.length(); a++)
		{
			for (int b = 0; b < dictionary.length(); b++)
			{
				if (inputString.charAt(a) == dictionary.charAt(b))
				{
					chars++ ;
				}
			}
		}
		return chars ;
	}
	
	public String input()
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.println("Input string:") ;
		String inputString = in.readString() ;
		return inputString ;
	}
	
	public static void main(String[] args)
	{
		countchars myProgram = new countchars() ;
		myProgram.output(myProgram.count(myProgram.input())) ;
	}
}