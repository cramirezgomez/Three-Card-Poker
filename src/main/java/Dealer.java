import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Dealer {
	
	//Deck of the game 
	Deck theDeck;
	
	//Holds the dealers hand 
	ArrayList<Card> dealersHand;

	Label lblName;
	ArrayList<ImageView> handPics;
	String curBack;
	Boolean cardsAreUp;
	//Initializes the (deck) and the (dealers hand) 
	public Dealer(){
		
		theDeck = new Deck();
		dealersHand = new ArrayList<Card>();
		handPics = new ArrayList<ImageView>();
		curBack = "asuka.jpg";
		cardsAreUp = false;
	} 
	
	//deal a hand of three cards
	public ArrayList<Card> dealHand(){
		
		//Store the 3 cards that are being taken from the deck
		ArrayList<Card> hand = new ArrayList<Card>();
		
		//To Do: Before each game starts the dealer must check there are > 34 cards in the deck 
		//If they are not then a new deck is to be constructed 
		if(theDeck.size() <= 34)// && ((52 - theDeck.size) % 9 == 0)
		{
			theDeck = new Deck();
		}
		
		//Add cards (3) to the hand and remove cards (3) from the deck 
		for(int i = 0; i < 3; ++i) 
		{
			hand.add(theDeck.get(i));
			theDeck.remove(i);
		}
		
		//Return a hand of 3 cards 
		return hand;
	} 
	
	//displays cards on play scene face up
	public void displayCards(){
		for(int i = 0; i < dealersHand.size(); ++i)
		{
			String val= Integer.toString(dealersHand.get(i).getValue());
			String suit = String.valueOf(dealersHand.get(i).getSuit());
			Image card = new Image(val + suit + ".jpg");
			handPics.get(i).setImage(card);
		}
		cardsAreUp = true;
	}
	
	//put cards face down on play scene
	public void hideCards() {
		for(int i = 0; i < dealersHand.size(); ++i)
		{
			Image card = new Image(curBack);
			handPics.get(i).setImage(card);
		}
		cardsAreUp = false;
	}
}
