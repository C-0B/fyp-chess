/**
 * 
 */
package windows;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @author Connor
 *
 */
public class tile{
	private String colour;
	private int rank;
	private int file;
	private char piece; //0 if tile has no piece/empty
//	tileButton label;
	JButton label;
	
	public tile(boolean COLOUR, int RANK, int FILE, char PIECE) {

		label = new JButton();
		if(COLOUR) {
			colour = "black";
//			label.setBackground(Color.BLACK);
			label.setBackground(new Color(139, 168, 76));
			
		}else {
			colour = "white";
			label.setBackground(new Color(222, 222, 222));
		}
		//label.setPreferredSize(new Dimension(100, 100));
		label.setBorderPainted(false);
		
		rank = RANK;
		file = FILE;
		piece = PIECE;
		
		if(colour == "light") {

		}
		
		
		switch (piece){
		case ('p'):
			
		}
		//label.setIcon(new ImageIcon(home.class.getResource("/windows/pawn.png")));
		
	}
}


