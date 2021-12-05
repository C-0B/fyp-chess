package engine_1;

import java.util.ArrayList;
import java.util.random.RandomGenerator.LeapableGenerator;

import engine_1.pieces.*;
import chessFunc.*;

public class game {
    String[][] board = new String[8][8];
    ArrayList<String> PGN; // What type should this be?
    private String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    String curentFEN = "";
    String enPassant = "";     // enPassant; the square where enPassant is available (empty if not)
    int playerToMove = 1; // 1 = white, -1 = black: plauerToMove = 0-playerToMove
    
    //Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.[7]
    //Fullmove number: The number of the full move. It starts at 1, and is incremented after Black's move.
    int halfmove = 0; //Num plies since a pawn was moved or piece taken
    int fullmove = 0; // Incremented after each of black's moves
    
    /*
     * Board is from the perspective of white
     * 
     *  - Fifty move rule check function to be applied after each move
     *  - Move function 
     *  - castling privileges check
     *  - check if the king is in 'check'
     *  - 
     */

    // Had 3 Constructors
    public game(){
        curentFEN = startFEN;
    }

    void readFEN(String fen){
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
        // read and set player to move
        // read and set move counters
        // read and set castling privileges
    }   
    
    ArrayList<move> generateMoves(){
    	ArrayList<move> pseudoLegalMoves = new ArrayList<move>();
    	ArrayList<piece> pieces = new ArrayList<piece>();
    	
    	/* Part 1
    	 * Gets a list of the players pieces
    	 */
    	if(playerToMove == 1){//White is next  to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				String currentSq = board[squareNum /8][squareNum % 8];
				if( (!currentSq.equals(" ")) && (currentSq.equals(currentSq.toUpperCase())) ) { //Upper case = white
					piece currentPiece = createPiece(currentSq, squareNum);
					pieces.add(currentPiece);
				}
			}
    	}else if(playerToMove == -1){//Black is next to move
    		for (int squareNum = 0; squareNum < 63; squareNum++) {//Starting form top left to bottom right loop though all squares
				String currentSq = board[squareNum /8][squareNum % 8];
				if( (!currentSq.equals(" ")) && (currentSq.equals(currentSq.toLowerCase())) ) {//Lower case = black
					piece currentPiece = createPiece(currentSq, squareNum);
					pieces.add(currentPiece);
				}
			}    		
    	}
    	
    	/* Part 2
    	 * Loops through the players pieces and calculates to moves for each piece
    	 */
     	for (piece curPiece : pieces) {
    		ArrayList<move> pieceMoves = new ArrayList<move>();
    		pieceMoves = curPiece.generateMoves(board);
    		for(move pieceMove : pieceMoves) {
    		}
    		pseudoLegalMoves.addAll(pieceMoves);
    	}
    	
    	/* 
    	 * Part 3
    	 * Check if each move leaves the players king in check
    	 * 
    	 *  TBD 
    	 *  
    	 */
    	
    	return pseudoLegalMoves;
    }
    
    void playMove (move MOVE) {
    	/*
    	 * Check move count + repetitions before generating a move
    	 */
    	board[MOVE.START_SQUARE / 8][MOVE.START_SQUARE % 8] = " ";
    	board[MOVE.TARGET_SQUARE / 8][MOVE.TARGET_SQUARE % 8] = MOVE.PIECE.getNAME();
    	
    	/* Update fen
    	 *  - move count - 50 move rule etc
    	 *  - castling privileges
    	 */
    }

    private piece createPiece(String pName, int sqaure) {
    	// Sets the piece colour
    	int colour = 0;
    	if(pName.equals(pName.toUpperCase())) {colour = 1;}//White
    	else if(pName.equals(pName.toLowerCase())){colour = -1;}//Black
    	
    	if (pName.equals("p") || pName.equals("P")) {
    		pawn PAWN = new pawn(pName, sqaure, colour);
    		return PAWN;

    	}else if (pName.equals("r") || pName.equals("R")) {
    		rook ROOK = new rook(pName, sqaure, colour);
    		return ROOK;

    	}else if (pName.equals("n") || pName.equals("N")) {
    		knight KNGIHT = new knight(pName, sqaure, colour);
    		return KNGIHT;

    	}else if (pName.equals("b") || pName.equals("B")) {
    		bishop BISHOP = new bishop(pName, sqaure, colour);
    		return BISHOP;

    	}else if (pName.equals("k") || pName.equals("K")) {
    		king KING = new king(pName, sqaure, colour);
    		return KING;

    	}else if (pName.equals("q") || pName.equals("Q")) {
    		queen QUEEN = new queen(pName, sqaure, colour);
    		return QUEEN;
    	}
    	// This would be an error
    	System.out.println("createPiece returns null on: "+pName+" @ "+sqaure);
		return null;
    }
    
    void printBoard(){
        System.out.println("+-+-+-+-+-+-+-+-+");
        for(int rank = 0; rank<8; rank++){
            System.out.print("|");
            for(int file = 0; file<8; file++){
                System.out.print(board[rank][file]+"|");
                //System.out.print(rank + ""+ file+"|");
            }
            System.out.println();
            
            System.out.println("+-+-+-+-+-+-+-+-+");
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
}
