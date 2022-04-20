package opponents;

import engine.game;

public class gameTree {
	
	public gameTree(game rootGame, int maxTreeDepth) {
		gameNode tree = new gameNode(rootGame,0 , maxTreeDepth);
	}
	
}
