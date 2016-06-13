//Written by N.Daheley, November 2000
//Asks the user to input two strings and determines which comes alphabetically first
class Q3_4
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
			System.out.print("Enter first string: ") ;
			String first = in.readString() ;
			System.out.print("Enter second string: ") ;
			String second = in.readString() ;
			int compare = first.compareTo(second) ;
			if (compare == 0)
			{
				System.out.println(first + " is alphabetically equal to " + second) ;
			}
			if (compare < 0)
			{
				System.out.println(first + " is alphabetically before " + second) ;
			}
			if (compare > 0)
			{
				System.out.println(first + " is alphabetically after " + second) ;
			}
			
	}
}