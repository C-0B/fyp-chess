package engineByte;

import java.util.ArrayList;
import java.util.Collections;

import chessFunc.func;
import engineByte.pieces.bishop;
import engineByte.pieces.king;
import engineByte.pieces.knight;
import engineByte.pieces.pawn;
import engineByte.pieces.piece;
import engineByte.pieces.queen;
import engineByte.pieces.rook;

public class byteGame {
    byte[][] board = new byte[8][8];
    ArrayList<String> PGN; // What type should this be?
    ArrayList<byteMove> legalMovesForPosition = new ArrayList<byteMove>();
    private String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    
    final byte empty = 0;
    
    final byte blackPawn = 1;
    final byte blackKnight = 2;
    final byte blackBishop = 3;
    final byte blackRook = 4;
    final byte blackQueen = 5;
    final byte blackKing = 6;
    
    final byte whitePawn = 7;
    final byte whiteKnight = 8;
    final byte whiteBishop = 9;
    final byte whiteRook = 10;
    final byte whiteQueen = 11;
    final byte whiteKing = 12;
    
    ArrayList<String> previousPositions = new ArrayList<String>();//Threefold repitition 
    
    String fen = "";
    String enPassant = "-";     // enPassant; the square where enPassant is available (empty if not)
    int playerToMove = 1; // 1 = white, -1 = black: plauerToMove = 0-playerToMove
    boolean wCastleKSide = false;
    boolean wCastleQSide = false;
    boolean bCastleKSide = false;
	boolean bCastleQSide = false;
	
    public boolean canwCastleKSide() { return wCastleKSide; }
	public boolean canwCastleQSide() { return wCastleQSide; }
	public boolean canbCastleKSide() { return bCastleKSide; }
	public boolean canbCastleQSide() { return bCastleQSide; }
    
    int halfmove = 0; // Num plies since a pawn was moved or piece taken //Halfmove clock: The number of halfmoves since the last capture or pawn advance, used for the fifty-move rule.[7]
    int fullmove = 0; // Incremented after each of black's moves //Fullmove number: The number of the full move. It starts at 1, and is incremented after Black's move.
    
    private String endCondition = "active";
    public String getEndCondition() { return endCondition; }
    
    /* Board is from the perspective of white  - does this need to be change when the board is flipped ? no
     *  - Fifty move rule check function to be applied after each move
     *  - Move function 
     *  - castling privileges check
     *  - check if the king is in 'check' */
    public byteGame() {
    	readFEN(startFEN); 
    	previousPositions.add(FENtoPosition(this.fen));
    }
    public byteGame(String FENtoStartFrom) {
    	readFEN(FENtoStartFrom);
	}
    
    
    //Getter and setters (utilities)
    public String getFEN() { return fen; }
	public int getPlayerToMove() { return playerToMove; }
	public void togglePlayerToMove() { playerToMove -= 2*(playerToMove); }
	public byte[][] getBoard() { return board; }
    
