package engineByte.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engineByte.byteMove;

public class knight extends piece {

	public knight(byte pieceName, int square, int colour) {
		super(pieceName, square, colour);
		// TODO Auto-generated constructor stub
	}
	@Override
	public ArrayList<byteMove> generateMoves(byte[][] BOARD){
		ArrayList<byteMove> moves = new ArrayList<byteMove>();
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
		int[] start_coord = func.sqIntToCoord(startSquare);
		int[] target_coord = new int[2];
		int[][] moveVectors = {{2, 1}, {2, -1}, {1, 2}, {1, -2},
							   {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}};
		for (int[] vector : moveVectors) {
			target_coord[0] = start_coord[0] + (vector[0]);
			target_coord[1] = start_coord[1] + (vector[1]);
			if(this.isOnBoard(target_coord)) {
				byte tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(this.COLOUR == 1) {// White player to move
					if((tgSquare==0) || tgSquare <= 6 ) {// If target is empty or black piece
						int targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						moves.add(MOVE);
					}
				}else if (COLOUR == -1) {// Black PLayer to move
					if( (tgSquare==0) || tgSquare >= 7) {// If target is empty or white piece
						int targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						moves.add(MOVE);
					}
				}
			}
		}
		
		return moves;
	}
}
