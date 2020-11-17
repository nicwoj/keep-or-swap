package card;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * <p>Title: The App Class</p>
 *
 * <p>Description: Starts a game of Keep or Swap. The player is
 * prompted to enter a valid target to start the game. The Computer
 * and the Player alternate turns adding and removing cards from their
 * hands until the target is reached, a player goes over the target, or
 * the deck of cards becomes empty. Once the game ends, the winner is
 * displayed along with the cards in each hand.</p>
 */
public class App
{
	public static void main(String[] args) throws EmptyDeckException
	{
		int counter = 0;
		int target = 0;
		boolean valid = false;
		
		while(counter < 3 && valid == false)
		{
			try
			{
				target = Integer.parseInt(JOptionPane.showInputDialog("Please enter a target for the game greater than or equal to 25."));	
				
				if(target >= 25)
					valid = true;
				else
				{
					counter++;
					JOptionPane.showMessageDialog(null, "Not a valid target. Try again.", null, JOptionPane.ERROR_MESSAGE);
				}
				
				if(counter > 2)
				{
					JOptionPane.showMessageDialog(null, "You have failed to enter a valid target for the game. Program terminating...", null, JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
			catch(NumberFormatException ex)
			{
				counter++;
				JOptionPane.showMessageDialog(null, "Not a valid target. Try again.", null, JOptionPane.ERROR_MESSAGE);

				if(counter > 2)
				{
					JOptionPane.showMessageDialog(null, "You have failed to enter a valid target for the game. Program terminating...", null, JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
		}
			
		//if valid input, continue with game...
			
		Hand computer = new Hand();					//computer's hand
		Hand player = new Hand();					//user's hand
		DiscardPile thePile = new DiscardPile();	//the discard pile
		Deck theDeck = new Deck();					//the deck of cards
		Card theCard;								//keeps track of the card being drawn from the deck
		Card removedCard;							//keeps track of the card being removed
		Card topCard;								//keeps track of top card in the hand
		Card discardTop;							//keeps track of the top card in the discard pile
		int choice;									//keeps track of player's choice
		boolean done = false;						//keeps track of status of game
		Random rand = new Random();
		
		String[] options = new String[] {"Draw from deck", "Draw from discard pile"};
		
		theDeck.shuffleDeck();						//the cards are shuffled
		
		//TURN v1 (take from deck, discard pile is empty)
		theCard = theDeck.dealCard();	
		computer.add(theCard);			//the computer takes a card from the top of the deck
		JOptionPane.showMessageDialog(null, "--- COMPUTER'S TURN ---"
				+ "\nThe Computer has drawn a " + theCard.toString() + " from the deck.\nThere is nothing to discard."
				+ "\nThe Computer now has " + theCard.getPointValue() + " point(s).");
		
		theCard = theDeck.dealCard();	
		player.add(theCard);			//the player takes a card from the top of the deck
		JOptionPane.showMessageDialog(null, "--- PLAYER'S TURN ---"
				+ "\nThe Player has drawn a " + theCard.toString() + " from the deck.\nThere is nothing to discard."
				+ "\nThe Player now has " + theCard.getPointValue() + " point(s).");
		
		
		//TURN v2 (take from deck, may discard at end of turn)
		while(done == false && !theDeck.isEmpty())		//while the target hasn't been reached and the deck isn't empty
		{
			//begin COMPUTER'S TURN
			topCard = computer.peek();
			if(thePile.isEmpty()) //there is NO discard pile
			{
				JOptionPane.showMessageDialog(null, "--- COMPUTER'S TURN ---"
						+ "\nThe Computer has " + computer.getPointTotal() + " point(s)"
						+ "\nThe top card in the computer's hand is a " + topCard.toString() 
						+ "\nThere is nothing in the discard pile. The Computer must draw from the deck.");
				theCard = theDeck.dealCard();
				JOptionPane.showMessageDialog(null, "--- COMPUTER'S TURN ---"
						+ "\nThe Computer has drawn a " + theCard.toString() + " from the deck.");
			}
			else	//there is a discard pile
			{
				discardTop = thePile.peek();
				JOptionPane.showMessageDialog(null, "--- COMPUTER'S TURN ---"
						+ "\nThe Computer has " + computer.getPointTotal() + " point(s)"
						+ "\nThe top card in the Computer's hand is a " + topCard.toString()
						+ "\nThe top card in the discard pile is a " + discardTop.toString()
						+ "\nWill the Computer draw from the deck or from the discard pile?");
				choice = rand.nextInt(2);
				if(choice == 0)	//Draw from deck
				{
					theCard = theDeck.dealCard();
					JOptionPane.showMessageDialog(null, "--- COMPUTER'S TURN ---"
							+ "\nThe Computer has drawn a " + theCard.toString() + " from the deck.");
				}
				else //Draw from discard pile
				{
					theCard = thePile.peek(); 	//retrieve the card being drawn
					thePile.remove();			//remove the drawn card from the discard pile
					JOptionPane.showMessageDialog(null, "--- COMPUTER'S TURN ---"
							+ "\nThe Computer has drawn a " + theCard.toString() + " from the discard pile.");
				}
			}

			//will the computer discard its top-most card? 
			if(topCard.getPointValue() <= 3 || (computer.getPointTotal() + theCard.getPointValue() > target))	//computer discards
			{
				removedCard = computer.remove();	//the computer chooses to remove the top of its hand and add it to the pile
				thePile.add(removedCard);			//the removed card is added to the discard pile
				computer.add(theCard);				//the drawn card is added
				JOptionPane.showMessageDialog(null, "--- COMPUTER'S TURN ---"
						+ "\nWill the Computer keep or discard its top-most card?"
						+ "\nThe Computer has chosen to discard its top-most card."
						+ "\nThe Computer now has " + computer.getPointTotal() + " point(s)");
			}
			//the computer adds and chooses not to discard
			else
			{
				computer.add(theCard);				//the drawn card is added
				JOptionPane.showMessageDialog(null, "--- COMPUTER'S TURN ---"
						+ "\nWill the Computer keep or discard its top-most card?"
						+ "\nThe Computer has chosen to keep its top-most card."
						+ "\nThe Computer now has " + computer.getPointTotal() + " point(s)");
			}
			//check if computer's/player's total is equal to the target.
			if(computer.getPointTotal() == target)
			{
				JOptionPane.showMessageDialog(null, "--- GAME OVER ---"
						+ "\n\nThe Computer wins because it has hit the target."
						+ "\n\nThe Computer's Hand:\n" + computer.toString() + "The Computer's point total: " + computer.getPointTotal()
						+ "\n\nThe Player's Hand:\n" + player.toString() + "The Player's point total: " + player.getPointTotal());
				done = true;
				break;
			}
			if(computer.getPointTotal() > target)
			{
				JOptionPane.showMessageDialog(null, "--- GAME OVER ---"
						+ "\n\nThe Player wins because the Computer has gone over the target."
						+ "\n\nThe Computer's Hand:\n" + computer.toString() + "The Computer's point total: " + computer.getPointTotal()
						+ "\n\nThe Player's Hand:\n" + player.toString() + "The Player's point total: " + player.getPointTotal());
				done = true;
				break;
			}
			//end COMPUTER'S TURN

			//begin PLAYER'S TURN
			topCard = player.peek();
			if(thePile.isEmpty()) //there is NO discard pile
			{
				JOptionPane.showMessageDialog(null, "--- PLAYER'S TURN ---"
						+ "\nThe Player has " + player.getPointTotal() + " point(s)"
						+ "\nThe top card in the Player's hand is a " + topCard.toString() 
						+ "\nThere is nothing in the discard pile. The Player must draw from the deck.");
				theCard = theDeck.dealCard();
				JOptionPane.showMessageDialog(null, "--- PLAYER'S TURN ---"
						+ "\nThe Player has drawn a " + theCard.toString() + " from the deck.");
			}
			else	//there is a discard pile
			{
				discardTop = thePile.peek();
				JOptionPane.showMessageDialog(null, "--- PLAYER'S TURN ---"
						+ "\nThe Player has " + player.getPointTotal() + " point(s)"
						+ "\nThe top card in the Player's hand is a " + topCard.toString()
						+ "\nThe top card in the discard pile is a " + discardTop.toString());
				choice = JOptionPane.showOptionDialog(null, "Do you want to draw from the deck or the discard pile?", "Message",
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options, options[0]);
				if(choice == 0)	//Draw from deck
				{
					theCard = theDeck.dealCard();
					JOptionPane.showMessageDialog(null, "--- PLAYER'S TURN ---"
							+ "\nThe Player has drawn a " + theCard.toString() + " from the deck.");
				}
				if(choice == 1)	//Draw from discard pile
				{
					theCard = thePile.peek(); 	//retrieve the card being drawn
					thePile.remove();			//remove the drawn card from the discard pile
					JOptionPane.showMessageDialog(null, "--- PLAYER'S TURN ---"
							+ "\nThe Player has drawn a " + theCard.toString() + " from the discard pile.");
				}
			}

			//will the player discard?
			choice = JOptionPane.showConfirmDialog(null, "Do you want to discard the top-most card in your hand?", null, JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION) //the player chooses to discard
			{
				removedCard = player.remove();		//the player removes the top of its hand and adds it to the pile
				thePile.add(removedCard);			//the removed card is added to the discard pile
				player.add(theCard);				//the drawn card is added

				JOptionPane.showMessageDialog(null, "--- PLAYER'S TURN ---"
						+ "\nThe Player has chosen to discard its top-most card."
						+ "\nThe Player now has " + player.getPointTotal() + " point(s)");
			}
			else	//the player adds and chooses not to discard
			{
				player.add(theCard);				//the drawn card is added
				JOptionPane.showMessageDialog(null, "--- PLAYER'S TURN ---"
						+ "\nThe Player has chosen to keep its top-most card."
						+ "\nThe Player now has " + player.getPointTotal() + " point(s)");
			}
			if(player.getPointTotal() == target)
			{
				JOptionPane.showMessageDialog(null, "--- GAME OVER ---"
						+ "\n\nThe Player wins because it has hit the target."
						+ "\n\nThe Computer's Hand:\n" + computer.toString() + "The Computer's point total: " + computer.getPointTotal()
						+ "\n\nThe Player's Hand:\n" + player.toString() + "The Player's point total: " + player.getPointTotal());
				done = true;
				break;
			}
			if(player.getPointTotal() > target)
			{
				JOptionPane.showMessageDialog(null, "--- GAME OVER ---"
						+ "\n\nThe Computer wins because the Player has gone over the target."
						+ "\n\nThe Computer's Hand:\n" + computer.toString() + "The Computer's point total: " + computer.getPointTotal()
						+ "\n\nThe Player's Hand:\n" + player.toString() + "The Player's point total: " + player.getPointTotal());
				done = true;
				break;
			}
			//end PLAYER'S TURN
		}
		
	
		if(theDeck.isEmpty()) //if the deck becomes empty before either player wins, then the player whose total is closest to the target wins
		{
			JOptionPane.showMessageDialog(null, "--- GAME OVER ---\nThe deck is empty...");
			
			int compDistance = target - computer.getPointTotal();
			int playDistance = target - player.getPointTotal();
			
			if(compDistance > playDistance)
			{
				JOptionPane.showMessageDialog(null, "--- GAME OVER ---"
						+ "\n\nThe Player wins because it is closer to the target."
						+ "\n\nThe Computer's Hand:\n" + computer.toString() + "The Computer's point total: " + computer.getPointTotal()
						+ "\n\nThe Player's Hand:\n" + player.toString() + "The Player's point total: " + player.getPointTotal());
			}
			else if(playDistance > compDistance)
			{
				JOptionPane.showMessageDialog(null, "--- GAME OVER ---"
						+ "\n\nThe Computer wins because it is closer to the target."
						+ "\n\nThe Computer's Hand:\n" + computer.toString() + "The Computer's point total: " + computer.getPointTotal()
						+ "\n\nThe Player's Hand:\n" + player.toString() + "The Player's point total: " + player.getPointTotal());
			}
			else	//if there is a tie at this point, then the player with the least number of cards in their hand wins
			{
				//loop through Hand, pop cards to count them. Re-add cards from temp hands
				int computerCount = 0;
				int playerCount = 0;
				Hand compTemp = new Hand();
				Hand playTemp = new Hand();
				
				while(!computer.isEmpty())
				{
					computerCount++;
					compTemp.add(computer.remove());
				}
				while(!player.isEmpty())
				{
					playerCount++;
					playTemp.add(player.remove());
				}
				
				while(!compTemp.isEmpty())	//re-adding the cards
				{
					computer.add(compTemp.remove());
				}
				while(!playTemp.isEmpty())
				{
					player.add(playTemp.remove());
				}

				if(computerCount > playerCount)
				{
					JOptionPane.showMessageDialog(null, "--- GAME OVER ---"
							+ "\n\nThere is a tie."
							+ "\n\nThe Player wins because it has less cards."
							+ "\n\nThe Computer's Hand:\n" + computer.toString() + "The Computer's point total: " + computer.getPointTotal()
							+ "\n\nThe Player's Hand:\n" + player.toString() + "The Player's point total: " + player.getPointTotal());
				}
				else
				{
					JOptionPane.showMessageDialog(null, "--- GAME OVER ---"
							+ "\n\nThere is a tie."
							+ "\n\nThe Computer wins because it has less cards."
							+ "\n\nThe Computer's Hand:\n" + computer.toString() + "The Computer's point total: " + computer.getPointTotal()
							+ "\n\nThe Player's Hand:\n" + player.toString() + "The Player's point total: " + player.getPointTotal());
				}
			}
		}		
	}
}
