package engine_1;

import java.util.ArrayList;
import engine_1.pieces.*;
import chessFunc.*;

public class game {
    String[][] board = new String[8][8];
    ArrayList<String> PGN; // What type should this be?
    private String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    String currentFEN = "";
    String enPassant = "-";     // enPassant; the square where enPassant is available (empty if not)
    int playerToMove = 1; // 1 = white, -1 = black: plauerToMove = 0-playerToMove
    boolean wCastleKSide = false;
    boolean wCastleQSide = false;
    boolean bCastleKSide = false;
    boolean bCastleQSide = false;
    
   
    //Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.[7]
    //Fullmove number: The number of the full move. It starts at 1, and is incremented after Black's move.
    int halfmove = 0; //Num plies since a pawn was moved or piece taken
    int fullmove = 0; // Incremented after each of black's moves
    
    /*
     * Board is from the perspective of white  - doe sthis need to be change when the board is flipped?
     * 
     *  - Fifty move rule check function to be applied after each move
     *  - Move function 
     *  - castling privileges check
     *  - check if the king is in 'check'
     *  - 
     */

    // Had 3 Constructors
    public game(){
        currentFEN = startFEN;
        readFEN(currentFEN);
    }

    public void readFEN(String fen){
    	currentFEN = fen;
        String[] fenArray = fen.split("(?!^)");
        
        int fenIndex = 0;
        for(int square = 0; square<64; square++){// Set up the board
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
        fenIndex++; // skip over empty space
        String p2Move = fenArray[fenIndex];
        if(p2Move.equals("w")) {
        	playerToMove = 1;
        }else if(p2Move.equals("b")) {
        	playerToMove = -1;
        }
        fenIndex++;
        // Castling privileges
        fenIndex++;// Skip empty space
        while(!fenArray[fenIndex].equals(" ")) {
        	if(fenArray[fenIndex].equals("K")) {
        		wCastleKSide = true;
        	}else if(fenArray[fenIndex].equals("Q")) {
        		wCastleQSide = true;
        	}else if(fenArray[fenIndex].equals("k")) {
        		bCastleKSide = true;
        	}else if(fenArray[fenIndex].equals("q")) {
        		bCastleQSide = true;
        	}
        	fenIndex++;
        }
 
        //En Passant
        fenIndex++; // Skip empty space
        if(fenArray[fenIndex].equals("-")) {
        	enPassant = "-";
        }else {
        	enPassant = fenArray[fenIndex] + fenArray[fenIndex+1];// enPassant is ALWAYS 2 characters eg. e3
        	fenIndex++;
        }
        fenIndex++;
        // read and set move counters
        fenIndex++; // Skip empty space
        if(isInteger(fenArray[fenIndex])) {
        	if(isInteger(fenArray[fenIndex+1])) {
        		fenIndex++;
        		halfmove = Integer.parseInt(fenArray[fenIndex-1] + fenArray[fenIndex]); //2 digit halfmove value - can never be more than 2 digits
        	}else {
        		halfmove = Integer.parseInt(fenArray[fenIndex]);
        	}
        	
        }else {System.out.println("FEN Read string that is not an integer for the halfmove value");}

        fenIndex++;

        // read and set castling privileges
        fenIndex++; // Skip empty space
        String fenFullmoveStr = "";
        while(fenIndex < fenArray.length){// ALl remaining characters should be integers in the correct format
        	fenFullmoveStr += fenArray[fenIndex];
        	fenIndex++;
        }
        if(isInteger(fenFullmoveStr)) {
        	fullmove = Integer.parseInt(fenFullmoveStr);
        }else {System.out.println("FEN Read string that is not an integer for the fullmove value");}       
    }   
    
    ArrayList<move> generatePseudoLegalMoves(){
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
    
    /**
     * IS ONLY PSUEDOLEGAL MOVES<br>
     * CHECK IF AFTER THE MOVE IS PLAYED THE PLAYER IS IN CHECK
     * 
     */
    public ArrayList<move> generateMoves(){
    	return generatePseudoLegalMoves();
    }
    
    
    /**
     * Uses a move object to play a chess move on the this game objects board[][]
     */
    public void playMove (move MOVE) {
    	/* Check move count + repetitions before generating a move
    	 */
    	
    	if(!board[MOVE.TARGET_SQUARE / 8][MOVE.TARGET_SQUARE % 8].equals(" ") || (MOVE.PIECE.getNAME().toUpperCase().equals("P"))) { // if capture occurs or pawn is moved halfmove counter is reset
    		halfmove = 0;
    	}else if(MOVE instanceof pawnMove) {// Set enPassant
    		try {
        	    this.enPassant = func.sqNumToStr( ((pawnMove)MOVE).EN_PASSANT_SQ );
    		}catch (ClassCastException e) {e.printStackTrace();}
    		
    		halfmove = 0;
    	}else {
    		halfmove += 1;
    	}
    	board[MOVE.START_SQUARE / 8][MOVE.START_SQUARE % 8] = " ";
    	board[MOVE.TARGET_SQUARE / 8][MOVE.TARGET_SQUARE % 8] = MOVE.PIECE.getNAME();
    	if(playerToMove == -1) { // If black has played a move
    		fullmove += 1;
    	}
    	
    	
    	
    	/*  
    	 *  - castling privileges
    	 *  - checkmate check
    	 */


    	
    	currentFEN = genFEN();
    	readFEN(currentFEN);
    	System.out.println("FEN: "+getFEN());
    	
    	/* 50 Move rule: check after move played
    	 * If the halfmove clock becomes greater or equal than 100, and the side to move has at least one legal move, a draw score should be assigned
    	 */
    }
    
    
    /** Generated the FEN for the current board[][] / game */
    
    // This is borked
    String genFEN() {
    	String newFEN = "";
    	
    	
    	// Method 1
    	
    	for(int sqNum = 0; sqNum<64; sqNum++) {
    		int curRank = sqNum/8;
    				
    		if(board[sqNum/8][sqNum%8].equals(" ")) {// empty space calc
    			int emptySquares = 0;
    			int chkSq = sqNum; // Square to check if the next value on the same rank is also blank
    			while((board[chkSq/8][chkSq%8].equals(" ")) && (curRank == chkSq/8)) {
    				chkSq++;
    				emptySquares++;
    			}
    			sqNum = chkSq - 1;
    			newFEN += emptySquares;
    		}else {
    			newFEN += board[sqNum/8][sqNum%8];
    		}
    		
    		if(sqNum%8 == 7) {// If is the last column in the row before moving down a row
    			newFEN += "/";
    		}
    	}
    	    	
       	newFEN = newFEN.substring(0, newFEN.length()-1);// remove the last / from the FEN
       	newFEN += " ";
       	
       	// Chnage the player to move
       	if(playerToMove == 1) {
       		newFEN += "b ";
       	}else if(playerToMove == -1) {
       		newFEN += "w ";
       	}else {
       		System.out.println("genFEN() setting the player to move"); // error: should never run
       	}
       
       	// Castling privileges
       	if(((wCastleKSide || wCastleQSide) || (bCastleKSide|| bCastleQSide))) {
       		if(wCastleKSide == true) {
       			newFEN += "K";
	       	}
	       	if(wCastleQSide == true) {
	       		newFEN += "Q";
	       	}if(bCastleKSide == true) {
	       		newFEN += "k";
	       	}if(bCastleQSide == true) {
	       		newFEN += "q";
	       	}
	       	newFEN += " ";
       	}else {
       		newFEN += "- ";
       	}

       	// Set the En Passant square if there is one
       	newFEN += enPassant+" ";
       	// halfmove count
       	newFEN += halfmove+" ";
       	// full move count
       	newFEN += fullmove;
       	
    	return newFEN;
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
    
    private boolean isInteger(String s){
        try{
            int i = Integer.parseInt(s);
            return true;
        }catch(java.lang.NumberFormatException e){
            return false;
        }
    }
    
    public String getFEN() {
    	return currentFEN;
    }
}
