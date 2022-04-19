package opponents;

import java.util.ArrayList;
import java.util.Random;

import engine.game;
import engine.move;
import engine.promotionMove;
import log.logger;

public class PlayOppoents {
	
	public static void main(String[] args) {
		for(int i = 0; i<500; i++) {
			randomVrandom();
		}
		
	}
	
	
	public static void randomVrandom() {
		game game = new game();
		ArrayList<move> moves = game.generateMoves();
		while( !game.isGameFinshed() ) {
			Random random = new Random();
			move randomMove = moves.get( random.nextInt(moves.size()) );
			if( randomMove instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)randomMove;
				game.playMove(randomMove, 1, pMove.getPromotionPiece());
			}
			game.playMove(randomMove, 1, "");
			moves = game.generateMoves();
		}
//		System.out.println(game.getFEN()+"\n");
		logger logger = new logger();
		logger.logGame("randomVrandom", game.getEndCondition()+" | "+game.getFEN());
	}

}
