package engine_1.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine_1.move;

/**
 * enPassant: value from the FEN string where an en passant capture is available (if there is one)
 */
public class pawn extends piece {
	String enPassant = "";
	
	/**
	 * @param pieceName
	 * @param square
	 * @param colour
	 * @param enpassant -> value from the FEN string where an en passant capture is available on the board(if there is one)
	 */
	public pawn(String pieceName, int square, int colour, String enpassant) {
		super(pieceName, square, colour);
		enPassant = enpassant;
	}
	
	@Override
	public ArrayList<move> generateMoves(String[][] BOARD){
		ArrayList<move> moves = new ArrayList<move>();
		int startSquare = getSQUARE();
		
		int[] start_coord = func.sqNumToCoord(getSQUARE());
		int[] target_coord = new int[2];// was passing by reference not by content
		
		//Move one rank forward
		target_coord[0] = start_coord[0] - (COLOUR);// one forwards / backwards depending on colour
		target_coord[1] = start_coord[1];
		int targetSquare = func.coordTosqNum(target_coord);
		
		if(this.isOnBoard(target_coord)) {// on board check 
			if(BOARD[target_coord[0]][target_coord[1]].equals(" ")){// empty space check
				move MOVE = new move(COLOUR, this, startSquare, targetSquare);
				moves.add(MOVE);
			}
		}
		
		target_coord = func.sqNumToCoord(getSQUARE());
		target_coord[0] = start_coord[0] - (COLOUR * 2);
		target_coord[1] = start_coord[1];
		// Move 2 ranks forward
		if(((COLOUR == 1) && (start_coord[0] == 6)) || ((COLOUR == -1) && (start_coord[0] == 1))){//On starting rank
			//move tempMove = new move(COLOUR, this, startSquare, targetSquare);
			if(this.isOnBoard(target_coord)) {// on board check 
				if((BOARD[target_coord[0]][target_coord[1]].equals(" ")) && (BOARD[(target_coord[0])+COLOUR][target_coord[1]].equals(" "))){// empty space check
					targetSquare = func.coordTosqNum(target_coord);
					int enPasssatSq = targetSquare + (COLOUR * 8);// The square behind the pawn where an enpassant capture is available on the next turn
					move MOVE = new move(COLOUR, this, startSquare, targetSquare, enPasssatSq);
					moves.add(MOVE);
				}
			}
		}
		
		/* Attack left diagonal
		 * seperate en passant captures and regular captures
		 */
		target_coord = func.sqNumToCoord(getSQUARE());
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1] - 1;
		if(this.isOnBoard(target_coord)) {// on board check 
			String tgtSqContents = BOARD[target_coord[0]][target_coord[1]];
			if(!tgtSqContents.equals(" ")){// non-empty space check
				if(COLOUR == 1) {//If the player is white
					if( tgtSqContents.equals(tgtSqContents.toLowerCase()) ) {
						targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare, BOARD[targetSquare/8][targetSquare%8]);
						if(!MOVE.isKingTarget()) {
							moves.add(MOVE);
						}
					}else if( func.sqNumToStr(getSQUARE()).equals(enPassant) ) {// capture with enpassant 
						
					}
				}else if(COLOUR == -1) {//If the player is black
					if( tgtSqContents.equals(tgtSqContents.toUpperCase()) ) {
						targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare, BOARD[targetSquare/8][targetSquare%8]);
						if(!MOVE.isKingTarget()) {
							moves.add(MOVE);
						}
					}else if( func.sqNumToStr(getSQUARE()).equals(enPassant) ) {// capture with enpassant 
					}
				}
			}
		}
		// Attack right diagonal
		target_coord = func.sqNumToCoord(getSQUARE());
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1] + 1;
		if(this.isOnBoard(target_coord)) {// on board check 
			String tgtSqContents = BOARD[target_coord[0]][target_coord[1]];
			if(!tgtSqContents.equals(" ")){// non-empty space check
				if(COLOUR == 1) {//If the player is white
					if(tgtSqContents.equals(tgtSqContents.toLowerCase())  || (func.sqNumToStr(getSQUARE()).equals(enPassant))  ) {
						targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare, BOARD[targetSquare/8][targetSquare%8]);
						if(!MOVE.isKingTarget()) {
							moves.add(MOVE);
						}
					}
				}else if(COLOUR == -1) {//If the player is black
					if(tgtSqContents.equals(tgtSqContents.toUpperCase())  || (func.sqNumToStr(getSQUARE()).equals(enPassant))  ) {
						targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare, BOARD[targetSquare/8][targetSquare%8]);
						if(!MOVE.isKingTarget()) {
							moves.add(MOVE);
						}
					}
				}
			}
		}
		return moves;
	}
}
