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
				if(this.COLOUR == 1) {// White player to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toLowerCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
						if(!tgSquare.equals(" ")){break;} // If a piece was taken, you cannot move in that direction any more
					}
				}else if (COLOUR == -1) {// Black PLayer to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toUpperCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
						if(!tgSquare.equals(" ")){break;} // If a piece was taken, you cannot move in that direction any more
					}
				}
			}
		}
		for(int index = 1; index <= 8; index++) {// Down and left (up from the perspective of the array)
			target_coord[0] = start_coord[0] + index;
			target_coord[1] = start_coord[1] - index;
			if(this.isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(this.COLOUR == 1) {// White player to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toLowerCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
						if(!tgSquare.equals(" ")){break;} // If a piece was taken, you cannot move in that direction any more
					}
				}else if (COLOUR == -1) {// Black PLayer to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toUpperCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
						if(!tgSquare.equals(" ")){break;} // If a piece was taken, you cannot move in that direction any more
					}
				}
			}
		}
		for(int index = 1; index <= 8; index++) {// Up and right (down from persp of array)
			target_coord[0] = start_coord[0] - index;
			target_coord[1] = start_coord[1] + index;
			if(this.isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(this.COLOUR == 1) {// White player to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toLowerCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
						if(tgSquare.equals(tgSquare.toLowerCase())){break;} // If a piece was taken, you cannot move in that direction any more
					}
				}else if (COLOUR == -1) {// Black PLayer to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toUpperCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
						if(tgSquare.equals(tgSquare.toUpperCase())){break;} // If a piece was taken, you cannot move in that direction any more
					}
				}
			}
		}
		for(int index = 1; index <= 8; index++) {// Up and left (down from persp of array)
			target_coord[0] = start_coord[0] - index;
			target_coord[1] = start_coord[1] - index;
			if(this.isOnBoard(target_coord)) {
				String tgSquare = BOARD[target_coord[0]][target_coord[1]];
				if(this.COLOUR == 1) {// White player to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toLowerCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
						if(tgSquare.equals(tgSquare.toLowerCase())){break;} // If a piece was taken, you cannot move in that direction any more
					}
				}else if (COLOUR == -1) {// Black PLayer to move
					if((tgSquare.equals(" ")) || tgSquare.equals(tgSquare.toUpperCase())) {// If target is empty or black piece
						int targetSquare = func.coordTosqNum(target_coord);
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
						if(tgSquare.equals(tgSquare.toUpperCase())){break;} // If a piece was taken, you cannot move in that direction any more
					}
				}
			}
		}
		//return new ArrayList<move>();
		return moves;
	}

}
