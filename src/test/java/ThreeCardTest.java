import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ThreeCardTest {
	ArrayList<Card> hand;
	ArrayList<Card> dealer;
	
	@BeforeEach
	void defaultStart()
	{
		hand = new ArrayList<Card>();
		dealer = new ArrayList<Card>();
	} 
	
//-------------Test Evaluate Hand/Winnings----------------------------	
	
	//Test Straight Flush 
	@ParameterizedTest
	@ValueSource(chars = {'C', 'D', 'S', 'H'})
	void testSF(char input) {
		
		hand.add(new Card(input, 12));
		hand.add(new Card(input, 13));
		hand.add(new Card(input, 11));
		
		assertEquals(1, ThreeCardLogic.evalHand(hand), "Is Not a Straight Flush");

	}

	@ParameterizedTest
	@ValueSource(chars = {'C', 'D', 'S', 'H'})
	void testSFW(char input) {
		
		hand.add(new Card(input, 6));
		hand.add(new Card(input, 7));
		hand.add(new Card(input, 5));
		
		assertEquals(1000, ThreeCardLogic.evalPPWinnings(hand, 25), "Did not recieve 40 to 1 Return");
	 }
	
	
	//Test Flush 
	@ParameterizedTest
	@ValueSource(chars = {'C', 'D', 'S', 'H'})
	void testF(char input) {
			
		hand.add(new Card(input, 9));
		hand.add(new Card(input, 11));
		hand.add(new Card(input, 12));
		
		assertEquals(4, ThreeCardLogic.evalHand(hand), "Is Not a Flush");

	}
	
	@ParameterizedTest
	@ValueSource(chars = {'C', 'D', 'S', 'H'})
	void testFW(char input) {
			
		hand.add(new Card(input, 4));
		hand.add(new Card(input, 14));
		hand.add(new Card(input, 2));
		
		assertEquals(75, ThreeCardLogic.evalPPWinnings(hand, 25), "Did not recieve 3 to 1 Return");
	}
	
	
	//Test 3-of-a-kind 
	@ParameterizedTest
	@ValueSource(ints = {2, 3, 4, 5, 6 , 7, 8, 9, 10, 11, 12, 13, 14})
	void test3K(int input)
	{
		hand.add(new Card('D', input));
		hand.add(new Card('H', input));
		hand.add(new Card('C', input));
		
		assertEquals(2, ThreeCardLogic.evalHand(hand), "Is Not a 3 of a Kind");
		
	}
	
	@ParameterizedTest
	@ValueSource(ints = {2, 3, 4, 5, 6 , 7, 8, 9, 10, 11, 12, 13, 14})
	void test3KW(int input)
	{
		hand.add(new Card('S', input));
		hand.add(new Card('C', input));
		hand.add(new Card('D', input));
		
		assertEquals(750, ThreeCardLogic.evalPPWinnings(hand, 25), "Did not recieve 30 to 1 Return");
		
	}
	

	//Test the two Possibilities for a Straight 
	
	//3 different Suits 
	@Test 
	void testStraight1()
	{
		hand.add(new Card('D', 13));
		hand.add(new Card('H', 14));
		hand.add(new Card('C', 12));
		
		assertEquals(3, ThreeCardLogic.evalHand(hand), "Is Not a Straight Type 1");
		
	}
	
	@Test 
	void testStraight1W()
	{
		hand.add(new Card('C', 4));
		hand.add(new Card('D', 6));
		hand.add(new Card('S', 5));
		
		assertEquals(150, ThreeCardLogic.evalPPWinnings(hand, 25), "Did not recieve 6 to 1 Return");
		
	}
	
	//2 same suits 
	@Test 
	void testStraight2()
	{
		hand.add(new Card('S', 2));
		hand.add(new Card('S', 1));
		hand.add(new Card('C', 3));
		
		assertEquals(3, ThreeCardLogic.evalHand(hand), "Is Not a Straight Type 2");
		
	}
	
	@Test 
	void testStraight2W()
	{
		hand.add(new Card('H', 8));
		hand.add(new Card('H', 9));
		hand.add(new Card('S', 10));
		
		assertEquals(150, ThreeCardLogic.evalPPWinnings(hand, 25), "Did not recieve 6 to 1 Return");
		
	}
	
	
	
	
	//Test the two possibilities for a Pair
	
	//2 same Suits 
	@Test
	void testPair1()
	{
		hand.add(new Card('H', 12));
		hand.add(new Card('H', 13));
		hand.add(new Card('C', 13));
		
		assertEquals(5, ThreeCardLogic.evalHand(hand), "Is Not a Pair Type 1");
	}
	
	@Test
	void testPair1W()
	{
		hand.add(new Card('D', 13));
		hand.add(new Card('D', 2));
		hand.add(new Card('S', 13));
		
		assertEquals(25, ThreeCardLogic.evalPPWinnings(hand, 25), "Did not recieve 1 to 1 Return");
	}
	
	//3 different Suits
	@Test 
	void testPair2()
	{
		hand.add(new Card('D', 4));
		hand.add(new Card('H', 2));
		hand.add(new Card('C', 4));
		
		assertEquals(5, ThreeCardLogic.evalHand(hand), "Is Not a Pair Type 2");
	}
	
	@Test 
	void testPair2W()
	{
		hand.add(new Card('S', 4));
		hand.add(new Card('D', 14));
		hand.add(new Card('H', 4));
		
		assertEquals(25, ThreeCardLogic.evalPPWinnings(hand, 25), "Did not recieve 1 to 1 Return");
	}
	
	
	
	//Test High Card 
	@Test
	void testHigh()
	{
		hand.add(new Card('S', 4));
		hand.add(new Card('D', 14));
		hand.add(new Card('H', 3));
		
		assertEquals(0, ThreeCardLogic.evalHand(hand), "Is Not a High Card");
	}
	
	@Test
	void testHighW()
	{
		hand.add(new Card('S', 13));
		hand.add(new Card('D', 2));
		hand.add(new Card('D', 3));
		
		assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, 25), "Did not recieve $0 in Winnings");
	}

	