    public void readFEN(String newFen){
    	fen = newFen;
        String[] splitFEN = newFen.split(" ");
        String boardFEN[] = splitFEN[0].split("(?!^)");
        int boardFENindex=0;
        for(int square = 0; square<64; square++){// Set up the board
            if(isInteger(boardFEN[boardFENindex])) {
                for(int i =  0; i < Integer.parseInt(boardFEN[boardFENindex]); i ++) {
                    board[square/8][square%8] = 0;//Blank
                    square++;
                }
                square--;
            }else if(!boardFEN[boardFENindex].equals("/")) {//else the character is a / which means go the next line
            	board[square/8][square%8] = func.pieceStrToByte( boardFEN[boardFENindex] );	
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
        enPassant = splitFEN[3];// eg a1, h4, d3 ....
        halfmove = Integer.parseInt(splitFEN[4]);// Half move counter
        fullmove = Integer.parseInt(splitFEN[5]);// Full move counter        
    } // End of readFEN function
    
    

	/** Part 1:
	 *  - Gets a list of the players pieces with piece name and location<br>
	 *  Part 2:
	 *  - Loops through the players pieces and calculates to pseudoLegal moves for each piece<br> */
    public ArrayList<byteMove> generatePseudoLegalMoves(){
    	ArrayList<byteMove> pseudolegalMoves = new ArrayList<byteMove>();
    	
    	/* Gets a list of the players pieces */
    	 ArrayList<piece> pieces = getListOfPiecesOf(getPlayerToMove());
    	 int count = 1;
    	/* Loops through the players pieces and calculates to pseudolegal moves for each piece */
     	for (piece curPiece : pieces) {
    		ArrayList<byteMove> pieceMoves = new ArrayList<byteMove>();
    		pieceMoves = curPiece.generateMoves(board);
    		pseudolegalMoves.addAll(pieceMoves);
    	}
     	
     	// Castling moves
    	if( !isPlayerInCheck(getPlayerToMove()) ) {
        	if( getPlayerToMove() == 1 ) {
            	if(wCastleKSide) {
            		if( !isSquareAttacked(61, getPlayerToMove())){
            			if((isSquareEmpty(61)) && (isSquareEmpty(62)) ) {
                			pseudolegalMoves.add(new byteMove("e1", "g1"));
            			}
            		}
            	}
            	if(wCastleQSide) {
            		if( !isSquareAttacked(58, getPlayerToMove())) {
            			if( isSquareEmpty(59) ) {
	            			if( isSquareEmpty(58)) {
	            				if( isSquareEmpty(57) ) {
	            					pseudolegalMoves.add(new byteMove("e1", "c1"));
	            				}
	            			}
            			}
            		} 
            	}
        	}else if( getPlayerToMove() == -1 ) {
            	if(bCastleKSide) {
            		if( !isSquareAttacked(5, getPlayerToMove()) ){
            			if( (isSquareEmpty(5)) && (isSquareEmpty(6)) ) {
            				pseudolegalMoves.add(new byteMove("e8", "g8"));	
            			}
            		}
	            	if(bCastleQSide) {
	            		if( !isSquareAttacked(3, getPlayerToMove()) ) {
	            			if( isSquareEmpty(3) ) {
		            			if( isSquareEmpty(2)) {
		            				if( isSquareEmpty(1) ) {
		            					pseudolegalMoves.add(new byteMove("e8", "c8"));
		            				}
		            			}
	            			}
	            		}
	            	}
            	}
        	}
    	}
    	
    	//en passant moves
    	if( !enPassant.equals("-") ) {
    		ArrayList<piece> pawns = getListOfPawns(getPlayerToMove());
    		ArrayList<byteMove> pawnAttackingMoves = new ArrayList<byteMove>();
    		for(piece pawn : pawns) {
    			pawnAttackingMoves.addAll(pawn.generateAttackingMoves(board));
    		}
    		for(byteMove attackingPawnMove : pawnAttackingMoves) {
    			if(attackingPawnMove.getTargetSquareStr().equals(enPassant)) {
    				pseudolegalMoves.add(attackingPawnMove);
    			}
    		}
    	}
    	return pseudolegalMoves;
    }// end of generateMoves function  
    
    
    
    /**@return A List of legal move for the current player given the current
     * board position */
    public ArrayList<byteMove> generateMoves(){
    	long startTime = System.nanoTime();
    	ArrayList<byteMove> pseudolegalMoves = generatePseudoLegalMoves();
    	ArrayList<byteMove> legalMoves = new ArrayList<byteMove>();

     	/* Check if each move leaves the players king in check */
    	for(byteMove move : pseudolegalMoves) {
    		int plyr = getPlayerToMove();
    		byteGame tempGame = new byteGame(this.fen);
    		tempGame.playMove(move, 2, (byte)0);
    		if( !tempGame.isPlayerInCheck(plyr) ) {
    			if( isPromotionMove(move) ){
    				if(getPlayerToMove() == 1) {
    					bytePromotionMove promoMoveQ = new bytePromotionMove(move, (byte)11);
    					bytePromotionMove promoMoveR = new bytePromotionMove(move, (byte)10);
    					bytePromotionMove promoMoveN = new bytePromotionMove(move, (byte)8);
    					bytePromotionMove promoMoveB = new bytePromotionMove(move, (byte)9);
    					
    					legalMoves.add(promoMoveQ);
    					legalMoves.add(promoMoveR);
    					legalMoves.add(promoMoveN);
    					legalMoves.add(promoMoveB);
    				}else if(getPlayerToMove() == -1) {
    					bytePromotionMove promoMoveq = new bytePromotionMove(move, (byte)5);
    					bytePromotionMove promoMover = new bytePromotionMove(move, (byte)4);
    					bytePromotionMove promoMoven = new bytePromotionMove(move, (byte)2);
    					bytePromotionMove promoMoveb = new bytePromotionMove(move, (byte)3);

    					legalMoves.add(promoMoveq);
    					legalMoves.add(promoMover);
    					legalMoves.add(promoMoven);
    					legalMoves.add(promoMoveb);
    				}	
    			}else {//Any move that is not a promotion move
    				legalMoves.add(move);
    			}
    			
    		}
    	}
    	// Remove all moves where the the current players king is left attacked, 
    	// by a following pseudolegal move by the opponent
    	long endTime = System.nanoTime();
    	long duration  = (endTime-startTime)/1000000;
    	
    	legalMovesForPosition = legalMoves;
//    	System.out.println(legalMovesForPosition.size()+" legal move(s) generated in "+duration+" ms\n");
    	return legalMoves;
    }
    
    
    /**@param colour of the king that you are checking is in check */
    public boolean isPlayerInCheck(int colour) {
    	int opponent = colour - (2*colour);
    	int[] plyrKingCoords = {0, 0};    	 	
   	 	if(colour == 1) {
   	 		for(int row = 0; row<8; row++) {
   	 			for(int column = 0; column < 8; column++) {
   	 				if(board[row][column]==12) {//White king
   	 					plyrKingCoords[0] = row;
   	 					plyrKingCoords[1] = column;
   	 					break;
   	 				}
   	 			}
   	 		}
   	 	}else if(colour == -1) {
   	 		for(int row = 0; row<8; row++) {
   	 			for(int column = 0; column < 8; column++) {
   	 				if(board[row][column]==6) {//black king
   	 					plyrKingCoords[0] = row;
   	 					plyrKingCoords[1] = column;
   	 					break;
   	 				}
   	 			}
   	 		}
   	 	}
   	 	
   	 	String kingCoordStr = func.sqIntToStr( (plyrKingCoords[0]*8) + plyrKingCoords[1] );
   	 	ArrayList<byteMove> attackingMoves = new ArrayList<byteMove>();
   	 	
   	 	ArrayList<piece> opponentPiecesNoPawns = getListOfPiecesNoPawns(opponent);
   	 	for(piece noPawns : opponentPiecesNoPawns) {
   	 		ArrayList<byteMove> noPawnMoves = noPawns.generateMoves(board);
   	 		for(byteMove move : noPawnMoves) {
   	 			attackingMoves.add(move);
   	 		}
   	 	}
   	 	
   	 	ArrayList<piece> opponentPiecesPawns = getListOfPawns(opponent);
   	 	for(piece pawn : opponentPiecesPawns) {
   	 		ArrayList<byteMove> pawnAttckingMoves = pawn.generateAttackingMoves(board);
   	 		for(byteMove attackingMove :pawnAttckingMoves) {
   	 			attackingMoves.add(attackingMove);
   	 		}
   	 	}
   	 	
   	 	ArrayList<String> squaresAttackingStr = new ArrayList<String>();
   	 	for(byteMove attackingMove : attackingMoves) {
   	 		squaresAttackingStr.add(attackingMove.getTargetSquareStr());
   	 	}
		return squaresAttackingStr.contains(kingCoordStr);
	}
    
    public boolean isPromotionMove(byteMove MOVE) {
    	int startSqInt = func.sqStrToInt(MOVE.getStartSquareStr());
    	int targetSqInt = func.sqStrToInt(MOVE.getTargetSquareStr());
    	
    	int[] startCoord = func.sqIntToCoord(startSqInt);
    	int[] targetCoord = func.sqIntToCoord(targetSqInt);
    	
    	byte piece = board[startCoord[0]][startCoord[1]];
    	
    	if( piece==1 ) {//black pawn
    		if(targetCoord[0] == 7) {
    			return true;
    		}
    	}else if( piece==7){// white pawn
    		if(targetCoord[0] == 0) {
    			return true;
    		}
    	}
    	return false;
    }
	
    public boolean isMoveLegal(byteMove userMove) {
    	for(byteMove legalMove: legalMovesForPosition) {
    		if(legalMove.equals(userMove)) { return true; }
    	}
    	return false;
    }
    
    /** Make a legal move on the board,
     *  only legal moves should be passed to this function <br><br>
     *  castling will call this twice.*/
    public void playMove (byteMove MOVE, int depth, byte promotionPiece) {
    	int startSqInt = func.sqStrToInt(MOVE.getStartSquareStr());
    	int targetSqInt = func.sqStrToInt(MOVE.getTargetSquareStr());
    	
    	int[] startCoord = func.sqIntToCoord(startSqInt);
    	int[] targetCoord = func.sqIntToCoord(targetSqInt);
    	
    	// Check move counters before the board is updated
    	if( board[startCoord[0]][startCoord[1]]==1 || 
    		board[startCoord[0]][startCoord[1]]==7) {
    		halfmove = 0;
    	} // Pawn is moved
    	else if( board[targetCoord[0]][targetCoord[1]]!=0) { halfmove = 0; } // Piece is captured
    	else { halfmove++; } // Increment halfmove if a piece is moved with no capture
    	
    	if(playerToMove == -1) { fullmove++; }// After black has made a move the fullmove counter is incremented
    	
    	byte piece = board[startCoord[0]][startCoord[1]];
    	
    	if(piece==1) {
    		if(targetCoord[0] == 7 && depth == 1) {//Promotions
    			//promote pawn
    			movePiece(promotionPiece, MOVE); 
    			enPassant = "-";
    		} else if(startCoord[0] == (targetCoord[0]-2) ){// double move
    			movePiece((byte)piece, MOVE);
    			int enPassantSqInt = targetSqInt - 8;
    			enPassant = func.sqIntToStr(enPassantSqInt);
    		} else if( MOVE.getTargetSquareStr().equals(enPassant) ){ // en passant move capture
    			movePiece((byte)1, MOVE);
    			int enPassantInt = func.sqStrToInt(enPassant);
    			int[] enPassantCoord = func.sqIntToCoord(enPassantInt);
    			board[enPassantCoord[0]-1][enPassantCoord[1]] = 0;
    			enPassant = "-";
    		} else {
    			movePiece(piece, MOVE);
    			enPassant = "-";
    		}
    	}else if(piece==7) {//White pawn 
    		if(targetCoord[0] == 0 && depth == 1) {//promote pawn
    			movePiece(promotionPiece, MOVE); 
    			enPassant = "-";
    		} else if(startCoord[0] == (targetCoord[0]+2) ){// double move
    			movePiece(piece, MOVE);
    			int enPassantSqInt = targetSqInt + 8;
    			enPassant = func.sqIntToStr(enPassantSqInt);
    		} else if( MOVE.getTargetSquareStr().equals(enPassant) ){ // en passant move
    			movePiece((byte)7, MOVE);
    			int enPassantInt = func.sqStrToInt(enPassant);
    			int[] enPassantCoord = func.sqIntToCoord(enPassantInt);
    			board[enPassantCoord[0]+1][enPassantCoord[1]] = 0;
    			enPassant = "-";
    		}
    		else {
    			movePiece(piece, MOVE);
    			enPassant = "-";
    		}
    	//Castling Moves
    	}else if (wCastleKSide && MOVE.equals(new byteMove("e1", "g1"))) {
    		movePiece((byte)12, new byteMove("e1", "g1"));//White king
    		movePiece((byte)10, new byteMove("h1", "f1"));//White rook
    		wCastleKSide = false;
    		wCastleQSide = false;
			enPassant = "-";
    	}else if (wCastleQSide && MOVE.equals(new byteMove("e1", "c1"))) {
    		movePiece((byte)12, new byteMove("e1", "c1"));
    		movePiece((byte)10, new byteMove("a1", "d1"));
    		wCastleKSide = false;
    		wCastleQSide = false;
			enPassant = "-";
    	}else if (bCastleKSide && MOVE.equals(new byteMove("e8", "g8"))) {
    		movePiece((byte)6, new byteMove("e8", "g8"));
    		movePiece((byte)4, new byteMove("h8", "f8"));
    		bCastleKSide = false;
    		bCastleQSide = false;
			enPassant = "-";
    	}else if (bCastleQSide && MOVE.equals(new byteMove("e8", "c8"))) {
    		movePiece((byte)6, new byteMove("e8", "c8"));
    		movePiece((byte)4, new byteMove("a8", "d8"));
    		bCastleKSide = false;
    		bCastleQSide = false;
			enPassant = "-";
		}else {
			movePiece(piece, MOVE);
			//Black queen side rook moved or taken in a move
			if(      ((piece==4) && (startSqInt==0))  || (targetSqInt==0) ) { bCastleQSide = false; }
			//Black king side rook moved or taken in a move
			else if( ((piece==4) && (startSqInt==7))  || (targetSqInt==7) ) { bCastleKSide = false; }
			//White queen side rook moved or taken in a move
			else if( ((piece==10) && (startSqInt==56)) || (targetSqInt==56) ) { wCastleQSide = false; }
			//White king side rook moved or taken in a move
			else if( ((piece==10) && (startSqInt==63)) || (targetSqInt==63) ) { wCastleKSide = false; }
			
			else if(piece==12) {
	    		wCastleKSide = false;
	    		wCastleQSide = false;
			}else if(piece==6) {
				bCastleKSide = false;
				bCastleQSide = false;
			}
			enPassant = "-";
			
	    	if(depth == 1) {
	    		//generateMoves();
	    		depth++;
	    	}
		}
    	
    	togglePlayerToMove();
    	fen = generateFENfromBoard();
    	
    	previousPositions.add( FENtoPosition(fen) ); //  for threefold repitition

    }
    
    /** Moves a piece from its square to another square and 
     *  leaves the starting tile blank */
    private void movePiece(byte piece, byteMove MOVE) {
    	int startSqInt = func.sqStrToInt(MOVE.getStartSquareStr());
    	int targetSqInt = func.sqStrToInt(MOVE.getTargetSquareStr());
    	
    	int[] startCoord = func.sqIntToCoord(startSqInt);
    	int[] targetCoord = func.sqIntToCoord(targetSqInt);
    	
    	board[startCoord[0]][startCoord[1]] = 0;
    	board[targetCoord[0]][targetCoord[1]] = piece;
	}
    /** Goes though a series of checks to see if
     *  the game is finished. eg stalemate, checkmate,
     *  draw by repetition. */
    public boolean isGameFinshed() {
    	if( legalMovesForPosition.size() == 0 ) {
			if( isPlayerInCheck(getPlayerToMove()) ){
				int winner = getPlayerToMove() - (2*getPlayerToMove());
				if(winner == 1) {
					endCondition = "white win";
					System.out.println(endCondition);
					return true; //White wins by checkmate
				}else if(winner == -1) {
					endCondition = "black win";
					System.out.println(endCondition);
					return true;//Black wins by checkmate
				}
			}else {
				System.out.println(fen);
				endCondition = "draw by stalemate";
				System.out.println(endCondition);
				return true; // Stalemate
			}
		}else if(halfmove >= 100) {
			endCondition = "draw by 50 move rule";
			System.out.println(endCondition);
    		return true; // 50 consecutive moves without the movement of any pawn and without any capture
    	}
    	
    	// 3 fold repitition

		String[] splitFEN = fen.split(" ");
		String currentPostion = splitFEN[0]+" "+splitFEN[1]+" "+splitFEN[2]+" "+splitFEN[3];
    	int numOccurences = Collections.frequency(previousPositions, currentPostion);
    	if( numOccurences >= 3) {
    		endCondition = "draw by threefold repitition";
			System.out.println(endCondition);
    		return true;
    	}
    
    	
    	// if one player has king and knight award win to the other player
    	// if one played has king and bishop award win to the other player    	
    	byte[] whitePieces = getAllPieces(1);
    	byte[] blackPieces = getAllPieces(-1);
    	
    	if(whitePieces.length == 2 && blackPieces.length == 2) {
    		if(func.ByteArrContains(whitePieces, whiteKing) &&
    				( func.ByteArrContains(whitePieces, whiteKnight) ||
    				  func.ByteArrContains(whitePieces, whiteBishop) )) {
    			if(func.ByteArrContains(blackPieces, blackKing) &&
    					(func.ByteArrContains(blackPieces, blackKnight) ||
    							func.ByteArrContains(blackPieces, blackBishop)) ) {
    				endCondition = "draw by insufficient material";
					System.out.println(endCondition);
        			return true; // King + Knight/Bishop v King + Knight/Bishop
        		}
    		}
    	}else if( (whitePieces.length == 1) && (blackPieces.length == 2) ) {
    		if( func.ByteArrContains(whitePieces, whiteKing) ) {
    			if(func.ByteArrContains(blackPieces, blackKing) &&
    					(func.ByteArrContains(blackPieces, blackKnight) ||
    							func.ByteArrContains(blackPieces, blackBishop)) ) {
    				endCondition = "draw by insufficient material";
					System.out.println(endCondition);
        			return true; // King V King + Bishop / Knight
    			}
    		}
    	}else if( (whitePieces.length == 2) && (blackPieces.length == 1) ) {
    		if( func.ByteArrContains(blackPieces, blackKing) ) {
    			if(func.ByteArrContains(whitePieces, whiteKing) &&
    					(func.ByteArrContains(whitePieces, whiteKnight) ||
    							func.ByteArrContains(whitePieces, whiteBishop)) ) {
    				endCondition = "draw by insufficient material";
					System.out.println(endCondition);
        			return true; // King V King and Bishop / Knight
    			}
    		}
    	}
    	else if( (whitePieces.length == 1) && (blackPieces.length == 1) ) {
    		//only kings left
    		endCondition = "draw by insufficient material";
			System.out.println(endCondition);
			return true; // Checkmate not possible
    	}
    	return false;
    }
    
    private String FENtoPosition(String fenToConvert) {
    	String[] splitFEN = fenToConvert.split(" ");
    	return splitFEN[0]+" "+splitFEN[1]+" "+splitFEN[2]+" "+splitFEN[3];
    }
    
    public byte[] getAllPieces(int colourOfPieceToGet) {
    	byte numPieces = 0;
    	for(byte[] row : board) {
    		for(byte pieceInRow : row) {
    			if(colourOfPieceToGet == 1) {
    				if(pieceInRow>=7 && pieceInRow!=0 ) {
    					numPieces++;
        			}
    			}else if(colourOfPieceToGet == -1) {
    				if(pieceInRow<=6 && pieceInRow!=0 ) {
    					numPieces++;
        			}
    			}
    			
    		}
    	}
    	byte[] pieces = new byte[numPieces];
    	byte count = 0;
    	for(byte[] row : board) {
    		for(byte pieceInRow : row) {
    			if(colourOfPieceToGet == 1) {
    				if(pieceInRow>=7 && pieceInRow!=0 ) {
    					pieces[count] = pieceInRow;
    					count++;
        			}
    			}else if(colourOfPieceToGet == -1) {
    				if(pieceInRow<=6 && pieceInRow!=0 ) {
    					pieces[count] = pieceInRow;
    					count++;
        			}
    			}
    			
    		}
    	}
    	
    	return pieces;
    }
    
	/** Generates the FEN for the current board[][] / game */
    public String generateFENfromBoard() {
        String newFEN = "";
        for(byte[] rank : board){
            int empty = 0;
            for(byte square : rank){
                if(square==0){
                    empty++;
                }else{
                    if(empty > 0){
                        newFEN += empty;
                        empty = 0;
                    }
                    newFEN += func.pieceByteToStr(square);
                }
            }
            if(empty > 0){
                newFEN += empty;
            }
            newFEN += "/";
        }
        newFEN = newFEN.substring(0, newFEN.length()-1);
       	newFEN += " ";
       	
       	// Change the player to move
       	if(getPlayerToMove() == 1) { newFEN += "w "; }
       	else if(getPlayerToMove() == -1) { newFEN += "b "; }
       	else { System.out.println("genFEN() error setting the player to move"); }// error: should never run 
       
       	// Castling privileges
       	if(( (wCastleKSide || wCastleQSide) || (bCastleKSide||bCastleQSide))) {
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
    
    private ArrayList<piece> getListOfPiecesOf(int colourOfPlayer){
    	ArrayList<piece> pieces = new ArrayList<piece>();
    	if(colourOfPlayer == 1){//White is next  to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				byte currentSqStr = board[squareNum /8][squareNum % 8]; //Contents of current square
				if( (currentSqStr!=0) && currentSqStr>=7 ) { //Upper case = white
					piece currentPiece = createPiece(currentSqStr, squareNum);
					pieces.add(currentPiece);
				}
			}
    	}else if(colourOfPlayer == -1){//Black is next to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				byte currentSqStr = board[squareNum /8][squareNum % 8]; //Contents of current square
				if( currentSqStr!=0 && currentSqStr<=6 ) {//Lower case = black
					piece currentPiece = createPiece(currentSqStr, squareNum);
					pieces.add(currentPiece);
				}
			}    		
    	}
    	return pieces;
    }
    
    
    
    private ArrayList<piece> getListOfPiecesNoPawns(int colourOfPlayer){
    	ArrayList<piece> piecesNoPawns = new ArrayList<piece>();
    	if(colourOfPlayer == 1){//White is next  to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				byte currentSqStr = board[squareNum /8][squareNum % 8]; //Contents of current square
				if( (currentSqStr!=0) && currentSqStr>=7 ) { //Upper case = white
					if( currentSqStr!=7 ) {// no pawns
						piece currentPiece = createPiece(currentSqStr, squareNum);
						piecesNoPawns.add(currentPiece);
					}
				}
			}
    	}else if(colourOfPlayer == -1){//Black is next to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				byte currentSqStr = board[squareNum /8][squareNum % 8]; //Contents of current square
				if( currentSqStr!=0 && currentSqStr<=6 ) {//Lower case = black
					if( currentSqStr!=1 ) {// no pawns
						piece currentPiece = createPiece(currentSqStr, squareNum);
						piecesNoPawns.add(currentPiece);
					}
				}
			}    		
    	}
    	return piecesNoPawns;
    }
    
    
    
