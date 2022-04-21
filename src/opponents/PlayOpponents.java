package opponents;

import java.util.ArrayList;
import java.util.Random;

import engine.game;
import engine.move;
import engine.promotionMove;
import log.logger;

public class PlayOpponents {
	
	public static void main(String[] args) {
//		playRandomVRandom(5000);
		playCheckmateVCheckamte(100);
	}
	
	
	
	/* 
	 * -------------------------------------
	 * 	   1 depth checkmate - random
	 * -------------------------------------
	 */
	public static void playCheckmateVCheckamte(int numGames) {
    	long startTime = System.nanoTime();
		for(int i = 0; i<numGames; i++) {
			checkmateVcheckmate();
		}
		long endTime = System.nanoTime();
    	long duration  = (endTime-startTime)/1000000000;
    	System.out.println(numGames+" game(s) computed in "+duration+"s");
	}
	
	public static void checkmateVcheckmate() {
		game game = new game();
		ArrayList<move> moves = game.generateMoves();
		while( !game.isGameFinshed() ) {
			Random random = new Random();
			move moveToPlay = moves.get( random.nextInt(moves.size()) );
			for(move move : moves) {
				game tempGame = new game ( game.getFEN() );
				if( move instanceof promotionMove ) {
					promotionMove pMove = (promotionMove)move;
					tempGame.playMove(pMove, 1, pMove.getPromotionPiece());
				}else {
					tempGame.playMove(move, 1, "");
				}
				tempGame.generateMoves();
				//If a checkmate is found play that move
				if( tempGame.isGameFinshed() ) {
					if( tempGame.getEndCondition().equals("white win") ||
						tempGame.getEndCondition().equals("black win")) {
						// You cannot checkmate yourself in one of your own turns
						System.out.println(game.getFEN());
						System.out.println("winning condition found: "+move);
						System.out.println();
						if( move instanceof promotionMove ) {
							promotionMove pMove = (promotionMove)move;
							moveToPlay = pMove;
						}else {
							moveToPlay = move;
						}
						break;
					}
				}
			}
			
			if( moveToPlay instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)moveToPlay;
				game.playMove(pMove, 1, pMove.getPromotionPiece());
			}else {
				game.playMove(moveToPlay, 1, "");
			}
			moves = game.generateMoves();
		}
		logger logger = new logger();
		logger.logGame("checkmateVcheckmate", game.getEndCondition()+" | "+game.getFEN());
	}
	
	/* 
	 * -------------------------
	 * 	   	  Random play
	 * -------------------------
	 */
	public static void playRandomVRandom(int numGames) {
    	long startTime = System.nanoTime();
		for(int i = 0; i<numGames; i++) {
			randomVrandom();
		}
		long endTime = System.nanoTime();
    	long duration  = (endTime-startTime)/1000000000;
    	System.out.println(numGames+" game(s) computed in "+duration+"s");
	}
	
	public static void randomVrandom() {
		game game = new game();
		ArrayList<move> moves = game.generateMoves();
		Random random = new Random();
		while( !game.isGameFinshed() ) {
			move randomMove = moves.get( random.nextInt(moves.size()) );
			if( randomMove instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)randomMove;
				game.playMove(randomMove, 1, pMove.getPromotionPiece());
			}else {
				game.playMove(randomMove, 1, "");
			}
			moves = game.generateMoves();
		}
		logger logger = new logger();
		logger.logGame("randomVrandom-2", game.getEndCondition()+" | "+game.getFEN());
	}
	
	public static move getRandomMove(game game) {
		Random random = new Random();
		ArrayList<move> moves = game.generateMoves();
		move randomMove = moves.get( random.nextInt(moves.size()) );
		return randomMove;
	}


}
