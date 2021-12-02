package engine_1;

import java.util.ArrayList;

import chessFunc.functions;

public class pawn extends piece {
	boolean hasMoved = false;

	public pawn(String[][] board, String pieceName, int square, int colour) {
		super(board, pieceName, square, colour);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		System.out.println("hello pawn");
	}
	
	ArrayList<move> generateMoves(){
		/*
		 * +8, +16, +7, +9 => 
		 */
		
		ArrayList<move> moves = new ArrayList<move>();
		int startSquare = SQUARE;
		
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
		if(functions.isOnBoard(targetSquare)) {
			if(!BOARD[targetSquare/8][targetSquare%8].equals(" ")) {
				targetSquare = SQUARE + 7;
				move MOVE = new move(COLOUR, this, startSquare, targetSquare);
				moves.add(MOVE);
			}
		}
	
		//Attack on right
		
		targetSquare = SQUARE - (7 * COLOUR);
		if(functions.isOnBoard(targetSquare)) {
			if(!BOARD[targetSquare/8][targetSquare%8].equals(" ") && (targetSquare%8 != 7)) {
				move MOVE = new move(COLOUR, this, startSquare, targetSquare);
				moves.add(MOVE);
			}
		}

		
		//Attack on left
		targetSquare = SQUARE - (9 * COLOUR);
		if(functions.isOnBoard(targetSquare)) {
			if(!BOARD[targetSquare/8][targetSquare%8].equals(" ") && (targetSquare%8 != 0)) {
				move MOVE = new move(COLOUR, this, startSquare, targetSquare);
				moves.add(MOVE);
			}
		}
		return moves;
	}


}
