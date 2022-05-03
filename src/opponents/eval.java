package opponents;

import java.util.ArrayList;

import engine.game;

public class eval {
	static int[][] KNIGHT_VAL =
		{{-40,-10, -5, -5, -5, -5,-10,-40},
	 	{ -5,  5,  5,  5,  5,  5,  5, -5},
	 	{ -5,  5, 10, 15, 15, 10,  5, -5},
	 	{ -5,  5, 10, 15, 15, 10,  5, -5},
	 	{ -5,  5, 10, 15, 15, 10,  5, -5},
	 	{ -5,  5,  8,  8,  8,  8,  5, -5},
	 	{ -5,  0,  5,  5,  5,  5,  0, -5},
	 	{-50,-20,-10,-10,-10,-10,-20,-50}};
	
	static int[][] PAWN_VAL =
		{{0, 0, 0, 0, 0, 0, 0, 0},
		 {20,26,26,28,28,26,26,20},
		 {12,14,16,21,21,16,14,12},
		 { 8,10,12,18,18,12,10, 8},
		 { 4, 6, 8,16,16, 8, 6, 4},
		 { 2, 2, 4, 6, 6, 4, 2, 2},
		 { 0, 0, 0,-4,-4, 0, 0, 0},
		 { 0, 0, 0, 0, 0, 0, 0, 0}};
	
	static int[][] BISHOP_VAL = 
		{{-40,-20,-15,-15,-15,-15,-20,-40},
		 {  0,  5,  5,  5,  5,  5,  5, 0},
		 {  0, 10, 10, 18, 18, 10, 10, 0},
		 {  0, 10, 10, 18, 18, 10, 10, 0},
		 {  0,  5, 10, 18, 18, 10,  5, 0},
		 {  0,  0,  5,  5,  5,  5,  0, 0},
		 {  0,  5,  0,  0,  0,  0,  5, 0},
		 {-50,-20,-10,-20,-20,-10,-20,-50}};
	
	static int[][] ROOK_VAL = 
		{{10,10,10,10,10,10,10,10},
		 { 5, 5, 5,10,10, 5, 5, 5},
		 { 0, 0, 5,10,10, 5, 0, 0},
		 { 0, 0, 5,10,10, 5, 0, 0},
		 { 0, 0, 5,10,10, 5, 0, 0},
		 { 0, 0, 5,10,10, 5, 0, 0},
		 { 0, 0, 5,10,10, 5, 0, 0},
		 { 0, 0, 5,10,10, 5, 0, 0}};
	
	static int[][] QUEEN_VAL = 
		{{0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0,10,10,10,10, 0, 0},
		 {0, 0,10,15,15,10, 0, 0},
		 {0, 0,10,15,15,10, 0, 0},
		 {0, 0,10,10,10,10, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0},
		 {0, 0, 0, 0, 0, 0, 0, 0}};
	
	static int[][] KING_VAL = 
		{{ 0, 0, 0, 0, 0, 0, 0, 0},
		 { 0, 0, 0, 0, 0, 0, 0, 0},
		 { 0, 0, 0, 0, 0, 0, 0, 0},
		 { 0, 0, 0, 0, 0, 0, 0, 0},
		 {12, 8, 4, 0, 0, 4, 8,12},
		 {16,12, 8, 4, 4, 8,12,16},
		 {24,20,16,12,12,16,20,24},
		 {24,24,24,16,16, 6,32,32}};
	
	static int[][] LATE_KING_VAL = 
		{{-30, -5,  0,  0,  0,  0, -5,-30},
		 { -5,  0,  0,  0,  0,  0,  0, -5},
		 {  0,  0,  0,  0,  0,  0,  0,  0},
		 {  0,  0,  0,  5,  5,  0,  0,  0},
		 {  0,  0,  0,  5,  5,  0,  0,  0},
		 {  0,  0,  0,  0,  0,  0,  0,  0},
		 {-10,  0,  0,  0,  0,  0,  0,-10},
		 {-40,-10, -5, -5, -5, -5,-10,-40}};

