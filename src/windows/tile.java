 package windows;

import java.awt.Color;
import java.awt.Dimension;

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
	}

	
	public void resetColour() {
		// Green and pale colours
		if(colour == -1)    { button.setBackground(new Color(139, 168, 76)); } // "dark"
		else if(colour == 1){ button.setBackground(new Color(222, 222, 222)); } // "light"	
	}
	
	public void setSelectedColour() {
		// Light and dark red colours
		if(colour == -1)    { button.setBackground(new Color(215, 102, 101)); } // "dark"
		else if(colour == 1){ button.setBackground(new Color(236, 126, 112)); } // "light"	
	}
	
	public void setCurrentTile() {
		if(colour == -1)    { button.setBackground(new Color(79, 95, 214)); } // "dark"
		else if(colour == 1){ button.setBackground(new Color(79, 162, 214)); } // "light"	
	}
}


