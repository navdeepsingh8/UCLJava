//Navdeep Daheley, April 2001
//Class for storing a dictionary entry; a String and its meaning
class Entry
{
	private String word = null ;
	private String meaning = null ;
	
	public Entry(String newWord, String newMeaning)
	{
		word = newWord ;
		meaning = newMeaning ;
	}
	
	public String getWord()
	{
		return word ;
	}
	
	public String getMeaning()
	{
		return meaning ;
	}
}