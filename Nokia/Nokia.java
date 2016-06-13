//Java class to represent the front end of a Nokia Phone Book
//Navdeep Daheley, March 2001
class Nokia
{
	private KeyboardInput in = new KeyboardInput() ;
	private PhoneBook NokiaPhoneBook = new PhoneBook() ;
	
	public void menu()
	{
		System.out.println("") ;
		System.out.println("1. Search") ;
		System.out.println("2. Browse") ;
		System.out.println("3. Add") ;
		System.out.println("4. Remove") ;
		System.out.println("5. Edit") ;
		System.out.println("6. Memory Status") ;
		System.out.print("\n" + ">") ;
		int choice = in.readInteger() ;
		if (choice == 1) { search() ; }
		if (choice == 2) {} // { browse(0) ; } //not to be implemented yet
		if (choice == 3) { add() ; }
		if (choice == 4) { remove() ; }
		if (choice == 5) { edit() ; }
		if (choice == 6) { memory() ; }
		System.out.println(choice) ;
	}
	
	public void listMenu()
	{
		System.out.println("\n" + "d browses down, u browses up, r removes, e edits, x cancels") ;
		char option = in.readCharacter() ;
		if (option == 'x') { menu() ; }
		//code for other options required here
		//browsing not to be implemented yet
	}
		
	public void display(PhoneEntry entry)
	{
		System.out.println("") ;
		System.out.println(entry.getName()) ;
		System.out.println(entry.getNumber()) ;
		listMenu() ;
	}	
	
	public void searchError()
	{
		System.out.println("\n" + "No matching entry was found.") ;
	}	
	
	public void search()
	{
		System.out.print("Name: ") ;
		String searchName = in.readString() ;
		PhoneEntry returnedEntry = NokiaPhoneBook.searchList(searchName) ;
		if (returnedEntry == null)
		{
			searchError() ;
		} else {
			display(returnedEntry) ;
		}
		menu() ;
	}
	
	public void add()
	{
		System.out.print("Name: ") ;
		String addName = in.readString() ;
		System.out.print("Number: ") ;
		String addNumber = in.readString() ;
		NokiaPhoneBook.addEntry(addName, addNumber) ;
		if (NokiaPhoneBook.isListFull() == false)
		{
			System.out.println("Entry successfully added.") ;
		} else {
			System.out.println("Phone Book is full. Please delete entries before storing any more.") ;
		}
		menu() ;
	}
	
	public void remove()
	{
	//	System.out.print("Name: ") ;
	}
	
	public void edit()
	{
	}	
	
	public void memory()
	{
		System.out.println("\n" + "Used: " + NokiaPhoneBook.memoryStatus()) ;
		System.out.println("Free: " + (200 - NokiaPhoneBook.memoryStatus())) ;
		menu() ;
	}
	
	public static void main(String[] args)
	{
		Nokia NokiaPhone = new Nokia() ;
		NokiaPhone.menu() ;
	}
}