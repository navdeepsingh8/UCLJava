//Tests the Math.sqrt method
//Navdeep Daheley, January 2001
class Q5_1
{
	private int testCounter ;
	private int errorCounter ;
	private FileInput inFile ;
	private static KeyboardInput in = new KeyboardInput() ;
	
	
	public Q5_1(String fileName)
	{
		testCounter = 0 ;
		errorCounter = 0 ;
		inFile = new FileInput(fileName) ;
	}
	
	public boolean compareSqrt(double test, double expected)
	{
		double lower = expected - (expected/10000) ;
		double upper = expected + (expected/10000) ;
		if (test < lower || test > upper)
		{
			return false ;
		}
		return true ;
	}
		
	public void runTest()
	{
		while(!inFile.eof())
		{
			double value = inFile.readDouble() ;
			double expectedSqrt = inFile.readDouble() ;
			double testSqrt = java.lang.Math.sqrt(value) ;
			testCounter++ ;
			if (compareSqrt(testSqrt, expectedSqrt) == false)
			{
				errorCounter++ ;
				System.out.println("Sqrt method returned " + testSqrt + ". Expected value was " + expectedSqrt + ".") ;
			}
		}
		inFile.close() ;
	}
	
	public void finish()
	{
		System.out.println("\n" + testCounter + " tests were run.") ;
		System.out.println(errorCounter + " errors were found.") ;
	}
		
	public static void main(String[] args)
	{
		System.out.print("File to read test values from: ") ;
		String fileName = in.readString() ;
		Q5_1 testProgram = new Q5_1(fileName) ;
		testProgram.runTest() ;
		testProgram.finish() ;
	}
}		