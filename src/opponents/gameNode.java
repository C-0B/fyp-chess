package opponents;

import java.util.ArrayList;

import engine.game;
import engine.move;
import engine.promotionMove;

public class gameNode {
	game game;
	int evaluation = 0;
	int depth, maxDepth;
	boolean isLeafNode = true;
	int PLAYERTOMOVE;
	
	// Immediate subnodes from this node.
	// One for each legal move a resulting following posiiton
	ArrayList<gameNode> subNodes = new ArrayList<gameNode>();
	ArrayList<move> legalMoves = new ArrayList<move>();
	
	public gameNode(game GAME, int DEPTH, int MAXDEPTH) {
		game = GAME;
		depth = DEPTH;
		maxDepth = MAXDEPTH;
		PLAYERTOMOVE = game.getPlayerToMove();
		legalMoves = game.generateMoves();
		
		if( depth < MAXDEPTH ) {
			for(move move :legalMoves) {
				//Copy the nodes game to all subnodes
				game subGame = new game(game.getFEN());
				
				if( move instanceof promotionMove ) {
					promotionMove pMove = (promotionMove)move;
					subGame.playMove(pMove, 1, pMove.getPromotionPiece());
				}else {
					subGame.playMove(move, 1, "");
				}
				
				gameNode subNode = new gameNode(subGame, (this.depth+1), maxDepth);
				subNodes.add(subNode);
			}
			isLeafNode = false;
		}
	}
	
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
		for(gameNode node : subNodes ) {
			int curMoveEval = node.evaluate();
			if (curMoveEval >= minEval) {
				minEval = curMoveEval;
			}
		}
		return minEval;
	}
	
	public int maximumEval() {
		int maxEval = Integer.MIN_VALUE;
		for(gameNode node :subNodes) {
			int curMoveEval = node.evaluate();
			if (curMoveEval >= maxEval) {
				maxEval = curMoveEval;
			}
		}
		return maxEval;
	}
	
	public void printNumSubNodes() {
		System.out.println(subNodes.size()+" subnodes");
	}
}
