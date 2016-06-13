//Navdeep Daheley, January 2001

class calc
{
	public void splash()
	{
		System.out.println("Java Calculator v1.0") ;
		System.out.println("by Navdeep Singh Daheley\n") ;
		System.out.println("Type a number, operator or quit\n") ;
	}
	
	public void run()
	{
		KeyboardInput in = new KeyboardInput() ;
		int input1 = 0 ;
		int input2 = 0 ;
		int output = 0 ;
		String operator = "" ;
		
		while(operator != "quit")
		{				
			System.out.print("number> ") ;
			input1 = in.readInteger() ;
			
			System.out.print("oper> ") ;
			operator = in.readString() ;
			
			System.out.print("number> ") ;
			input2 = in.readInteger() ;
			
			if (operator == "+")
			{
				System.out.println(input1 + input2) ;
			}
					
			//if (operator == "-")
			//{
		//		output = input1 - input2 ;
		//	}
		//	
		//	if (operator == "*")
		//	{	
		//		output = input1*input2 ;
		//	}
		//	
		//	if (operator == "/")
		//	{	
		//		output = input1/input2 ;
		//	}
		//			
		//	System.out.println(output) ;
		}
		
	}
	
	public static void main(String[] args)
	{
		calc myCalc = new calc() ;
		myCalc.splash() ;
		myCalc.run() ;
	}
}