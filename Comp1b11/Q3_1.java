//Written by N.Daheley, November 2000
//Displays a string a number of times defined by an integer inputted by the user
class Q3_1
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		String message = "" ;
		int num = 0 ;
		
		System.out.print("Enter string: ") ;
		message = in.readString() ;
		
		System.out.print("Enter number of times to be shown: ") ;
		num = in.readInteger() ;
		
		while (num-- > 0)
		{
			System.out.println(message) ;
		}
	}
}