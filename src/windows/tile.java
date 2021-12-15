package windows;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

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
	private String piece; //"none" if tile has no piece/empty
	private boolean isAttacked = false; //Initially no pieces can attack each other.
//	tileButton label;
	JButton button;
	
	public tile(boolean COLOUR, int RANK, int FILE, String PIECE) {
		//label.setIcon(new ImageIcon(home.class.getResource("/windows/pawn.png")));		
		rank = RANK;
		file = FILE;
		piece = PIECE;
		
		button = new JButton();
		if(COLOUR) {
			colour = "dark";
			button.setBackground(new Color(139, 168, 76)); //Color.BLACK
			
		}else {
			colour = "light";
			button.setBackground(new Color(222, 222, 222)); //Color.WHITE
		}
		//label.setPreferredSize(new Dimension(100, 100));
		button.setPreferredSize(new Dimension(100, 100));
		button.setMinimumSize(new Dimension(100, 100));
		button.setBorderPainted(false);		
	}
}


