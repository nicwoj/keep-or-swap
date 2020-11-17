package card;

/**
 * <p>Title: The DiscardPile Class</p>
 *
 * <p>Description: Has the ability to create a DiscardPile capable of storing
 * cards. Contains methods capable of adding and removing cards from the
 * DiscardPile.</p>
 */
public class DiscardPile extends ArrayStack<Card>
{
	private Card[] contents;
	private int top;
	
	/**
	 * default constructor --
	 * Creates an empty pile capable of storing at most 52 items.
	 */
	public DiscardPile()
	{
		contents = (Card[]) (new Card[52]);
		top = -1;
	}
	
	/**
	 * add method --
	 * Stores a new item on the top of the pile
	 * @param theCard, a reference to the item to be stored on the top of the pile
	 */
	public void add(Card theCard)
	{
		top++;
		contents[top] = theCard;
	}
	
	/**
	 * remove method --
	 * Removes the top-most item from the pile.
	 * @return a reference to the item which was stored on top of the pile
	 * @throws EmptyDeckException if the pile is empty
	 */
	public Card remove() throws EmptyDeckException
	{
		if(top == -1)
			throw new EmptyDeckException("The discard pile is empty.");
		
		Card theCard = contents[top];
		contents[top] = null;
		top--;
		
		return theCard;	
	}
	
	/**
	 * peek method --
	 * Returns the top-most item on the pile without removing it
	 * @return a reference to the item which is currently stored on top of the pile.
	 * @throws EmptyDeckException if the pile is empty
	 */ 
	public Card peek() throws EmptyDeckException
	{
		if(top == -1)
			throw new EmptyDeckException("The discard pile is empty.");	
		
		return contents[top];
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
	 * Returns a String representing the state of the pile
	 * @return a string containing all items in the pile
	 */
	public String toString()
	{
		String str = "";
		
		for(int i = top; i > -1; i--)
			str+= contents[i].toString() + "\n";
		
		return str;
	}
}