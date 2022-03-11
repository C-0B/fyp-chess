package windows;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.TileObserver;

import javax.swing.JButton;

import org.w3c.dom.html.HTMLTitleElement;

import engine_1.pieces.pawn;
import engine_1.pieces.piece;
import chessFunc.func;

/**
 * @author Connor
<<<<<<< HEAD
 * 
=======
 * <p>
>>>>>>> 3b4c720239bc76a91a1a4572fe4e79f6bff6e1a3
 * The tile class is to used to represent one on the 64 tiles on a chess board.
 * 
 */

public class tile{
	private String colour;
	private int rank;
	private int file;
	private String current; //"none" if tile has no piece/empty
	private boolean isAttacked = false; //Initially no pieces can attack each other.
	JButton button;
	piece PIECE;
	
	public tile(boolean COLOUR, int RANK, int FILE, String PIECE) {	
		rank = RANK;
		file = FILE;
		current = PIECE;
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


