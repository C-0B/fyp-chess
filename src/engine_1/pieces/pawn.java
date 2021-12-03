package engine_1.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine_1.move;

public class pawn extends piece {
	
	boolean hasMoved = false;

	public pawn(String pieceName, int square, int colour) {
		super(pieceName, square, colour);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		System.out.println("hello pawn");
	}
	
	@Override
	public ArrayList<move> generateMoves(String[][] BOARD){
		System.out.println("pawn call");
		ArrayList<move> moves = new ArrayList<move>();
		int startSquare = SQUARE;

		
		if(((8 - (startSquare / 8)) == 2) && getNAME().equals(getNAME().toUpperCase())){
			// On whites starting rank, can only be here if pawn hasn't moved
			hasMoved = true;
		}else if(((8 - (startSquare / 8)) == 6) && getNAME().equals(getNAME().toLowerCase())) {
			// On blacks starting rank, pawn hasn't moved.
			hasMoved = true;
		}
		
		// Move forward one rank
		int targetSquare = SQUARE - (8 * COLOUR);
		if((targetSquare < 63 && targetSquare > 0)// Off board check
			&& 
			(BOARD[targetSquare/8][targetSquare%8].equals(" "))) {
			move MOVE = new move(COLOUR, this, startSquare, targetSquare);
			moves.add(MOVE);
		}
		
		// Double move on first move
		if(!this.hasMoved) {
			//Impossible to move off board from first move
			targetSquare = SQUARE - (16 * COLOUR);
			move MOVE = new move(COLOUR, this, startSquare, targetSquare);
			moves.add(MOVE);
		}
		
		//Attack on left
		targetSquare = SQUARE - (9 * COLOUR);
		if(func.isOnBoard(targetSquare)) {
			if(!BOARD[targetSquare/8][targetSquare%8].equals(" ")) {
				targetSquare = SQUARE + 7;
				move MOVE = new move(COLOUR, this, startSquare, targetSquare);
				moves.add(MOVE);
			}
		}
	
		//Attack on right
		
		targetSquare = SQUARE - (7 * COLOUR);
		if(func.isOnBoard(targetSquare)) {
			if(!BOARD[targetSquare/8][targetSquare%8].equals(" ") && (targetSquare%8 != 7)) {
				move MOVE = new move(COLOUR, this, startSquare, targetSquare);
				moves.add(MOVE);
			}
		}

		
		//Attack on left
		targetSquare = SQUARE - (9 * COLOUR);
		if(func.isOnBoard(targetSquare)) {
			if(!BOARD[targetSquare/8][targetSquare%8].equals(" ") && (targetSquare%8 != 0)) {
				move MOVE = new move(COLOUR, this, startSquare, targetSquare);
				moves.add(MOVE);
			}
		}
		return moves;
	}
}
