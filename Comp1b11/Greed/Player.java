//Navdeep Daheley, March 2001
//Player class for Greed, handles Player name and score.
public class Player
{
	private String name ;				//Player's name.
	private int totalScore ;			//Player's total score in game so far.
	private int turnScore ;				//Player's score from a turn.
		
	public Player(String playerName)	//Constructor initialises name and scores.
	{
		name = playerName ;
		totalScore = 0 ;
		turnScore = 0 ;
	}
	
	public int getTotalScore()			//Returns total score.
	{
		return totalScore ;
	}

	public int getTurnScore()			//Returns current turn's score.
	{
		return turnScore ;
	}
	
	public String getName()				//Returns player's name.
	{
		return name ;
	}
	
	public void updateTurnScore(int score)	//Updates current turn's score.
	{
		turnScore = score ;
	}
	
	public void bust()					//Sets scores to zero.
	{
		turnScore = 0 ;
		totalScore = 0 ;
	}
	
	public void saveTurnScore()			//Updates total score.
	{
		totalScore += turnScore ;
	}
}