//-------------Test Compare Hands----------------------------	
	
	//Check if 0 is returned when Dealer does not have a Queen or better
	@ParameterizedTest
	@ValueSource(ints = {5, 6 , 7, 8, 9, 10, 11})
	void testQueenHigh(int input)
	{
		dealer.add(new Card('H', input));
		dealer.add(new Card('C', 2));
		dealer.add(new Card('S', 3));
		
		hand.add(new Card('S', 14));
		hand.add(new Card('D', 12));
		hand.add(new Card('S', 13));
		
		assertEquals(0, ThreeCardLogic.compareHands(dealer, hand), "Dealer Has a Queen");
		
	}
	
	//Check Ties 
	
	//Check Tie in High Card 
	@Test
	void highCardTie()
	{
		dealer.add(new Card('C', 2));
		dealer.add(new Card('H', 3));
		dealer.add(new Card('S', 13));
		
		hand.add(new Card('D', 2));
		hand.add(new Card('D', 3));
		hand.add(new Card('C', 13));
		
		assertEquals(0, ThreeCardLogic.compareHands(dealer, hand), "Was Not a High Card Tie");
		
		
	}
	
	//Check Tie in Pair Hand
	@Test
	void pairTie()
	{
		dealer.add(new Card('C', 12));
		dealer.add(new Card('H', 3));
		dealer.add(new Card('S', 3));
		
		hand.add(new Card('D', 12));
		hand.add(new Card('D', 3));
		hand.add(new Card('C', 3));
		
		assertEquals(0, ThreeCardLogic.compareHands(dealer, hand), "Was Not a Pair Hand Tie");
		
		
	}
	
	
	//Check Tie in Flush
	@Test
	void flushTie()
	{
		dealer.add(new Card('C', 9));
		dealer.add(new Card('C', 10));
		dealer.add(new Card('C', 13));
		
		hand.add(new Card('D', 13));
		hand.add(new Card('D', 9));
		hand.add(new Card('D', 10));
		
		assertEquals(0, ThreeCardLogic.compareHands(dealer, hand), "Was Not a Flush Tie");
	}
	
	//Check for Tie in Straight
	@Test
	void straightTie()
	{
		dealer.add(new Card('C', 11));
		dealer.add(new Card('S', 12));
		dealer.add(new Card('H', 13));
		
		hand.add(new Card('D', 12));
		hand.add(new Card('D', 13));
		hand.add(new Card('H', 11));
		
		assertEquals(0, ThreeCardLogic.compareHands(dealer, hand), "Was Not a Straight Tie");
		
	}
	
	//Check for Straight Flush Tie
	@Test
	void straightFlushTie()
	{
		dealer.add(new Card('H', 13));
		dealer.add(new Card('H', 12));
		dealer.add(new Card('H', 14));
		
		hand.add(new Card('S', 13));
		hand.add(new Card('S', 14));
		hand.add(new Card('S', 12));
		
		assertEquals(0, ThreeCardLogic.compareHands(dealer, hand), "Was Not a Straight Flush Tie");
		
	}
	
	//Check For Dealer Win 
	@Test
	void dealerWin()
	{
		dealer.add(new Card('H', 13));
		dealer.add(new Card('H', 12));
		dealer.add(new Card('H', 14));
		
		hand.add(new Card('D', 2));
		hand.add(new Card('D', 3));
		hand.add(new Card('C', 13));
				
		assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win");
		
	}
	
	//Highest Ranking Card Wins
	@Test
	void dealerWin1()
	{
		dealer.add(new Card('H', 14));
		dealer.add(new Card('H', 3));
		dealer.add(new Card('H', 2));
		
		hand.add(new Card('D', 8));
		hand.add(new Card('D', 9));
		hand.add(new Card('D', 13));
				
		assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win (1)");
		
	}
	
	//Second Highest Ranking Card Wins
	@Test
	void dealerWin2()
	{
		dealer.add(new Card('S', 7));
		dealer.add(new Card('S', 12));
		dealer.add(new Card('S', 10));
		
		hand.add(new Card('C', 8));
		hand.add(new Card('C', 9));
		hand.add(new Card('C', 12));
				
		assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win (2)");
		
	}
	
	//Third Highest Ranking Card Wins
	@Test
	void dealerWin3()
	{
		dealer.add(new Card('S', 7));
		dealer.add(new Card('S', 10));
		dealer.add(new Card('S', 14));
		
		hand.add(new Card('D', 10));
		hand.add(new Card('D', 6));
		hand.add(new Card('D', 14));
				
		assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win (3)");
		
	}
	
	//Biggest Card Wins (3 of a kind)
	@Test
	void dealerWin4()
	{
		dealer.add(new Card('S', 13));
		dealer.add(new Card('D', 13));
		dealer.add(new Card('C', 13));
		
		hand.add(new Card('S', 12));
		hand.add(new Card('D', 12));
		hand.add(new Card('C', 12));
				
		assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win (4)");
		
	}
	
	//Same Pair Values. Highest Kicker Card wins 
	@Test
	void dealerWin5()
	{
		dealer.add(new Card('H', 12));
		dealer.add(new Card('H', 6));
		dealer.add(new Card('C', 6));
		
		hand.add(new Card('S', 6));
		hand.add(new Card('D', 6));
		hand.add(new Card('C', 3));
				
		assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win (5)");
		
	}
	
	//Same Pair Values. Highest Kicker Card wins
	@Test
	void dealerWin6()
	{
		dealer.add(new Card('H', 4));
		dealer.add(new Card('H', 12));
		dealer.add(new Card('C', 12));
		
		hand.add(new Card('S', 12));
		hand.add(new Card('D', 12));
		hand.add(new Card('C', 3));
				
		assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win (6)");
		
	}
	
	//Check For Player Win 
	@Test
	void playerWin()
	{
		dealer.add(new Card('C', 2));
		dealer.add(new Card('H', 3));
		dealer.add(new Card('S', 13));
		
		hand.add(new Card('S', 13));
		hand.add(new Card('S', 14));
		hand.add(new Card('S', 12));
				
		assertEquals(2, ThreeCardLogic.compareHands(dealer, hand), "Player did Not Win");
		
	}
	
	//Highest Ranking Card Wins
	@Test
	void playerWin1()
	{
		hand.add(new Card('H', 14));
		hand.add(new Card('H', 3));
		hand.add(new Card('H', 2));
		
		dealer.add(new Card('D', 8));
		dealer.add(new Card('D', 9));
		dealer.add(new Card('D', 13));
				
		assertEquals(2, ThreeCardLogic.compareHands(dealer, hand), "Player did Not Win (1)");
		
	}
	
	//Second Highest Ranking Card Wins
	@Test
	void playerWin2()
	{
		hand.add(new Card('S', 7));
		hand.add(new Card('S', 12));
		hand.add(new Card('S', 10));
		
		dealer.add(new Card('C', 8));
		dealer.add(new Card('C', 9));
		dealer.add(new Card('C', 12));
				
		assertEquals(2, ThreeCardLogic.compareHands(dealer, hand), "Player did Not Win (2)");
		
	}
	
	//Third Highest Ranking Card Wins
	@Test
	void playerWin3()
	{
		hand.add(new Card('S', 7));
		hand.add(new Card('S', 10));
		hand.add(new Card('S', 14));
		
		dealer.add(new Card('D', 10));
		dealer.add(new Card('D', 6));
		dealer.add(new Card('D', 14));
				
		assertEquals(2, ThreeCardLogic.compareHands(dealer, hand), "Player did Not Win (3)");
		
	}
	
	//Biggest Card Wins (3 of a kind)
	@Test
	void playerWin4()
	{
		hand.add(new Card('S', 13));
		hand.add(new Card('D', 13));
		hand.add(new Card('C', 13));
		
		dealer.add(new Card('S', 12));
		dealer.add(new Card('D', 12));
		dealer.add(new Card('C', 12));
				
		assertEquals(2, ThreeCardLogic.compareHands(dealer, hand), "Player did Not Win (4)");
		
	}
	
	//Same Pair Values. Highest Kicker Card wins
	@Test
	void playerWin5()
	{
		hand.add(new Card('H', 14));
		hand.add(new Card('H', 12));
		hand.add(new Card('C', 12));
		
		dealer.add(new Card('S', 12));
		dealer.add(new Card('D', 12));
		dealer.add(new Card('C', 3));
				
		assertEquals(2, ThreeCardLogic.compareHands(dealer, hand), "Player did Not Win (5)");
		
	}
	
	//Same Pair Values. Highest Kicker Card wins
	@Test
	void playerWin6()
	{
		hand.add(new Card('H', 4));
		hand.add(new Card('H', 12));
		hand.add(new Card('C', 12));
		
		dealer.add(new Card('S', 12));
		dealer.add(new Card('D', 12));
		dealer.add(new Card('C', 3));
				
		assertEquals(2, ThreeCardLogic.compareHands(dealer, hand), "Player did Not Win (6)");
		
	}
	
	//dealer wins with 2 pairs lower than queen
		@Test
		void dealerWinChris()
		{
			dealer.add(new Card('H', 4));
			dealer.add(new Card('H', 6));
			dealer.add(new Card('C', 6));
			
			hand.add(new Card('S', 2));
			hand.add(new Card('D', 6));
			hand.add(new Card('C', 3));
					
			assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win (5)");
			
		}
		
		//Dealer wins with 3 cards lower than queen
		@Test
		void dealerWinChris2()
		{
			dealer.add(new Card('S', 2));
			dealer.add(new Card('H', 2));
			dealer.add(new Card('C', 2));
			
			hand.add(new Card('S', 13));
			hand.add(new Card('D', 6));
			hand.add(new Card('C', 3));
					
			assertEquals(1, ThreeCardLogic.compareHands(dealer, hand), "Dealer did Not Win (5)");
			
		}
}
