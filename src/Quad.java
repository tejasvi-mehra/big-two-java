/**

 * A Sub Class of Hand for a Hand of Quad
 * @author Tejasvi Mehra
 * 
 */
@SuppressWarnings("serial")

public class Quad extends Hand
{
	/**
	 * Assign the Cards and Player
	 * @param player Player
	 * @param cards List of Cards
	 */
	public Quad(CardGamePlayer player, CardList cards) 
	{
		super(player, cards);	
	}

	/**
	 * Check the List of Cards for a Hand of Quad
	 * @return True if valid hand else False
	 */
	public boolean isValid()
	{
		this.sort();
		int rank1,rank2,rank3,rank4,rank5;
		rank1=this.getCard(0).getRank();
		rank2=this.getCard(1).getRank();
		rank3=this.getCard(2).getRank();
		rank4=this.getCard(3).getRank();
		rank5=this.getCard(4).getRank();

		if(rank1==rank2 && rank2==rank3 && rank3==rank4)
			return true;
		else if(rank2==rank3 && rank3==rank4 && rank4==rank5)
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
		return "Quad";
	}

}
