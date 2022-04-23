package engineByte.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engineByte.byteMove;

public class bishop extends piece {

	public bishop(byte pieceName, int square, int colour) {
		super(pieceName, square, colour);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public ArrayList<byteMove> generateMoves(byte[][] BOARD){
		ArrayList<byteMove> moves = new ArrayList<byteMove>();
		int startSquare = getSQUARE();
		int[] start_coord = func.sqIntToCoord(startSquare);
		int[] target_coord = new int[2];
		
		int[][] moveVectors = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		
		for(int[] vector : moveVectors) {
			for(int index = 1; index < 8; index++) {
				target_coord[0] = start_coord[0] + (vector[0]*index);
				target_coord[1] = start_coord[1] + (vector[1]*index);
				if(isOnBoard(target_coord)) {
					byte tgSquare = BOARD[target_coord[0]][target_coord[1]];
					if(tgSquare==0) {
						int targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						moves.add(MOVE);
					}else if((COLOUR == 1) && tgSquare <= 6 ){// White Player
						int targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						moves.add(MOVE);
						break;
					}else if((COLOUR == -1) && tgSquare >= 7 ) {// Black Player
						int targetSquare = func.coordTosqInt(target_coord);
						byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
						moves.add(MOVE);
						break;
					}else {
						break;
					}
				}
			}
		}
		return moves;
	}

}
