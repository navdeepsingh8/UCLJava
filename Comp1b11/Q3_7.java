//Written by N.Daheley, November 2000
//Determines the number of n-sized cubes on the surface of an nxnxn cube
class Q3_7
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("Value of n: ") ;
		int n = in.readInteger() ;
		int cubes = 0 ;
		//The formula for the number of n-sized surface cubes
		//for an nxnxn cube is 6n^2 - 12n + 8 for n > 2 and
		//3n^2 - 6n + 4 for n < 3.
		if (n > 2)
		{
			cubes = 6*(n*n) - 12*n + 8 ;
		}
		else
		{
			cubes = 3*(n*n) - 6*n + 4 ;
		}
		
		System.out.println("A cube of side " + n + " has " + cubes + " n-sized cubes on its surface.") ;		
	}
}