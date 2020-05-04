import java.util.ArrayList;
import java.util.Collections;

public class ThreeCardLogic {
	
	
	
	//Evaluate the type of Hand the Player(s)/Dealer has 
	public static int evalHand(ArrayList<Card> hand)
	{
		ArrayList<Integer> playerVal = new ArrayList<Integer>();
		
		for(int i = 0; i < 3; i++){
			playerVal.add(hand.get(i).getValue());
		}
		
		//Sort Values into ascending order 
		Collections.sort(playerVal);
			
		//Get each individual cards from the hand 
		Card card1 = hand.get(0);
		Card card2 = hand.get(1);
		Card card3 = hand.get(2);
		
		int difference1 = playerVal.get(2) - playerVal.get(1);
		int difference2 = playerVal.get(2) - playerVal.get(0);
		
		//Prevents a difference of 0 and 3 to equal 3 in sum  
		if(difference1 == 0){
			difference1 = 10;
		}
		if(difference2 == 0){
			difference2 = 10;
		}
		
		//Check if all cards have the same suit  
		if(card1.getSuit() == card2.getSuit() && card1.getSuit() == card3.getSuit() && card2.getSuit() == card3.getSuit()){ 
			
			//If the cards are in sequential order then the sum of the differences must be 3 
			if(Math.abs(difference1) + Math.abs(difference2) == 3){
				
				//The Hand is a (Straight Flush)
				return 1;
			}
			//If the difference is not 3 
			else{
				
				//The Hand is a (Flush)
				return 4;
			}	
		}
		//Check if all cards have different suits  
		else if(card1.getSuit() != card2.getSuit() && card1.getSuit() != card3.getSuit() && card2.getSuit() != card3.getSuit()){
			
			//Check if all of the cards values are all the same 
			if(card1.getValue() == card2.getValue() && card1.getValue() == card3.getValue() && card2.getValue() == card3.getValue()){
				
				//The Hand is (Three of a Kind)
				return 2;
			}
			//If the cards are in sequential order then the sum of the differences must be 3 
			else if(Math.abs(difference1) + Math.abs(difference2) == 3){
				
				//The Hand is a (Straight)
				return 3;
			}
			//Check if their is a pair of cards with the same Value
			else if(card1.getValue() == card2.getValue() || card1.getValue() == card3.getValue() || card2.getValue() == card3.getValue()){
				
				//The Hand is a (Pair)
				return 5;
			}
		}
		//Check for 2 same suits and 1 different suit 
		else if(card1.getSuit() != card2.getSuit() || card1.getSuit() != card3.getSuit() || card2.getSuit() != card3.getSuit()){ 
			
			//If the cards are in sequential order then the sum of the differences must be 3 
			if(Math.abs(difference1) + Math.abs(difference2) == 3){
				//The Hand is a (Straight)
				return 3;
			}
			//Check if their is a pair of cards with the same Value
			else if(card1.getValue() == card2.getValue() || card1.getValue() == card3.getValue() || card2.getValue() == card3.getValue()){
				//The Hand is a (Pair)
				return 5;	
			}
		}
		else{
			//The Hand is a (High Card)
			return 0;
		}
		
		//Never gets called 
		return 0;
		
	}
	
	//Calculate the Winnings of the Pair Plus Wager 
	public static int evalPPWinnings(ArrayList<Card> hand, int bet)
	{
		//Evaluate the hand 
		int handType = evalHand(hand);
		int winPP;
		
		//Calculate winnings of PP wager 
		switch(handType) {
        
        case 1:
        	winPP = (40 * bet);
        	break;
        case 2 :
        	winPP = (30 * bet);
           break;
        case 3 :
        	winPP = (6 * bet);
        	break;
        case 4 :
        	winPP = (3 * bet);
           break;
        case 5:
        	winPP = (1 * bet);
        	break;
        default :
        	winPP = 0;
     }
		
		//Return the total winnings of the PP wager 
		return winPP;
	}
	
	
	public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player)
	{
		
		ArrayList<Integer> playerVal = new ArrayList<Integer>();
		ArrayList<Integer> dealerVal = new ArrayList<Integer>();
		
		//Store the values of the hand of both the dealer and player into an array list
		for(int i = 0; i < 3; i++){
			
			playerVal.add(player.get(i).getValue());
			dealerVal.add(dealer.get(i).getValue());
		}
		
		//Sort Values into ascending order 
		Collections.sort(playerVal);
		Collections.sort(dealerVal);
		
		//Evaluate the type of hands 
		int handTypePlayer = evalHand(player);
		int handTypeDealer = evalHand(dealer);
		
		//Change High Card value to 6 so it makes more sense in terms 
		//of the rest of the hand hierarchy 
		if(handTypePlayer == 0){
			handTypePlayer = 6;
		}
		if(handTypeDealer == 0){
			handTypeDealer = 6;
		}
		
		//Check if the dealer has a Queen High
		if(evalHand(dealer) == 0 && dealerVal.get(2) < 12){
			//Does not have a Queen High
			return 0;
		}
		
		//Both Users do not have the same type of hand  
		if(handTypePlayer != handTypeDealer){
			
			//Player Wins 
			if(handTypePlayer < handTypeDealer){
				return 2;
			}
			//Dealer Wins 
			else if(handTypePlayer > handTypeDealer){
				return 1;
			}	
		}
		//The cards are the same type  
		else
		{
			//Check for a absolute tie
			if(dealerVal.get(0) == playerVal.get(0) && dealerVal.get(1) == playerVal.get(1) && dealerVal.get(2) == playerVal.get(2)){
				//Tie 
				return 0;
			}
			//Check for Pair Hand Winner. Special Case because the Highest Kicker card decides the Winner 
			else if(handTypePlayer == 5 && handTypeDealer == 5)
			{
				if(dealerVal.get(1) < playerVal.get(1)){
					//Player Wins 
					return 2;
				}
				else if(dealerVal.get(1) > playerVal.get(1)){
					//Dealer wins 
					return 1;
				}
				else if((dealerVal.get(0) < playerVal.get(0)) || (dealerVal.get(2) < playerVal.get(2))){
					//Player Wins 
					return 2;
				}
				else if((dealerVal.get(0) > playerVal.get(0)) || (dealerVal.get(2) > playerVal.get(2))){
					//Dealer wins 
					return 1;
				}
				
			}
			//Highest Ranking Card Wins 
			else if(dealerVal.get(2) > playerVal.get(2)){
				//Dealer wins 
				return 1;
			}
			else if(dealerVal.get(2) < playerVal.get(2)){
				//Player Wins 
				return 2;
			}
			//Second Highest Ranking Card Wins 
			else if(dealerVal.get(1) > playerVal.get(1)){
				//Dealer wins 
				return 1;
			}
			else if(dealerVal.get(1) < playerVal.get(1)){
				//Player Wins 
				return 2;
			}
			//Third Highest Ranking Card Wins 
			else if(dealerVal.get(0) > playerVal.get(0)){
				//Dealer wins 
				return 1;
			}
			else if(dealerVal.get(0) < playerVal.get(0)){
				//Player Wins 
				return 2;
			}
			
		}
		
		return 0; 
	}
}
