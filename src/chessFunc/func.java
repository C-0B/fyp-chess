package chessFunc;

public abstract class func {
	
	public static boolean ByteArrContains(byte[] arr, byte elementToCheck) {
		for(byte element : arr) {
			if(element == elementToCheck) {
				return true;
			}
		}
		return false;
	}
	
	
	public static String pieceByteToStr(byte piece) {
		switch (piece) {
		case 0:
			return " ";
		case 1:
			return "p";
		case 2:
			return "n";
		case 3:
			return "b";
		case 4:
			return "r";
		case 5:
			return "q";
		case 6:
			return "k";
		case 7:
			return "P";
		case 8:
			return "N";
		case 9:
			return "B";
		case 10:
			return "R";
		case 11:
			return "Q";
		case 12:
			return "K";
		default:
			throw new IllegalArgumentException("Unexpected value: " + piece);
		}
	}
	
	public static byte pieceStrToByte (String piece) {
		switch (piece) {
		case " ":
			return 0;
		case "p":
			return 1;
		case "n":
			return 2;
		case "b":
			return 3;
		case "r":
			return 4;
		case "q":
			return 5;
		case "k":
			return 6;
		case "P":
			return 7;
		case "N":
			return 8;
		case "B":
			return 9;
		case "R":
			return 10;
		case "Q":
			return 11;
		case "K":
			return 12;
		default:
			throw new IllegalArgumentException("Unexpected value: " + piece);
		}
	}
	
	/**@param squareNum 0-63 int
	 * @return 2D array of [0,0] to [7, 7] aka a8 to h1 [top left to bottom right]
	 */
	public static int[] sqIntToCoord(int squareNum) {
		// 0 = a8 - top left
		// 7 = h8 - top right
		// 56 = a1 - bottom left
		// 63 = h1 - bottom right
		
		int rank = squareNum / 8;
		int file = squareNum % 8;
		int[] coord = {rank, file};
		
		return coord;
	}
	
	public static int coordTosqInt(int[] coord) {
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
		
	public static String sqIntToStr(int square) {
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
	
	
	/**checks if 2 strings of length one are both
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
	
	
	public static int sqStrToInt(String squareStr) {
		int sqrInt = 0;
		int fileInt=0, rankInt=0;
		if(squareStr.length() == 2) {
			String fileStr = squareStr.substring(0, 1);
			String rankStr = squareStr.substring(1, 2);
			switch (fileStr) {
			case "h":
				fileInt = 7;
				break;
			case "g":
				fileInt = 6;
				break;
			case "f":
				fileInt = 5;
				break;
			case "e":
				fileInt = 4;
				break;
			case "d":
				fileInt = 3;
				break;
			case "c":
				fileInt = 2;
				break;
			case "b":
				fileInt = 1;
				break;
			case "a":
				fileInt = 0;
				break;
			default:
				break;
			}
			rankInt = Integer.parseInt(rankStr);
			sqrInt = ((8-rankInt)*8) + fileInt;
			return sqrInt;
		}else {
			System.out.println("func squareStrToInt input is not of length 2");
			return sqrInt;
		}		
	}
}
