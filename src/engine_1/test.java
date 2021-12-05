package engine_1;

import java.util.ArrayList;
import chessFunc.*;

public class test {
	public static void main(String[] args) {
        game game1 = new game();
        
        //game1.readFENtoBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        //game1.readFENtoBoard("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
        
        //castling fen practice
        // "r2qk2r/8/8/8/8/8/8/R2QK2R w KQkq - 0 1"
        
        String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        
        game1.readFEN("8/8/8/8/4B3/8/8/8 w - - 0 1");
        game1.printBoard();
        
        ArrayList<move> moves =  game1.generateMoves();
        
        // Plays each of the moves
        int moveCount = 0;
        for(move move : moves) {
        	game tempGame = new game();
        	tempGame.readFEN("8/8/8/8/4B3/8/8/8 w - - 0 1");
        	System.out.println("Move: "+ ++moveCount);
        	System.out.println(move);
        	tempGame.playMove(move);
        	tempGame.printBoard();
        	System.out.println();
        }
    }
}