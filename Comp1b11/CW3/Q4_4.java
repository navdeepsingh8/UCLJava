//N.Daheley, November 2000
//Checks to see if a number is a palindrome or not

class Q4_4
{
	String getInput()
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("Enter integer to check: ") ;
		String s = ("" + in.readLong()) ;
		return s ;
	}
	
	String reverse(String s)
	{
		String result = new String() ;
		int position = 0 ;
		while (position < s.length())
		{
			result = new Character(s.charAt(position)).toString() + result ;
			position = position + 1 ;
		}
		return result ;
	}
	
	boolean check(String s, String s1)
	{
	    String s2 = reverse(s) ;
	    if (s1.compareTo(s2) == 0)
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	
	void test(String s)
	{
		if (check(s.toLowerCase(), s.toLowerCase()))
		{
			System.out.println("Integer is a palindrome.") ;
		}
		else
		{
			System.out.println("Integer is not a palindrome.") ;
		}
	}
			
	public static void main(String args[])
	{
		
		Q4_4 obj = new Q4_4() ;
		obj.test(obj.getInput()) ;
	}
}