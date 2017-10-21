
/**
 * The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a Big
 * Two card game
 * @author Tejasvi Mehra
 *
 */
@SuppressWarnings("serial")
public class BigTwoCard extends Card 
{
	/**
	 * a constructor for building a card with the specified suit and rank
	 * @param suit Suit of Card
	 * @param rank rank Of Card
	 */
	public BigTwoCard(int suit, int rank)
	{
			super(suit,rank);
		
	}
	
	/**
	 * a method for comparing this card with the specified card for order
	 * @return  a negative integer, zero, or a positive integer as this card is less than, equal
	 * to, or greater than the specified card
	 */
	public int compareTo(Card card) 
	{
		boolean check=false;
		if(this.rank == 0 || this.rank == 1 || card.rank == 0 || card.rank == 1)
		{
			if (this.rank == 1 && card.rank != 1)
			{
				return 1;
			}
			else if (this.rank == 0 && card.rank != 1 && card.rank != 0)
			{
				return 1;
			}
			else if (card.rank == 1 && this.rank != 1)
			{
				return -1;
			}
			else if (card.rank == 0 && this.rank != 1 && this.rank != 0)
			{
				return -1;
			}
			else
			{
				check = true;
			}
		}
		else if (this.rank > card.rank)
		{
			return 1;
		}
		else if (this.rank < card.rank) 
		{
			return -1;
		}
		else
		{
			check = true;
		}
		if(check == true)
		{
			if(this.suit < card.suit)
			{
				return -1;
			}
			else if(this.suit > card.suit)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}
}
