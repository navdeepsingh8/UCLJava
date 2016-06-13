//Counts the number of a word in a text file
//Navdeep Daheley, January 2001

class WordFind
{
	public String selectFile()
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("File to search: ") ;
		String fileName = in.readString() ;
		return fileName ;
	}
	
	public String selectWord()
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("Word to search for (case insensitive): ") ;
		String word = in.readString() ;
		String wordLowerCase = word.toLowerCase() ;
		return wordLowerCase ;
	}
		
	public String stringChars(int startAt, int stopAt, String line)
	{
		char[] word = new char[stopAt - startAt + 1] ;
		for (int i = 0; i < word.length; i++)
		{
			word[i] = line.charAt(startAt + i) ;
		}
		String  fullWord = new String(word) ;
		String loweredFullWord = fullWord.toLowerCase() ;
		return loweredFullWord ;
	}
	
	public int splitLine(String line, String word)
	{
		int wordsInLine = 0 ;
		int startAt = -1 ;
		int stopAt = 0 ;
		for (int i = 0; i < line.length(); i++)
		{
			if (line.charAt(i) == ' ' || line.charAt(i) == '.' || line.charAt(i) == ',')
			{
				if (startAt > -1)
				{
					stopAt = (i - 1) ;
					String compiledWord = stringChars(startAt, stopAt, line) ;
					if (compiledWord.equals(word))
					{
						wordsInLine++ ;
				 	}
					startAt = -1 ;
				}
			}
			else
			{
				if (startAt == -1)
				{
					startAt = i ;
				}
				if (startAt > -1 && i == (line.length() - 1))
				{
					stopAt = i ;
					String compiledWord = stringChars(startAt, stopAt, line) ;
					if (compiledWord.equals(word))
					{
						wordsInLine++ ;
					}
					startAt = -1 ;
				}
			}
		}
		return wordsInLine ;
	}
	
	public int loopThruFile(String fileName, String word)
	{
		FileInput inFile = new FileInput(fileName) ;
		int totalWords = 0 ;
		while (!inFile.eof()) {
			String currentLine = inFile.readString() ;
			totalWords += splitLine(currentLine, word) ;
		}
		inFile.close() ;
		return totalWords ;
	}
	
	public static void main(String[] args)
	{
		WordFind program = new WordFind() ;
		String fileName = program.selectFile() ;
		String word = program.selectWord() ;
		int count = program.loopThruFile(fileName, word) ;
		System.out.println("There are " + count + " instances of " + word + ".") ;
	}
}