package opponents;

import java.util.ArrayList;
import java.util.Random;

import engine.game;
import engine.move;
import engine.promotionMove;

public class OpponentCheckmate{
	
	public static void main(String[] args) {
		game game = new game("6q1/8/8/8/8/8/5k1K/8 b - - 0 1");
//		game = new game("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		move tempMove = getCheckmateMove(game);
		System.out.println(tempMove);
		System.out.println(game.getPlayerToMove());
		if( tempMove instanceof promotionMove ) {
			promotionMove pMove = (promotionMove)tempMove;
			game.playMove(pMove, 1, pMove.getPromotionPiece());
		}else {
			game.playMove(tempMove, 1, "");
		}
	}
	
	public static move getCheckmateMove(game game) {
		ArrayList<move> moves = game.generateMoves();
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
						game.playMove(move, 1, pMove.getPromotionPiece());
						return pMove;
					}else {
						game.playMove(move, 1, "");
						return move;
					}
				}
			}
		}//end of loop through moves
		//No Checkmate found, play random move
		Random random = new Random();
		System.out.println(game.getFEN());
		move randomMove = moves.get( random.nextInt(moves.size()) );
		if( randomMove instanceof promotionMove ) {
			promotionMove pMove = (promotionMove)randomMove;
			game.playMove(randomMove, 1, pMove.getPromotionPiece());
			return pMove;
		}else {
			game.playMove(randomMove, 1, "");
			return randomMove;
		}
	}

}
