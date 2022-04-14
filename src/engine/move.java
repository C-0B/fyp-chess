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
	private String startSquareStr;
	private String targetSquareStr;
	
	/** only constructor that should be used*/
	public move(String startSquareStr, String targetSquareStr) {
		this.startSquareStr = startSquareStr;
		this.targetSquareStr = targetSquareStr;
	}
	public int getPLAYER_TO_MOVE() { return PLAYER_TO_MOVE; }
	public String getStartSquareStr() {return startSquareStr;}
	public String getTargetSquareStr() {return targetSquareStr;}
	public int getTARGET_SQUARE() { return TARGET_SQUARE; }
	
	
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
	// maybe this is useless - calculate take pieces from the pieces missing on the board
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
		startSquareStr = func.sqIntToStr(START_SQUARE);
		TARGET_SQUARE = targetSquare;
		targetSquareStr = func.sqIntToStr(TARGET_SQUARE);
		EN_PASSANT_SQ = enPassant;
	}
	
	/** Checks if the the opponents king is on the target square<p>
	 * No piece can capture a king */
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
		
	/** Compare this and another move */
	public boolean equals(move move2) {
		return 	this.startSquareStr.equals(move2.startSquareStr) &&
				this.targetSquareStr.equals(move2.targetSquareStr);
	}
		
	@Override
	public String toString() { return startSquareStr + " -> "+targetSquareStr; }
}
