
public class Card {
	
	//Suit and Value of card 
	private char suit;
	private int value;
	
	//Default constructor 
	public Card(char suit, int value){
		this.value = value;
		this.suit = suit;
	}
	
	//Set the Suit of the card 
	public void setSuit(char s){
		this.suit = s;
	}
	
	//Set the Value of the card 
	public void setValue(int v) {
		this.value = v;
	}
	
	//Get the Suit of the card 
	public char getSuit(){
		return this.suit;
	}
	
	//Get the Value of the card 
	public int getValue(){
		return this.value;
	}
}
