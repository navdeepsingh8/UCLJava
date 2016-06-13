//N.Daheley, November 2000
//Converts metric units to imperical
class Q4_3
{
	double cmToFeet(double cm)
	{
		double feet = cm/30 ;
		return feet ;
	}
	
	double mToYards(double m)
	{ 
		double Yards = (9*m)/10 ;
		return Yards ;
	}
	
	double kmToMiles(double km)
	{
		double Miles = (7*km)/5 ;
		return Miles ;
	}
	
	public static void main(String args[])
	{
		KeyboardInput in = new KeyboardInput() ;
		Q4_3 myConverter = new Q4_3() ;
		
		int option = 0 ;
		while (option != 4)
		{
			System.out.println("") ;		
			System.out.println("1. Convert centimetres to feet.") ;
			System.out.println("2. Convert metres to yards.") ;
			System.out.println("3. Convert kilometres to miles.") ;
			System.out.println("4. Quit") ;
			System.out.println("") ;
			System.out.print("Please choose an option: ") ;
				
			option = in.readInteger() ;
		
			if (option >=1 && option <=4)
			{
				//System.out.println("") ;
				//System.out.print("Enter initial value: ") ;
				double initial = 40 ; //in.readDouble() ;
				double output = initial ;
			
				if (option == 1)
				{
					output = myConverter.cmToFeet(initial) ;
				}
				if (option == 2)
				{
					output = myConverter.mToYards(initial) ;
				}
				if (option == 3)
				{
					output = myConverter.kmToMiles(initial) ;
				}
				//System.out.println("The final value is: " + output) ;
			}
			else
			{
				System.out.println("Please select a valid option.") ;
			}
		}
	}
}