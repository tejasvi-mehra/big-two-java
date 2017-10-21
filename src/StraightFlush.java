
import java.util.*;
/**
 * A Sub Class of Hand for a Hand of StraightFlush
 * @author Tejasvi Mehra *
 */
@SuppressWarnings("serial")

public class StraightFlush extends Hand
{
	/**
	 * Assign the Cards and Player
	 * @param player Player
	 * @param cards List of Cards
	 */
	public StraightFlush(CardGamePlayer player, CardList cards)
	{
		super(player, cards);	
	}

	/**
	 * Check the List of Cards for a Hand of StraightFlush
	 * @return True if valid hand else False
	 */
	public boolean isValid()
	{
		int[] newRanks = new int[5];
		
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
		for(int i=0;i<this.size()-1;i++)
		{
			if((this.getCard(i).getSuit())!=this.getCard(i+1).getSuit())
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
		return "StraightFlush";
	}
}
