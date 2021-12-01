package engine;

public class game {
	private String FEN;
	private String[] PGN;
	private String playerToMove;
	private String board;
	private String enPassant; //Square where the enpassant take is available
	
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
	
	public boolean loadFEN(String fenToLoad) {

		return true;
	}
	
	private String updateFEN() {
		return "";
	}
	
	public String PGNtoFEN(String[] PGN) {
		String fen = "";
		//Calculate board move by move
		return fen;
	}

}
