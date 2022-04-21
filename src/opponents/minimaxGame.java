package opponents;

import java.util.ArrayList;

import engine.game;
import engine.move;
import engine.promotionMove;


public class minimaxGame {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		game rootGame = new game("rn2kbnr/p1pb1ppp/3p1q2/1p2P3/2PN4/8/PP3PPP/RNBQKB1R b KQkq - 0 7");
		int maxTreeDepth = 1;
		ArrayList<moveEval> moveEvals = new ArrayList<moveEval>();
		ArrayList<move> possibleMoves = rootGame.generateMoves();
		
		int count = 0;
		for( move possibleMove : possibleMoves) {
			System.out.println(count++);
			game tempGame = new game(rootGame.getFEN());
			
			if( possibleMove instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)possibleMove;
				tempGame.playMove(pMove, 1, pMove.getPromotionPiece());
			}else {
				tempGame.playMove(possibleMove, 1, "");
			}
			
			int eval = minimax(tempGame, 0, maxTreeDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
			moveEvals.add(new moveEval(possibleMove, eval));
		}
		
		System.out.println(moveEval.getBestMoveEval(rootGame.getPlayerToMove(), moveEvals));
		
		
		long endTime = System.nanoTime();
		long duration  = (endTime-startTime)/1000000000;
    	System.out.println("minimax completed in "+duration+"s");
	}

	
	public static int minimax(game currentGame, int currentDepth, int maxDepth, int alpha, int beta) {
		System.out.println("minimax called");
		int minimaxEval = 0;
		
		// Evaluating a board
		if ( (currentDepth==maxDepth) || (currentGame.isGameFinshed()) ) {
			System.out.println(currentDepth==maxDepth);
			System.out.println(currentGame.isGameFinshed());
			System.out.println("end of depth");
			minimaxEval += 3*(eval.getPieceEval(currentGame.getBoard(), currentGame.getPlayerToMove()));
			minimaxEval += eval.getPawnStrucutureValue(currentGame.getBoard(), currentGame.getPlayerToMove());
			return minimaxEval;
		
			
		// MAX
		} else if ( currentGame.getPlayerToMove() == 1 ) { 
			ArrayList<move> moves = currentGame.generateMoves();
			int maximumEval = Integer.MIN_VALUE;
			for(move moveToCheck : moves) {
				game nextGame = new game(currentGame.getFEN());

				if( moveToCheck instanceof promotionMove ) {
					promotionMove pMove = (promotionMove)moveToCheck;
					nextGame.playMove(pMove, 1, pMove.getPromotionPiece());
				}else {
					nextGame.playMove(moveToCheck, 1, "");
				}

				int currentEval = minimax(nextGame, currentDepth+1, maxDepth, alpha, beta);
				maximumEval = Math.max(currentEval, alpha);
				alpha = Math.max(alpha, currentEval);
				if ( alpha >= beta ) {
					break;
				}
			}
			return maximumEval;
			
			
		// MIN
		}else if ( currentGame.getPlayerToMove() == -1 ) {
			ArrayList<move> moves = currentGame.generateMoves();
			int minimumEval = Integer.MAX_VALUE;
			for(move moveToCheck : moves) {
				game nextGame = new game(currentGame.getFEN());

				if( moveToCheck instanceof promotionMove ) {
					promotionMove pMove = (promotionMove)moveToCheck;
					nextGame.playMove(pMove, 1, pMove.getPromotionPiece());
				}else {
					nextGame.playMove(moveToCheck, 1, "");
				}
				
				int currentEval = minimax(nextGame, currentDepth+1, maxDepth, alpha, beta);
				minimumEval = Math.max(currentEval, alpha);
				beta = Math.min(beta, currentEval);
				if ( alpha >= beta ) {
					break;
				}
			}
			return minimumEval;
		}else {
			System.out.println("ERROR");
			return minimaxEval;
		}
	}
/*
 * -----------------------------------------------------------------------------
 */
}
