package engine;

import chessFunc.func;
import engine.pieces.piece;

public class move {
	int PLAYER_TO_MOVE = 1; //1 = white -1 = black
	piece PIECE;
	int START_SQUARE = 0;
	private int TARGET_SQUARE = 0;
	String CAPTURED_PIECE = ""; //Empty if no piece captured
	int EN_PASSANT_SQ = -1;// -1 where no en passant is available, 0-63 where enPassant available
	
	
//	maybe :?
//	float value = 0;//? for evaluation
//	String[][] board;
//	boolean isPieceTaken;
	
	private String startSquareStr;
	public String getStartSquareStr() {return startSquareStr;}
	
	private String targetSquareStr;
	public String getTargetSquareStr() {return targetSquareStr;}

	public move(String startSquareStr, String targetSquareStr) {
		this.startSquareStr = startSquareStr;
		this.targetSquareStr = targetSquareStr;
	}
	
	
	//basic move
	public move(int playerToMove, piece piece, int startSquare, int targetSquare){
		PLAYER_TO_MOVE = playerToMove;
		PIECE = piece;
		START_SQUARE = startSquare;
		startSquareStr = func.sqIntToStr(START_SQUARE);
		TARGET_SQUARE = targetSquare;
		targetSquareStr = func.sqIntToStr(TARGET_SQUARE);
		EN_PASSANT_SQ = -1;
	}
	
	// move with capture
	// maybe this is useless - calcualte take pieces from the peices missing on the board
	public move(int playerToMove, piece piece, int startSquare, int targetSquare, String capturePiece){
		PLAYER_TO_MOVE = playerToMove;
		PIECE = piece;
		START_SQUARE = startSquare;
		startSquareStr = func.sqIntToStr(START_SQUARE);
		TARGET_SQUARE = targetSquare;
		targetSquareStr = func.sqIntToStr(TARGET_SQUARE);
		EN_PASSANT_SQ = -1;
		CAPTURED_PIECE = capturePiece;
	}
	
	// double pawn move leaving en passant available
	// if after double move the pawnjust moved is on the same ranks as another opponents pawns then en oassant is available
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
	
//	@Override
//	public String toString() {
//		return PIECE.getNAME()+"@"+START_SQUARE+" -> "+getTARGET_SQUARE()+" \n  "+func.sqIntToStr(this.START_SQUARE)+" -> "+func.sqIntToStr(getTARGET_SQUARE())+" \n En Passant: "+EN_PASSANT_SQ;
//	}
	
	@Override
	public String toString() {
		return startSquareStr + " -> "+targetSquareStr;
	}

	public int getTARGET_SQUARE() {
		return TARGET_SQUARE;
	}

	public int getPLAYER_TO_MOVE() {
		return PLAYER_TO_MOVE;
	}
}
