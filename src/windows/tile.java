package windows;

import java.awt.Color;
import javax.swing.JButton;

/**
 * @author Connor
 * 
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
		button.setBorderPainted(false);		
	}
}


