package engine_1;

import chessFunc.func;
import engine_1.pieces.piece;

public class pawnMove extends move {
	
	int EN_PASSANT_SQ = 0;

	public pawnMove(int playerToMove, piece piece, int startSquare, int targetSquare, int enPassantSq) {
		super(playerToMove, piece, startSquare, targetSquare);
		EN_PASSANT_SQ = enPassantSq;
	}
	
	@Override
	public String toString() {
		return PIECE.getNAME()+"@"+START_SQUARE+" -> "+TARGET_SQUARE+" \n  "+func.sqNumToStr(this.START_SQUARE)+" => "+func.sqNumToStr(TARGET_SQUARE)+"\n eP: "+EN_PASSANT_SQ;
	}
	
}
