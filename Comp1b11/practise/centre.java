//N.Daheley January 2001

class centre
{
	public void display(String message)
	{
		int spaces = 40 - (message.length()/2) ;
		for (int i = 0 ; i < spaces; i++)
		{
			System.out.print(" ") ;
		}
		System.out.print(message) ;
		System.out.println("") ;	
	}
	
	public static void main(String[] args)
	{
		centre myCentreObj = new centre() ;
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("Text to display? ") ;
		String message = in.readString() ;
		myCentreObj.display(message) ;
	}
}