    private ArrayList<piece> getListOfPawns(int colourOfPlayer){
    	ArrayList<piece> piecesNoPawns = new ArrayList<piece>();
    	if(colourOfPlayer == 1){//White is next  to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				byte currentSqStr = board[squareNum /8][squareNum % 8]; //Contents of current square
				if( currentSqStr!=0 && currentSqStr>=6 ) { //Upper case = white
					if( currentSqStr==7 ) {// no pawns
						piece currentPiece = createPiece(currentSqStr, squareNum);
						piecesNoPawns.add(currentPiece);
					}
				}
			}
    	}else if(colourOfPlayer == -1){//Black is next to move
    		for (int squareNum = 0; squareNum < 64; squareNum++) {//Starting form top left to bottom right loop though all squares
				byte currentSqStr = board[squareNum /8][squareNum % 8]; //Contents of current square
				if( (currentSqStr!= 0) && currentSqStr<=6 ) {//Lower case = black
					if( currentSqStr==1) {// no pawns
						piece currentPiece = createPiece(currentSqStr, squareNum);
						piecesNoPawns.add(currentPiece);
					}
				}
			}    		
    	}
    	return piecesNoPawns;
    }
    
    
    private piece createPiece(byte pName, int square) {
    	// Sets the piece colour
    	int colour = 0;
    	if     (pName>=7) {colour = 1;}//White
    	else if(pName<=6){colour = -1;}//Black
    	
    	if (pName == 1|| pName== 7) {
    		pawn PAWN = new pawn(pName, square, colour, enPassant);
    		return PAWN;
    	}else if (pName==4 || pName==10) {
    		rook ROOK = new rook(pName, square, colour);
    		return ROOK;
    	}else if (pName==2 || pName==8) {
    		knight KNGIHT = new knight(pName, square, colour);
    		return KNGIHT;
    	}else if (pName==3 || pName==9) {
    		bishop BISHOP = new bishop(pName, square, colour);
    		return BISHOP;
    	}else if (pName==6 || pName==12) {
    		king KING = new king(pName, square, colour);
    		return KING;
    	}else if (pName==5 || pName==11) {
    		queen QUEEN = new queen(pName, square, colour);
    		return QUEEN;
    	}
    	// This would be an error
    	System.out.println("createPiece returns null on: "+pName+" @ "+square);
		return null;
    }
    
	/** creates a temporary game from the current position to check which if
	 *  any pieces of the given colour are attacking the sqToChk.
	 *  </br></br>
	 *  If so returns true */
	public boolean isSquareAttacked(int sqToChk, int movingPieceColour) {		
		int opponent = movingPieceColour-(2*movingPieceColour);
		
   	 	String sqToChkStr = func.sqIntToStr( sqToChk );
   	 	ArrayList<byteMove> attackingMoves = new ArrayList<byteMove>();
   	 	ArrayList<piece> opponentPiecesNoPawns = getListOfPiecesNoPawns(opponent);
   	 	ArrayList<String> squaresAttacked = new ArrayList<String>(); 
   	 	
   	 	for(piece noPawns : opponentPiecesNoPawns) {
   	 		ArrayList<byteMove> noPawnMoves = noPawns.generateMoves(board);
   	 		for(byteMove move : noPawnMoves) {
   	 			attackingMoves.add(move);
   	 		}
   	 	}
   	 	
   	 	ArrayList<piece> opponentPiecesPawns = getListOfPawns(opponent);
   	 	for(piece pawn : opponentPiecesPawns) {
   	 		ArrayList<byteMove> pawnAttckingMoves = pawn.generateAttackingMoves(board);
   	 		for(byteMove attackingMove:pawnAttckingMoves) {
   	 			attackingMoves.add(attackingMove);
   	 		}
   	 	}
   	 	for(byteMove attackingMove : attackingMoves){
   	 		squaresAttacked.add(attackingMove.getTargetSquareStr());
   	 	}
		return squaresAttacked.contains(sqToChkStr);
	}
	
	private boolean isSquareEmpty(int sqToChk) {
		int[] coords = func.sqIntToCoord(sqToChk);
		return board[coords[0]][coords[1]] == 0;
	}
	
	
    /** ONLT FOR TESTING THE GUI */
    public void printPossibleMoves(ArrayList<byteMove> moves) {
    	int moveCount = 0;
        for(byteMove move : moves) {
        	byteGame tempGame = new byteGame();
        	System.out.println("Move: "+ ++moveCount);
        	System.out.println(move);
        	tempGame.playMove(move, 2, (byte)0);
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
            Integer.parseInt(s);
            return true;
        }catch(java.lang.NumberFormatException e){ return false; }
    }
}
