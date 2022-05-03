package opponents;

import java.util.ArrayList;

import engine.promotionMove;
import engineByte.byteGame;
import engineByte.byteMove;
import engineByte.bytePromotionMove;

public class gameNodeByte {
	byteGame game;
	int evaluation = 0;
	int depth, maxDepth;
	boolean isLeafNode = false;
	int PLAYERTOMOVE;
	
	// Immediate subnodes from this node.
	// One for each legal move a resulting following posiiton
	ArrayList<gameNodeByte> subNodes = new ArrayList<gameNodeByte>();
	ArrayList<byteMove> legalMoves = new ArrayList<byteMove>();
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		byteGame rootGame = new byteGame("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
		int maxTreeDepth = 3;
		
		gameNodeByte tree = new gameNodeByte(rootGame, 0, maxTreeDepth);
		
		long endTime = System.nanoTime();
		long duration  = (endTime-startTime)/1000000;
    	System.out.println(tree.getTotalPossibleGames()+" games counted in "+duration+" ms");
	}
	
	/**
	 * create the tree
	 * @param GAME root game
	 * @param DEPTH current depth, starts at 0
	 * @param MAXDEPTH maximum depth of the tree.
	 */
	public gameNodeByte(byteGame GAME, int DEPTH, int MAXDEPTH) {
		game = GAME;
		depth = DEPTH;
		maxDepth = MAXDEPTH;
		PLAYERTOMOVE = game.getPlayerToMove();
		legalMoves = game.generateMoves();
		
		if ( !(game.isGameFinshed()) ) {//if the has possible moves to be played
			if( depth < MAXDEPTH ) {
				for(byteMove move :legalMoves) { // create all subnodes (0 legal moves if game is finished)
					
					byteGame subGame = new byteGame(game.getFEN());
					
					if( move instanceof bytePromotionMove ) {
						bytePromotionMove pMove = (bytePromotionMove)move;
						subGame.playMove(pMove, 1, pMove.getPromotionPiece());
					}else {
						subGame.playMove(move, 1, (byte)0);
					}
					
					gameNodeByte subNode = new gameNodeByte(subGame, (this.depth+1), maxDepth);
					subNodes.add(subNode);
				}
			}else {
				isLeafNode = true;
			}
		}else {
			isLeafNode = true;
		}
		
	}
	
	/**
	 * Minimax algorithm to evaluate a tree of gameNode objects with 
	 * @param rootNode
	 * @param currentDepth
	 * @param maxDepth
	 * @return evaluation
	 */
	public int minimaxGame(gameNodeByte rootNode, int currentDepth, int maxDepth) {
		if ( (rootNode.game.isGameFinshed()) ||
			 (currentDepth == maxDepth) ) {
			// static eval of board
			// return static eval
		}
		if ( game.getPlayerToMove() == 1 ) { // white to play, max
			int maximumEval = Integer.MIN_VALUE;
			for(gameNodeByte subGameNode : subNodes) {
				int evaluation = minimaxGame(subGameNode, currentDepth+1, maxDepth);
				maximumEval = Math.max(maximumEval, evaluation);
			}
			return maximumEval;
		} else if (game.getPlayerToMove() == -1) { // black to play, min
			int minimumEval = Integer.MAX_VALUE;
			for(gameNodeByte subGameNode : subNodes) {
				int evaluation = minimaxGame(subGameNode, currentDepth+1, maxDepth);
				minimumEval = Math.min(minimumEval, evaluation);
			}
			return minimumEval;
		}
		
		return 0;
	}
	
	/**
	 * Minimax algorithm with alpha beta pruning to
	 * increase performance
	 * @param rootNode
	 * @param currentDepth
	 * @param maxDepth
	 * @param alpha
	 * @param beta
	 * @return the final evaluation
	 */
	public int alphaBetaPruning(gameNodeByte rootNode, int currentDepth, int maxDepth, int alpha, int beta) {
		if ( (rootNode.game.isGameFinshed()) ||
				 (currentDepth == maxDepth) ) {
				// static eval of board
				// return static eval
			}
			if ( game.getPlayerToMove() == 1 ) { // white to play, max
				int maximumEval = Integer.MIN_VALUE;
				for(gameNodeByte subGameNode : subNodes) {
					maximumEval = alphaBetaPruning(subGameNode, currentDepth+1, maxDepth, alpha, beta);
					if (maximumEval >= beta) {
						break;
					}
					alpha = Math.max(alpha, maximumEval);
				}
				return maximumEval;
			} else if (game.getPlayerToMove() == -1) { // black to play, min
				int minimumEval = Integer.MAX_VALUE;
				for(gameNodeByte subGameNode : subNodes) {
					minimumEval = alphaBetaPruning(subGameNode, currentDepth+1, maxDepth, alpha, beta);
					if (minimumEval <= alpha) {
						break;
					}
					beta = Math.min(beta, minimumEval);
				}
				return minimumEval;
			}
			return 0;
	}
	
	
	public int getTotalPossibleGames() {
		if ( subNodes.size() == 0) {
			return 1;
		}else {
			int sum = 0;
			for(gameNodeByte subNode : subNodes) {
				sum += subNode.getTotalPossibleGames();
			}
			return sum;
		}
	}
	
	public int getTotalNodes() {
		if ( isLeafNode ) {
			return 1;
		}else {
			int sum = 0;
			for(gameNodeByte subNode : subNodes) {
				sum += subNode.getTotalNodes();
			}
			sum += 1;// count all nodes in the tree
			return sum;
		}
	}
/*
 * -----------------------------------------------------------------------------
 */
	public int evaluate() {
		int EVALUATION = 0;
		
		if(game.isGameFinshed()) {// win / loss / draw
			if(game.getEndCondition().equals("white win")) {
				EVALUATION = Integer.MAX_VALUE;
			}else if(game.getEndCondition().equals("black win")){
				EVALUATION = Integer.MIN_VALUE;
			}else {// draw or stalemate or 50 move limit
				EVALUATION = 0;
			}
			
		}else {//game not finished
			if( depth >= maxDepth) { // no subnodes
				//eval unfinished position
				//eval function
			}else { // has subnodes
				if(PLAYERTOMOVE == 1) {//white to move
					EVALUATION = maximumEval();
				}else {//black to move
					EVALUATION = minimumEval();
				}
			}
		}
		return EVALUATION;
	}
	
	
	public int minimumEval() {
		int minEval = Integer.MAX_VALUE;
		for(gameNodeByte node : subNodes ) {
			int curMoveEval = node.evaluate();
			if (curMoveEval >= minEval) {
				minEval = curMoveEval;
			}
		}
		return minEval;
	}
	
	public int maximumEval() {
		int maxEval = Integer.MIN_VALUE;
		for(gameNodeByte node :subNodes) {
			int curMoveEval = node.evaluate();
			if (curMoveEval >= maxEval) {
				maxEval = curMoveEval;
			}
		}
		return maxEval;
	}
}