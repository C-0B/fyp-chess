package opponents;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import engine.game;
import engine.move;

class gameNodeTest {

	@Test
	void PerftPos1() {
		game rootGame = new game();
		int maxTreeDepth = 3;
		
		gameNode tree = new gameNode(rootGame, 0, maxTreeDepth, null);
		
    	int numGames = tree.getTotalPossibleGames();
    	assertEquals(8902	, numGames);
	}
	
	@Test
	void PerftPos2() {
		game rootGame = new game("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
		int maxTreeDepth = 2;
		
		gameNode tree = new gameNode(rootGame, 0, maxTreeDepth, null);
		
    	int numGames = tree.getTotalPossibleGames();
    	assertEquals(2039	, numGames);
	}
	
	@Test
	void PerftPos3() {
		game rootGame = new game("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1");
		int maxTreeDepth = 3;
		
		gameNode tree = new gameNode(rootGame, 0, maxTreeDepth, null);
		
    	int numGames = tree.getTotalPossibleGames();
    	assertEquals(2812	, numGames);
	}
	
	@Test
	void PerftPos4() {
		game rootGame = new game("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
		int maxTreeDepth = 3;
		
		gameNode tree = new gameNode(rootGame, 0, maxTreeDepth, null);
		
    	int numGames = tree.getTotalPossibleGames();
    	assertEquals(9467	, numGames);
	}
	
	@Test
	void PerftPos5() {
		game rootGame = new game("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
		int maxTreeDepth = 3;
		
		gameNode tree = new gameNode(rootGame, 0, maxTreeDepth, null);
		
    	int numGames = tree.getTotalPossibleGames();
    	assertEquals(62379	, numGames);
	}
	
	@Test
	void PerftPos6() {
		game rootGame = new game("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
		int maxTreeDepth = 2;
		
		gameNode tree = new gameNode(rootGame, 0, maxTreeDepth, null);
		
    	int numGames = tree.getTotalPossibleGames();
    	assertEquals(2079	, numGames);
	}
	

}
