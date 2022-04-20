package opponents;

import java.util.ArrayList;

import engine.game;

public class eval {

	public eval() { }

	public static void main(String[] args) {
		game game = new game("r3kbnr/p2ppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		System.out.println(evalPieces(game.getBoard(), 1));
		System.out.println(evalPieces(game.getBoard(), -1));
	}
	
	/**@return a sum of the piece value of the specified colour */
	public static int evalPieces(String[][] board, int colourToEval){
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

}
