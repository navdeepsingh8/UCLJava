//Navdeep Daheley, January 2001

class array
{
	public int[] input()
	{
		KeyboardInput in = new KeyboardInput() ;
		int[] arrayIn = new int[10] ;
		for (int i = 0; i < 10; i++)
		{
			System.out.print("Enter an integer? ") ;
			arrayIn[i] = in.readInteger() ;
		}
		return arrayIn ;
	}
	
	public void display(int[] arrayOut)
	{
		for (int m = 0; m < arrayOut.length; m++)
		{
			System.out.println("At index " + m + ", integer is " + arrayOut[m]) ;
		}
	}
	
	public static void main(String[] args)
	{
		array program = new array() ;
		program.display(program.input()) ;
	}
}