	public static void main(String[] args) {
		game game = new game("r3k2r/Pppp1ppp/1b3nbN/nP1Q4/BBPQP3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
		System.out.println(getPawnStrucutureValue(game.getBoard()) );
		System.out.println(getPieceEval(game.getBoard()) );
		System.out.println(getPositionEval(game.getBoard()) );
	}
		
	/** Pawns defending each other */
	public static int getPawnStrucutureValue(String[][] board) {
		int eval = 0;
		// Start at the bottom of the board
		for(int rank = 6; rank > 0; rank--) {
			for(int file = 7; file >= 0; file--) {
				if(board[rank][file].equals("P")) {
					try {
						if( board[rank-1][file+1].equals("P") ) {
							eval++;
						}
					}catch(IndexOutOfBoundsException e) {}
					try {
						if(board[rank-1][file-1].equals("P")) {
							eval++;
						}
					}catch(IndexOutOfBoundsException e){}
				}
			}
		}
		for(int rank = 1; rank < 7; rank++) {
			for(int file = 0; file <= 7; file++) {
				if(board[rank][file].equals("p")) {
					try {
						if(board[rank+1][file+1].equals("p")) {
							eval--;
						}
					}catch(IndexOutOfBoundsException e){}
					try {
						if(board[rank+1][file-1].equals("p")) {
							eval--;
						}
					}catch(IndexOutOfBoundsException e){}
				}
			}
		}
		return eval;
	}
	
	/**@return a sum of the piece value of the specified colour */
	public static int getPieceEval(String[][] board){
		ArrayList<String> wPieces = new ArrayList<String>();
		ArrayList<String> bPieces = new ArrayList<String>();
		for(String[] row : board) {
			for(String element : row) {
				if( !element.equals(" ")) {
					if(element.equals(element.toUpperCase())) {
						wPieces.add(element);
					}
				}
			}
		}
		for(String[] row : board) {
			for(String element : row) {
				if( !element.equals(" ")) {
					if(element.equals(element.toLowerCase())) {
						bPieces.add(element);
					}
				}
			}
		}
		int score = 0;
		for(String piece : wPieces) {
			if( piece.equals("P") ) {
				score += 1;
			}else if( piece.equals("N") ){
				score += 3;
			}else if( piece.equals("B") ){
				score += 3;
			}else if( piece.equals("R") ){
				score += 5;
			}else if( piece.equals("Q") ){
				score += 9;
			}
		}
		for(String piece : bPieces) {
			if( piece.equals("p") ) {
				score -= 1;
			}else if( piece.equals("n") ){
				score -= 3;
			}else if( piece.equals("b") ){
				score -= 3;
			}else if( piece.equals("r") ){
				score -= 5;
			}else if( piece.equals("q") ){
				score -= 9;
			}
		}
		return score;
	}
	
	public static int getPositionEval(String[][] board) {
		int evaluation = 0;
		
		for(int row = 0; row<8; row++) {
			for(int column = 0; column<7; column++) {
				String current = board[row][column];
				if ( current.equals(" ")) {}
				else if( current.toLowerCase().equals("p") ) {
					if(current.equals(current.toUpperCase())) {//White Piece
						evaluation += PAWN_VAL[row][column];
					}else {//Black Piece
						evaluation -= PAWN_VAL[7-row][7-column];
					}
				}else if( current.toLowerCase().equals("n") ) {
					if(current.equals(current.toUpperCase())) {//White Piece
						evaluation += KNIGHT_VAL[row][column];
					}else {//Black Piece
						evaluation -= KNIGHT_VAL[7-row][7-column];
					}
				}else if( current.toLowerCase().equals("b") ) {
					if(current.equals(current.toUpperCase())) {//White Piece
						evaluation += BISHOP_VAL[row][column];
					}else {//Black Piece
						evaluation -= BISHOP_VAL[7-row][7-column];
					}
				}else if( current.toLowerCase().equals("r") ) {
					if(current.equals(current.toUpperCase())) {//White Piece
						evaluation += ROOK_VAL[row][column];
					}else {//Black Piece
						evaluation -= ROOK_VAL[7-row][7-column];
					}
				}else if( current.toLowerCase().equals("q") ) {
					if(current.equals(current.toUpperCase())) {//White Piece
						evaluation += QUEEN_VAL[row][column];
					}else {//Black Piece
						evaluation -= QUEEN_VAL[7-row][7-column];
					}
				}else if( current.toLowerCase().equals("k") ) {
					if(current.equals(current.toUpperCase())) {//White Piece
						evaluation += KING_VAL[row][column];
					}else {//Black Piece
						evaluation -= KING_VAL[7-row][7-column];
					}
				}else {
					System.out.println("error reading the board passed to piecePosition");
				}
			}
		}
		
		return evaluation;
	}
}
