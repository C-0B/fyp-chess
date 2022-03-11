package engine_1.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine_1.move;
import engine_bb.board;

public class king extends piece {
	boolean inCheck = false;

	public king(String pieceName, int square, int colour) {
		super(pieceName, square, colour);
		// TODO Auto-generated constructor stub
	}
	@Override
	public ArrayList<move> generateMoves(String[][] BOARD){
		ArrayList<move> moves = new ArrayList<move>();
		int startSquare = getSQUARE();
		int[] start_coord = func.sqNumToCoord(startSquare);
		int[] target_coord = new int[2];
		
		int[][] moveVectors = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, 
							   {1, 1}, {1, -1}, {-1, 1}, {-1, -1},
							   {0, 2}, {2, 0}}; //Castling vectors;
		
		for(int[] vector : moveVectors) {
			target_coord[0] = start_coord[0] + (vector[0]);
			target_coord[1] = start_coord[1] + (vector[1]);
			if(isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(tgSquare.equals(" ")) {
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					if(!this.isAdjacentToKing(targetSquare, BOARD, COLOUR)){ // If the new king position is not next to the opponents king
						moves.add(MOVE);
					}
				}else if((COLOUR == 1) && (tgSquare.equals(tgSquare.toLowerCase()))){// White Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					if(!this.isAdjacentToKing(targetSquare, BOARD, COLOUR)){ // If the new king position is not next to the opponents king
						moves.add(MOVE);
					}
				}else if((COLOUR == -1) && (tgSquare.equals(tgSquare.toUpperCase()))) {// Black Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					if(!this.isAdjacentToKing(targetSquare, BOARD, COLOUR)){ // If the new king position is not next to the opponents king
						moves.add(MOVE);
					}
				}else {
					break;
				}
			}
		}
		return moves;
	}
	
	/** Used to check if the move would place the players king
	 *  adjacent to the opponents king
	 */
	private boolean isAdjacentToKing(int newCurrSq, String[][] BOARD, int pColour) {
		// make new king piece with target as current
		// any of possible move involve capture of king return true

		int[] start_coord = func.sqNumToCoord(newCurrSq);
		int[] target_coord = new int[2];
		
		int[][] moveVectors = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, 
				   			   {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		
		for(int[] vector : moveVectors) {
			target_coord[0] = start_coord[0] + (vector[0]);
			target_coord[1] = start_coord[1] + (vector[1]);
			if(isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				int targetSquare = func.coordTosqNum(target_coord);
				move MOVE = new move(pColour, this, newCurrSq, targetSquare, BOARD[targetSquare/8][targetSquare%8]);
				if(!tgSquare.equals(" ")) {// If the target is a peice
					if(MOVE.isKingTarget()) {
						// If the new target sq is a the opponents king then the king should not have been there is the first place
						return true;
					}
				}
			}
		}
	return false;
	}
}
