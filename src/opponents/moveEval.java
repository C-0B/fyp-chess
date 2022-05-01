package opponents;

import java.util.ArrayList;

import engine.move;

public class moveEval {
	move move;
	int eval;
	
	public move getMove() { return move; }
	public int getEval() { return eval; }
	public void setMove(move newMove) { move = newMove; }
	public void setEval(int newEval) { eval = newEval; }
	public void addEval(int newEval) { eval += newEval; }
	
	public moveEval(move MOVE, int EVAL) {
		move = MOVE;
		eval = EVAL;
	}
	
	
	public static moveEval getBestMoveEval(int playerToMove, ArrayList<moveEval> moveEvals) {
		moveEval bestMoveEval = null;
		if(playerToMove == 1) {
			int highestEval = Integer.MIN_VALUE;
			for(moveEval moveEval : moveEvals) {
				if (moveEval.getEval() >= highestEval) {
					bestMoveEval = moveEval;
				}
			}
		}else if (playerToMove == -1) {
			int lowestEval = Integer.MAX_VALUE;
			for(moveEval moveEval : moveEvals) {
				if (moveEval.getEval() <= lowestEval) {
					bestMoveEval = moveEval;
				}
			}
		}
		return bestMoveEval;
	}
	
	@Override
	public String toString() {
		return getMove()+" | "+getEval();
	}

}
