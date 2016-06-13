/**
 * Title: class PhoneBook
 * Description: This class uses class PhoneList to implement a simple
 * phone book application. It provides the applications main method, along
 * with a number of instance methods to provide the terminal based user.
 * Note that all keyboard input and output to the screen is done in this
 * class.
 * Copyright: Copyright (c) 2000
 * Company: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 1.0
 */
public class PhoneBook
{
  // Only one KeyboardInput object needs to be created. It can be
  // shared amongst the methods by storing a reference to it in
  // an instance variable.
  private KeyboardInput in ;
  // The phone list used by an instance of this class.
  private PhoneList phoneList ;
  public PhoneBook()
  {
    in = new KeyboardInput() ;
    phoneList = new PhoneList() ;
  }
  public void go()
  {
    boolean quit = false ;
    while (!quit)
    {
      System.out.println("\nPhone Book") ;
      System.out.println("1. Search for a person's phone number") ;
      System.out.println("2. Add a new person and phone number") ;
      System.out.println("3. Remove a person and phone number") ;
      System.out.println("4. Save phone list to a file") ;
      System.out.println("5. Add a phone list from a file") ;
      System.out.println("6. Quit") ;
      System.out.print("\nSelect an item from the menu: ") ;
      int item = in.readInteger() ;
      switch (item)
      {
        case 1 :
          searchForEntry() ;
          break ;
        case 2 : 
          addEntry() ;
          break ;
        case 3 :
          removeEntry() ;
          break ;
        case 4 :
          saveList() ;
          break ;
        case 5 :
          loadList() ;
          break ;
	case 6 : 
          quit = true ; 
          break ;
        default :
          System.out.println("\nSorry - don't recognise that item, try again") ;
       }
     }
  }
  public void addEntry()
  {
    System.out.println("\nAdd entry to phone list") ;
    System.out.print("Enter the person's name: ") ;
    String name = in.readString() ;
    System.out.print("Enter the person's phone number: ") ;
    String phone = in.readString() ;
    phoneList.add(name,phone) ;
  }
  public void removeEntry()
  {
    System.out.println("\nRemove entry from phone list") ;
    System.out.print("Enter the person's name: ") ;
    String name = in.readString() ;
    phoneList.remove(name) ;
  }
  public void searchForEntry()
  {
    System.out.println("\nSearch phone list") ;
    System.out.print("Enter the person's name: ") ;
    String name = in.readString() ;
    String phone = phoneList.getPhoneNumber(name) ;
    if (phone.equals(""))
    {
      System.out.println("Sorry - nothing found") ;
    }
    else
    {
      System.out.println("The phone number is: " + phone) ;
    }
  }
  public void saveList()
  {
    System.out.println("\nSave phone list to a file") ;
    System.out.print("Enter the file name: ") ;
    String fileName = in.readString() ;
    FileOutput out = new FileOutput(fileName) ;
    phoneList.writeToFile(out) ;
    out.close() ;
  }
  // Load a phone list from a file and append it to the
  // existing list.
  public void loadList()
  {
    System.out.println("\nAppend phone list from a file") ;
    System.out.print("Enter the file name: ") ;
    String fileName = in.readString() ;
    FileInput fileIn = new FileInput(fileName) ;
    phoneList.readFromFile(fileIn) ;
    fileIn.close() ;
  } 
  // The main method is as simple as possible, just creating
  // a PhoneBook object and calling its go method.
  public static void main(String[] args)
  {
    PhoneBook book = new PhoneBook() ;
    book.go() ;
  }
}