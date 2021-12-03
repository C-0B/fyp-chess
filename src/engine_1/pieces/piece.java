package engine_1.pieces;

import java.util.ArrayList;

import engine_1.move;

public abstract class piece {
	String NAME = "";
	int SQUARE = 0;
	int COLOUR = 1; // 1 = white, -1 = black
	
	public piece(String pieceName, int square, int colour) {
		NAME = pieceName;
		SQUARE = square; // 0 -> 63
		COLOUR = colour;
	}
	
	public ArrayList<move> generateMoves(String Board[][]){
		return null;
	}
	
	
	//public ArrayList<move> generateMoves(){return null;}

	public String getNAME() {
		return NAME;
	}
	
	
	
	
}
