package engine_1.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine_1.move;

public class bishop extends piece {

	public bishop(String pieceName, int square, int colour) {
		super(pieceName, square, colour);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public ArrayList<move> generateMoves(String[][] BOARD){
		ArrayList<move> moves = new ArrayList<move>();
		int startSquare = getSQUARE();
		int[] start_coord = func.sqNumToCoord(startSquare);
		int[] target_coord = new int[2];
		
		for(int index = 1; index <= 8; index++) {// Down and right (up from the perspective of the array)
			target_coord[0] = start_coord[0] + index;
			target_coord[1] = start_coord[1] + index;
			if(this.isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(tgSquare.equals(" ")) {
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
				}else if((COLOUR == 1) && (tgSquare.equals(tgSquare.toLowerCase()))){// White Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
					break;
				}else if((COLOUR == -1) && (tgSquare.equals(tgSquare.toUpperCase()))) {// Black Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
					break;
				}else {
					break;
				}
			}
		}
		
		for(int index = 1; index <= 8; index++) {// Down and left (up from the perspective of the array)
			target_coord[0] = start_coord[0] + index;
			target_coord[1] = start_coord[1] - index;
			if(this.isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(tgSquare.equals(" ")) {
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
				}else if((COLOUR == 1) && (tgSquare.equals(tgSquare.toLowerCase()))){// White Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
					break;
				}else if((COLOUR == -1) && (tgSquare.equals(tgSquare.toUpperCase()))) {// Black Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
					break;
				}else {
					break;
				}
			}
		}
		for(int index = 1; index <= 8; index++) {// Up and right (down from persp of array)
			target_coord[0] = start_coord[0] - index;
			target_coord[1] = start_coord[1] + index;
			if(this.isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(tgSquare.equals(" ")) {
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
				}else if((COLOUR == 1) && (tgSquare.equals(tgSquare.toLowerCase()))){// White Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
					break;
				}else if((COLOUR == -1) && (tgSquare.equals(tgSquare.toUpperCase()))) {// Black Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
					break;
				}else {
					break;
				}
			}
		}
		for(int index = 1; index <= 8; index++) {// Up and left (down from persp of array)
			target_coord[0] = start_coord[0] - index;
			target_coord[1] = start_coord[1] - index;
			if(this.isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(tgSquare.equals(" ")) {
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
				}else if((COLOUR == 1) && (tgSquare.equals(tgSquare.toLowerCase()))){// White Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
					break;
				}else if((COLOUR == -1) && (tgSquare.equals(tgSquare.toUpperCase()))) {// Black Player
					int targetSquare = func.coordTosqNum(target_coord);
					move MOVE = new move(COLOUR, this, startSquare, targetSquare);
					moves.add(MOVE);
					break;
				}else {
					break;
				}
			}
		}
		//return new ArrayList<move>();
		return moves;
	}

}
