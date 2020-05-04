public class TestMain {
	public static void main(String[] args) {
		Deck curDeck = new Deck();
		System.out.println("Hello\n");
		for (int i = 0; i < curDeck.size(); ++i) {
			System.out.println(i + ": " + '(' + curDeck.get(i).getSuit() + ',' + curDeck.get(i).getValue() +')');
		}
	}
}
