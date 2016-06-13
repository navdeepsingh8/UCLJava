//Class representing an employee
//Navdeep Daheley, January 2001

class Employee
{
	private String _name ;
	private int _age ;
	private Employer _employer ;
	
	public Employee(String name, int age, Employer employer)
	{
		_name = name ;
		_age = age ;
		_employer = employer ;
	}
	
	public String getName()
	{
		return _name ;
	}
	
	public Employer getEmployer()
	{
		return _employer ;
	}
	
	public static void main(String[] args)
	{
	}
}