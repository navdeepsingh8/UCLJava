//Algorithm for calculating a square root
//Navdeep Daheley, January 2001
class Q5_2
{
	public double squareRoot(double value)
	{
		double iterativeValue = 0.0001 ;
		while(iterativeValue < value/iterativeValue)
		{
			iterativeValue += 0.0001 ;
		}
		return iterativeValue ;
	}
	
	public static void main(String[] args)
	{
		Q5_2 program = new Q5_2() ;
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("Enter a value: ") ;
		System.out.println("\nSquare root: " + program.squareRoot(in.readDouble())) ;
	}
}