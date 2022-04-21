package opponents;

import java.util.ArrayList;

import javax.naming.InterruptedNamingException;

import engine.game;

public class eval {

	public eval() { }

	public static void main(String[] args) {
		game game = new game("rnbqkbnr/4p1p1/3p1p1p/2P1P1p1/1P1P1P1p/2P1P1p1/3P4/RNBQKBNR w KQkq - 0 1");
		System.out.println(getPawnStrucutureValue(game.getBoard(), -1));
	}
		
	/** Pawns defending each other */
	public static int getPawnStrucutureValue(String[][] board, int colourToMove) {
		int eval = 0;
		if( colourToMove == 1) {
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
		}else if( colourToMove == -1 ) {
			for(int rank = 1; rank < 7; rank++) {
				for(int file = 0; file <= 7; file++) {
					if(board[rank][file].equals("p")) {
						System.out.println(board[rank][file]+" "+rank+" "+file);
						try {
							if(board[rank+1][file+1].equals("p")) {
								eval++;
							}
						}catch(IndexOutOfBoundsException e){}
						try {
							if(board[rank+1][file-1].equals("p")) {
								eval++;
							}
						}catch(IndexOutOfBoundsException e){}
					}
				}
			}
		}else {System.out.println("error");}
		return eval;
	}
	
	/**@return a sum of the piece value of the specified colour */
	public static int getPieceEval(String[][] board, int colourToEval){
		ArrayList<String> pieces = new ArrayList<String>();
		if( colourToEval == 1) {
			for(String[] row : board) {
				for(String element : row) {
					if( !element.equals(" ")) {
						if(element.equals(element.toUpperCase())) {
							pieces.add(element);
						}
					}
				}
			}
		}else if( colourToEval == -1 ) {
			for(String[] row : board) {
				for(String element : row) {
					if( !element.equals(" ")) {
						if(element.equals(element.toLowerCase())) {
							pieces.add(element);
						}
					}
				}
			}
		}
		
		int score = 0;
		for(String piece : pieces) {
			if( piece.toLowerCase().equals("p") ) {
				score += 1;
			}else if( piece.toLowerCase().equals("n") ){
				score += 3;
			}else if( piece.toLowerCase().equals("b") ){
				score += 3;
			}else if( piece.toLowerCase().equals("r") ){
				score += 5;
			}else if( piece.toLowerCase().equals("q") ){
				score += 9;
			}
		}
		return score;
	}
	
	public static int getCastlingEval(game gameToEval) {
		int eval = 0;
		
		if(gameToEval.getPlayerToMove() == 1) {
		}else if(gameToEval.getPlayerToMove() == -1){
			
		}
		
		return eval;
	}
	
	
	public static int getMobility(game gameToEval) {
		int playerToMove = gameToEval.getPlayerToMove();
		if( playerToMove == 1) {
			return gameToEval.generateMoves().size();
		}else if ( playerToMove == -1 ){
			return gameToEval.generateMoves().size();
		}
		return 0;
	}
	
	public static int getSafeMobility(game gameToEval) {
		int playerToMove = gameToEval.getPlayerToMove();
		if( playerToMove == 1) {
			
		}else if ( playerToMove == -1 ){
			
		}
		return 0;
	}

}
