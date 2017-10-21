import java.util.Arrays;

/**
* The Hand class is a subclass of the CardList class, and is used to model a hand of cards
 * @author Tejasvi Mehra
 *
 */
@SuppressWarnings("serial")
public abstract class Hand extends CardList
{
	private CardGamePlayer player; //the player who plays this hand
	
	/**
	 * a constructor for building a hand with the specified player and list of cards
	 * @param player Specified Player
	 * @param cards List of Cards
	 */
	public Hand(CardGamePlayer player, CardList cards)
	{
		this.player = player;	
		cards = player.getCardsInHand();	
	}
	
	/**
	 * a method for retrieving the player of this hand
	 * @return Player of this hand
	 */
	public CardGamePlayer getPlayer()
	{
		return player;
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 * @return the Top Card
	 */
	public Card getTopCard()
	{
		this.sort();
		if(this.getType() == "Single")
		{
			return this.getCard(0);
		}
		else if(this.getType() == "Pair")
		{
			return this.getCard(1);
		}
		else if (this.getType() == "Triple")
		{
			return this.getCard(2);
		}
		else if(this.getType()=="FullHouse")
		{
			return this.getCard(2);
		}
		else if(this.getType()=="Quad")
		{
			return this.getCard(2);
		}
		else
		{
			int[] ranks = new int[5];
			for(int i=0;i<5;i++)
			{
				if(this.getCard(i).getRank()<=1)
				{
					ranks[i]=this.getCard(i).getRank()+13;
				}
				else
					ranks[i]=this.getCard(i).getRank();
			}
			Arrays.sort(ranks);
			if(ranks[4]>=13)
				ranks[4]-=13;
			int i;
			for(i=0;i<5;i++)
			{
				if(this.getCard(i).getRank()==ranks[4])
				{
					break;
				}
			}
			return this.getCard(i);
		}	
	}
	
	/**
	 * a method for checking if this hand beats a specified hand
	 * @param hand Specific Hand
	 * @return True if beats else False
	 */
	public boolean beats(Hand hand)
	{
		String type1 = this.getType();
		String type2 = hand.getType();
		
		String order[] = {"Single", "Pair", "Triple", "Straight", "Flush", "FullHouse", "Quad", "StraightFlush"};
		int a=0;
		int b=0;
		for(int i=0;i<8;i++)
		{
			if(type1.equals(order[i]))
			{
				a=i;
			}
			if(type2.equals(order[i]))
			{
				b=i;
			}
		}
		if(a>b)
		{
			return true;
		}
		else if(a<b)
		{
			return false;
		}
		else
		{
			if(this.getTopCard().compareTo(hand.getTopCard()) == 1)
			{
				return true;
			}
			else
				return false;
		}
	}
	
	/**
	 * a method for checking if this is a valid hand
	 * @return Not Used Here
	 */
	public abstract boolean isValid();
	/**
	 * a method for returning a string specifying the type of this hand
	 * @return Not Used Here
	 */
	public abstract String getType();
	
	
}
