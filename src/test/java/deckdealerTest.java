import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class deckdealerTest {

	Deck testDeck;
	Dealer testDealer;
	ArrayList<Card> testHand;
	
	
	@BeforeEach
	void defaultStarts()
	{
		testDeck = new Deck();
		testDealer = new Dealer();
	}
	
	//---------------Deck Tests---------------------------------------------------------
	
	//Test Creating a default Deck 
	@Test 
	void createDeckTest()
	{
		assertEquals(52, testDeck.size(), "Deck was not created");
	}
	
	//Test the Deck comes with the right size 
	@Test
	void testSize()
	{	
		assertEquals(52, testDeck.size(), "Deck does not come in the right size");
	}
	
	//Test the Deck has 52 Different cards 
	@ParameterizedTest
	@ValueSource(chars = {'C', 'D', 'S', 'H'})
	void uniqueCardsDeck(char input)
	{
		int suitNum = 0;
		for(int i = 0; i < 52; i++)
		{
			if(testDeck.get(i).getSuit() == input)
			{
				suitNum++;
			}
		}
		
		assertEquals(13, suitNum, "There is not 52 Unique Cards in Deck");
	}
	
	//Test Deck Remove works 
	@Test 
	void removeDeck()
	{
		testDeck.remove(42);
		testDeck.remove(5);
		testDeck.remove(14);
			
		assertEquals(49, testDeck.size(), "remove did not work on deck");
	}
	

	//Test Create a New Deck 
	@Test
	void newDeckTest()
	{
		testDeck.remove(0);
		testDeck.remove(1);
		testDeck.remove(2);		
		
		testDeck.newDeck();
		
		assertEquals(52, testDeck.size(), "New Deck was not made");
	}
	
	//Test that the old deck size, when remove is used, is not the same size as a new deck
	@Test
	void oldnewDeckTest()
	{
		testDeck.remove(0);
		testDeck.remove(1);
		testDeck.remove(2);
		
		int a = testDeck.size();		
		
		testDeck.newDeck();
		
		int b = testDeck.size();
		
		assertNotEquals(a, b, "New and Old deck are the same Size");
	}
	

	//---------------Dealer Tests---------------------------------------------------------
	
	//Test that the Dealer creates a new Deck
	@Test
	void testDealerDeck()
	{
		assertEquals(52, testDealer.theDeck.size(), "Dealers Deck is not the right size");
	}
	
	//Test the Dealers Deck has 52 Different cards 
	@ParameterizedTest
	@ValueSource(chars = {'C', 'D', 'S', 'H'})
	void uniqueCardsDealer(char input)
	{
		int suitNum = 0;
		for(int i = 0; i < 52; i++)
		{
			if(testDealer.theDeck.get(i).getSuit() == input)
			{
				suitNum++;
			}
		}
			
		assertEquals(13, suitNum, "There is not 52 Unique Cards in the Dealers Deck");
	}
	
	//Test that the Dealer deals a hand of 3 cards 
	@Test 
	void testHandDealer()
	{	
		testHand = testDealer.dealHand();
		
		assertEquals(3, testHand.size(), "Hand is not 3 cards");
	}
	
	//Test that Dealing a Hand results in removing 3 cards from the deck 
	@Test 
	void testDeckHand()
	{	
		testHand = testDealer.dealHand();
		testHand = testDealer.dealHand();
		
		assertEquals(46, testDealer.theDeck.size(), "The Hand did not remove 6 cards from the deck");

	}
	
	//Test the dealers Hand stores a Hand
	@Test 
	void testDealersHand()
	{
		testDealer.dealersHand = testDealer.dealHand();
		
		assertEquals(3, testDealer.dealersHand.size(), "Dealer does not have a hand of 3");
		
	}
	
	//Check New deck is created if there is 34 cards and a hand is dealt 
	@Test 
	void newDeck34()
	{	
		testHand = testDealer.dealHand();
		testHand = testDealer.dealHand();
		testHand = testDealer.dealHand();
		
		testHand = testDealer.dealHand();
		testHand = testDealer.dealHand();
		testHand = testDealer.dealHand();
		
		testHand = testDealer.dealHand();

		assertEquals(49, testDealer.theDeck.size(), "A New Deack was not created when cards in deck are <= 34");
		
	}
	
}
