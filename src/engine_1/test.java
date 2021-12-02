package engine_1;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        game game1 = new game();
        
        //game1.readFENtoBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        //game1.readFENtoBoard("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
        game1.readFENtoBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        
        //game1.playMove(null);
        game1.printBoard();
        
        pawn A_PAWN = new pawn(game1.board, "P", 0, -1);
        ArrayList<move> a_pawn_moves =  A_PAWN.generateMoves();
        for(move _Move : a_pawn_moves) {
        	System.out.println();
        }
    }
}
