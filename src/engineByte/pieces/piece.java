package engineByte.pieces;

import java.util.ArrayList;

import chessFunc.func;
import engineByte.byteMove;

public class piece {
	byte NAME = 0;
	int SQUARE = 0;
	int COLOUR = 1; // 1 = white, -1 = black
	
	public piece(byte pieceName, int square, int colour) {
		NAME = pieceName;
		SQUARE = square; // 0 -> 63 starting from top left of the baord
		COLOUR = colour;
//		System.out.println(colour+" "+pieceName+" created at "+func.sqIntToStr(square));
	}
	
	boolean isOnBoard(int[] coord) {
		int rank = coord[0];
		int file = coord[1];
		if(rank < 8 && rank >= 0) {
			if(file < 8 && file >= 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSameColour(piece pieceToCheck) {
		if(this.COLOUR == pieceToCheck.COLOUR) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * Use a piece to call this method to generate pseudolegal
	 * moves for that piece with the given board parameter
	 */
	public ArrayList<byteMove> generateMoves(byte Board[][]){
		return null;
	}
	
	/** @return string values such as a1, a2, h4, h5 etc */
	public ArrayList<byteMove> generateAttackingMoves(byte Board[][]) {
		return null;
	}
	
	// Compare two pieces
	public boolean equals(piece piece2) {
		if(this.NAME == piece2.NAME) {
			if(this.COLOUR == piece2.COLOUR) {
				if(this.SQUARE == piece2.SQUARE) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Getters
	public String getNAME() {
		return func.pieceByteToStr(NAME);
	}

	public int getSQUARE() {
		return SQUARE;
	}
	public int getColour() {
		return COLOUR;
	}
}
