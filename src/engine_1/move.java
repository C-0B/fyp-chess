package engine_1;

import engine_1.pieces.piece;
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
	
	// Compare two moves
	public boolean equals(move move2) {
		if(this.PLAYER_TO_MOVE == move2.PLAYER_TO_MOVE) {
			if(this.PIECE.equals(move2.PIECE)) {
				if(this.START_SQUARE == move2.START_SQUARE) {
					if(this.TARGET_SQUARE == move2.TARGET_SQUARE) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return PIECE.getNAME()+"@"+START_SQUARE+" -> "+TARGET_SQUARE+" \n  "+func.sqNumToStr(this.START_SQUARE)+" => "+func.sqNumToStr(TARGET_SQUARE);
	}
}
