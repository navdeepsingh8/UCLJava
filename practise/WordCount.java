//Counts the number of words in a text file
//Navdeep Daheley, January 2001

class WordCount
{

	public String selectFile()
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("File to search: ") ;
		String fileName = in.readString() ;
		return fileName ;
	}
		
	public int splitLine(String line)
	{
		int words = 0 ;
		boolean spaceFlag = false ;
		for (int i = 0; i < line.length(); i++)
		{
			if (line.charAt(i) == ' ')
			{
				if (spaceFlag == false)
				{
					words++ ;
					spaceFlag = true ;
				}
			}
			else
			{
				spaceFlag = false ;
			}
		}
		return words ;
	}
	
	public int countWords(String fileName)
	{
		FileInput inFile = new FileInput(fileName) ;
		int totalWords = 0 ;
		while (!inFile.eof()) {
			String currentLine = inFile.readString() ;
			totalWords += splitLine(currentLine) ;
		}
		inFile.close() ;
		return totalWords ;
	}
	
	public static void main(String[] args)
	{
		WordCount program = new WordCount() ;
		int count = program.countWords(program.selectFile()) ;
		System.out.println("There are " + count + " words.") ;
	}
}