//N.Daheley, November 2000
//counts the number of occurences of different characters

class Q4_5
{
	String getInput(String filename)
	{
		FileInput filein = new FileInput(filename) ;
		String wholestring = "" ;
		while (!filein.eof())
		{
			char c = filein.readCharacter() ;
			wholestring = ("" + wholestring + c) ;
		}
		String chars = "" ;
		for (int num = 0 ; num < wholestring.length() ; num++)
		{
			if (wholestring.charAt(num) != ' ')
			{
				chars = ("" + chars + wholestring.charAt(num)) ;
			}
		}
		return chars ;
	}
	
	void count(String chars)
	{
		int[] tally = new int[26]  ;
		for (int num = 0 ; num < chars.length() ; num++)
		{
			int charnum = (chars.charAt(num) - 97) ;
			if (charnum >= 0 & charnum <= 25)
			{
				++tally[charnum] ;
			}
		}
		for (int num = 0 ; num < 26 ; num++)
		{
	    	System.out.println(tally[num]) ;
		}
	}		
	
	public static void main(String args[])
	{
		Q4_5 obj = new Q4_5() ;
		obj.count(obj.getInput("test.txt")) ;
	}
}
			