package engine_1.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine_1.move;

public class rook extends piece {

	public rook(String pieceName, int square, int colour) {
		super(pieceName, square, colour);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public ArrayList<move> generateMoves(String[][] BOARD){
		ArrayList<move> moves = new ArrayList<move>();
		int startSquare = getSQUARE();
		return moves;
	}

}
