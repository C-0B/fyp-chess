package chessFunc;

public abstract class func {
	
	/**
	 * @param squareNum 0-63 int
	 * @return 2D array of [0,0] to [7, 7] aka a8 to h1 [top left to bottom right]
	 */
	public static int[] sqNumToCoord(int squareNum) {
		// 0 = a8 - top left
		// 7 = h8 - top right
		// 56 = a1 - bottom left
		// 63 = h1 - bottom right
		
		int rank = squareNum / 8;
		int file = squareNum % 8;
		int[] coord = {rank, file};
		
		return coord;
	}
	public static int coordTosqNum(int[] coord) {
		int rank = coord[0] ;
		int file = coord[1];
		return (rank*8) + file;
	}
	
	// 0 = a7
	// 63 = h1
	//depreceated : see piece.isOnBoard
	public static boolean isOnBoard(int square) {
		if(square < 63 && square > 0) {
			return true;
		}else {
			return false;
		}
		
	}
		
	public static String sqNumToStr(int square) {
		int rank = square / 8;
		int file = square % 8;
		
		String fileString = "";
		
		switch (file) {
		case 7:
			fileString = "h";
			break;
		case 6:
			fileString = "g";
			break;
		case 5:
			fileString = "f";
			break;
		case 4:
			fileString = "e";
			break;
		case 3:
			fileString = "d";
			break;
		case 2:
			fileString = "c";
			break;
		case 1:
			fileString = "b";
			break;
		case 0:
			fileString = "a";
			break;

		default:
			break;
		}
		
		rank = 8 - rank;
		return fileString + rank;
	}
	
	
	/*
	 * checks if 2 strings of length one are both
	 * upper or lower case. if so returns true
	 * 
	 * deprecated : check piece.isSameColour
	 */
	public static boolean isSameColour(String piece, String attackedPiece) {
		if(piece.equals(piece.toUpperCase())) {// If the player is white
			if(attackedPiece.equals(attackedPiece.toUpperCase())) {//Attacked piece is white
				return true;
			}else if(attackedPiece.equals(attackedPiece.toLowerCase())) {//Attack piece is black
				return false;
			}
		}else if(piece.equals(piece.toLowerCase())) {// If the player is black
			if(attackedPiece.equals(attackedPiece.toUpperCase())) {//Attacked piece is white
				return true;
			}else if(attackedPiece.equals(attackedPiece.toLowerCase())) {//Attack piece is black
				return false;
			}
		}
		
		return false; // Don't think this should ever run :/
	}
}
