package engine.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine.game;
import engine.move;

public class king extends piece {
	
	public king(String pieceName, int square, int colour) {
		super(pieceName, square, colour);
	}
	@Override
	public ArrayList<move> generateMoves(String[][] BOARD){
		ArrayList<move> moves = new ArrayList<move>();
		int startSquare = getSQUARE();
		int[] start_coord = func.sqIntToCoord(startSquare);
		int[] target_coord = new int[2];
		
		int[][] moveVectors = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, 
							   {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
		
		for(int[] vector : moveVectors) {
			target_coord[0] = start_coord[0] + (vector[0]);
			target_coord[1] = start_coord[1] + (vector[1]);
			if(isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				int targetSquare = func.coordTosqInt(target_coord);
				if(tgSquare.equals(" ")) {
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
				}else if( (COLOUR == 1) && (tgSquare.equals(tgSquare.toLowerCase())) ){// White Player capture move
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
				}else if( (COLOUR == -1) && (tgSquare.equals(tgSquare.toUpperCase())) ) {// Black Player capture move
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
				}
			}
		}
		return moves;
	}
}
