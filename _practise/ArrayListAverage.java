//Navdeep Daheley, January 2001
import java.util.ArrayList ;

class ArrayListAverage
{	
	public double calculate(ArrayList values)
	{
		double average = 0 ;
		for (int i = 0; i < values.size(); i++)
		{
			average += (double)values.get(i) ;
		}
		average = average/values.size() ;
		return average ;
	}
	
	public ArrayList input()
	{
		ArrayList numbers = new ArrayList() ;
		KeyboardInput in = new KeyboardInput() ;
		while (true) {
			System.out.print("Enter a number: ") ;
			double number = in.readDouble() ;
			if (number == "stop")
			{
				break ;
			}
			numbers.add(new Double (number)) ;
		}
		return numbers ;
	}
	
	public static void main(String[] args)
	{
		ArrayListAverage program = new ArrayListAverage() ;
		double average = program.calculate(program.input()) ;
		System.out.println("The average is " + average) ;
	}
}
		