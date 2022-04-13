package engine;

import java.util.ArrayList;

import log.logger;
import chessFunc.func;
import engine.pieces.*;
import log.logger;

public class game {
    String[][] board = new String[8][8];
    ArrayList<String> PGN; // What type should this be?
    private String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
//    private String startFEN = "4k3/8/8/8/8/8/8/4K3 w - - 0 1";
    String fen = "";
    String enPassant = "-";     // enPassant; the square where enPassant is available (empty if not)
    int playerToMove = 1; // 1 = white, -1 = black: plauerToMove = 0-playerToMove
    boolean wCastleKSide = false;
    boolean wCastleQSide = false;
    boolean bCastleKSide = false;
    boolean bCastleQSide = false;
    int halfmove = 0; // Num plies since a pawn was moved or piece taken //Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.[7]
    int fullmove = 0; // Incremented after each of black's moves //Fullmove number: The number of the full move. It starts at 1, and is incremented after Black's move.
    
    pawn vunerbalePawn; // pawn that will be captured when the en passant capture occurs 
    // could be change to lastPawnMoved
    
    
    /* Board is from the perspective of white  - does this need to be change when the board is flipped ? no
     *  - Fifty move rule check function to be applied after each move
     *  - Move function 
     *  - castling privileges check
     *  - check if the king is in 'check'
     *  - 
     */
    public game(){
        readFEN(startFEN);
    }
    public game(String FENtoStartFrom) {
        readFEN(FENtoStartFrom);
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

    public void readFEN(String newFen){
    	fen = newFen;
        String[] splitFEN = newFen.split(" ");
        // Board
        String boardFEN[] = splitFEN[0].split("(?!^)");
        int boardFENindex=0;
        for(int square = 0; square<64; square++){// Set up the board
            if(isInteger(boardFEN[boardFENindex])) {
                for(int i =  0; i < Integer.parseInt(boardFEN[boardFENindex]); i ++) {
                    board[square/8][square%8] = " ";
                    square++;
                }
                square--;
            }else if(!boardFEN[boardFENindex].equals("/")) {//else the character is a / which means go the next line
            	board[square/8][square%8] = boardFEN[boardFENindex];	
            }else {
                square--;
            }
            boardFENindex++; 
        }

        // Player To Move
        if(splitFEN[1].equals("w"))		{ playerToMove = 1; }
        else if(splitFEN[1].equals("b")){ playerToMove = -1; }
        else{ System.out.println("Error reading player to move from FEN -> "+newFen); }

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
//        System.out.println("Player To Move: "+splitFEN[1]+" aka "+playerToMove);
//        System.out.println("wK: "+wCastleKSide+" wQ: "+wCastleQSide);
//        System.out.println("bK: "+bCastleKSide+" bQ: "+bCastleQSide);
//        System.out.println("En Passant: "+enPassant);
//        System.out.println("fullmove: "+fullmove);
//        System.out.println("halfmove: "+halfmove);
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
    	System.out.println("move generation beigns");
    	ArrayList<move> legalMoves = new ArrayList<move>();
    	ArrayList<piece> pieces = new ArrayList<piece>();
    	/* Part 1
    	 * Gets a list of the players pieces */
    	if(getPlayerToMove() == 1){//White is next  to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				String currentSqStr = board[squareNum /8][squareNum % 8]; //Contents of current square
				if( (!currentSqStr.equals(" ")) && (currentSqStr.equals(currentSqStr.toUpperCase())) ) { //Upper case = white
					piece currentPiece = createPiece(currentSqStr, squareNum);
					pieces.add(currentPiece);
				}
			}
    	}else if(getPlayerToMove() == -1){//Black is next to move
    		for (int squareNum = 0; squareNum < 63; squareNum++) {//Starting form top left to bottom right loop though all squares
				String currentSqStr = board[squareNum /8][squareNum % 8]; //Contents of current square
				if( (!currentSqStr.equals(" ")) && (currentSqStr.equals(currentSqStr.toLowerCase())) ) {//Lower case = black
					piece currentPiece = createPiece(currentSqStr, squareNum);
					pieces.add(currentPiece);
				}
			}    		
    	}
    	
    	/* Part 2
    	 * Loops through the players pieces and calculates to pseudolegal moves for each piece
    	 */
     	for (piece curPiece : pieces) {
    		ArrayList<move> pieceMoves = new ArrayList<move>();
    		pieceMoves = curPiece.generateMoves(board);
    		legalMoves.addAll(pieceMoves);
    	}
    	
    	/* Part 4
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
    	 * add castling moves where appropriate
    	 * must check if the king passes though check here.
    	 */
    	if(playerToMove == 1) {
    		if(wCastleKSide) {
    			
    		}
    		if(wCastleQSide) {
    			
    		}
    	}else if(playerToMove == -1) {
    		if(bCastleKSide) {
    			
    		}
    		if(bCastleQSide) {
    			
    		}
    	}
	
     	/* Part 3
    	 * Check if each move leaves the players king in check
    	 */
    	for(move move : legalMoves) {
    		System.out.println(move);
    	}
    	System.out.println("move generation ends");
    	return legalMoves;
    }// end of generateMoves function
    
