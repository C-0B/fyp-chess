package engine_1;

import engine_1.pieces.piece;
import engine_bb.board;
import chessFunc.*;

public class move {
	int PLAYER_TO_MOVE = 1; //1 = white -1 = black
	piece PIECE;
	int START_SQUARE = 0;
	int TARGET_SQUARE = 0;
	String CAPTURED_PIECE = ""; //Empty if no piece captured
	
	
//	maybe :?
//	float value = 0;//? for evaluation
//	String[][] board;
//	boolean isPieceTaken;
	
	public move(int playerToMove, piece piece, int startSquare, int targetSquare){
		PLAYER_TO_MOVE = playerToMove;
		PIECE = piece;
		START_SQUARE = startSquare;
		TARGET_SQUARE = targetSquare;
	}
	
	public move(int playerToMove, piece piece, int startSquare, int targetSquare, String capturePiece){
		PLAYER_TO_MOVE = playerToMove;
		PIECE = piece;
		START_SQUARE = startSquare;
		TARGET_SQUARE = targetSquare;
		CAPTURED_PIECE = capturePiece;
	}
	
	/**
	 * Checks if the the opponents king is on the target square<p>
	 * No piece can capture a king
	 */
	public boolean isKingTarget() {
		if(getPLAYER_TO_MOVE() == 1){// White to move
			if(CAPTURED_PIECE.equals("k")) {// Black king
				return true;
			}
		}else if(getPLAYER_TO_MOVE() == -1) {// Black to move
			if(CAPTURED_PIECE.equals("K")) {// White king
				return true;
			}
		}
		return false;
	}
		
	/**
	 *  Compare two moves
	 */
	public boolean equals(move move2) {
		if(this.getPLAYER_TO_MOVE() == move2.getPLAYER_TO_MOVE()) {
			if(this.PIECE.equals(move2.PIECE)) {
				if(this.START_SQUARE == move2.START_SQUARE) {
					if(this.getTARGET_SQUARE() == move2.getTARGET_SQUARE()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return PIECE.getNAME()+"@"+START_SQUARE+" -> "+getTARGET_SQUARE()+" \n  "+func.sqNumToStr(this.START_SQUARE)+" -> "+func.sqNumToStr(getTARGET_SQUARE());
	}

	public int getTARGET_SQUARE() {
		return TARGET_SQUARE;
	}

	public int getPLAYER_TO_MOVE() {
		return PLAYER_TO_MOVE;
	}
}
