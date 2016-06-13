//Written by N.Daheley, November 2000
//Asks the user for how many numbers to input, which are then printed with their average
class Q3_6
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("How many numbers: ") ;
		int howManyNumbers = in.readInteger() ;
		double[] nums = new double[howManyNumbers] ;
		double total = 0.00 ;
		
		for (int counter = 0 ; counter < howManyNumbers ; counter++)
		{
			System.out.print("Enter number " + counter + ": ") ;
			double value = in.readDouble() ;
			nums[counter] = value ;
			total = total + value ;
		}
		
		for (int counter = 0 ; counter < howManyNumbers ; counter++)
		{
			System.out.println("Number " + counter + ": " + nums[counter]) ;
		}
		
		double average = total/howManyNumbers ;
		System.out.println("Average: " + average) ;
	}
}