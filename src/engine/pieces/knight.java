package engine.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine.move;

public class knight extends piece {

	public knight(String pieceName, int square, int colour) {
		super(pieceName, square, colour);
		// TODO Auto-generated constructor stub
	}
	@Override
	public ArrayList<move> generateMoves(String[][] BOARD){
		ArrayList<move> moves = new ArrayList<move>();
		/*
		 * 8 possible moves for a knight
		 * +2 +1
		 * +2 -1
		 * +1 +2
		 * +1 -2
		 * -1 +2
		 * -1 -2
		 * -2 +1
		 * -2 -1
		 */
		
		int startSquare = getSQUARE();		
		int[] start_coord = func.sqNumToCoord(startSquare);
		int[] target_coord = new int[2];
		int[][] moveVectors = {{2, 1}, {2, -1}, {1, 2}, {1, -2},
							   {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}};
		for (int[] vector : moveVectors) {
			target_coord[0] = start_coord[0] + (vector[0]);
			target_coord[1] = start_coord[1] + (vector[1]);
			if(this.isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(this.COLOUR == 1) {// White player to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toLowerCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare, BOARD[targetSquare/8][targetSquare%8]);
						moves.add(MOVE);
					}
				}else if (COLOUR == -1) {// Black PLayer to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toUpperCase())) {// If target is empty or white piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare, BOARD[targetSquare/8][targetSquare%8]);
						moves.add(MOVE);
					}
				}
			}
		}
		
		//return new ArrayList<move>(); return empty arraylist, was used for testing
		return moves;
	}
}
