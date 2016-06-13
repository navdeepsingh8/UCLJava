//Class representing an Employer
//Navdeep Daheley, January 2001

class Employer
{
	private String _companyName ;
	private ArrayList _employees ;
	
	public Employer(String companyName)
	{
		_companyName = companyName ;
		_employees = new ArrayList() ;
	}
	
	public String getCompanyName()
	{
		return _companyName ;
	}
	
	public void addEmployee(String name, int age, Employer employer)
	{
		Employee employee = new Employee(name, age, this) ;
		_employees.add(employee) ;
	}
	
	public Employee getEmployee(String name)
	{
		for (int i = 0; i < _employees.size() ; i++)
		{
			Employee temp = (Employee)_employees(i)
			if (temp._name.compareTo(name) == 0)
			{
				return temp ;
			}
		}
		return null ;
	}
	
	public static void main(String[] args)
	{
	}
}