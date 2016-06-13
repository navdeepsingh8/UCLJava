//Navdeep Daheley, January 2001

class array2
{
	public int[] input()
	{
		KeyboardInput in = new KeyboardInput() ;
		int[] arrayIn = new int[] ;
		int index = 0 ;
		while (in.readInteger() != 0)
		{
			arrayIn[index] = in.readInteger() ;
			//System.out.print("Enter an integer? ") ;
			//int inp =  in.readInteger() ;
			index++ ;
		}
		return arrayIn ;
	}
	
	public void display(int[] arrayOut)
	{
		int sum = 0 ;
		for (int i = 0; i < arrayOut.length; i++)
		{
			sum += arrayOut[i] ;
		}
		int average = sum/arrayOut.length ;
		System.out.println("The average value in the array is " + average) ;
	}
	
	public static void main(String[] args)
	{
		array2 program = new array2() ;
		program.display(program.input()) ;
	}
}