//Generates 1000 random numbers and writes them to a data file
//Written by Navdeep Daheley, January 2001
import java.util.Random ;

class RandomIntDataFile
{
	public int[] generateValues()
	{
		Random generator = new Random() ;
		int[] numbers = new int[1000] ;
		int i = 0 ;
		while (i < 1000)
		{
			int randomValue = generator.nextInt(26) ;
			if (randomValue > 0)
			{
				numbers[i] = randomValue ;
				i++ ;
			}
		}
		return numbers ;
	}
	
	public void writeValues(int[] numbers)
	{
		FileOutput outFile = new FileOutput("RandomIntDataFile.txt") ;
		for (int m = 0; m < 1000; m++)
		{
			outFile.writeInteger(numbers[m]) ;
			outFile.writeNewline() ;
	    }
		outFile.close() ;
	}
	
	public static void main(String[] args)
	{
		RandomIntDataFile program = new RandomIntDataFile() ;
		program.writeValues(program.generateValues()) ;
	}
}