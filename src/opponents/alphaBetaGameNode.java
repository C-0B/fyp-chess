package opponents;

import java.util.ArrayList;

import engine.game;
import engine.move;
import engine.promotionMove;

public class alphaBetaGameNode {
	game game;
	int evaluation = 0;
	int depth, maxDepth;
	boolean isLeafNode = false;
	int PLAYERTOMOVE;
	
	move lastMovePlayed = null;
	move moveToPlay = null;
	
	ArrayList<alphaBetaGameNode> subNodes = new ArrayList<alphaBetaGameNode>();
	ArrayList<move> legalMoves = new ArrayList<move>();
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		game rootGame = new game("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
		int maxTreeDepth = 3;
		
		alphaBetaGameNode tree = new alphaBetaGameNode(rootGame, 0, maxTreeDepth, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		long endTime = System.nanoTime();
		long duration  = (endTime-startTime)/1000000;
    	System.out.println(tree.getTotalPossibleGames()+" games counted in "+duration+" ms");
    	System.out.println(tree.evaluation);
    	System.out.println(tree.moveToPlay);
	}
	
	public alphaBetaGameNode(game GAME, int DEPTH, int MAXDEPTH, move lastMove, int alpha, int beta) {
		game = GAME;
		depth = DEPTH;
		maxDepth = MAXDEPTH;
		PLAYERTOMOVE = game.getPlayerToMove();
		legalMoves = game.generateMoves();
		lastMovePlayed = lastMove;
		
		//Create the tree
		if ( !(game.isGameFinshed()) ) {//if the has possible moves to be played
			if( depth < MAXDEPTH ) {
				if(this.game.getPlayerToMove() == 1) {//White to move MAXIMUM
					alphaBetaGameNode maxEvalSubNode = null;
					
					for(move move :legalMoves) { // create all subnodes (0 legal moves if game is finished)
						game subGame = new game(game.getFEN());
						if( move instanceof promotionMove ) {
							promotionMove pMove = (promotionMove)move;
							subGame.playMove(pMove, 1, pMove.getPromotionPiece());
						}else {
							subGame.playMove(move, 1, "");
						}
						
						alphaBetaGameNode subNode = new alphaBetaGameNode(subGame, (this.depth+1), maxDepth, move, alpha, beta);
						subNodes.add(subNode);
						maxEvalSubNode = max(maxEvalSubNode, subNode);
						alpha = Math.max(alpha, subNode.evaluation);
						if( beta <= alpha ) {
							evaluation = maxEvalSubNode.evaluation;
							break;
						}
					}
					evaluation = this.staticEvaluate();
				}else {//Black to move MINIMAL
					alphaBetaGameNode minEvalSubNode = null;
					
					for(move move :legalMoves) { // create all subnodes (0 legal moves if game is finished)
						game subGame = new game(game.getFEN());
						if( move instanceof promotionMove ) {
							promotionMove pMove = (promotionMove)move;
							subGame.playMove(pMove, 1, pMove.getPromotionPiece());
						}else {
							subGame.playMove(move, 1, "");
						}
						
						alphaBetaGameNode subNode = new alphaBetaGameNode(subGame, (this.depth+1), maxDepth, move, alpha, beta);
						subNodes.add(subNode);
						minEvalSubNode = min(minEvalSubNode, subNode);
						beta = Math.min(beta, subNode.evaluation);
						if( beta <= alpha ) {
							evaluation = minEvalSubNode.evaluation;
							break;
						}
					}
					evaluation = this.staticEvaluate();
				}
			}else {// Max depth reached
				isLeafNode = true;
				evaluation = this.staticEvaluate();
			}
		}else {//Game finished
			isLeafNode = true;
			evaluation = this.staticEvaluate();
		}// Game has ended 
	}
	

	
	public static alphaBetaGameNode min(alphaBetaGameNode a,alphaBetaGameNode b) {
		if(a==null) {
			return b;
		}
		if(a.evaluation <= b.evaluation) {
			return a;
		}else {
			return b;
		}
	}
	
	public static alphaBetaGameNode max(alphaBetaGameNode a,alphaBetaGameNode b) {
		if(a==null) {
			return b;
		}
		if(a.evaluation >= b.evaluation) {
			return a;
		}else {
			return b;
		}
	}
	

// --------- Static Evaluation -----------------------------------------------------------
	public int staticEvaluate() {
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
				EVALUATION = eval.getPieceEval(this.game.getBoard());
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
		for(alphaBetaGameNode node : subNodes ) {
			int curMoveEval = node.staticEvaluate();
			if (minEval >= curMoveEval) {
				minEval  = curMoveEval;
				moveToPlay = node.lastMovePlayed;
			}
		}
		return minEval;
	}
	
	public int maximumEval() {
		int maxEval = Integer.MIN_VALUE;
		for(alphaBetaGameNode node :subNodes) {
			int curMoveEval = node.staticEvaluate();
			if (maxEval <= curMoveEval) {
				maxEval  = curMoveEval;
				moveToPlay = node.lastMovePlayed;
			}
		}
		return maxEval;
	}
// ---------- Node Count --------------------------------------------------------------------
	public int getTotalPossibleGames() {
		if ( subNodes.size() == 0) {
			return 1;
		}else {
			int sum = 0;
			for(alphaBetaGameNode subNode : subNodes) {
				sum += subNode.getTotalPossibleGames();
			}
			return sum;
		}
	}
}
