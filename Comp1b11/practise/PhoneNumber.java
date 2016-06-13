//Class to represent phone numbers
//Navdeep Daheley, January 2001

class PhoneNumber
{
	//declare instance variable
	private String[] theNumber ;
	private String name ;
	
	//constructors to initialise object
	public PhoneNumber(String intl, String area, String number, String nameOfPerson)
	{
		theNumber = new String[3] ;
		theNumber[0] = intl ;
		theNumber[1] = area ;
		theNumber[2] = number ;
		name = nameOfPerson ;
	}
	
	public PhoneNumber(String area, String number, String nameOfPerson)
	{
		this("", area, number, nameOfPerson) ;
	}
	
	//methods
	public String getNumber(int part)
	{
		if (part > -1 && part < 3)	{
			return theNumber[part] ;
		}
		else {
			return null ;
		}
	}
	
	public String getName()
	{
		return name ;
	}
	
	public static void main(String[] args)
	{
		PhoneNumber myNumber = new PhoneNumber("44", "7799", "432181", "Navdeep") ;
		System.out.println(myNumber.getNumber(0) + myNumber.getNumber(1) + myNumber.getNumber(2)) ;
		System.out.println(myNumber.getNumber(4)) ;
		System.out.println(myNumber.getName()) ;
		System.out.println(myNumber.name) ;
	}
}