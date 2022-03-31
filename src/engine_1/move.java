package engine_1;

import engine_1.pieces.piece;
import chessFunc.*;

public class move {
	int PLAYER_TO_MOVE = 1; //1 = white -1 = black
	piece PIECE;
	int START_SQUARE = 0;
	int TARGET_SQUARE = 0;
	String CAPTURED_PIECE = ""; //Empty if no piece captured
	int EN_PASSANT_SQ = -1;// -1 where no en passant is available, 0-63 where enPassant available
	
	
//	maybe :?
//	float value = 0;//? for evaluation
//	String[][] board;
//	boolean isPieceTaken;
	
	//basic move
	public move(int playerToMove, piece piece, int startSquare, int targetSquare){
		PLAYER_TO_MOVE = playerToMove;
		PIECE = piece;
		START_SQUARE = startSquare;
		TARGET_SQUARE = targetSquare;
		EN_PASSANT_SQ = -1;
	}
	
	// move with capture
	public move(int playerToMove, piece piece, int startSquare, int targetSquare, String capturePiece){
		PLAYER_TO_MOVE = playerToMove;
		PIECE = piece;
		START_SQUARE = startSquare;
		TARGET_SQUARE = targetSquare;
		EN_PASSANT_SQ = -1;
		CAPTURED_PIECE = capturePiece;
	}
	/*
	public move() {
		move when a pawn captures another pawn with en passant
	}
	*/
	
	// double pawn move leaving en passant available
	public move(int playerToMove, piece piece, int startSquare, int targetSquare, int enPassant){
		PLAYER_TO_MOVE = playerToMove;
		PIECE = piece;
		START_SQUARE = startSquare;
		TARGET_SQUARE = targetSquare;
		EN_PASSANT_SQ = enPassant;
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
		return PIECE.getNAME()+"@"+START_SQUARE+" -> "+getTARGET_SQUARE()+" \n  "+func.sqNumToStr(this.START_SQUARE)+" -> "+func.sqNumToStr(getTARGET_SQUARE())+" \n En Passant: "+EN_PASSANT_SQ;
	}

	public int getTARGET_SQUARE() {
		return TARGET_SQUARE;
	}

	public int getPLAYER_TO_MOVE() {
		return PLAYER_TO_MOVE;
	}
}
