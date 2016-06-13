//Written by N.Daheley, November 2000
//Asks the user to input ten numbers, which are then printed out followed by their average
class Q3_5
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		double[] nums = new double[10] ;
		double total = 0.00 ;
		
		for (int counter = 0 ; counter < 10 ; counter++)
		{
			System.out.print("Enter number " + counter + ": ") ;
			double value = in.readDouble() ;
			nums[counter] = value ;
			total = total + value ;
		}
		
		for (int counter = 0 ; counter < 10 ; counter++)
		{
			System.out.println("Number " + counter + ": " + nums[counter]) ;
		}
		
		double average = total/10 ;
		System.out.println("Average: " + average) ;
	}
}