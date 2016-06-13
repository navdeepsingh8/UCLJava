//Java class to represent a Nokia Phone Book entry
//Navdeep Daheley, March 2001

class PhoneEntry
{
	private String _name = "none" ;
	private String _number = "none" ;
	
	public PhoneEntry(String name, String number)
	{
		_name = name ;
		_number = number ;
	}
	
	public String getName()
	{
		return _name ;
	}
	
	public String getNumber()
	{
		return _number ;
	}
}