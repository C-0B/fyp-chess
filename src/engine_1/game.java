package engine_1;

import java.util.ArrayList;

import engine_1.pieces.*;
import log.logger;
import chessFunc.func;
import log.logger;

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
    int halfmove = 0; //Num plies since a pawn was moved or piece taken //Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.[7]
    int fullmove = 0; // Incremented after each of black's moves //Fullmove number: The number of the full move. It starts at 1, and is incremented after Black's move.
    
    pawn vunerbalePawn; // pawn that will be captured when the en passant capture occurs
    
    
    /*
     * Board is from the perspective of white  - does this need to be change when the board is flipped?
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
    
    public game(String FENtoStartFrom) {
    	currentFEN = FENtoStartFrom;
        readFEN(currentFEN);
    }
    
    /**
     * Check if the player 's king is in check
     * <p>
     * @param player 1 for white, -1 for black
     * @return true is the player given as a parameter is in check
     */
    public boolean isPlayerInCheck(int player) {
    	// TO BE DONE
    	return false;
    }

    public void readFEN(String fen){
    	currentFEN = fen;
        String[] splitFEN = fen.split(" ");

        // Board
        String boardFEN[] = splitFEN[0].split("(?!^)");
        int boardFENindex=0;
        for(int square = 0; square<64; square++){// Set up the board
            if(isInteger(boardFEN[boardFENindex])) {
                for(int i =  0; i < Integer.parseInt(boardFEN[boardFENindex]); i ++) {
                    getBoard()[square/8][square%8] = " ";
                    square++;
                }
                square--;
            }else if(!boardFEN[boardFENindex].equals("/")) {//else the character is a / which means go the next line
                getBoard()[square/8][square%8] = boardFEN[boardFENindex];	
            }else {
                square--;
            }
            boardFENindex++; 
        }

        // Player To Move
        if(splitFEN[1].equals("w"))		{ playerToMove = 1; }
        else if(splitFEN[1].equals("b")){ playerToMove = -1; }
        else{ System.out.println("Error reading player to move from FEN -> "+fen); }

        // Castling Rights
        if(!splitFEN[2].equals("-")){ // there are some castling rights
            if(splitFEN[2].contains("K")) { wCastleKSide = true; }
            if(splitFEN[2].contains("Q")) { wCastleQSide = true; }
            if(splitFEN[2].contains("k")) { bCastleKSide = true; }
            if(splitFEN[2].contains("q")) { bCastleQSide = true; }
        }

        // En passant
        halfmove = Integer.parseInt(splitFEN[4]);// Half move counter
        fullmove = Integer.parseInt(splitFEN[5]);// Full move counter
        
        printBoard();
        System.out.println("Player To Move: "+splitFEN[1]+" aka "+playerToMove);
        System.out.println("wK: "+wCastleKSide+" wQ: "+wCastleQSide);
        System.out.println("bK: "+bCastleKSide+" bQ: "+bCastleQSide);
        System.out.println("En Passant -> "+enPassant);
        System.out.println("fullmove: "+fullmove);
        System.out.println("halfmove: "+halfmove);
    } // End of readFEN function
    
	/**
	 * Part 1:
	 *  - Gets a list of the players pieces with piece name and location
	 *  <br>
	 * Part 2:
	 *  - Loops through the players pieces and calculates to pseudolegal moves for each piece
	 *  <br>
	 * Part 3/4:
	 *  - Remove all moves where the current player is left in check
	 *  - Check to see of the opponent is left in check?
	 * @return 
	 */
    public ArrayList<move> generateMoves(){
    	ArrayList<move> legalMoves = new ArrayList<move>();
    	ArrayList<piece> pieces = new ArrayList<piece>();
    	/* Part 1
    	 * Gets a list of the players pieces */
    	if(getPlayerToMove() == 1){//White is next  to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				String currentSq = getBoard()[squareNum /8][squareNum % 8]; //Contents of current square
				if( (!currentSq.equals(" ")) && (currentSq.equals(currentSq.toUpperCase())) ) { //Upper case = white
					piece currentPiece = createPiece(currentSq, squareNum);
					pieces.add(currentPiece);
				}
			}
    	}else if(getPlayerToMove() == -1){//Black is next to move
    		for (int squareNum = 0; squareNum < 63; squareNum++) {//Starting form top left to bottom right loop though all squares
				String currentSq = getBoard()[squareNum /8][squareNum % 8]; //Contents of current square
				if( (!currentSq.equals(" ")) && (currentSq.equals(currentSq.toLowerCase())) ) {//Lower case = black
					piece currentPiece = createPiece(currentSq, squareNum);
					pieces.add(currentPiece);
				}
			}    		
    	}
    	
    	/* Part 2
    	 * Loops through the players pieces and calculates to pseudolegal moves for each piece
    	 */
     	for (piece curPiece : pieces) {
    		ArrayList<move> pieceMoves = new ArrayList<move>();
    		pieceMoves = curPiece.generateMoves(getBoard());

    		legalMoves.addAll(pieceMoves);
    	}
    	
    	/* 
    	 * Part 4
    	 * Check if the target square is the opponents king(which is not allowed)
    	 * 
    	 *  - removed the items after iteration 
    	 */
     	
    	int moveCount = 0;
    	ArrayList<Integer> movesToRemove = new ArrayList<Integer>();
    	for(move move : legalMoves) {
    		if(move.isKingTarget()) {
    			legalMoves.remove(moveCount);
    			movesToRemove.add(moveCount);
    			try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} // Removes all moves that would capture the king
    		moveCount++;
    	}
     	/*
    	 * Part 3
    	 * Check if each move leaves the players king in check
    	 *  
    	 */
    	
    	return legalMoves;
    }
    
    /**
     * Uses a move object to play a chess move on the this game objects board[][]
     */
    public void playMove (move MOVE) {
    	/* Check move count + repetitions before generating a move
    	 */
    	enPassant = "-"; // Reset en passant
    	
    	if(!getBoard()[MOVE.getTARGET_SQUARE() / 8][MOVE.getTARGET_SQUARE() % 8].equals(" ") || (MOVE.PIECE.getNAME().toUpperCase().equals("P"))) { // if capture occurs or pawn is moved halfmove counter is reset
    		halfmove = 0;
    	}
    	//else if(MOVE instanceof pawnMove) {// Set enPassant
    	else {
    		halfmove += 1;
    	}
    	
		try {
			System.out.println("before: "+this.enPassant);
    	    this.enPassant = func.sqNumToStr( MOVE.EN_PASSANT_SQ );
    	    System.out.println("after: "+this.enPassant);
		}catch (ClassCastException e) {e.printStackTrace();}
		
		
		
    	getBoard()[MOVE.START_SQUARE / 8][MOVE.START_SQUARE % 8] = " ";
    	getBoard()[MOVE.getTARGET_SQUARE() / 8][MOVE.getTARGET_SQUARE() % 8] = MOVE.PIECE.getNAME();
    	if(getPlayerToMove() == -1) { // If black has played a move
    		fullmove += 1;
    	}
    	
    	
    	
    	/*  
    	 *  - castling privileges
    	 *  - checkmate check
    	 */


    	
    	currentFEN = genFENfromBoard();
    	readFEN(currentFEN);
    	
    	/* 50 Move rule: check after move played
    	 * If the halfmove clock becomes greater or equal than 100, and the side to move has at least one legal move, a draw score should be assigned
    	 */
    	
    	logger log = new logger();
    	log.logMove(MOVE.PIECE.getColour(), MOVE.PIECE.getNAME(), func.sqNumToStr(MOVE.START_SQUARE), func.sqNumToStr(MOVE.TARGET_SQUARE), currentFEN);
    	
    }
    
    /** Generates the FEN for the current board[][] / game
     * this should no longer be borked and should work correctly ... Kap ...
     */
    public String genFENfromBoard() {
    	String newFEN = "";
    	for(int sqNum = 0; sqNum<64; sqNum++) {
    		int curRank = sqNum/8;
    				
    		if(getBoard()[sqNum/8][sqNum%8].equals(" ")) {// empty space calc
    			int emptySquares = 0;
    			int chkSq = sqNum; // Square to check if the next value on the same rank is also blank
    			while((getBoard()[chkSq/8][chkSq%8].equals(" ")) && (curRank == chkSq/8)) {
    				chkSq++;
    				emptySquares++;
    			}
    			sqNum = chkSq - 1;
    			newFEN += emptySquares;
    		}else {
    			newFEN += getBoard()[sqNum/8][sqNum%8];
    		}
    		
    		if(sqNum%8 == 7) {// If is the last column in the row before moving down a row
    			newFEN += "/";
    		}
    	}
    	    	
       	newFEN = newFEN.substring(0, newFEN.length()-1);// remove the last / from the FEN
       	newFEN += " ";
       	
       	// Change the player to move
       	if(getPlayerToMove() == 1) {
       		newFEN += "b ";
       	}else if(getPlayerToMove() == -1) {
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
    		pawn PAWN = new pawn(pName, sqaure, colour, enPassant);
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
    
	/**
	 * creates a temporary game from the current position to check which if
	 * any pieces of the given colour are attacking the sqToChk.
	 * </br></br>
	 * If so returns true
	 */
	public boolean isSquareAttacked(int sqToChk, int pieceColour) {		
		game tempGame = new game(this.getFEN());
		tempGame.setPlayerToMove(pieceColour);
		
		ArrayList<move> movesToCheck = tempGame.generateMoves();
		
		for(move move : movesToCheck) {
			if(move.getTARGET_SQUARE() == sqToChk) {
				return true;
			}
		}
		return false;
	}
	
	
	
    /**
     * ONLT FOR TESTING THE GUI
     * @param moves
     */
    public void printPossibleMoves(ArrayList<move> moves) {
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
    
    void printBoard(){
        System.out.println("+-+-+-+-+-+-+-+-+");
        for(int rank = 0; rank<8; rank++){
            System.out.print("|");
            for(int file = 0; file<8; file++){
                System.out.print(getBoard()[rank][file]+"|");
                //System.out.print(rank + ""+ file+"|");
            }
            System.out.println();
            
            System.out.println("+-+-+-+-+-+-+-+-+");
        }
    }
    
    public String getBoardasStr() {
    	String boardStr = "<html>";
    	boardStr += "+-+-+-+-+-+-+-+-+<br/>";
        for(int rank = 0; rank<8; rank++){

        	boardStr += "|";
            for(int file = 0; file<8; file++){
            	boardStr += getBoard()[rank][file]+"|";
                //System.out.print(rank + ""+ file+"|");
            }
        	boardStr += "<br/>+-+-+-+-+-+-+-+-+<br/>";
        }
        boardStr += "</html>";
        return boardStr;
    }
	
    private boolean isInteger(String s){
        try{
            int i = Integer.parseInt(s);
            return true;
        }catch(java.lang.NumberFormatException e){
            return false;
        }
    }
    
    
    // --- GETERS & SETTERS GO HERE ---
    public String getFEN() {
    	return currentFEN;
    }
    
	public int getPlayerToMove() {
		return playerToMove;
	}
	
	public void setPlayerToMove(int pToMove) {
		playerToMove = pToMove;
	}

	public String[][] getBoard() {
		return board;
	}

}
