package opponents;

import engine.game;

public class Opponent {
	game game;
	int opponentColour;
	
	public Opponent(int opponentColour) {
		game = new game();
		this.opponentColour = opponentColour;	
	}
	
	public static void main(String args[]) {}
	
	/** update player2 to the game of player 1 */
	private static void syncPlayers(Opponent player1, Opponent player2) {
		player2.game = new game(player1.game.getFEN());
	}
}
