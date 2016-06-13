//Navdeep Daheley, April 2001
//Class representing the dictionary
import java.util.ArrayList ;
class Dictionary
{
	private ArrayList entries = new ArrayList() ;
	private KeyboardInput in = new KeyboardInput() ;
	private FileOutput fOut ;
	private FileInput fIn ;
	
	public void addEntry()
	{
		System.out.print("\n" + "Entry name: ") ;
		String name = in.readString() ;
		System.out.print("Entry meaning: ") ;
		String meaning = in.readString() ;
		if (findEntry(name) == null)
		{
			entries.add(new Entry(name, meaning)) ;
			System.out.println("Entry added successfully.") ;
		} else {
			System.out.println("Entry already exists.") ;
		}
	}

	public void removeEntry()
	{
		System.out.print("\n" + "Name of entry to remove: ") ;
		Entry entryToGo = findEntry(in.readString()) ;
		if (entryToGo != null)
		{
			entries.remove(entryToGo) ;
			System.out.println("Entry removed successfully.") ;
		} else {
			System.out.println("Entry not found.") ;
		}		
	}
	
	public Entry findEntry(String name)
	{
		for (int n = 0; n < entries.size(); n++)
		{
			Entry tempEntry = (Entry)entries.get(n) ;
			if (tempEntry.getWord().equalsIgnoreCase(name))
			{
				return tempEntry ;
			}
		}
		return null ;
	}
	
	public void lookUp()
	{
		System.out.print("\n" + "Word to look up: ") ;
		Entry entryToShow = findEntry(in.readString()) ;
		if (entryToShow != null)
		{
			System.out.println(entryToShow.getWord() + " : " + entryToShow.getMeaning()) ;
		} else {
			System.out.println("Entry not found.") ;
		}
						
	}
	
	public void toFile()
	{
		System.out.print("\n" + "Filename: ") ;
		fOut = new FileOutput(in.readString()) ;
		for (int n = 0; n < entries.size(); n++)
		{
			Entry tempEntry = (Entry)entries.get(n) ;
			fOut.writeString(tempEntry.getWord() + "\n" + tempEntry.getMeaning() + "\n") ;
		}
		fOut.close() ;
		System.out.println("Dictionary saved successfully.") ;
	}
	
	public void fromFile()
	{
		System.out.print("\n" + "Filename: ") ;
		fIn = new FileInput(in.readString()) ;
		entries = new ArrayList() ;
		int index = 0 ;
		while(!fIn.eof())
		{
			String word = fIn.readString() ;
			String meaning = fIn.readString() ;
			entries.add(new Entry(word, meaning)) ;
		}
	}
		
	
	public void menu()
	{
		int choice = 0 ;
		while (choice != 6)
		{
			System.out.println("\n" + "1. Look up word") ;
			System.out.println("2. Add word") ;
			System.out.println("3. Remove word") ;
			System.out.println("4. Save dictionary") ;
			System.out.println("5. Load dictionary") ;
			System.out.println("6. Quit") ;
			System.out.print(">") ;
			choice = in.readInteger() ;
			if (choice == 1)
				{ lookUp() ; }
			if (choice == 2)
				{ addEntry() ; }
			if (choice == 3)
				{ removeEntry() ; }
			if (choice == 4)
				{ toFile() ; }
			if (choice == 5)
				{ fromFile() ; }
		}
	}
	
	public static void main(String[] args)
	{
		Dictionary testObj = new Dictionary() ;
		testObj.menu() ;
	}
}