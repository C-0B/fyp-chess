package opponents;

import java.util.ArrayList;
import java.util.Random;

import engine.game;
import engine.move;
import engine.promotionMove;
import engineByte.byteGame;
import engineByte.byteMove;
import log.logger;

public class PlayOpponents {
	
	public static void main(String[] args) {
    	long startTime = System.nanoTime();
    	long endTime = 0;
    	long duration = 0;
    	
		int numGameToPlay  = 50;
		String[] whitePlayers = {"random"};
		String[] blackPlayers = {"alpha2"};
		for(String white:whitePlayers) {
			for(String black:blackPlayers) {
		    	startTime = System.nanoTime();
				for(int i=0; i<numGameToPlay; i++) {
					playGame(white, black);

					endTime = System.nanoTime();
			    	duration  = (endTime-startTime)/1000000;
			    	System.out.println(white+"--"+black+" "+i+" game(s) computed in "+duration+"ms");
				}
				
				logger logger = new logger();
				logger.logGame(white+"--"+black, white+"--"+black+" "+numGameToPlay+" game(s) computed in "+duration+" ms");
				
				System.out.println();
		    	System.out.println(white+"--"+black+" "+numGameToPlay+" game(s) computed in "+duration+" ms");
		    	System.out.println();
			}
		}
	}
	
	/* random
	 * checkmate
	 * mat
	 * pos
	 * minimax2
	 * alpha2
	 * alpha4 */
	public static void playGame(String whitePlayer,String blackPlayer) {
		game game = new game();
		game.generateMoves();
		while ( !game.isGameFinshed() ) {
			move moveToPlay = null;
			
			//White player
			if(whitePlayer.equals("random")) 		 { moveToPlay = getRandomMove(game); }
			else if(whitePlayer.equals("checkmate")) { moveToPlay = getCheckMateMove(game); }
			else if(whitePlayer.equals("mat")) 		 { moveToPlay = getMaterialValueMove(game); }
			else if(whitePlayer.equals("pos")) 		 { moveToPlay = getMaterialPosValueMovePos(game); }
			else if(whitePlayer.equals("minimax2"))  { moveToPlay = getMinimaxMove(game); }
			else if(whitePlayer.equals("alpha2")) 	 { moveToPlay = getAlphaBetaMove(game, 2); }
			else if(whitePlayer.equals("alpha4")) 	 { moveToPlay = getAlphaBetaMove(game, 4); }
			else {System.out.println("error with white player");}
			if(moveToPlay == null) {
				moveToPlay = getRandomMove(game);
			}
			
			//Play whites move
			if( moveToPlay instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)moveToPlay;
				game.playMove(pMove, 1, pMove.getPromotionPiece());
			}else {
				game.playMove(moveToPlay, 1, "");
			}
			
			game.generateMoves();
			if( !game.isGameFinshed() ) {//Black Player
				if(blackPlayer.equals("random")) 		 { moveToPlay = getRandomMove(game); }
				else if(blackPlayer.equals("checkmate")) { moveToPlay = getCheckMateMove(game); }
				else if(blackPlayer.equals("mat")) 		 { moveToPlay = getMaterialValueMove(game); }
				else if(blackPlayer.equals("pos")) 		 { moveToPlay = getMaterialPosValueMovePos(game); }
				else if(blackPlayer.equals("minimax2"))  { moveToPlay = getMinimaxMove(game); }
				else if(blackPlayer.equals("alpha2")) 	 { moveToPlay = getAlphaBetaMove(game, 2); }
				else if(blackPlayer.equals("alpha4")) 	 { moveToPlay = getAlphaBetaMove(game, 4); }
				else {System.out.println("error with black player");}
				if(moveToPlay == null) {
					moveToPlay = getRandomMove(game);
				}
				
				// Play black move
				if( moveToPlay instanceof promotionMove ) {
					promotionMove pMove = (promotionMove)moveToPlay;
					game.playMove(pMove, 1, pMove.getPromotionPiece());
				}else {
					game.playMove(moveToPlay, 1, "");
				}

				game.generateMoves();

			}else {//Game ended
				break;
			}	
		}
		logger logger = new logger();
		logger.logGame(whitePlayer+"--"+blackPlayer, game.getEndCondition()+" | "+game.getFEN());
	}
	
	//Get move functions =================================================================================
	public static move getRandomMove(game game) {
		Random random = new Random();
		ArrayList<move> moves = game.generateMoves();
		int randomInt = random.nextInt(moves.size());
		move randomMove = moves.get( randomInt );
		return randomMove;
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
	
	public static move getMaterialValueMove(game game) {
		gameNodeMat tree = new gameNodeMat(game, 0, 1, null);
		return tree.getMoveToPlay();
	}
	
	public static move getMaterialPosValueMovePos(game game) {
		gameNodePos tree = new gameNodePos(game, 0, 1, null);
		return tree.getMoveToPlay();
	}
	
	public static move getMinimaxMove(game game) {
		gameNode tree = new gameNode(game, 0, 2, null);
		return tree.getMoveToPlay();
	}
	
	
	public static move getAlphaBetaMove(game game, int depth) {
		alphaBetaGameNode tree = new alphaBetaGameNode(game, 0, depth, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return tree.getMoveToPlay();
	}
	//==============================================================================================================

	
	
	// AlphaBeta (4) ----------------------------------------------------------------
	public static void AlphaBetaGameDepth4() {
		game game = new game();
		game.generateMoves();
		while ( !game.isGameFinshed() ) {
			move move = getAlphaBetaMove(game, 4);
			if( move instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)move;
				game.playMove(pMove, 1, pMove.getPromotionPiece());
			}else {
				game.playMove(move, 1, "");
			}
			game.generateMoves();
		}
		
		logger logger = new logger();
		logger.logGame("AlphaBetaGameDepth4", game.getEndCondition()+" | "+game.getFEN());
	}
	

	
	// Minimax (2) ----------------------------------------------------------------
	public static void minimaxGameDepth2() {
		game game = new game();
		game.generateMoves();
		while ( !game.isGameFinshed() ) {
			move move = getMinimaxMove(game);
			if( move instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)move;
				game.playMove(pMove, 1, pMove.getPromotionPiece());
			}else {
				game.playMove(move, 1, "");
			}
			game.generateMoves();
		}
		
		logger logger = new logger();
		logger.logGame("minimaxGameDepth2", game.getEndCondition()+" | "+game.getFEN());
	}

	
	
	// Material Game ----------------------------------------------------------------
	public static void materialVmaterial() {
		game game = new game();
		game.generateMoves();
		while ( !game.isGameFinshed() ) {
			move move = getMaterialPosValueMovePos(game);
			if( move instanceof promotionMove ) {
				promotionMove pMove = (promotionMove)move;
				game.playMove(pMove, 1, pMove.getPromotionPiece());
			}else {
				game.playMove(move, 1, "");
			}
			game.generateMoves();
		}
		
		logger logger = new logger();
		logger.logGame("positionVposition", game.getEndCondition()+" | "+game.getFEN());
	}
	

	
	//----------------------------------------------------------------

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
				moveEvals.add(eval.getPieceEval( tempGame.getBoard()) );
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
	 * ----------------------------------------------
	 * 	   	  		   Random play
	 * ----------------------------------------------
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
	
	public static byteMove getRandomByteMove(byteGame game) {
		Random random = new Random();
		ArrayList<byteMove> moves = game.generateMoves();
		byteMove randomMove = moves.get( random.nextInt(moves.size()) );
		return randomMove;
	}	
}
