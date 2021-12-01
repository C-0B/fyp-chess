package engine_1;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class game {
    String[][] board = new String[8][8];
    ArrayList<String> PGN; // What typ eshould this be?
    String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    String curentFEN = "";
    String enPassant = "";     // enPassant; the square where enPassant is available (empty if not)
    int playerToMove = 0; // 0 = white, 1 = black

    /*
     * Board is from the perspective of white
     * 
     *  - Fifty move rule check function to be aplied after each move
     *  - Move function 
     *  - castling privalages check
     *  - check if the king is in 'check'
     *  - 
     */

    // 3 Constructors
    public game(){
        curentFEN = startFEN;
    }


    void readFENtoBoard(String fen){
        String[] fenArray = fen.split("(?!^)");
        int fenIndex = 0;
        
        for(int square = 0; square<64; square++){
        	if(isInteger(fenArray[fenIndex])) {
        		for(int i =  0; i < Integer.parseInt(fenArray[fenIndex]); i ++) {
        			board[square/8][square%8] = " ";
        			square++;
        		}
        		square--;
        	}else if(!fenArray[fenIndex].equals("/")) {//else the character is a / which means go the next line
        		board[square/8][square%8] = fenArray[fenIndex];	
        	}else {
        		square--;
        	}
        	fenIndex++;
        }
    }
    
    private void setFEN(String fen){
        curentFEN = fen;
    }


    private boolean isInteger(String s){
        try{
            int i = Integer.parseInt(s);
            return true;
        }catch(java.lang.NumberFormatException e){
            return false;
        }
    }

    void printBoard(){
        System.out.println("+-+-+-+-+-+-+-+-+");
        for(int rank = 0; rank<8; rank++){
            System.out.print("|");
            for(int file = 0; file<8; file++){
                System.out.print(board[rank][file]+"|");
            }
            System.out.println();
            
            System.out.println("+-+-+-+-+-+-+-+-+");
        }
    }

    @Test
    public void test_board(){
        game game1 = new game();
        game1.printBoard();
        int i = 10;
        Assertions.assertFalse(false);
    }
}
