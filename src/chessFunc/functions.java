package chessFunc;

public abstract class functions {
	
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
		
		String rankString = "", fileString = "";
		
		switch (rank) {
		case value:
			
			break;

		default:
			break;
		}
		
		
		return "";
	}
}
