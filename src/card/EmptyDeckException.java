package card;

/**
 * <p>Title: The EmptyDeckException Class</p>
 *
 * <p>Description: Exception Class for use by Deck.</p>
 * 
 */
public class EmptyDeckException extends Exception //checked
{
	/**
	 * Parameterized constructor
	 */
	public EmptyDeckException(String msg)
	{
		super(msg);
	}
}
