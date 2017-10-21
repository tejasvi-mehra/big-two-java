import java.util.Arrays;

/**
 * A Sub Class of Hand for a Hand of Straight
 * @author Tejasvi Mehra
 */
@SuppressWarnings("serial")

public class Straight extends Hand
{
	/**
	 * Assign the Cards and Player
	 * @param player Player
	 * @param cards List of Cards
	 */
	public Straight(CardGamePlayer player, CardList cards) 
	{
		super(player, cards);	
	}

	/**
	 * Check the List of Cards for a Hand of Straight
	 * @return True if valid hand else False
	 */
	public boolean isValid()
	{
			int [] newRanks = new int[5];
			
			for(int i=0;i<5;i++)
			{
				if(this.getCard(i).getRank()<=1)
					newRanks[i]=this.getCard(i).getRank()+13;
				else
					newRanks[i]=this.getCard(i).getRank();
			}
			Arrays.sort(newRanks);
			for(int i=0;i<4;i++)
			{
				if(newRanks[i]+1 != newRanks[i+1])
					return false;
			}
			return true;
	}
	
	/**
	 * Return the name of the class in String Format
	 * @return Class Name
	 */
	public String getType()
	{
		return "Straight";
	}

}
