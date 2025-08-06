/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A concrete class that implements the War card game.
 * This class manages the game flow, including deck creation, dealing,
 * round comparison, war resolution, and determining the winner.
 *
 * @author Prabmehak Singh
 * @author Suman Dhungel
 * @author Hammed Seehar
 * @author Chris Jude Stellus August 2025
 */
public class WarGame extends Game {

  private static final int MAX_ROUNDS = 10; // Prevent infinite games
  private GroupOfCards deck; // The main deck of 52 cards
  private WarPlayer player1;
  private WarPlayer player2;
  private Scanner scanner;

  /**
   * Constructs a WarGame with a given name.
   * Initializes the main deck and sets up the players.
   *
   * @param name The name of the game (e.g., "War").
   */
  public WarGame(String name) {
    super(name);
    this.deck = new GroupOfCards(52); // Standard 52-card deck
    this.scanner = new Scanner(System.in);
    initializePlayers();
    generateDeck();
  }

  /**
   * Initializes the two players for the game.
   * Prompts the user for player names.
   */
  private void initializePlayers() {
    System.out.println("Welcome to the Game of War!");
    System.out.print("Enter name for Player 1: ");
    String name1 = scanner.nextLine();
    this.player1 = new WarPlayer(name1);

    System.out.print("Enter name for Player 2: ");
    String name2 = scanner.nextLine();
    this.player2 = new WarPlayer(name2);

    // Add players to the game's player list
    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    setPlayers(players);
  }

  /**
   * Generates a complete 52-card deck and shuffles it.
   */
  private void generateDeck() {
    for (StandardCard.Suit suit : StandardCard.Suit.values()) {
      for (StandardCard.Rank rank : StandardCard.Rank.values()) {
        deck.addCard(new StandardCard(suit, rank));
      }
    }
    deck.shuffle();
    System.out.println("Deck generated and shuffled.");

    // printing deck
    System.out.println(deck.getCards());
    System.out.println("Total cards in the deck " + deck.getCardCount());
    System.out.println();
  }

  /**
   * Deals cards evenly to both players.
   * Each player gets half of the deck (26 cards each).
   */
  private void dealCards() {
    int cardsPerPlayer = deck.getSize() / getPlayers().size();
    for (int i = 0; i < cardsPerPlayer; i++) {
      player1.addCard(deck.removeTopCard());
      player2.addCard(deck.removeTopCard());
    }
    System.out.println("Cards dealt to " + player1.getName() + " and " + player2.getName() + ".");
    System.out.println(player1.getName() + " has " + player1.getHand().getCardCount() + " cards.");
    System.out.println(player2.getName() + " has " + player2.getHand().getCardCount() + " cards.");
  }

  /**
   * The main game loop for War.
   * Continues playing rounds until one player runs out of cards.
   */
  @Override
  public void play() {
    dealCards(); // Deal cards at the start of the game

    int round = 1;
    while (player1.hasCards() && player2.hasCards() && round <= MAX_ROUNDS) {
      System.out.println("\n--- Round " + round + " ---");
      System.out.println(player1.getName() + " cards: " + player1.getHand().getCardCount());
      System.out.println(player2.getName() + " cards: " + player2.getHand().getCardCount());
      System.out.println("Press Enter to play the next round...");
      scanner.nextLine(); // Wait for user to press Enter

      List<Card> playedCards = new ArrayList<>();
      StandardCard card1 = player1.drawCard();
      StandardCard card2 = player2.drawCard();

      if (card1 == null || card2 == null) {
        // Defensive check for robustness
        System.out.println("One player ran out of cards during the draw. Ending game.");
        break;
      }

      playedCards.add(card1);
      playedCards.add(card2);

      System.out.println(player1.getName() + " plays: " + card1);
      System.out.println(player2.getName() + " plays: " + card2);

      int comparison = Integer.compare(card1.getValue(), card2.getValue());
      if (comparison > 0) {
        System.out.println(player1.getName() + " wins the round!");
        player1.addCardsToBottom(playedCards);
      } else if (comparison < 0) {
        System.out.println(player2.getName() + " wins the round!");
        player2.addCardsToBottom(playedCards);
      } else {
        System.out.println("It's a tie! Time for WAR!");
        resolveWar(playedCards);
      }

      round++;
    }

    declareWinner();
  }

