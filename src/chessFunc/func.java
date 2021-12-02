package chessFunc;

public abstract class func {
	
	// 0 = a7
	// 63 = h1
	public static boolean isOnBoard(int square) {
		if(square < 63 && square > 0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public static String squareNumToString(int square) {
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
