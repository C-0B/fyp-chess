package opponents;

import java.util.ArrayList;

import engine.game;
import engine.move;
import engine.promotionMove;

public class gameNodePos {
	game game;
	int evaluation = 0;
	int depth, maxDepth;
	boolean isLeafNode = false;
	int PLAYERTOMOVE;
	
	move lastMovePlayed = null;
	private move moveToPlay = null;
	public move getMoveToPlay() {return moveToPlay;}
	
	
	// Immediate subnodes from this node.
	// One for each legal move a resulting following posiiton
	ArrayList<gameNodePos> subNodes = new ArrayList<gameNodePos>();
	ArrayList<move> legalMoves = new ArrayList<move>();
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		game rootGame = new game("rnQq1k1r/pp2bppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R b KQ - 0 8");
		int maxTreeDepth = 2;
		
		gameNodePos tree = new gameNodePos(rootGame, 0, maxTreeDepth, null);
		
		long endTime = System.nanoTime();
		long duration  = (endTime-startTime)/1000000;
    	System.out.println(tree.getTotalPossibleGames()+" games counted in "+duration+" ms");
    	System.out.println(tree.evaluation);
    	System.out.println(tree.moveToPlay);
	}
	
	/**
	 * create the tree
	 * @param GAME root game
	 * @param DEPTH current depth, starts at 0
	 * @param MAXDEPTH maximum depth of the tree.
	 */
	public gameNodePos(game GAME, int DEPTH, int MAXDEPTH, move lastMove) {
		game = GAME;
		depth = DEPTH;
		maxDepth = MAXDEPTH;
		PLAYERTOMOVE = game.getPlayerToMove();
		legalMoves = game.generateMoves();
		lastMovePlayed = lastMove;
		
		//Create the tree
		if ( !(game.isGameFinshed()) ) {//if the has possible moves to be played
			if( depth < MAXDEPTH ) {
				for(move move :legalMoves) { // create all subnodes (0 legal moves if game is finished)
					
					game subGame = new game(game.getFEN());
					
					if( move instanceof promotionMove ) {
						promotionMove pMove = (promotionMove)move;
						subGame.playMove(pMove, 1, pMove.getPromotionPiece());
					}else {
						subGame.playMove(move, 1, "");
					}
					
					gameNodePos subNode = new gameNodePos(subGame, (this.depth+1), maxDepth, move);
					subNodes.add(subNode);
				}
			}else { isLeafNode = true; }// Max depth reached
		}else { isLeafNode = true; }// Game has ended 
		
		// Evaluate the tree
		evaluation = this.evaluate();
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
			if( this.isLeafNode ) { // no subnodes
				EVALUATION = eval.getPositionEval(this.game.getBoard());
			}else { // Has subnodes
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
		for(gameNodePos node : subNodes ) {
			int curMoveEval = node.evaluate();
			if (minEval >= curMoveEval) {
				minEval  = curMoveEval;
				moveToPlay = node.lastMovePlayed;
			}
		}
		return minEval;
	}
	
	public int maximumEval() {
		int maxEval = Integer.MIN_VALUE;
		for(gameNodePos node :subNodes) {
			int curMoveEval = node.evaluate();
			if (maxEval <= curMoveEval) {
				maxEval  = curMoveEval;
				moveToPlay = node.lastMovePlayed;
			}
		}
		return maxEval;
	}
/*
 * -----------------------------------------------------------------------------
 */
	public int getTotalPossibleGames() {
		if ( subNodes.size() == 0) {
			return 1;
		}else {
			int sum = 0;
			for(gameNodePos subNode : subNodes) {
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
			for(gameNodePos subNode : subNodes) {
				sum += subNode.getTotalNodes();
			}
			sum += 1;// count all nodes in the tree
			return sum;
		}
	}
}