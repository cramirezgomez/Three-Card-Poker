import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ThreeCardPokerGame extends Application {

	Player playerOne;
	Player playerTwo;
	Dealer theDealer;
	
	//PLay scene Buttons 
	private Button btnStart;
	private Button btnContinue;
	private Button btnp1Play;
	private Button btnp1Fold;
	private Button btnp2Play;
	private Button btnp2Fold;
	private Button btnPlayAgain;
	
	//Menus for all scenes
	private MenuBar menubar;
	private Menu menuOptions;
	private ArrayList<MenuItem> itemExit;
	private ArrayList<MenuItem> itemFresh;
	private ArrayList<MenuItem> itemLook;
	
	//structures to keep references to objects
	HashMap<String, Scene> sceneMap;
	HashMap<String, BorderPane> paneMap;
	Label resultsOutput;
	
	//Buttons-----------------Scene 2
	private Button btnPP;
	private Button btnAn;
	//-------------------------------
	
	//Text Fields--------------Scene 2
	TextField textPP;
	TextField textAnte;
	
	TextField textPP2;
	TextField textAnte2;
	//---------------------------------
	//Scene two variables	
	//-----------------------------
	Label bDanteBet;
	Label bWinnings;
	Label bPPBet;
	
	Label bDanteBet1;
	Label bWinnings1;
	Label bPPBet1;
	
	private Button makeBet;
	
	private Button btnPP2;
	private Button btnAn2;
	
	ArrayList<Label> scene2Labels;
	
	//-------------------------------
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//checks if continue should be enabled
	public void checkForContinueBtn()
	{
		if((btnp1Play.isDisabled() == true || btnp1Fold.isDisabled() == true) &&
		   (btnp2Play.isDisabled() == true || btnp2Fold.isDisabled() == true)	){
					btnContinue.setDisable(false);
		}
	}
	
	//resets buttons and cards to default positions
	void resetButtonsAndCards() {
		btnContinue.setDisable(true);
		btnPlayAgain.setDisable(true);
		btnp1Fold.setDisable(false);
		btnp1Play.setDisable(false);
		btnp2Fold.setDisable(false);
		btnp2Play.setDisable(false);
		playerOne.hideCards();
		playerTwo.hideCards();
		theDealer.hideCards();
		
		textPP.setDisable(true);
		textPP2.setDisable(true);
		makeBet.setDisable(true);
		btnPP.setDisable(true);
		btnPP2.setDisable(true);
		
	}
	
	//change card back-face to whatever file is inputed
	void setCardBack(String file)
	{
		playerOne.curBack = file;
		playerTwo.curBack = file;
		theDealer.curBack = file;
		if(playerOne.cardsAreUp == false)
		{
			playerOne.hideCards();
		}
		if(playerTwo.cardsAreUp == false)
		{
			playerTwo.hideCards();
		}
		if(theDealer.cardsAreUp == false)
		{
			theDealer.hideCards();
		}
		
	}
	
	//set background of border inputed to function with file from string
	void setBackground(BorderPane bpane, String file) {
		Image bgPic = new Image(file);
		BackgroundSize bgSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		BackgroundImage bgImage =
		    new BackgroundImage(
		        bgPic,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundPosition.CENTER,
		        bgSize);
		Background background = new Background(bgImage);
        bpane.setBackground(background);
	}
	
	//changes look of game after menu option is pressed
	void changeLook() {
		if(playerOne.curBack == "asuka.jpg") {
			//call other functions to change card and bg
			setCardBack("rei.jpg");
			setBackground(paneMap.get("start"), "bg2.jpg");
			setBackground(paneMap.get("bet"), "bg2.jpg");
			setBackground(paneMap.get("play"), "bg2.jpg");
			
			//change font color of labels
			playerOne.changeColor("black");
			playerTwo.changeColor("black");
			resultsOutput.setTextFill(Color.web("#111111"));
			for(int i = 0; i < scene2Labels.size(); ++i)
			{
				scene2Labels.get(i).setTextFill(Color.web("#111111"));
			}
			
		}
		else
		{
			//call other functions to change card and bg
			setCardBack("asuka.jpg");
			setBackground(paneMap.get("start"), "bg.jpg");
			setBackground(paneMap.get("bet"), "bg.jpg");
			setBackground(paneMap.get("play"), "bg.jpg");
			
			//change font color of labels
			playerOne.changeColor("white");
			playerTwo.changeColor("white");
			resultsOutput.setTextFill(Color.web("#ffffff"));
			for(int i = 0; i < scene2Labels.size(); ++i)
			{
				scene2Labels.get(i).setTextFill(Color.web("#ffffff"));
			}
			
		}
	}
	
	//calls game logic to start playing game after folding or playing
	void execGameLogic(Player curPlayer) {
		int newWinnings = 0;
		int curAnteBet = curPlayer.anteBet;
		int curPPBet = curPlayer.pairPlusBet;
		
		//check who won the hand
		switch(ThreeCardLogic.compareHands(theDealer.dealersHand, curPlayer.hand))
		{
			case 0: //neither won
				//curPlayer.lblName.setText("Neither");
				resultsOutput.setText(resultsOutput.getText() + "Dealer does not have at least Queen high;\n ante wager is pushed\n");
				break;
			case 1://dealer won
				resultsOutput.setText(resultsOutput.getText() + curPlayer.lblName.getText() + " loses to dealer.\n");
				newWinnings -= (2 * curAnteBet);
				break;
			case 2: //player won
				resultsOutput.setText(resultsOutput.getText() + curPlayer.lblName.getText() + " beats dealer.\n");
				newWinnings += (4 * curAnteBet);
				break;
			
			default:
				break;
		}
		//add pair plus winnings as long as no one folded
		if(curPPBet > 0) {
			int ppWinnings = ThreeCardLogic.evalPPWinnings(curPlayer.hand, curPPBet);
			if(ppWinnings == 0)
			{
				newWinnings -= ppWinnings;
				resultsOutput.setText(resultsOutput.getText() + curPlayer.lblName.getText() + " loses Pair Plus.\n");
			}
			else
			{
				newWinnings += ppWinnings;
				resultsOutput.setText(resultsOutput.getText() + curPlayer.lblName.getText() + " wins Pair Plus.\n");
			}
			
		}
		curPlayer.totalWinnings += newWinnings;
		
	}
	
	//event loop function
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		playerOne = new Player();
		playerTwo = new Player();
		theDealer = new Dealer();
		
		//Save Labels for font color change

		scene2Labels = new ArrayList<Label>();
		
		itemExit = new ArrayList<MenuItem>();
		itemFresh = new ArrayList<MenuItem>();
		itemLook = new ArrayList<MenuItem>();
		paneMap = new HashMap<String, BorderPane>();
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("start", createStartScene());
		sceneMap.put("bet", createBetScene());
		
		sceneMap.put("play", createPlayScene());
		
		primaryStage.setTitle("Let's Play Three Card Poker!!!");
		
		
		primaryStage.setScene(sceneMap.get("start"));
		primaryStage.show();
		
		
		
		//All Event Handlers
		///////////////////
		
		//Start Scene setup
		btnStart.setOnAction(e->primaryStage.setScene(sceneMap.get("bet")));
		
		//Bet Scene setup
		makeBet.setOnAction(e->{
			primaryStage.setScene(sceneMap.get("play"));
			
			//Labels for bet scene
			bDanteBet.setText("(Player 2) Ante Bet: $" + 0);
			bPPBet.setText("(Player 2) Pay Plus Wager: $" + 0);
			
			bDanteBet1.setText("(Player 1) Ante Bet: $" + 0);
			bPPBet1.setText("(Player 1) Pay Plus Wager: $" + 0);
			
			//set labels on next screen to correct values
			displayPlayLabels(playerOne);
			displayPlayLabels(playerTwo);
			
			
			//deal out the cards
			playerOne.hand = theDealer.dealHand();
			playerTwo.hand = theDealer.dealHand();
			theDealer.dealersHand = theDealer.dealHand();
			
			//show player cards face up
			playerOne.displayCards();
			playerTwo.displayCards();
		});
		
		btnp1Play.setOnAction(e->{//Pressing play for player 1
			btnp1Play.setDisable(true);
			btnp1Fold.setDisable(false);
			checkForContinueBtn();
		});
		
		btnp1Fold.setOnAction(e->{//Pressing fold for player 1
			btnp1Play.setDisable(false);
	    	btnp1Fold.setDisable(true);
	    	checkForContinueBtn();
	    });
		
		btnp2Play.setOnAction(e->{//Pressing play for player 2
			btnp2Play.setDisable(true);
			btnp2Fold.setDisable(false);
			checkForContinueBtn();
		});
		btnp2Fold.setOnAction(e->{//Pressing fold for player 2
			btnp2Play.setDisable(false);
		    btnp2Fold.setDisable(true);
		    checkForContinueBtn();
		});
		
		btnContinue.setOnAction(e->{ //Press continue
			theDealer.displayCards();
			btnContinue.setDisable(true);
			btnPlayAgain.setDisable(false);
			
			//if players play
			if(btnp1Play.isDisable() == true)
			{
				execGameLogic(playerOne);
			}
			if(btnp2Play.isDisable() == true)
			{
				execGameLogic(playerTwo);
			}
			
			//if players fold
			if(btnp1Fold.isDisable() == true)
			{
				resultsOutput.setText(resultsOutput.getText() + playerOne.lblName.getText() + " Folded.\n");
				playerOne.totalWinnings -= playerOne.anteBet;
				playerOne.totalWinnings -= playerOne.pairPlusBet;
			}
			if(btnp2Fold.isDisable() == true)
			{
				resultsOutput.setText(resultsOutput.getText() + playerTwo.lblName.getText() + " Folded.\n");
				playerTwo.totalWinnings -= playerTwo.anteBet;
				playerTwo.totalWinnings -= playerTwo.pairPlusBet;
			}
			
			//Disable buttons 
			btnp1Play.setDisable(true);
			btnp2Play.setDisable(true);
			btnp1Fold.setDisable(true);
			btnp2Fold.setDisable(true);
			
			//change labels since winnings may have changed
			displayPlayLabels(playerOne);
			displayPlayLabels(playerTwo);
		});
		
		btnPlayAgain.setOnAction(e->{ // Press play again
			primaryStage.setScene(sceneMap.get("bet"));
			resetButtonsAndCards();
			playerOne.resetBets();
			playerTwo.resetBets();
			bWinnings.setText("(Player 2) Total Winnings: $"+ playerTwo.totalWinnings);
			bWinnings1.setText("(Player 1) Total Winnings: $"+ playerOne.totalWinnings);
			resultsOutput.setText("Information\n");
		});
		
		//listen for new look option on menu
		for(int i = 0; i < itemLook.size(); ++i) {
			itemLook.get(i).setOnAction(e->changeLook());
		}
		
		//listen for exit option on menu
		for(int i = 0; i < itemExit.size(); ++i) {
			itemExit.get(i).setOnAction(e->{
				Platform.exit();
				System.exit(0);
			});
		}
		
		//listen for fresh game on menu
		for(int i = 0; i < itemFresh.size(); ++i) {
			itemFresh.get(i).setOnAction(e->{
				resetButtonsAndCards();
				primaryStage.setScene(sceneMap.get("start"));
				playerOne.resetBets();
				playerTwo.resetBets();
				playerOne.totalWinnings = 0;
				playerTwo.totalWinnings = 0;
				resultsOutput.setText("Information\n");
			});
		}
		
		
		//Scene two event handlers 
		//--------------------------------------------------------------------------
			
			//listen for PP button
			btnPP.setOnAction(new EventHandler<ActionEvent> (){
				public void handle(ActionEvent action)
				{
					  try {
						  int bet = Integer.parseInt(textPP.getText());
						  
						  if(bet == 0 || (bet > 4 && bet < 26))
						  {
							  textPP.clear();
							  playerOne.pairPlusBet = bet;
						  
							  bPPBet1.setText("(Player 1) Pay Plus Wager: $" + playerOne.pairPlusBet);
							  
						  }
					  }
					  catch(NumberFormatException ex){
						  
					  }
				}
			});
			
			//listen for ante button
			btnAn.setOnAction(new EventHandler<ActionEvent> (){
				public void handle(ActionEvent action)
				{
					 try {
						  int bet = Integer.parseInt(textAnte.getText());
						  if(bet > 4 && bet < 26)
						  {
							  
							  if(playerTwo.anteBet > 4 && playerTwo.anteBet < 26)
							  {
								 makeBet.setDisable(false);
							  }
							 
							  btnPP.setDisable(false);
							  textPP.setDisable(false);
							  textAnte.clear();
							  playerOne.anteBet = bet;
							  bDanteBet1.setText("(Player 1) Ante Bet: $" + playerOne.anteBet);
						  }
					  }
					  catch(NumberFormatException ex){
						  
					  }
				}
			});
			
			//listen for PP button
			btnPP2.setOnAction(new EventHandler<ActionEvent> (){
				public void handle(ActionEvent action)
				{
					  try {
						  int bet = Integer.parseInt(textPP2.getText());
						  
						  if(bet == 0 || (bet > 4 && bet < 26))
						  {
							  textPP2.clear();
							  playerTwo.pairPlusBet = bet;
						  
							  bPPBet.setText("(Player 2) Pay Plus Wager: $" + playerTwo.pairPlusBet);
							  
						  }
					  }
					  catch(NumberFormatException ex){
						  
					  }
				}
			});
			
			//listen for ante button
			btnAn2.setOnAction(new EventHandler<ActionEvent> (){
				public void handle(ActionEvent action)
				{
					  try {
						  int bet = Integer.parseInt(textAnte2.getText());
					  
						  if(bet > 4 && bet < 26)
						  {
							  
							  if(playerOne.anteBet > 4 && playerOne.anteBet < 26)
							  {
								 makeBet.setDisable(false);
							  }
							  
							  //makeBet.setDisable(false);
							  btnPP2.setDisable(false);
							  textPP2.setDisable(false);
							  
							  textAnte2.clear();
							  
							  playerTwo.anteBet = bet;
							 
							  bDanteBet.setText("(Player 2) Ante Bet: $" + playerTwo.anteBet);
							 
						  }
					  }
					  catch(NumberFormatException ex){
							  
					  }
				}
			});
		
		
	}
	
	//Change labels on screen with values from player class
	void displayPlayLabels(Player curPlayer) {
		curPlayer.lblAnteBet.setText("Ante Bet: " + curPlayer.anteBet);
		curPlayer.lblPairPlusBet.setText("Pair Plus Bet: " + curPlayer.pairPlusBet);
		curPlayer.lblTotalWinnings.setText("Winnings: " + curPlayer.totalWinnings);
	}
	
	//returns a new menu-bar
	public MenuBar createMenuBar()
	{
		menubar = new MenuBar();
		menuOptions= new Menu("Options");
		
		MenuItem itemE= new MenuItem("Exit");
		MenuItem itemF = new MenuItem("Fresh Start");
		MenuItem itemL = new MenuItem("New Look ");
		
		//add references to an array list to access later
		itemExit.add(itemE);
		itemFresh.add(itemF);
		itemLook.add(itemL);
		
		//add items to menu and menu to bar
		menuOptions.getItems().addAll(itemE, itemF, itemL);
		menubar.getMenus().addAll(menuOptions);
		return menubar;
	}
	
	
	
	//sets the size for a single card
	public void setCardSize(ImageView card)
	{
		card.setFitHeight(200);
		card.setFitWidth(200);
		card.setPreserveRatio(true);
	}
	
	//returns an h-box with 3 card images
	public HBox createHand(Player curPlayer)
	{
		Image card = new Image("asuka.jpg");
		
		ImageView viewCard1 = new ImageView(card);
		ImageView viewCard2 = new ImageView(card);
		ImageView viewCard3 = new ImageView(card);
		
		setCardSize(viewCard1);
		setCardSize(viewCard2);
		setCardSize(viewCard3);
		
		//add references of image view to player class
		curPlayer.handPics.add(viewCard1);
		curPlayer.handPics.add(viewCard2);
		curPlayer.handPics.add(viewCard3);
		
		return new HBox(10, viewCard1, viewCard2, viewCard3);
	}
	
	//return h-box with dealers hand
	public HBox createHand(Dealer dealer)
	{
		Image card = new Image("asuka.jpg");
		
		ImageView viewCard1 = new ImageView(card);
		ImageView viewCard2 = new ImageView(card);
		ImageView viewCard3 = new ImageView(card);
		
		setCardSize(viewCard1);
		setCardSize(viewCard2);
		setCardSize(viewCard3);
		
		//add references of image view to dealer class
		dealer.handPics.add(viewCard1);
		dealer.handPics.add(viewCard2);
		dealer.handPics.add(viewCard3);
		
		return new HBox(10, viewCard1, viewCard2, viewCard3);
	}
	
	//returns a v-box with all the information for a single player
	public VBox createPlayerInfo(String name, Player curPlayer) {
		//labels with player info
		Label ptxt = new Label(name);
		Label pWinnings = new Label("Winnings: ");
		Label pAnte = new Label("Ante Bet: ");
		Label pPP =   new Label("Pair Plus Bet: ");
		
		//change font
		ptxt.setFont(new Font("Arial", 20));
		pWinnings.setFont(new Font("Arial", 20));
		pAnte.setFont(new Font("Arial", 20));
		pPP.setFont(new Font("Arial", 20));
		
		
		//create needed h-boxes with different objects
		HBox anteBox = new HBox(pAnte);
		anteBox.setAlignment(Pos.CENTER);
		HBox ppBox = new HBox(pPP);
		ppBox.setAlignment(Pos.CENTER);
		
		//Add references to player class
		curPlayer.lblName = ptxt;
		curPlayer.lblAnteBet = pAnte;
		curPlayer.lblTotalWinnings = pWinnings;
		curPlayer.lblPairPlusBet = pPP;
		curPlayer.changeColor("white");
		
		return new VBox(5, ptxt, pWinnings, anteBox, ppBox);
	}
	
	//returns an h=box with the buttons needed for each player
	public HBox addButtonstoInfo(String player) {
		HBox gameButtons = new HBox(10);
		gameButtons.setAlignment(Pos.CENTER);
		
		//check which player  buttons need to be added to box
		if(player == "Player 1")
		{
			btnp1Play = new Button("Play");
			btnp1Fold = new Button("Fold");
			gameButtons.getChildren().addAll(btnp1Play,btnp1Fold);
		}
		else
		{
			btnp2Play = new Button("Play");
			btnp2Fold = new Button("Fold");
			gameButtons.getChildren().addAll(btnp2Play,btnp2Fold);
		}
		return gameButtons;
	}
	
	//returns play scene
	public Scene createPlayScene() {
		BorderPane border = new BorderPane();
		paneMap.put("play", border);
		
		btnContinue = new Button("Continue");
		btnPlayAgain = new Button("Play Again");
		btnPlayAgain.setDisable(true);
		btnContinue.setDisable(true);
		Label dtxt = new Label("Dealer");
		dtxt.setFont(new Font("Arial", 20));
		dtxt.setTextFill(Color.web("#ac101d"));
		theDealer.lblName = dtxt;
		HBox dHand = createHand(theDealer);
		VBox dInfo = new VBox(5, dtxt, dHand, btnContinue, btnPlayAgain);
		dHand.setAlignment(Pos.TOP_CENTER);
		dInfo.setAlignment(Pos.TOP_CENTER);
		border.setCenter(dInfo);
		
		
		
		
		VBox p1Info = createPlayerInfo("Player 1", playerOne);
		p1Info.getChildren().addAll(addButtonstoInfo("Player 1"), createHand(playerOne));
		p1Info.setAlignment(Pos.CENTER);
		
		VBox p2Info = createPlayerInfo("Player 2", playerTwo);
		p2Info.getChildren().addAll(addButtonstoInfo("Player 2"), createHand(playerTwo));
		p2Info.setAlignment(Pos.CENTER);
		
		//Use and anchor t separate the two players information/card panal
		AnchorPane spaceBetweenPlayers = new AnchorPane();
		HBox.setHgrow(spaceBetweenPlayers, Priority.ALWAYS);
		HBox bothPlayerInfo = new HBox(p1Info, spaceBetweenPlayers, p2Info);
		border.setBottom(bothPlayerInfo);
		
		setBackground(border, "bg.jpg");
		
		
		border.setTop(createMenuBar());
		
		//add label on left for output
		resultsOutput = new Label("Information\n");
		resultsOutput.setFont(new Font("Arial", 20));
		resultsOutput.setTextFill(Color.web("#ffffff"));
		border.setLeft(resultsOutput);
		return new Scene(border, 1100, 800);
	}

	//return the bet scene
	public Scene createBetScene() //<--->
	{
		BorderPane border = new BorderPane();
		paneMap.put("bet", border);
		makeBet = new Button("Make Bets");
		makeBet.setDisable(true);

		
		//Player 1
//----------------------------------------------------------------
		
		textPP = new TextField();
		textPP.setDisable(true);
		Label ppL = new Label("Pay Plus Wager (Player 1)");
		
		textAnte = new TextField();
		Label anL = new Label("Ante Wager (Player 1)");
		
		anL.setTextFill(Color.web("#000000"));
		ppL.setTextFill(Color.web("#000000"));
		
		anL.setStyle("-fx-font-weight: bold");
		ppL.setStyle("-fx-font-weight: bold");
		
		btnPP = new Button("Enter");
		btnPP.setDisable(true);
		btnAn = new Button("Enter");
		
		Label c = new Label(" ");
		Label d = new Label(" ");
		
		VBox gameButtons = new VBox(3);
		gameButtons.getChildren().addAll(c, btnAn, d, btnPP);
		
		VBox pWagers = new VBox(3, anL, textAnte, ppL, textPP);
		
		HBox p1Input = new HBox(3, pWagers, gameButtons);
		p1Input.setPadding(new Insets(470, 0, 0, 10));
		
		bWinnings1 = new Label("(Player 1) Total Winnings: $"+ playerOne.totalWinnings);
		bDanteBet1 = new Label("(Player 1) Ante Bet: $" + playerOne.anteBet); //<--->
		bPPBet1 = new Label("(Player 1) Pay Plus Wager: $" + playerOne.pairPlusBet);
		
		bWinnings1.setTextFill(Color.web("#000000"));
		bDanteBet1.setTextFill(Color.web("#000000"));
		bPPBet1.setTextFill(Color.web("#000000"));
		
		bWinnings1.setStyle("-fx-font-weight: bold");
		bDanteBet1.setStyle("-fx-font-weight: bold");
		bPPBet1.setStyle("-fx-font-weight: bold");
		
		VBox p1Winnings = new VBox(3, bWinnings1, bDanteBet1, bPPBet1);
		p1Winnings.setPadding(new Insets(0, 0, 0, 10));
		
		VBox p1 = new VBox(10, p1Winnings, p1Input);
		
		scene2Labels.add(ppL);
		scene2Labels.add(anL);
		scene2Labels.add(c);
		scene2Labels.add(d);
		//Player 2
//----------------------------------------------------------------	
		Label anL2 = new Label("Ante Wager (Player 2)");
		Label ppL2 = new Label("Pay Plus Wager (Player 2)");
		
		anL2.setTextFill(Color.web("#000000"));
		ppL2.setTextFill(Color.web("#000000"));
		
		anL2.setStyle("-fx-font-weight: bold");
		ppL2.setStyle("-fx-font-weight: bold");
		
		Label a = new Label(" ");
		Label b = new Label(" ");
		
		textPP2 = new TextField();
		textAnte2 = new TextField();
		
		textPP2.setDisable(true);

		 btnPP2 = new Button("Enter");
		 btnPP2.setDisable(true);
		 btnAn2 = new Button("Enter");
		
		VBox p2Wagers = new VBox(3, anL2, textAnte2, ppL2, textPP2);
		VBox p2Buttons = new VBox(3, a, btnAn2, b, btnPP2);
		
		HBox p2Input = new HBox(3, p2Buttons, p2Wagers);
		p2Input.setPadding(new Insets(470, 10, 0, 0));
		
		scene2Labels.add(ppL2);
		scene2Labels.add(anL2);
		scene2Labels.add(a);
		scene2Labels.add(b);
//----------------------------------------------------------------		


		//Money information (Winnings/Wagers)	
		bWinnings = new Label("(Player 2) Total Winnings: $"+ playerTwo.totalWinnings);
		bDanteBet = new Label("(Player 2) Ante Bet: $" + playerTwo.anteBet); //<--->
		bPPBet = new Label("(Player 2) Pay Plus Wager: $" + playerTwo.pairPlusBet);
		
		bWinnings.setTextFill(Color.web("#000000"));
		bDanteBet.setTextFill(Color.web("#000000"));
		bPPBet.setTextFill(Color.web("#000000"));
		
		bWinnings.setStyle("-fx-font-weight: bold");
		bDanteBet.setStyle("-fx-font-weight: bold");
		bPPBet.setStyle("-fx-font-weight: bold");
		
		//Instructions information 
		Label bTxt1 = new Label("\n\n\n\n\nMake Ante Bet and Optional Pay Plus Bet\n\n" + "Here are the odds for Pair Plus Bet:\n\n"
				+ "Straight Flush: 40 to 1\n"
				+ "Three of a kind: 30 to 1\n"
				+ "Stright: 6 to 1\n"
				+ "Flush: 3 to 1\n"
				+ "Pair: 1 to 1\n");
		scene2Labels.add(bTxt1);
		bTxt1.setFont(new Font("Arial", 16));
		bTxt1.setTextFill(Color.web("#111111"));
		bTxt1.setStyle("-fx-font-weight: bold");
		
		//Box manipulation 
		VBox betInstruct = new VBox(300, bTxt1, makeBet);
		VBox pWinnings = new VBox(3, bWinnings, bDanteBet, bPPBet, p2Input);//<---
		
		betInstruct.setAlignment(Pos.TOP_CENTER);
		
		
		//Border Manipulation
		border.setCenter(betInstruct);
		border.setRight(pWinnings);
		border.setLeft(p1);
		
		setBackground(border, "bg.jpg");
		border.setTop(createMenuBar());
		
		scene2Labels.add(bDanteBet);
		scene2Labels.add(bWinnings);
		scene2Labels.add(bPPBet);
		
		scene2Labels.add(bDanteBet1);
		scene2Labels.add(bWinnings1);
		scene2Labels.add(bPPBet1);
		
		//default white color
		for(int i = 0; i < scene2Labels.size(); ++i)
		{
			scene2Labels.get(i).setTextFill(Color.web("#ffffff"));
		}
		
		return new Scene(border, 1200, 700);
		
	}
	
	//return the start scene
	public Scene createStartScene(){
			
			btnStart = new Button("Start Game");
			
			BorderPane border = new BorderPane();
			paneMap.put("start", border);
			Label txt1 = new Label("\n\n\n\n\nWelcome to 3-Card Poker!\n"
					+ "Rules of the game:\n\n"
					+ "Each player must make an Ante Bet in order to get a hand. If the player decides to play a Play Bet with an equal value to the Ante bet will be made.\n"
					+ "The Players also have an option to make an optional Pair Plus Bet. An Ante Bet can only be up to $25 and not less than $5. The same goes for the Pair Plus Bet.\n"
					+ "The Pair Plus Bet will be evaluated if the player decides to play. The outcome between the player and the dealer does not affect the evaluation of the Pair Plus Bet.\n"
					+ "If the player wins their hand they will be returned a 1 to 1 return. If the dealer does not have a queen or higher the bets will be returned to the player(s).\n"
					+ "If the player Folds instead of Play they will loose their Ante Bet and Pair Plus Bet.\n\n"
					+ "Here are the order of winning hands: \n\n"
					+ "(1) Straight Flush\n"
					+ "(2) Three of a kind\n"
					+ "(3) Stright\n"
					+ "(4) Flush\n"
					+ "(5) Pair\n"
					+ "(6) High Card");
			scene2Labels.add(txt1);
			
			txt1.setTextFill(Color.web("#ffffff"));
			txt1.setStyle("-fx-font-weight: bold");
			txt1.setFont(new Font("Arial", 14));
			
			VBox startBox = new VBox(150, txt1, btnStart);
			startBox.setAlignment(Pos.TOP_CENTER);
			border.setCenter(startBox);
			
			border.setTop(createMenuBar());
			setBackground(border, "bg.jpg");
			//border.setStyle("-fx-background-color: LIMEGREEN;");
			
			return new Scene(border, 1200, 700);
		}
	
}
