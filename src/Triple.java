
/**
* A Sub Class of Hand for a Hand of Triple
 * @author Tejasvi Mehra
  *
 */
@SuppressWarnings("serial")

public class Triple extends Hand
{
	/**
	 * Assign the Cards and Player
	 * @param player Player
	 * @param cards List of Cards
	 */
	public Triple(CardGamePlayer player, CardList cards) 
	{
		super(player, cards);
	}

	/**
	 * Check the List of Cards for a Hand of Triple
	 * @return True if valid hand else False
	 */
	public boolean isValid()
	{
			int rank1,rank2,rank3;
			rank1=this.getCard(0).getRank();
			rank2=this.getCard(1).getRank();
			rank3=this.getCard(2).getRank();

			if(rank1==rank2 && rank2==rank3)
				return true;
			else
				return false;
	}
	
	/**
	 * Return the name of the class in String Format
	 * @return Class Name
	 */
	public String getType()
	{
		return "Triple";
	}
}
