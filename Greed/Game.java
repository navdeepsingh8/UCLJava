//Navdeep Daheley, March 2001
//Game class in Greed, interacts with Player objects and DiceManager object,
//handles Player turns and user input/output.
public class Game
{
	private Player[] players ;							//Array of Players. Not initialised yet.
	private DiceManager cup = new DiceManager() ;		
	private KeyboardInput in = new KeyboardInput() ;	
	
	public void addPlayers()							
	{
		System.out.print("\n" + "How many players? ") ;
		players = new Player[in.readInteger()] ;		//Dimensionalise array to user specification.
		for (int i = 0; i < players.length; i++)
		{
			System.out.print("Player " + (i + 1) + " name: ") ;
			players[i] = new Player(in.readString()) ;	//Create player objects in 
		}												//array to user specification.
	}
	
	public void turnSelector()
	{
		int highestScore = 0 ;
		while (highestScore < 5000)						//Loops player turns til player(s)
		{												//score 5000 or over.
			for (int m = 0; m < players.length; m++)
			{
				cup.resetDice() ;						//Resets dice before a turn.
				if (players[m].getTotalScore() == 0)
				{
					firstTurn(players[m]) ;
				} else {
					nextTurn(players[m]) ;
				}
				if (players[m].getTotalScore() > highestScore)
				{
					highestScore = players[m].getTotalScore() ; //Retrieves highest score so far.
				}
			}
		}
		getWinner(highestScore) ;
	}
		
	public void firstTurn(Player thisPlayer)		//Runs through a player's first turn.
	{
		System.out.print("\n" + thisPlayer.getName() + " Press Enter to throw your first roll.") ;
		in.readString() ;
		int[] firstLot = cup.rollDice() ;			//Retrieves dice values for first roll.
		System.out.println("You scored:") ;
		for (int i = 0; i < 5; i++)
		{
			System.out.print(firstLot[i] + " ") ;
		}
		int firstLotScore = cup.score(firstLot) ;	//Retrieves score for first roll.
		thisPlayer.updateTurnScore(firstLotScore) ;
		System.out.println("\n" + firstLotScore + " off this roll. " + cup.activeDice() + " dice remaining to throw in this turn.") ;
		if (firstLotScore >= 300)					//Determines if player is bust and
		{											//whether any active dice left.
			if (cup.activeDice() > 0)
			{
				thisPlayer.saveTurnScore() ;		
				promptPlayer(thisPlayer) ;			//Player may roll again.
			} else {
				thisPlayer.bust() ;
				System.out.println("You have scored on every die. Your turn is over.") ;
			}
		} else {
				thisPlayer.bust() ;
				System.out.println("You are bust. Your turn is over.") ;
		}
	}
	
	public void promptPlayer(Player thisPlayer)		//Prompts player who is able to roll again.
	{
		System.out.print("\n" + "Do you wish to play your (N)ext Turn or (P)ass? ") ;
		String choice = in.readString() ;
		if (choice.equalsIgnoreCase("N") == true)
		{
			nextTurn(thisPlayer) ;					//Player has next turn.
		}
		if (choice.equalsIgnoreCase("P") == true)
		{
													//Player passes up turn to next player.
		}		
	}
	
	public void promptQuit()						//Prompts player whether to quit game or play again.
	{
		System.out.print("\n" + "Do you wish to (P)lay again or (Q)uit? ") ;
		String choice = in.readString() ;
		if (choice.equalsIgnoreCase("P") == true)
		{
			startGame() ;
		}
		if (choice.equalsIgnoreCase("Q") == true)
		{
			System.out.println("\n") ;
		}
	}
	
	public void nextTurn(Player thisPlayer)				//Runs through a player's subsequent turn.
	{
		System.out.print("\n" + thisPlayer.getName() + " you have " + thisPlayer.getTotalScore() + " points. Press Enter to throw your next roll.") ;
		in.readString() ;
		int[] nextLot = cup.rollDice() ;				//Retrieves values for turn's roll.
		System.out.println("You scored :") ;
		for (int i = 0; i < 5; i++)
		{
			if (nextLot[i] != 0)
			{
				System.out.print(nextLot[i] + " ") ;
			}
		}
		int nextLotScore = cup.score(nextLot) ;			//Retrieves score for this turn's roll.
		thisPlayer.updateTurnScore(nextLotScore) ;
		System.out.println("\n" + nextLotScore + " off this roll. " + cup.activeDice() + " dice remaining to throw in this turn.") ;
		if (nextLotScore > 0)
		{
			if (cup.activeDice() > 0)					//Determines if player is bust and
			{											//whether any active dice left.
				thisPlayer.saveTurnScore() ;
				promptPlayer(thisPlayer) ;				//Player may roll again.
			} else {
				thisPlayer.bust() ;
				System.out.println("You have scored on every die. Your turn is over.") ;
			}
		} else {
				thisPlayer.bust() ;
				System.out.println("You are bust. Your turn is over.") ;
		}
	}
	
	public void getWinner(int scoreToCheck)			//Announces winner of game.
	{
		Player Winner = new Player("dummy") ;		//Creates Winner object and initialises dummy values.
		for (int i = 0; i < players.length; i++)
		{
			if (players[i].getTotalScore() == scoreToCheck)
			{
				Winner = players[i] ;				//Locates Player with winning score and sets it to Winner.
			}
		}
		System.out.println("\n" + Winner.getName() + " has won the game with " + Winner.getTotalScore() + " points.") ;
	}
	
	public void startGame()
	{
		Game GreedGame = new Game() ;	//Sequence for running a game.
		GreedGame.addPlayers() ;
		GreedGame.turnSelector() ;
		GreedGame.promptQuit() ;
	}		
		
	public static void main(String[] args)
	{
		Game GreedStartUp = new Game() ;
		GreedStartUp.startGame() ;
	}
}