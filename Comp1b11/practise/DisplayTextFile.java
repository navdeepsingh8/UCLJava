//Navdeep Daheley, January 2001

class DisplayTextFile
{
	public static void main(String[] args)
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("Enter filename of file to display: ") ;
		String fileName = in.readString() ;
		FileInput inFile = new FileInput(fileName) ;
		String s = inFile.readString() ;
		while (!inFile.eof()) {
			System.out.print(s) ;
			s = inFile.readString() ;
		}
		System.out.print("\n") ;
		inFile.close() ;
	}

}