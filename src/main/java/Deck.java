import java.util.ArrayList;
import java.util.Collections;
public class Deck extends ArrayList<Card>{
	
	//ignore this, piazza post said to add to remove error
	private static final long serialVersionUID = 1L;

	//Initial Deck created 
	public Deck() {
		
		//Loop through Value of cards 
		for(int i = 2; i < 15; ++i)
		{
			//Add value to each suit 
			this.add(new Card('C', i));
			this.add(new Card('D', i));
			this.add(new Card('S', i));
			this.add(new Card('H', i));
		}
		
		//Randomizes the cards in the deck 
		Collections.shuffle(this);
	
	}
	
	//Create a new deck 
	public void newDeck(){
		
		//Clear old deck 
		this.clear();
		
		//Loop through Value of cards 
		for(int i = 2; i < 15; ++i)
		{
			this.add(new Card('C', i));
			this.add(new Card('D', i));
			this.add(new Card('S', i));
			this.add(new Card('H', i));
		}
		
		//Randomizes the cards in the deck 
		Collections.shuffle(this);
	}
}
