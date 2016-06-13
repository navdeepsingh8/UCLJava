//Written by N.Daheley January 2001
class methods
{
	
double convert(double valuefeet)
{
	double metresvalue = (2*valuefeet)/5 ;
	return metresvalue ;
}	

public static void main(String[] args)
{
	KeyboardInput in = new KeyboardInput() ;
	methods obj = new methods() ;
	System.out.println("Converts feet to metres. Enter value in feet.") ;
	double feet = in.readDouble() ;
	double metres = obj.convert(feet) ;
	System.out.println("" + metres + " metres.") ;
}
}