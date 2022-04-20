package opponents;

import java.util.ArrayList;
import java.util.Random;

import engine.game;
import engine.move;
import engine.promotionMove;
import log.logger;

public class PlayOpponents {
	
	public static void main(String[] args) {
    	long startTime = System.nanoTime();
    	int numGames = 8000;
		for(int i = 0; i<numGames; i++) {
			randomVrandom();
		}
		long endTime = System.nanoTime();
    	long duration  = (endTime-startTime)/1000000000;
    	System.out.println(numGames+" game(s) computed in "+duration+"s");
		
	}
		
	public static void checkmateVcheckmate() {
		game game = new game();
		System.out.println(game.generateMoves());
		while( !game.isGameFinshed() ) {
			System.out.println(game.getPlayerToMove());
			move checkmateMove = OpponentCheckmate.getCheckmateMove(game);
			System.out.println(checkmateMove);
			if( checkmateMove instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)checkmateMove;
				game.playMove(pMove, 1, pMove.getPromotionPiece());
			}else {
				game.playMove(checkmateMove, 1, "");
			}

			System.out.println(game.getPlayerToMove());
			break;
//			System.out.println(game.getPlayerToMove()+" "+checkmateMove);
		}
		logger logger = new logger();
		logger.logGame("checkmateVcheckmate", game.getEndCondition()+" | "+game.getFEN());
	}
	
	public static void randomVrandom() {
		game game = new game();
		ArrayList<move> moves = game.generateMoves();
		Random random = new Random();
		while( !game.isGameFinshed() ) {
			if( moves.size() == 0) {
				System.out.println("no moves for game");
				System.out.println(game.getFEN());
				System.out.println();
				break;
			}
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
		logger.logGame("randomVrandom2", game.getEndCondition()+" | "+game.getFEN());
	}
	
	
	public static move getRandomMove(game game) {
		Random random = new Random();
		ArrayList<move> moves = game.generateMoves();
		move randomMove = moves.get( random.nextInt(moves.size()) );
		return randomMove;
	}


}
