//Navdeep Daheley, March 2001
//Die class for Greed, represents a single die.
import java.util.Random ;
public class Die
{
	private boolean active; 				//Represents status of dice as rollable or not.
	private static Random generator = new Random() ;	//Random generator object.
	
	public Die()							//Die Constructor calls method to set
	{										//status as rollable.
		setStatus(true) ;
	}
	
	public void setStatus(boolean value)	//Sets die status.
	{
		active = value ;
	}
	
	public boolean getStatus()				//Returns die status.
	{
		return active ;
	}
	
	public int throwDie()					//Rolls die returning
	{										//randomly generated value.
		return (generator.nextInt(6) + 1) ;
	}	
	
	public static void main(String[] args)	//Test method.
	{										//Rolls die ten times.
		Die Die1 = new Die() ;
		for (int i = 0; i < 10; i++)
		{
			System.out.println(Die1.throwDie()) ;
		}
	}
}