  /**
   * Resolves a "war" scenario when both players play cards of equal rank.
   * Each player places 3 cards face-down and then 1 card face-up.
   * The face-up cards are then compared.
   *
   * @param currentPrizePile The cards accumulated before the war.
   */
  private void resolveWar(List<Card> currentPrizePile) {
    // Check if players have enough cards for war (need 4 cards: 3 face-down + 1
    // face-up)
    if (player1.getHand().getCardCount() < 4) {
      System.out.println(
          player1.getName() + " does not have enough cards for war. " + player2.getName() + " wins by default!");
      player2.addCardsToBottom(currentPrizePile); // Player 2 gets current pile
      // Player 2 gets all remaining cards from Player 1
      while (player1.hasCards()) {
        player2.addCard(player1.drawCard());
      }
      return;
    }
    if (player2.getHand().getCardCount() < 4) {
      System.out.println(player2.getName() + " does not have enough cards for war!");
      System.out.println(player1.getName() + " wins by default!");
      System.out.println("\nTransferring cards:");
      System.out.println("  Prize pile (" + currentPrizePile.size() + " cards): " + currentPrizePile);

      player1.addCardsToBottom(currentPrizePile); // Player 1 gets current pile

      // Player 1 gets all remaining cards from Player 2
      System.out.print("  " + player2.getName() + "'s remaining cards: ");
      List<Card> remainingCards = new ArrayList<>();
      while (player2.hasCards()) {
        Card card = player2.drawCard();
        remainingCards.add(card);
        player1.addCard(card);
      }
      System.out.println(remainingCards);
      System.out.println("  Total cards transferred to " + player1.getName() + ": " +
          (currentPrizePile.size() + remainingCards.size()));
      return;
    }

    // Both players have enough cards for war, proceed
    List<Card> warCards = new ArrayList<>(currentPrizePile);

    System.out.println();
    System.out.println("WAR! Each player places draw 4 cards where they hide 3 and show 1.");

    System.out.println();
    System.out.println(player1.getName() + " has ");
    System.out.println();
    // Each player draws 3 cards and hides it
    for (int i = 0; i < 3; i++) {
      Card c = player1.drawCard();
      warCards.add(c);
      System.out.println(c + " (hidden)");
    }

    System.out.println("___________________________");

    System.out.println();
    System.out.println(player2.getName() + " has ");
    System.out.println();

    for (int i = 0; i < 3; i++) {
      Card d = player2.drawCard();
      warCards.add(d);
      System.out.println(d + " (hidden)");
    }
    // Each player draws 1 more card to compare
    StandardCard warCard1 = player1.drawCard();
    StandardCard warCard2 = player2.drawCard();
    warCards.add(warCard1);
    warCards.add(warCard2);

    System.out.println();
    System.out.println(player1.getName() + " war card: " + warCard1 + " (shown)");
    System.out.println(player2.getName() + " war card: " + warCard2 + " (shown)");
    System.out.println();

    int warComparison = Integer.compare(warCard1.getValue(), warCard2.getValue());
    if (warComparison > 0) {
      System.out.println(player1.getName() + " wins the war!");
      player1.addCardsToBottom(warCards);
    } else if (warComparison < 0) {
      System.out.println(player2.getName() + " wins the war!");
      player2.addCardsToBottom(warCards);
    } else {
      System.out.println("Another tie! War continues!");
      resolveWar(warCards); // Recursive war
    }
  }

  /**
   * Declares the winner based on who has cards remaining or who has more cards if
   * max rounds reached.
   */
  @Override
  public void declareWinner() {
    System.out.println("\n=== GAME OVER ===");
    if (player1.hasCards() && player2.hasCards()) {
      // Game ended due to max rounds - determine winner by card count
      int p1Cards = player1.getHand().getCardCount();
      int p2Cards = player2.getHand().getCardCount();
      if (p1Cards > p2Cards) {
        System.out
            .println("*** " + player1.getName() + " WINS by having more cards after " + MAX_ROUNDS + " rounds! ***");
        System.out.println(player1.getName() + " has " + p1Cards + " cards.");
        System.out.println(player2.getName() + " has " + p2Cards + " cards.");
      } else if (p2Cards > p1Cards) {
        System.out
            .println("*** " + player2.getName() + " WINS by having more cards after " + MAX_ROUNDS + " rounds! ***");
        System.out.println(player2.getName() + " has " + p2Cards + " cards.");
        System.out.println(player1.getName() + " has " + p1Cards + " cards.");
      } else {
        System.out.println("It's a tie! Both players have " + p1Cards + " cards after " + MAX_ROUNDS + " rounds.");
      }
    } else if (player1.hasCards()) {
      System.out.println("*** " + player1.getName() + " WINS THE GAME! ***");
      System.out.println(player1.getName() + " wins the game with " + player1.getHand().getCardCount() + " cards!");
      System.out.println(player2.getName() + " finished with " + player2.getHand().getCardCount() + " cards.");
    } else if (player2.hasCards()) {
      System.out.println("*** " + player2.getName() + " WINS THE GAME! ***");
      System.out.println(player2.getName() + " wins the game with " + player2.getHand().getCardCount() + " cards!");
      System.out.println(player1.getName() + " finished with " + player1.getHand().getCardCount() + " cards.");
    } else {
      System.out.println("It's a tie! Both players ran out of cards simultaneously.");
    }
  }

  /**
   * Main method to start the War game.
   * Creates a new WarGame instance and begins gameplay.
   *
   * @param args Command line arguments (not used).
   */
  public static void main(String[] args) {
    WarGame game = new WarGame("War");
    game.play();
  }
}
