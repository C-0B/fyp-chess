package engine_1;

import chessFunc.func;
import engine_1.pieces.piece;

public class pawnMove extends move {
	
	int EN_PASSANT_SQ = 0;

	public pawnMove(int playerToMove, piece piece, int startSquare, int targetSquare, int enPassantSq) {
		super(playerToMove, piece, startSquare, targetSquare);
		EN_PASSANT_SQ = enPassantSq;
	}

	public pawnMove(move MOVE, int enPassantSq) {
		super(MOVE.getPLAYER_TO_MOVE(), MOVE.PIECE, MOVE.START_SQUARE, MOVE.getTARGET_SQUARE());
		EN_PASSANT_SQ = enPassantSq;
	}
	
	@Override
	public String toString() {
		return PIECE.getNAME()+"@"+START_SQUARE+" -> "+getTARGET_SQUARE()+" \n  "+func.sqNumToStr(this.START_SQUARE)+" -> "+func.sqNumToStr(getTARGET_SQUARE())+"\n eP: "+func.sqNumToStr(EN_PASSANT_SQ);
	}
	
}
