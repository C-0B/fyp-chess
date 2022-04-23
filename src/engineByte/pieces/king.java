package engineByte.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine.game;
import engine.move;
import engineByte.byteMove;

public class king extends piece {
	
	public king(byte pieceName, int square, int colour) {
		super(pieceName, square, colour);
	}
	@Override
	public ArrayList<byteMove> generateMoves(byte[][] BOARD){
		ArrayList<byteMove> moves = new ArrayList<byteMove>();
		int startSquare = getSQUARE();
		int[] start_coord = func.sqIntToCoord(startSquare);
		int[] target_coord = new int[2];
		
		int[][] moveVectors = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, 
							   {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		
		for(int[] vector : moveVectors) {
			target_coord[0] = start_coord[0] + (vector[0]);
			target_coord[1] = start_coord[1] + (vector[1]);
			if(isOnBoard(target_coord)) {
				byte tgSquare = BOARD[target_coord[0]][target_coord[1]];
				int targetSquare = func.coordTosqInt(target_coord);
				if(tgSquare==0) {
					byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
					moves.add(MOVE);
				}else if( (COLOUR == 1) && tgSquare<=6){// White Player capture move
					byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
					moves.add(MOVE);
				}else if( (COLOUR == -1) && tgSquare>=7) {// Black Player capture move
					byteMove MOVE = new byteMove(func.sqIntToStr(startSquare), func.sqIntToStr(targetSquare));
					moves.add(MOVE);
				}
			}
		}
		return moves;
	}
}
