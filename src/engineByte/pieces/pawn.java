package engineByte.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engineByte.byteMove;

/** enPassant: value from the FEN string where an en passant capture is available (if there is one)
 */
public class pawn extends piece {
	String enPassant = "";
	
	/**@param pieceName
	 * @param square
	 * @param colour
	 * @param enpassant -> value from the FEN string where an en passant capture is available on the board(if there is one) */
	public pawn(byte pieceName, int square, int colour, String enpassant) {
		super(pieceName, square, colour);
		enPassant = enpassant;
	}
	
	@Override
	public ArrayList<byteMove> generateMoves(byte[][] BOARD){
		ArrayList<byteMove> moves = new ArrayList<byteMove>();
		int startSquare = getSQUARE();
		int[] start_coord = func.sqIntToCoord(getSQUARE());
		int[] target_coord = new int[2];// was passing by reference not by content
		
		//Move one rank forward
		target_coord[0] = start_coord[0] - (COLOUR);// one forwards / backwards depending on colour
		target_coord[1] = start_coord[1];
		int targetSquare = func.coordTosqInt(target_coord);
		
		if(this.isOnBoard(target_coord)) {// on board check 
			if(BOARD[target_coord[0]][target_coord[1]] == 0){// empty space check
				byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
				moves.add(MOVE);
			}
		}
		
		target_coord = func.sqIntToCoord(getSQUARE());
		target_coord[0] = start_coord[0] - (COLOUR * 2);
		target_coord[1] = start_coord[1];
		targetSquare = func.coordTosqInt(target_coord);
		// Move 2 ranks forward
		if(((COLOUR == 1) && (start_coord[0] == 6)) || ((COLOUR == -1) && (start_coord[0] == 1))){//On starting rank
			//move tempMove = new move(COLOUR, this, startSquare, targetSquare);
			if(this.isOnBoard(target_coord)) {// on board check 
				if((BOARD[target_coord[0]][target_coord[1]] == 0) && (BOARD[(target_coord[0])+COLOUR][target_coord[1]] == 0)){// empty space check
					int enPasssatSq = targetSquare + (COLOUR * 8);// The square behind the pawn where an enpassant capture is available on the next turn
					byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
					moves.add(MOVE);
				}
			}
		}
		
		/* Attack left diagonal
		 * seperate en passant captures and regular captures
		 */
		target_coord = func.sqIntToCoord(getSQUARE());
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1] - 1;
		targetSquare = func.coordTosqInt(target_coord);
		if(this.isOnBoard(target_coord)) {// on board check 
			byte tgtSqContents = BOARD[target_coord[0]][target_coord[1]];
			if( tgtSqContents != 0 ){// non-empty space check
				if(COLOUR == 1) {//If the player is white
					if( tgtSqContents <= 6) {
						targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						if(!MOVE.isKingTarget()) {
							moves.add(MOVE);
						}
					}else if( func.sqIntToStr(getSQUARE()).equals(enPassant) ) {// capture with enpassant 
						
					}
				}else if(COLOUR == -1) {//If the player is black
					if( tgtSqContents >= 7) {
						targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						if(!MOVE.isKingTarget()) {
							moves.add(MOVE);
						}
					}
				}
			}
		}
		// Attack right diagonal
		target_coord = func.sqIntToCoord(getSQUARE());
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1] + 1;
		if(this.isOnBoard(target_coord)) {// on board check 
			byte tgtSqContents = BOARD[target_coord[0]][target_coord[1]];
			if(tgtSqContents != 0 ){// non-empty space check
				if(COLOUR == 1) {//If the player is white
					if( (tgtSqContents <= 6)  || (func.sqIntToStr(getSQUARE()).equals(enPassant))  ) {
						targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						if(!MOVE.isKingTarget()) {
							moves.add(MOVE);
						}
					}
				}else if(COLOUR == -1) {//If the player is black
					if( (tgtSqContents >= 7)  || (func.sqIntToStr(getSQUARE()).equals(enPassant))  ) {
						targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						if(!MOVE.isKingTarget()) {
							moves.add(MOVE);
						}
					}
				}
			}
		}
		return moves;
	}
	
	@Override
	public ArrayList<byteMove> generateAttackingMoves(byte Board[][]){
		ArrayList<byteMove> attackingMoves = new ArrayList<byteMove>();
		int[] start_coord = func.sqIntToCoord(getSQUARE());
		int startSquare = func.coordTosqInt(start_coord);
		String startSquareStr = func.sqIntToStr(startSquare);
		
		int[] target_coord = func.sqIntToCoord(getSQUARE());
		
		//Attacking diagonal left from the perspective of the player
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1] - 1;
		int targetSquare = func.coordTosqInt(target_coord);
		if(this.isOnBoard(target_coord)) {// on board check
			String targetSquareStr = func.sqIntToStr(targetSquare);
			attackingMoves.add(new byteMove(startSquareStr, targetSquareStr));
		}
		
		//Attacking diagonal right from the perspective of the player
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1] + 1;
		targetSquare = func.coordTosqInt(target_coord);
		if(this.isOnBoard(target_coord)) {// on board check
			String targetSquareStr = func.sqIntToStr(targetSquare);
			attackingMoves.add(new byteMove(startSquareStr, targetSquareStr));
		}

		return attackingMoves;
	}
}
