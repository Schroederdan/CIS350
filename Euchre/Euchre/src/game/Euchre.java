package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Euchre {
	private Suits DIAMOND = Suits.Diamonds;
	private Suits HEART = Suits.Hearts;
	private Suits SPADE = Suits.Spades;
	private Suits CLUB = Suits.Clubs;
	private static Team TEAM1 = Team.Team1;
	private static Team TEAM2 = Team.Team2;
	private static Suits trump;
	public static Card a[] = new Card[5];
	public static Card b[] = new Card[5];
	public static Card c[] = new Card[5];
	public static Card d[] = new Card[5];
	public ArrayList<Card> deck = new ArrayList<Card>();
	static int t1Score;
	static int t2Score;
	public int cardCount;
	public static Player p1;
	public static Player p2;
	public static Player p3;
	public static Player p4;
	public Card played[] = new Card[4];
	Card turnUp;
	public boolean t1CallSuit,goAlone;
	int n;
	public Card highCard;
	Scanner reader = new Scanner(System.in); // Reading from System.in
	public static int t1Trick;
	public static int t2Trick;
	public Card Blank = new Card();
	String yorn;
	
	public ArrayList<Card> createDeck() {
		for (int i = 0; i <= 5; i++) {
			deck.add(new Card(i, DIAMOND));
			deck.add(new Card(i, HEART));
			deck.add(new Card(i, CLUB));
			deck.add(new Card(i, SPADE));
		}
		return deck;
	}

	public void printCards(ArrayList<Card> deck) {
		System.out.println(deck);
	}

	public static void main(String[] args) {
		Euchre game = new Euchre();
		ArrayList<Card> gameDeck = game.createDeck();
		game.printCards(gameDeck);
		t1Score = 0;
		t2Score = 0;

		p1 = new Player(TEAM1, a);
		p2 = new Player(TEAM2, b);
		p3 = new Player(TEAM1, c);
		p4 = new Player(TEAM2, d);

		game.shuffle(gameDeck);
		game.printCards(gameDeck);
		game.deal();
		// printHand(p1.getHand());
		// printHand(p2.getHand());
		// printHand(p3.getHand());
		// printHand(p4.getHand());
		game.playTrick(p1, p2, p3, p4, trump);
		System.out.println(t1Trick);
		System.out.println(t2Trick);

	}

	public void T1Point() {
		t1Score++;
	}

	public void T2Point() {
		t2Score++;
	}

	public boolean GameStatus() {
		if (t1Score >= 10)
		{
			System.out.println("Team 1 wins");
			return true;
		}
		else if (t2Score >= 10)
		{
			System.out.println("Team 2 wins");
			return true;
		}
		else
		{
			System.out.println("Team one score: " + t1Score + "\nTeam two score: " + t2Score);
			return false;
		}
	}

	public void shuffle(ArrayList<Card> deck) {
		Collections.shuffle(deck);
	}

	public boolean playable(Card Lead, Card Played, Card hand[]) { // also need
																	// users
		if(Played.getSuit()==null)
			return false;											// hand
		if (Lead.getSuit() == Played.getSuit())
			return true;
		else if (Lead.getSuit() != Played.getSuit()) {
			for (int i = 0; i < hand.length; i++) {
				if (Lead.getSuit() == hand[i].getSuit())
					return false;
			}
		}
		return true;
	}

	public void setTrump(Suits t) {
		trump = t;
	}

	public Card takeTrick(Card active[], Suits t) { // order played
		Card High;
		High = active[0];
		for (int i = 1; i < 4; i++) {
			if (High.getSuit() != active[i].getSuit()) {
				if (active[i].getSuit() == t) {
					High = active[i];
				}
			} else if (High.getCardName() < active[i].getCardName())
				High = active[i];
		}
		return High;
	}

	public void deal() {
		cardCount = 0;
		t1Trick = 0;
		t2Trick = 0;
		for (int i = 0; i < 5; i++) {
			p1.setCard(i, this.deck.get(cardCount));
			cardCount++;
			p2.setCard(i, this.deck.get(cardCount));
			cardCount++;
			p3.setCard(i, this.deck.get(cardCount));
			cardCount++;
			p4.setCard(i, this.deck.get(cardCount));
			cardCount++;
		}
		turnUp = deck.get(cardCount);
		organizeHand(p1.getHand());
		organizeHand(p2.getHand());
		organizeHand(p3.getHand());
		organizeHand(p4.getHand());
		playTrick(p1,p2,p3,p4,trump);
	}

	public void organizeHand(Card hand[]) { // still need to move cards down
											// were just swapping cards right
											// now
		Card change;
		for (int i = 2; i < hand.length; i++) {
			for (int j = 0; j < i; j++) {
				if (hand[i].getSuit() == hand[j].getSuit()) {
					if (j + 1 < 5) {
						if (hand[i].getSuit() == hand[j + 1].getSuit()) {
							if (j + 2 < 5) {
								if (hand[i].getSuit() == hand[j + 2].getSuit()) {
									if (j + 3 < 5) {
										if (hand[i].getSuit() == hand[j + 3].getSuit()) {
											if (j + 4 < 5) {
												if (hand[i].getSuit() == hand[j + 3].getSuit()) {
													i = hand.length;
													j = i;
												} else if (hand[i].getSuit() != hand[j + 4].getSuit()) {
													change = hand[i];
													hand[i] = hand[j + 4];
													hand[j + 4] = change;
												}
											}
										} else if (hand[i].getSuit() != hand[j + 3].getSuit()) {
											change = hand[i];
											hand[i] = hand[j + 3];
											hand[j + 3] = change;
										}
									}
								} else if (hand[i].getSuit() != hand[j + 2].getSuit()) {
									change = hand[i];
									hand[i] = hand[j + 2];
									hand[j + 2] = change;
								}
							}
						} else if (hand[i].getSuit() != hand[j + 1].getSuit()) {
							change = hand[i];
							hand[i] = hand[j + 1];
							hand[j + 1] = change;
						}
					}
				}

			}
		}
		for (int i = 1; i < 5; i++) {
			for (int j = 0; j < i; j++) {
				if (hand[i].getSuit() == hand[j].getSuit()) {
					if (hand[i].getCardName() > hand[j].getCardName()) {
						change = hand[i];
						hand[i] = hand[j];
						hand[j] = change;
					}
				}
			}
		}
	}

	public void playTrick(Player pl1, Player pl2, Player pl3, Player pl4, Suits t) {

		printHand(pl1.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		played[0] = pl1.getCard(n);
		pl1.setCard(n, Blank);

		printHand(pl2.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while (playable(played[0], pl2.getCard(n), pl2.getHand()) == false) {
			System.out.println("not a valid card");
			printHand(pl2.getHand());
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[1] = pl2.getCard(n);
		pl2.setCard(n, Blank);

		printHand(pl3.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while (playable(played[0], pl3.getCard(n), pl3.getHand()) == false) {
			System.out.println("not a valid card");
			printHand(pl3.getHand());
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[2] = pl3.getCard(n);
		pl3.setCard(n, Blank);

		printHand(pl4.getHand());
		System.out.println("Play a Card: ");
		n = reader.nextInt();
		while (playable(played[0], pl4.getCard(n), pl4.getHand()) == false) {
			System.out.println("not a valid card");
			printHand(pl4.getHand());
			System.out.println("Play a Card: ");
			n = reader.nextInt();
		}
		played[3] = pl4.getCard(n);
		pl4.setCard(n, Blank);

		highCard = takeTrick(played, t);
		if (highCard == played[0] || highCard == played[2]) {
			if (pl1.getTeam() == TEAM1 || pl3.getTeam() == TEAM1)
				t1Trick++;
			else
				t2Trick++;
		} else {
			if (pl2.getTeam() == TEAM1 || pl4.getTeam() == TEAM1)
				t1Trick++;
			else
				t2Trick++;
		}
		if (t1Trick + t2Trick < 5) {
			if (highCard == played[0])
				playTrick(pl1, pl2, pl3, pl4, t);
			else if (highCard == played[1])
				playTrick(pl2, pl3, pl4, pl1, t);
			else if (highCard == played[2])
				playTrick(pl3, pl4, pl1, pl2, t);
			else
				playTrick(pl4, pl1, pl2, pl3, t);
		}
		else if(t1CallSuit)
		{
			
			if(t1Trick>=3 && t1Trick<5)
				T1Point();
			else if(t1Trick==5&&goAlone)
			{
				T1Point();
				T1Point();
				T1Point();
				T1Point();
			}
			else if(t1Trick==5)
			{
				T1Point();
				T1Point();
			}
			else
			{
				T2Point();
				T2Point();
			}
		}
		else
		{	
			if(t2Trick>=3 && t2Trick<5)
				T2Point();
			else if(t2Trick==5 && goAlone)
			{
				T2Point();
				T2Point();
				T2Point();
				T2Point();
			}
			else if(t2Trick==5)
			{
				T2Point();
				T2Point();
			}
			else
			{
				T1Point();
				T1Point();
			}
		}
		if(GameStatus())
		{
			System.out.println("Would you like to play again? (y) or (n)");
			yorn = reader.next();
			if(yorn== "y" || yorn == "Y" )
			{
				t1Score = 0;
				t2Score = 0;
				deal();
			}
		}
		else
		{
			t1Trick=0;
			t2Trick=0;
			deal();
		}
	}

	public static void printHand(Card hand[]) {
		System.out.println("\nyour hand is:");
		for (int i = 0; i < hand.length; i++) {
			System.out.print(i + ":");
			switch (hand[i].getCardName()) {
			case -1:
				break;
			case 0:
				System.out.println("9 of " + hand[i].getSuit());
				break;
			case 1:
				System.out.println("10 of " + hand[i].getSuit());
				break;
			case 2:
				System.out.println("Jack of " + hand[i].getSuit());
				break;
			case 3:
				System.out.println("Queen of " + hand[i].getSuit());
				break;
			case 4:
				System.out.println("King of " + hand[i].getSuit());
				break;
			case 5:
				System.out.println("Ace of " + hand[i].getSuit());
				break;
			}
		}
	}
}