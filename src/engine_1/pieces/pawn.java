package engine_1.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engine_1.move;
import engine_1.pawnMove;

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
		System.out.println(this.getNAME()+" "+this.getSQUARE());
		ArrayList<move> moves = new ArrayList<move>();
		int startSquare = getSQUARE();
		
		int[] start_coord = func.sqNumToCoord(getSQUARE());
		int[] target_coord = new int[2];// was passing by reference not by content
		
		//Move one rank forward
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1];
		int targetSquare = func.coordTosqNum(target_coord);
		
		if(this.isOnBoard(target_coord)) {// on board check 
			if(BOARD[target_coord[0]][target_coord[1]].equals(" ")){// empty space check
				move MOVE = new move(COLOUR, this, startSquare, targetSquare);
				moves.add(MOVE);
			}
		}
		
		
		target_coord = func.sqNumToCoord(getSQUARE());
		target_coord[0] = start_coord[0] - (COLOUR * 2);
		target_coord[1] = start_coord[1];
		// Move 2 ranks forward
		if(((COLOUR == 1) && (start_coord[0] == 6)) || ((COLOUR == -1) && (start_coord[0] == 1))){//On starting rank
			//move tempMove = new move(COLOUR, this, startSquare, targetSquare);
			
			if(this.isOnBoard(target_coord)) {// on board check 
				if((BOARD[target_coord[0]][target_coord[1]].equals(" ")) && (BOARD[(target_coord[0])+COLOUR][target_coord[1]].equals(" "))){// empty space check
					targetSquare = func.coordTosqNum(target_coord);
					int enPasssatSq = targetSquare + (COLOUR * 8);
					pawnMove MOVE = new pawnMove(COLOUR, this, startSquare, targetSquare, enPasssatSq);
					moves.add(MOVE);
				}
			}
		}
		/*
		// Attack left diagonal
		target_coord = start_coord;
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1] - 1;
		if(this.isOnBoard(target_coord)) {// on board check 
			String tgtSqContents = BOARD[target_coord[0]][target_coord[1]];
			if(!tgtSqContents.equals(" ")){// non-empty space check
				if(COLOUR == 1) {//If the player is white
					if(tgtSqContents.equals(tgtSqContents.toLowerCase())) {// If the piece in left diagonal is black
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
					}
				}else if(COLOUR == -1) {//If the player is black
					if(tgtSqContents.equals(tgtSqContents.toUpperCase())) {// If the piece in left diagonal is black
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
					}
				}
			}
		}
		
		// Attack right diagonal
		target_coord = start_coord;
		target_coord[0] = start_coord[0] - (COLOUR);
		target_coord[1] = start_coord[1] + 1;
		if(this.isOnBoard(target_coord)) {// on board check 
			String tgtSqContents = BOARD[target_coord[0]][target_coord[1]];
			if(!tgtSqContents.equals(" ")){// non-empty space check
				if(COLOUR == 1) {//If the player is white
					if(tgtSqContents.equals(tgtSqContents.toLowerCase())) {// If the piece in left diagonal is black
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
					}
				}else if(COLOUR == -1) {//If the player is black
					if(tgtSqContents.equals(tgtSqContents.toUpperCase())) {// If the piece in left diagonal is black
						move MOVE = new move(COLOUR, this, startSquare, targetSquare);
						moves.add(MOVE);
					}
				}
			}
		}
		*/
		return new ArrayList<move>();
		//return moves;
	}
}
