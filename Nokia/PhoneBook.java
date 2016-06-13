//Java class to represent a Nokia Phone Book
//Navdeep Daheley, March 2001

import java.util.ArrayList ;
class PhoneBook
{
	public ArrayList entryList = new ArrayList() ;
	private boolean fullList = false ;
	
	public PhoneEntry searchList(String searchName)
	{
		for (int i = 0; i < entryList.size(); i++)
		{
			PhoneEntry tempEntry = (PhoneEntry)entryList.get(i) ;
			if (tempEntry.getName().equalsIgnoreCase(searchName))
			{
				return tempEntry ;
			}
		}
		return null ;
	}
	
	public PhoneEntry downList(int currentIndex)
	{
		if (currentIndex < 200)
		{
			return (PhoneEntry)entryList.get(currentIndex + 1) ;
		} else {
			return (PhoneEntry)entryList.get(0) ;
		}
	}
	
	public PhoneEntry upList(int currentIndex)
	{
		if (currentIndex > 0)
		{
			return (PhoneEntry)entryList.get(currentIndex - 1) ;
		} else {
			return (PhoneEntry)entryList.get(199) ;
		}
	}
	
	public void addEntry(String name, String number)
	{
		PhoneEntry entry = new PhoneEntry(name, number) ;
		if (entryList.size() < 200)
		{
			entryList.add(entry) ;
			fullList = false ;
			} else {
			fullList = true ;
		}
	}
	
	public void eraseEntry(PhoneEntry entry)
	{
		for (int i = 0; i < entryList.size(); i++)
		{
			PhoneEntry tempEntry = (PhoneEntry)entryList.get(i) ;
			if (tempEntry == entry)
			{
				entryList.remove(i) ;
				if (entryList.size() < 200)
				{
					fullList = false ;
				}
			}
		}
	}
	
	public void editEntry(PhoneEntry entry, String newName, String newNumber)
	{
		for (int i = 0; i < entryList.size(); i++)
		{
			PhoneEntry tempEntry = (PhoneEntry)entryList.get(i) ;
			if (tempEntry == entry)
			{
				entryList.remove(i) ;
				entryList.add(i, new PhoneEntry(newName, newNumber)) ;
			}
		}
	}
	
	
	public boolean isListFull()
	{
		return fullList ;
	}
	
	public int memoryStatus()
	{
		return entryList.size() ;
	}
				
}