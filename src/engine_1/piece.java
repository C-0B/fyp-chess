package engine_1;

public abstract class piece {
	String[][] BOARD;
	String NANE = "";
	int SQUARE = 0;
	int COLOUR = 1; // 1 = white, -1 = black
	
	public piece(String[][] board, String pieceName, int square, int colour) {
		BOARD = board;
		NANE = pieceName;
		SQUARE = square; // 0 -> 63
		COLOUR = colour;
	}
}
