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
//		playCheckmateVCheckamte(100);
//		playPieceEvalVPieceEval(1);
		
    	long startTime = System.nanoTime();

    	int numGameEach = 25;
    	for(int i = 0;i<numGameEach; i++) {
			randomVCheckmate("random");
		}
		long endTime = System.nanoTime();
    	long duration  = (endTime-startTime)/1000000000;
    	System.out.println((numGameEach)+" game(s) computed in "+duration+"s");
    	
    	
    	startTime = System.nanoTime();
		System.out.println("halfway :)");
		for(int i = 0;i<numGameEach; i++) {
			randomVCheckmate("checkmate");
		}
    	
		endTime = System.nanoTime();
    	duration  = (endTime-startTime)/1000000000;
    	System.out.println((numGameEach)+" game(s) computed in "+duration+"s");
	}
	
	public static void randomVCheckmate(String whitePlayer) {
		game game = new game();
		if(whitePlayer.equals("random")) {
			ArrayList<move> moves = game.generateMoves();
			while( !game.isGameFinshed() ) {
				if (game.getPlayerToMove() == 1 ) {// white random
					move moveToPlay = getRandomMove(game);
					if( moveToPlay instanceof promotionMove ) {
						promotionMove pMove = (promotionMove)moveToPlay;
						game.playMove(pMove, 1, pMove.getPromotionPiece());
					}else {
						game.playMove(moveToPlay, 1, "");
					}	
				}else if( game.getPlayerToMove() == -1 ) {// black checkmate
					move moveToPlay = getCheckMateMove(game);
					if(moveToPlay == null) {
						moveToPlay = getRandomMove(game);
					}
					if( moveToPlay instanceof promotionMove ) {
						promotionMove pMove = (promotionMove)moveToPlay;
						game.playMove(pMove, 1, pMove.getPromotionPiece());
					}else {
						game.playMove(moveToPlay, 1, "");
					}	
				}
				game.generateMoves();
			}
		}else if(whitePlayer.equals("checkmate")) {
			ArrayList<move> moves = game.generateMoves();
			while( !game.isGameFinshed() ) {
				if (game.getPlayerToMove() == -1 ) {// black - random
					move moveToPlay = getRandomMove(game);
					if( moveToPlay instanceof promotionMove ) {
						promotionMove pMove = (promotionMove)moveToPlay;
						game.playMove(pMove, 1, pMove.getPromotionPiece());
					}else {
						game.playMove(moveToPlay, 1, "");
					}	
				}else if( game.getPlayerToMove() == 1 ) {// white checkmate
					move moveToPlay = getCheckMateMove(game);
					if(moveToPlay == null) {
						moveToPlay = getRandomMove(game);
					}
					if( moveToPlay instanceof promotionMove ) {
						promotionMove pMove = (promotionMove)moveToPlay;
						game.playMove(pMove, 1, pMove.getPromotionPiece());
					}else {
						game.playMove(moveToPlay, 1, "");
					}	
				}
				game.generateMoves();
			}
		}else {
			System.out.println("error is reading whitePlayer");
		}
		
		logger logger = new logger();
		if(whitePlayer.equals("random")) {
			logger.logGame("randomVcheckmate", game.getEndCondition()+" | "+game.getFEN());
		}else if(whitePlayer.equals("checkmate")) {
			logger.logGame("checkmateVrandom", game.getEndCondition()+" | "+game.getFEN());
		}else {
			System.out.println("error logging");
		}
		
	}
	
	/*
	 * -------------------------------------
	 * 	 1 depth piece eval and checkmate
	 * -------------------------------------
	 */
	public static void playPieceEvalVPieceEval(int numGames) {
    	long startTime = System.nanoTime();
		for(int i = 0; i<numGames; i++) {
			pieceEvalVpieceEval();
		}
		long endTime = System.nanoTime();
    	long duration  = (endTime-startTime)/1000000000;
    	System.out.println(numGames+" game(s) computed in "+duration+"s");
	}
	
	public static void pieceEvalVpieceEval() {
		game game = new game();
		ArrayList<move> moves = game.generateMoves();
		while( !game.isGameFinshed() ) {
			ArrayList<Integer> moveEvals = new ArrayList<Integer>();
			for(move move:moves) {
				int playerToMove = game.getPlayerToMove();
				game tempGame = new game(game.getFEN());
				if( move instanceof promotionMove ) {
					promotionMove pMove = (promotionMove)move;
					tempGame.playMove(pMove, 1, pMove.getPromotionPiece());
				}else {
					tempGame.playMove(move, 1, "");
				}
				moveEvals.add(eval.evalPieces( tempGame.getBoard(), tempGame.getPlayerToMove() ));
			}
			
			int maxEval = Integer.MIN_VALUE;
			for(Integer eval : moveEvals) {
				if(eval > maxEval) {
					maxEval = eval;
				}
			}
			int indexOfMax = moveEvals.indexOf(maxEval);
			move moveToPlay = moves.get(indexOfMax);
			System.out.println(game.getFEN());
			System.out.println("max move: "+moveToPlay);
			System.out.println();
			
			if( moveToPlay instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)moveToPlay;
				game.playMove(pMove, 1, pMove.getPromotionPiece());
			}else {
				game.playMove(moveToPlay, 1, "");
			}
			moves.clear();
			moves = game.generateMoves();
		}
		logger logger = new logger();
		logger.logGame("pieceEvalVpieceEval", game.getEndCondition()+" | "+game.getFEN());
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
	public static move getCheckMateMove(game gameToCheck) {
		ArrayList<move> moves = gameToCheck.generateMoves();
		move moveToPlay = null;
		for(move move : moves) {
			game tempGame = new game ( gameToCheck.getFEN() );
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
		return moveToPlay;
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
