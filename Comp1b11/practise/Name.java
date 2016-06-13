//A first class: Person
//Navdeep Daheley, January 2001
import java.util.* ;
public class Name {
	private String surname = "nobody" ;
	private ArrayList firstNames ;
	
	//Constructors to initialise a new Name object.
	public Name(final String firstName1,
				final String firstName2,	
				final String surname)
	{
		this.surname = surname ;
		setFirstNames(firstName1, firstName2) ;
	}
	
	public Name(final String firstName1, final String surname)
	{
		this(firstName1, null, surname) ;
	}
	
	public Name(final String surname)
	{
		this(null, null, surname) ;
	}
	
	public void setSurname(final String surname)
	{
		this.surname = surname ;
	}
	
	public String getSurname()
	{
		return surname ;
	}
	
	public void setFirstNames(final String firstName1,
							  final String firstName2)
	{
		firstNames = new ArrayList() ;
		if (firstName1 != null) {
			firstNames.add(firstName1) ;
		}
		if (firstName2 != null) {
			firstNames.add(firstName2) ;
		}
	}
	
	public String getFirstName(final int position)
	{
		if(position > firstNames.size()) {
			return null ;
		} else {
			return (String)firstNames.get(position-1) ;
		}
	}
	
	public static void main(String[] args)
	{
		Name name1 = new Name("Navdeep", "Singh", "Daheley") ;
		System.out.println(name1.getSurname()) ;
		System.out.println(name1.getFirstName(1)) ;
	}
}