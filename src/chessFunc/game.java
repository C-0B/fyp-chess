package chessFunc;

public class game {
	private String FEN;
	private String[] PGN;
	private String playerToMove;
	
	//Constructors
	public game() {
		FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		playerToMove = "white";
	}
	public game(String fen) {
		FEN = fen;
		playerToMove = "white";
	}
	public game(String[] pgn) {
		FEN = PGNtoFEN(pgn);
		PGN = pgn;
		playerToMove = "white";
	}
	
	
	
	public String PGNtoFEN(String[] PGN) {
		String fen = "";
		
		//Calcualte board move by move
		
		return fen;
	}

}
