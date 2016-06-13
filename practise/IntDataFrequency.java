//Navdeep Daheley, January 2001

class IntDataFrequency
{
	public int[] readData(String fileName)
	{
		FileInput inFile = new FileInput(fileName) ;
		int[] frequencyTable = new int[26] ;
		while(!inFile.eof())
		{
			int currentInt = inFile.readInteger() ;
			frequencyTable[(currentInt)]++ ;
		}
		return frequencyTable ;
	}
	
	public void displayData(int[] frequencyTable)
	{
		for (int i = 1; i < frequencyTable.length; i++)
		{
			System.out.print(" " + i + '\t' + frequencyTable[i] + "\n") ;
		}
	}
	
	public static void main(String[] args)
	{
		IntDataFrequency program = new IntDataFrequency() ;
		program.displayData(program.readData("RandomIntDataFile.txt")) ;
	}
}