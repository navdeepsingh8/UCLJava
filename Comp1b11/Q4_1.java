//N.Daheley, November 2000
//A Class with two methods that deal with doubles

class Q4_1
{
	public double returnValue(double number)
	{
		return number ;
	}
	
	public double cubeValue(double number)
	{
		double cube = number*number*number ;
		return cube ;
	}
	
	public static void main(String args[])
	{
		KeyboardInput in = new KeyboardInput() ;
		Q4_1 myObject = new Q4_1() ;
		
		System.out.print("Enter a number: ") ;
		double number = in.readDouble() ;
		
		myObject.returnValue(number) ;
		double cubeOut = myObject.cubeValue(number) ;
		
		System.out.println("The cube of the number is: " + cubeOut) ;
	}
}