/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A concrete class that represents any grouping of cards for a Game. HINT, you
 * might want to subclass this more than
 * once. The group of cards has a maximum size attribute which is flexible for
 * reuse.
 * 
 * @author Prabmehak Singh
 * @author Suman Dhungel
 * @author Hammed Seehar
 * @author Chris Jude Stellus Aug 2025
 */
public class GroupOfCards {

    // The group of cards, stored in an ArrayList
    private ArrayList<Card> cards;
    private int size;// the size of the grouping

    public GroupOfCards(int size) {
        this.size = size;
        this.cards = new ArrayList<>();
    }

    /**
     * A method that will get the group of cards as an ArrayList
     *
     * @return the group of cards.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * @return the size of the group of cards
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the current number of cards in the group
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * @param size the max size for the group of cards
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Adds a single card to the top (end) of the group.
     *
     * @param card The card to add.
     */
    public void addCard(Card card) {
        if (card != null) {
            this.cards.add(card);
        }
    }

    /**
     * Adds a collection of cards to the bottom (end) of the group.
     * This is useful for adding won cards to a player's deck.
     *
     * @param cardsToAdd The list of cards to add.
     */
    public void addCardsBottom(List<Card> cardsToAdd) {
        if (cardsToAdd != null && !cardsToAdd.isEmpty()) {
            this.cards.addAll(cardsToAdd); //bulk addition
        }
    }

    /**
     * Removes and returns the top card from the group.
     *
     * @return The top card, or null if the group is empty.
     */
    public Card removeTopCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0); // Remove from the "top" of the deck
        }
        return null;
    }

    /**
     * Checks if the group of cards is empty.
     *
     * @return true if the group has no cards, false otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
