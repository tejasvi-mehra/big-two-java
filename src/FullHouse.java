/**
 * 
 * A Sub Class of Hand for a Hand of FullHouse
 * @author Tejasvi Mehra *
 */
@SuppressWarnings("serial")


public class FullHouse extends Hand
{
	/**
	 * Assign the Cards and Player
	 * @param player Player
	 * @param cards List of Cards
	 */
	public FullHouse(CardGamePlayer player, CardList cards) 
	{
		super(player, cards);
	}

	/**
	 * Check the List of Cards for a Hand of FullHouse
	 * @return True if valid hand else False
	 */
	public boolean isValid()
	{
			this.sort();
			if((this.getCard(0).getRank()==this.getCard(1).getRank()) && this.getCard(1).getRank()==this.getCard(2).getRank())
			{
				if(this.getCard(3).getRank() == this.getCard(4).getRank())
					return true;
				else
					return false;
			}
			else if(this.getCard(2).getRank() == this.getCard(3).getRank() && this.getCard(3).getRank() == this.getCard(4).getRank())
			{
				if(this.getCard(0).getRank() == this.getCard(1).getRank())
					return true;
				else
					return false;
			}
			else
				return false;
	}
	/**
	 * Return the name of the class in String Format
	 * @return Class Name
	 */
	public String getType()
	{
		return "FullHouse";
	}
}
