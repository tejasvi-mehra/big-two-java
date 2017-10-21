
/**
 * 
 * A Sub Class of Hand for a Hand of Single
 * @author Tejasvi Mehra
 */
@SuppressWarnings("serial")
public class Single extends Hand
{
	/**
	 * Assign the Cards and Player
	 * @param player Player
	 * @param cards List of Cards
	 */
	public Single(CardGamePlayer player, CardList cards)
	{
		super(player, cards);	
	}

	/**
	 * Check the List of Cards for a Hand of Single
	 * @return True if valid hand else False
	 */
	public boolean isValid()
	{
			return true;
	}
	
	/**
	 * Return the name of the class in String Format
	 * @return Class Name
	 */
	public String getType()
	{
		return "Single";
	}
}
