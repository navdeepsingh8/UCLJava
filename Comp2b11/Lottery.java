//Simple data class representing a lottery ticket
import java.util.Random ;
class Lottery
{
	private int[] Ticket = new int[6] ;
	private int[] Results = { 2, 42, 23, 25, 16, 28, 13 } ;
	private Random randomizer = new Random() ;
	private int totalScore = 0 ;
	
	public void generateTicket()
	{
		int firstNumber = 0;
		do {
			firstNumber = randomizer.nextInt(50) ;
			if (firstNumber != 0) {
			Ticket[0] = firstNumber ;
			}
		}
		while (Ticket[0] != firstNumber) ;
		
		for (int i = 1; i < 6; i++)
		{
			Ticket[i] = generateNumber(i) ;
		}
	}
	
	public int generateNumber(int i)
	{
		boolean valid = true ;
		int potential = randomizer.nextInt(50) ;
		for (int m = 0; m < i; m++)
		{
			if (potential == Ticket[m] || potential == 0) {
				valid = false ;
			}
		}
		if (valid) {
			return potential ;
		} else {
			return generateNumber(i) ;
		}
	}
	
	public int[] getTicket()
	{
		return Ticket ;
	}
	
	public int getTicket(int i)
	{
		return Ticket[i] ;
	}
	
	public int getTotalScore()
	{
		return totalScore ;
	}

	public void resetTotalScore()
	{
		totalScore = 0 ;
	}
	
	public void scoreTicket()
	{
		int matches = 0 ;
		for (int i = 0; i < 6; i++)
		{
			for (int m = 0; m < 6; m++)
			{
				if (Ticket[m] == Results[i]) {
					matches++ ;
					break ;
				}
			}
		}
		
		if (matches == 5)
		{
			for (int m = 0; m < 6; m++)
			{
				if (Ticket[m] == Results[6]) {
					matches = 7 ;
				}
			}
		}
		
		switch (matches) {
			case 3:
				totalScore += 10 ;
				break ;
			case 4:
				totalScore += 150 ;
				break ;
			case 5:
				totalScore += 1500 ;
				break ;
			case 6:
				totalScore += 4000000 ;
				break ;
			case 7:
				totalScore += 200000 ;
				break ;
				}
	}
	
	public static void main(String[] args)
	{
		Lottery test = new Lottery() ;
		long averageScore = 0 ;
		int tests = 15000000 ;
		int tickets = 1 ;
		for (int n = 0; n < tests; n++) {
			test.resetTotalScore() ;
			for (int m = 0; m < tickets; m++)
			{
				test.generateTicket() ;
				test.scoreTicket() ;
			}
			if (test.getTotalScore() > 100000)
			{ System.out.print(test.getTotalScore() + " ") ; }
			if (n == tests/2) {
				System.out.println("halfway...") ;
			}
		}
	//averageScore = averageScore/tests ;
	//System.out.println("After " + tests + " tests, the average return on " + tickets + " tickets was " + averageScore + ".") ;	
	}	
	
	
			
}