//Written by N.Daheley, November 2000
//Prints the largest, smallest and average of 10,000 random numbers between 1 and 100
import java.util.Random ;

class Q3_10
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		int[] numbers = new int[10000] ;
		Random generator = new Random() ;
		
		for (int generate = 0 ; generate < 10000 ; generate++)
		{
			numbers[generate] = generator.nextInt(100) + 1 ;
		}
		
		int largest = numbers[0] ;
		int smallest = numbers[0] ;
		int sum = 0 ;
		
		for (int check = 0 ; check < 10000 ; check++)
		{
			sum = sum + numbers[check] ;
			if (numbers[check] > largest)
			{
				largest = numbers[check] ;
			}
			
			if (numbers[check] < smallest)
			{
				smallest = numbers[check] ;
			}
		}
		
		int average = sum/10000 ;
		
		System.out.println("The largest number is " + largest + ".") ;
		System.out.println("The smallest number is " + smallest + ".") ;
		System.out.println("The average of the numbers is " + average + ".") ;
	}
}