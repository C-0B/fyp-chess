package engine_1;

import java.util.ArrayList;
import chessFunc.*;

public class engine1_test {
	public static void main(String[] args) {
        game game = new game();
        
        game.printBoard();
        System.out.println();
        
        ArrayList<move> moves =  game.generateMoves();
        
        // Plays each of the moves
        int moveCount = 0;
        for(move move : moves) {
        	game tempGame = new game();
        	System.out.println("Move: "+ ++moveCount);
        	System.out.println(move);
        	tempGame.playMove(move);
        	tempGame.printBoard();
        	System.out.println();
        }
    }
	
	
	// Define recursive function
}