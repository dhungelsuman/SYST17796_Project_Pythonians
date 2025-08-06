package ca.sheridancollege.project;

/**
 * A concrete class representing a standard playing card with a suit and rank.
 * This class extends the abstract Card class and provides specific
 * implementation for a standard 52-card deck.
 *
 * @author Prabmehak Singh
 * @author Suman Dhungel
 * @author Hammed Seehar
 * @author Chris Jude Stellus Aug 2025
 */
public class StandardCard extends Card {

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

     public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private final Suit suit;
    private final Rank rank;

    /**
     * Constructs a StandardCard with the given suit and rank.
     *
     * @param suit The suit of the card (e.g., HEARTS, DIAMONDS).
     * @param rank The rank of the card (e.g., ACE, KING, TWO).
     */
    public StandardCard(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return The suit of the card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return The rank of the card.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the numerical value of the card's rank, used for comparison in War.
     *
     * @return The integer value of the card's rank.
     */
    public int getValue() {
        return rank.getValue();
    }

    /**
     * Returns a string representation of the card (e.g., "ACE of SPADES").
     *
     * @return A string describing the card.
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}