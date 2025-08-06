/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;
import java.util.List;

/**
 * A concrete class representing a player in the War card game.
 * Each WarPlayer has a name and a deck (GroupOfCards) of cards.
 *
 * @author Prabmehak Singh
 * @author Suman Dhungel
 * @author Hammed Seehar
 * @author Chris Jude Stellus Aug2025
 */
public class WarPlayer extends Player {

    private GroupOfCards hand; // The player's deck/hand of cards

    /**
     * Constructs a WarPlayer with a given name.
     * Initializes the player's hand as an empty GroupOfCards.
     *
     * @param name The unique name of the player.
     */
    public WarPlayer(String name) {
        super(name);
        this.hand = new GroupOfCards(0); // Initialize with size 0, cards will be added later
    }

    /**
     * Returns the player's hand (deck) of cards.
     *
     * @return The GroupOfCards representing the player's deck.
     */
    public GroupOfCards getHand() {
        return hand;
    }

    /**
     * Sets the player's hand (deck) of cards.
     *
     * @param hand The GroupOfCards to set as the player's hand.
     */
    public void setHand(GroupOfCards hand) {
        this.hand = hand;
    }

    /**
     * Draws a single card from the top of the player's deck.
     *
     * @return The card drawn, or null if the deck is empty.
     */
    public StandardCard drawCard() {
        return (StandardCard) hand.removeTopCard();
    }

    /**
     * Adds a single card to the bottom of the player's deck.
     *
     * @param card The card to add.
     */
    public void addCard(Card card) {
        hand.addCard(card);
    }

    /**
     * Adds a list of cards to the bottom of the player's deck.
     * This is typically used when a player wins a round and collects cards.
     *
     * @param cardsToAdd The list of cards to add.
     */
    public void addCardsToBottom(List<Card> cardsToAdd) {
        hand.addCardsBottom(cardsToAdd);
    }

    /**
     * Checks if the player's deck is empty.
     *
     * @return true if the player has no cards, false otherwise.
     */
    public boolean hasCards() {
        return !hand.isEmpty();
    }

    /**
     * The play method for the WarPlayer. In a text-based War game,
     * this might simply involve drawing a card or indicating readiness.
     * For this implementation, the game logic in WarGame will handle
     * the "playing" actions.
     */
    @Override
    public void play() {
        // In this text-based game, the 'play' action is handled by the WarGame class
        // (e.g., drawing a card, participating in a war).
        // This method can be left empty or used for future extensions.
        System.out.println(getName() + " is ready to play!");
    }
}