    /** 
     * Uses a move object to play a chess move on the this game objects board[][] */
//    public void playMove (move MOVE) {
//    	if(!board[MOVE.getTARGET_SQUARE() / 8][MOVE.getTARGET_SQUARE() % 8].equals(" ")
//    		|| (MOVE.PIECE.getNAME().toUpperCase().equals("P"))) { // if capture occurs or pawn is moved halfmove counter is reset
//    		halfmove = 0;
//    	}
//    	//else if(MOVE instanceof pawnMove) {// Set enPassant
//    	else { halfmove += 1; }
//		try {
//			System.out.println("before: "+this.enPassant);
//    	    this.enPassant = func.sqNumToStr( MOVE.EN_PASSANT_SQ );
//    	    System.out.println("after: "+this.enPassant);
//		}catch (ClassCastException e) {e.printStackTrace();}
//
//    	board[MOVE.START_SQUARE / 8][MOVE.START_SQUARE % 8] = " ";
//    	board[MOVE.getTARGET_SQUARE() / 8][MOVE.getTARGET_SQUARE() % 8] = MOVE.PIECE.getNAME();
//    	if(getPlayerToMove() == -1) { // If black has played a move
//    		fullmove += 1;
//    	}
//    	/*  
//    	 *  - castling privileges
//    	 *  - checkmate check */
//    	currentFEN = generateFENfromBoard();
//    	readFEN(currentFEN);
//    	
//    	/* 50 Move rule: check after move played
//    	 * If the halfmove clock becomes greater or equal than 100, and the side to move has at least one legal move, a draw score should be assigned
//    	 */
//    	/* Check move count + repetitions before generating a move */
//    	
//    	enPassant = "-"; // Reset en passant
//    	
////    	logger log = new logger();
////    	log.logMove(MOVE.PIECE.getColour(), MOVE.PIECE.getNAME(), func.sqNumToStr(MOVE.START_SQUARE), func.sqNumToStr(MOVE.TARGET_SQUARE), currentFEN);
//    	
//    }// end of playMove function
    
    
    /**Make a legal move on the board,
     * only legal moves should be passed to this function
     * <p>
     * castling will call this twice.*/
    public void playMove (move MOVE) {
    	int startSqInt = func.sqStrToInt(MOVE.getStartSquareStr());
    	System.out.println(MOVE.getStartSquareStr()+" "+startSqInt);
    	int targetSqInt = func.sqStrToInt(MOVE.getTargetSquareStr());
    	System.out.println(MOVE.getTargetSquareStr()+" "+targetSqInt);
    	
    	int[] startCoord = func.sqIntToCoord(startSqInt);
    	int[] targetCoord = func.sqIntToCoord(targetSqInt);
    	
    	String piece = board[startCoord[0]][startCoord[1]];
    	board[targetCoord[0]][targetCoord[1]] = piece;
    	board[startCoord[0]][startCoord[1]] = " ";
    	
    	fen = generateFENfromBoard();
    }
    
    
    
