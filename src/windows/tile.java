 package windows;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import engine.pieces.piece;

/** The tile class is to used to represent one on the 64 tiles on a chess board.
 */
public class tile{
	private String colour;// this is dumb / not needed i think; but it stays here for now
	private int rank;
	private int file;
	private String current; //" " if tile has no piece/empty
	JButton button;
//	piece PIECE;
	
	public tile(boolean COLOUR, int RANK, int FILE, String pieceStr) {	
		rank = RANK;
		file = FILE;
		current = pieceStr;
		if(current.toUpperCase().equals("P")) {
			
		}else if(current.toUpperCase().equals("R")) {
			
		}else if(current.toUpperCase().equals("N")) {
			
		}else if(current.toUpperCase().equals("B")) {
			
		}else if(current.toUpperCase().equals("Q")) {
			
		}else if(current.toUpperCase().equals("K")) {
			
		}		
		button = new JButton();
		if(COLOUR) {
			colour = "dark";
			button.setBackground(new Color(139, 168, 76)); //Color.BLACK
			
		}else {
			colour = "light";
			button.setBackground(new Color(222, 222, 222)); //Color.WHITE
		}
		button.setPreferredSize(new Dimension(100, 100));
		button.setMinimumSize(new Dimension(100, 100));
		button.setBorderPainted(false);		
	}
	
	public String getCurrent() {
		return current;
	}

	public int getRank() {
		return rank;
	}

	public int getFile() {
		return file;
	}
}


