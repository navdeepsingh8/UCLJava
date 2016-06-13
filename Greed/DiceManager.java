//Navdeep Daheley, March 2001
//Dice Manager class for Greed, handles dice rolling, scoring and status.
public class DiceManager
{
	private Die[] dice = new Die[5] ;				//Array of five die objects.
	
	public DiceManager()							//Constructor intialises
	{												//each die object.
		for (int i = 0; i < 5; i++)
		{
			dice[i] = new Die() ;
		}
	}
	
	public void resetDice()							//Re-intialises each die object.
	{
		for (int i = 0; i < 5; i++)
		{
			dice[i] = new Die() ;
		}
	}
	
	public int[] rollDice()							//Rolls each die object in turn.
	{
		int[] diceValues = new int[5] ;
		for (int i = 0; i < 5; i++)
		{
			if (dice[i].getStatus() == true)
			{
				diceValues[i] = dice[i].throwDie() ;
			}
		}
		return diceValues ;							//Returns array of die values.
	}
	
	public int score(int[] diceValues)				//Calculates score for array
	{												//of dice values.
		int score = 0 ;								
		
		for (int value = 1; value < 7; value++)		//Check for triples.
		{
			int counter = 0 ;
			for (int die = 0; die < 5; die++)
				{
					if (diceValues[die] == value)
					{
						counter++ ;
					}
				}
				
				if (counter >= 3)
				{
					if (value == 1)
					{
						score += 1000 ;
					} else {
						score += 100*value ;
					}
					
					int downcounter = 3 ;			//Sets triple scored dice to zero.
					for (int i = 0; i < 5; i++)
					{
						if (diceValues[i] == value)
						{
						diceValues[i] = 0 ;
						downcounter-- ;
						}
						if (downcounter == 0)
						{
							break ;
						}
					}
				}
		}
		
		for (int die = 0; die < 5; die++)			//Checks for singles.
		{											//Sets single scored dice to zero.
			if (diceValues[die] == 1)
			{
				score += 100 ;
				diceValues[die] = 0 ;
			}
			if (diceValues[die] == 5)
			{
				score += 50 ;
				diceValues[die] = 0 ;
			}
		}
		
		for (int die = 0; die < 5; die++)			//Sets scored (zero valued) dice
		{											//to inactive.
			if (diceValues[die] == 0)
			{
				dice[die].setStatus(false) ;
			}
		}
	return score ;				
	}
	
	public int activeDice()							//Returns number of remaining active dice.
	{
		int count = 0 ;
		for (int i = 0; i < 5; i++)
		{
			if (dice[i].getStatus() == true)
			{
				count++ ;
			}
		}
		return count ;
	}
	
	public static void main(String[] args)			//Test method.
	{												//Rolls dice twice.
		DiceManager DM = new DiceManager() ;
		//test roll
		int[] testRollValues = DM.rollDice() ;
		for (int i = 0; i < 5; i++)
		{
			System.out.print(testRollValues[i] + " ") ;
		}
		System.out.println("\n" + DM.score(testRollValues)) ;
		
		//another roll
		System.out.println("\n") ;
		int[] testRollValues2 = DM.rollDice() ;
		for (int i = 0; i < 5; i++)
		{
			System.out.print(testRollValues2[i] + " ") ;
		}
		System.out.println("\n" + DM.score(testRollValues2)) ;
	}	
}