    void castleKSide(int colour) {
    	//play move of the king
    	//play move of the rook
    }
    void castleQside(int colour) {
    	//play move of the king
    	//play move of the rook
    }
    
    /** Generates the FEN for the current board[][] / game
     * this should no longer be borked and should work correctly ... Kap ...
     */
    public String generateFENfromBoard() {
    	String newFEN = "";
    	for(int sqNum = 0; sqNum<64; sqNum++) {
    		int[] sqCoords = func.sqIntToCoord(sqNum);
    		int curRank = sqCoords[0];
    		int curFile = sqCoords[1];
    		if(board[curRank][curFile].equals(" ")) {// empty space calc
    			int emptySquares = 0;

        		int[] chkCoord = func.sqIntToCoord(sqNum);
        		int chkRank = chkCoord[0];
        		int chkFile = chkCoord[1];
    			int chkSq = sqNum; // Square to check if the next value on the same rank is also blank
    			while((board[curRank][curFile].equals(" ")) && (curRank == chkSq/8)) {
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
       	
       	// Change the player to move
       	if(getPlayerToMove() == 1) { newFEN += "b "; }
       	else if(getPlayerToMove() == -1) { newFEN += "w "; }
       	else { System.out.println("genFEN() setting the player to move"); }// error: should never run 
       
       	// Castling privileges
       	if(((wCastleKSide || wCastleQSide) || (bCastleKSide|| bCastleQSide))) {
       		if(wCastleKSide == true) { newFEN += "K"; }
	       	if(wCastleQSide == true) { newFEN += "Q"; }
	       	if(bCastleKSide == true) { newFEN += "k"; }
	       	if(bCastleQSide == true) { newFEN += "q"; }
	       	newFEN += " ";
       	}else { newFEN += "- "; }

       	// Set the En Passant square if there is one
       	newFEN += enPassant+" ";
       	// halfmove count
       	newFEN += halfmove+" ";
       	// full move count
       	newFEN += fullmove;
       	
    	return newFEN;
    }

    private piece createPiece(String pName, int square) {
    	// Sets the piece colour
    	int colour = 0;
    	if(pName.equals(pName.toUpperCase())) {colour = 1;}//White
    	else if(pName.equals(pName.toLowerCase())){colour = -1;}//Black
    	
    	if (pName.equals("p") || pName.equals("P")) {
    		pawn PAWN = new pawn(pName, square, colour, enPassant);
    		return PAWN;
    	}else if (pName.equals("r") || pName.equals("R")) {
    		rook ROOK = new rook(pName, square, colour);
    		return ROOK;
    	}else if (pName.equals("n") || pName.equals("N")) {
    		knight KNGIHT = new knight(pName, square, colour);
    		return KNGIHT;
    	}else if (pName.equals("b") || pName.equals("B")) {
    		bishop BISHOP = new bishop(pName, square, colour);
    		return BISHOP;
    	}else if (pName.equals("k") || pName.equals("K")) {
    		king KING = new king(pName, square, colour);
    		return KING;
    	}else if (pName.equals("q") || pName.equals("Q")) {
    		queen QUEEN = new queen(pName, square, colour);
    		return QUEEN;
    	}
    	// This would be an error
    	System.out.println("createPiece returns null on: "+pName+" @ "+square);
		return null;
    }
    
	/**creates a temporary game from the current position to check which if
	 * any pieces of the given colour are attacking the sqToChk.
	 * </br></br>
	 * If so returns true */
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
	
    /** ONLT FOR TESTING THE GUI
     *  @param moves */
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
                System.out.print(board[rank][file]+"|");
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
            	boardStr += board[rank][file]+"|";
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
    	return fen;
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
