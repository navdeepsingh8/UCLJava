//N.Daheley, November 2000
//Converts decimal integers to binary

class Q4_2
{
	
	
	int getInput()
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("Enter integer to convert: ") ;
		int number = in.readInteger() ;
		return number ;
	}
	
	void output(int number)
	{
		String out = convert(number) ;
		System.out.println("The binary equivalent is:") ;
		System.out.println(out) ;
	}
	
	String convert(int number)
	{
		int powerOfTwo = intial(number) ;
		String expression = "" ;
		do{
			while (powerOfTwo > number)
			{
				expression = ("" + expression + "0") ;
				powerOfTwo = powerOfTwo/2 ;
			}
			
			if (number >= powerOfTwo)
			{
				expression = ("" + expression + "1") ;
				number = number - powerOfTwo ;
				powerOfTwo = powerOfTwo/2 ;
			}
		}while (powerOfTwo > 1) ;
		return expression ;
	}
	
	int intial(int number)
	{
		int powerOfTwo = 1 ;
		while (number > powerOfTwo)
		{
			powerOfTwo = 2*powerOfTwo ;
			}
		System.out.println("starting value is " + powerOfTwo/2) ;
		return powerOfTwo/2 ; 
	}
	
	public static void main(String args[])
	{
		Q4_2 obj = new Q4_2() ;
		obj.output(obj.getInput()) ;
	}
}