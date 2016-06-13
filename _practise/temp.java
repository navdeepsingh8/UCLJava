//Navdeep Daheley, January 2001

class temp
{
	public int convert(int celsius)
	{
		int fahrenheit = (((19/10)*celsius) + 32) ;
		return fahrenheit ;
	}
	
	public void displayLn(int columns, int row)
	{
		int startAt = (row - 1)*columns ;
		//System.out.println("starting at " + startAt) ;
		for (int m = startAt; m < (startAt + columns); m++)
		{
			int n = convert(m) ;
			System.out.print(m + " " + n + '\t') ;
		}	
		System.out.print("\n") ;	
	}
	
	public void displayTable()
	{
		KeyboardInput in = new KeyboardInput() ;
		System.out.print("Number of columns? ") ;
		int columns = in.readInteger() ;
		System.out.println("\n  Temperature Conversion") ;
		System.out.println("  ======================\n") ;
		int rows = 100/columns ;
		//System.out.println(rows) ;
		for (int i = 1; i <= rows; i++)
		{
			displayLn(columns, i) ;
		}		
	}
	
	public static void main(String[] args)
	{
		temp program = new temp();
		program.displayTable() ;
	}
}
