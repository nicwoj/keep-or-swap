package card;

/**
 * <p>Title: The Hand Class</p>
 *
 * <p>Description: Has the ability to create a Hand capable of storing
 * cards. Contains methods capable of adding and removing cards from the
 * Hand. Can calculate the total point value of the cards in the Hand.</p>
 * 
 */
public class Hand extends ArrayStack<Card>
{
	private Card[] contents;
	private int top;
	
	/**
	 * default constructor --
	 * Creates an empty hand capable of storing at most 52 items.
	 */
	public Hand()
	{
		contents = (Card[]) (new Card[52]);
		top = -1;
	}
	
	/**
	 * add method --
	 * Stores a new item on the top of the hand
	 * @param theCard, a reference to the item to be stored on the top of the hand
	 */
	public void add(Card theCard)
	{
		top++;
		contents[top] = theCard;
	}
	
	/**
	 * remove method --
	 * Removes the top-most item from the hand.
	 * @return a reference to the item which was stored on top of the hand
	 * @throws EmptyDeckException if the hand is empty
	 */
	public Card remove() throws EmptyDeckException
	{
		if(top == -1)
			throw new EmptyDeckException("The hand is empty.");
		
		Card theCard = contents[top];
		contents[top] = null;
		top--;
		
		return theCard;	
	}
	
	/**
	 * peek method --
	 * Returns the top-most item on the hand without removing it
	 * @return a reference to the item which is currently stored on top of the hand.
	 * @throws EmptyDeckException if the hand is empty
	 */ 
	public Card peek() throws EmptyDeckException
	{
		if(top == -1)
			throw new EmptyDeckException("The hand is empty.");	
		
		return contents[top];
	}
	
	/**
	 * getPointTotal method --
	 * Calculates the total points in the hand
	 * @return the total points
	 */
	public int getPointTotal()
	{
		int total = 0;
		
		for(int i = top; i > -1; i--)
			total+= contents[i].getPointValue();
		
		return total;
	}
	
	/**
	 * isEmpty method --
	 * Determines whether or not the hand is empty.
	 * @return true if the hand is empty; false if the hand is not empty
	 */
	public boolean isEmpty()
	{
		return top == -1;
	}
	
	/**
	 * toString method --
	 * Returns a String representing the state of the hand
	 * @return a string containing all items in the hand
	 */
	public String toString()
	{
		String str = "";
		
		for(int i = top; i > -1; i--)
			str+= contents[i].toString() + "\n";
		
		return str;
	}
}