import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Player {
	
	//Player Variables
	ArrayList<Card> hand;
	int anteBet;
	int playBet;
	int pairPlusBet;
	int totalWinnings;
	
	Label lblName;
	Label lblAnteBet;
	Label lblPairPlusBet;
	Label lblTotalWinnings;
	ArrayList<ImageView> handPics;
	String curBack;
	Boolean cardsAreUp;
	
	//set variables to 0
	//Default constructor of player 
	Player(){
		hand = new ArrayList<Card>();
		handPics = new ArrayList<ImageView>();
		anteBet = 0;
		playBet = 0;
		pairPlusBet = 0;
		totalWinnings = 0;
		curBack = "asuka.jpg";
		cardsAreUp = false;
	}
	
	//put cards face up on play scene
	public void displayCards(){
		for(int i = 0; i < hand.size(); ++i)
		{
			String val= Integer.toString(hand.get(i).getValue());
			String suit = String.valueOf(hand.get(i).getSuit());
			Image card = new Image(val + suit + ".jpg");
			handPics.get(i).setImage(card);
		}
		cardsAreUp = true;
		
	}
	//put cards face down on play scene
	public void hideCards() {
		for(int i = 0; i < hand.size(); ++i)
		{
			Image card = new Image(curBack);
			handPics.get(i).setImage(card);
		}
		cardsAreUp = false;
	}
	
	//change color of label fonts depending on string inputed
	public void changeColor(String color) {
		if (color == "black")
		{
			lblName.setTextFill(Color.web("#111111"));
			lblAnteBet.setTextFill(Color.web("#111111"));
			lblPairPlusBet.setTextFill(Color.web("#111111"));
			lblTotalWinnings.setTextFill(Color.web("#111111"));
		}
		else {
			lblName.setTextFill(Color.web("#ffffff"));
			lblAnteBet.setTextFill(Color.web("#ffffff"));
			lblPairPlusBet.setTextFill(Color.web("#ffffff"));
			lblTotalWinnings.setTextFill(Color.web("#ffffff"));
		}
	}
	//reset bet values to zero
	public void resetBets() {
		anteBet = 0;
		playBet = 0;
		pairPlusBet = 0;
		//totalWinnings = 0;
	}
}
