 package windowsByteGame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import chessFunc.func;
import engine.pieces.piece;

/** The tile class is to used to represent one on the 64 tiles on a chess board.
 */
public class tile{
	private int colour; // 1 = white, -1 = black
	private int rank;
	private int file;
	private String sqName = "";
	private String current; //" " if tile has no piece/empty
	JButton button;
	

	public int getRank() { return rank; }
	public int getFile() { return file; }
	public String getSqName() {return sqName; }
	public void setCurrent(String CURRENT) { current = CURRENT; }
	public String getCurrent() { return current; }
		
	public tile(int COLOUR, int RANK, int FILE, String pieceStr) {
		colour = COLOUR;
		file = FILE;
		rank = RANK;
		current = pieceStr;
		
		sqName = func.sqIntToStr((rank*8 + file));
		
		button = new JButton();
		
		resetColour();
		//setSelectedColour(colour);
		button.setPreferredSize(new Dimension(100, 100));
		button.setMinimumSize(new Dimension(100, 100));
		button.setBorderPainted(false);	
		button.setFocusPainted(false);
	}
	
	public void setRankandFile(int RANK, int FILE) {
		sqName = func.sqIntToStr((RANK*8 + FILE));
		rank = RANK;
		file = FILE;
	}
	
	public void setChessIcon() {
		String imgPathString = genIconImgPath(current);
		button.setIcon(new ImageIcon(MainWindow.class.getResource(imgPathString)));
	}	
	
	/** Generates the path of the png that will be used as the icon for the tile */
    private String genIconImgPath(String pieceName) {
		String imgPathString = "/images/_100x100/";
		if(!pieceName.equals("") && !pieceName.equals(" ")) {
			if(pieceName.equals(pieceName.toUpperCase())) {
				imgPathString += "white-";
			}else if(pieceName.equals(pieceName.toLowerCase())) {
				imgPathString += "black-";
			}else { System.out.println("img colour error");}
		}
		// Assigning a piece to each of the tiles to on the GUI
		if(pieceName.toUpperCase().equals("P")) 	 {imgPathString += "pawn";}
		else if(pieceName.toUpperCase().equals("R")) {imgPathString += "rook";}
		else if(pieceName.toUpperCase().equals("N")) {imgPathString += "knight";}
		else if(pieceName.toUpperCase().equals("B")) {imgPathString += "bishop";}
		else if(pieceName.toUpperCase().equals("Q")) {imgPathString += "queen";}
		else if(pieceName.toUpperCase().equals("K")) {imgPathString += "king";}
		else {imgPathString += "blank";}
		imgPathString += "_100x100.png";
		return imgPathString;
    }

	
	public void resetColour() {
		// Green and pale colours
		if(colour == -1)    { button.setBackground(new Color(139, 168, 76)); } // "dark"
		else if(colour == 1){ button.setBackground(new Color(222, 222, 222)); } // "light"	
	}
	
	public void setSelectedColour() {
		// Light and dark red colours
		if(colour == -1)    { button.setBackground(new Color(215, 102, 101)); } // "dark"
		else if(colour == 1){ button.setBackground(new Color(230, 150, 140)); } // "light"	236, 126, 112
		
	}
	
	public void setCurrentTile() {
		if(colour == -1)    { button.setBackground(new Color(54, 100, 227)); } // "dark"
		else if(colour == 1){ button.setBackground(new Color(99, 195, 230)); } // "light"	
	}
}


