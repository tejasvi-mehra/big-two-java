
/**
 * A Sub Class of Hand for a Hand of Flush
 * @author Tejasvi Mehra *
 */
@SuppressWarnings("serial")


public class Flush extends Hand
{
	/**
	 * Assign the Cards and Player
	 * @param player Player
	 * @param cards List of Cards
	 */
	public Flush(CardGamePlayer player, CardList cards) 
	{
		super(player, cards);	
	}

	/**
	 * Check the List of Cards for a Hand of Flush
	 * @return True if valid hand else False
	 */
	public boolean isValid()
	{
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
		return "Flush";
	}
}
