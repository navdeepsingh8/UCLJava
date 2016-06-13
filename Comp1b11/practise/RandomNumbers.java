//Navdeep Daheley, January 2001
import java.util.Random ;
class RandomNumbers
{
	public static void main(String[] args)
	{
		RandomNumbers program = new RandomNumbers() ;
		Random generator = new Random() ;
		int i = 0 ;
		while (i < 1000)
		{
			int randomValue = generator.nextInt(26) ;
			if (randomValue > 0)
			{
				System.out.println(randomValue) ;
				i++ ;
			}
		}
	}
}