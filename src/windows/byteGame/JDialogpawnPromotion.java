package windows.byteGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JDialogpawnPromotion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private byte pieceSelected = 0;
	
    final byte empty = 0;
    
    final byte blackPawn = 1;
    final byte blackKnight = 2;
    final byte blackBishop = 3;
    final byte blackRook = 4;
    final byte blackQueen = 5;
    final byte blackKing = 6;
    
    final byte whitePawn = 7;
    final byte whiteKnight = 8;
    final byte whiteBishop = 9;
    final byte whiteRook = 10;
    final byte whiteQueen = 11;
    final byte whiteKing = 12;

	/** Launch the application. */
	
	public static void main(String[] args) {
		try {
			JDialogpawnPromotion dialog = new JDialogpawnPromotion(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Create the dialog. */
	public JDialogpawnPromotion(int colour) {
		setBackground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogpawnPromotion.class.getResource("/images/_100x100/black-pawn_100x100.png")));
		setTitle("Promote a pawn");
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		int windowWidth = 450, windowHeight = 300;
		int xCoord = 0, yCoord = 0;
		
		if(mouse.x-(windowWidth/2) < 0) { xCoord = mouse.x; }
		else { xCoord = mouse.x-(windowWidth/2); }
		
		if(mouse.y-(windowHeight/2) < 0) { yCoord = mouse.y; }
		else { yCoord = mouse.y-(windowHeight/2); }
		
		setBounds(xCoord, yCoord, windowWidth, windowHeight);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JButton btnQueen = new JButton();
		if(colour == 1) {
			btnQueen.setIcon(new ImageIcon(JDialogpawnPromotion.class.getResource("/images/_100x100/white-queen_100x100.png")));
		}else if(colour == -1) {
			btnQueen.setIcon(new ImageIcon(JDialogpawnPromotion.class.getResource("/images/_100x100/black-queen_100x100.png")));
		} 
		btnQueen.setBackground(new Color(99, 195, 230));
		btnQueen.setPreferredSize(new Dimension(100, 100));
		btnQueen.setMinimumSize(new Dimension(100, 100));
		btnQueen.setBorderPainted(false);	
		btnQueen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(colour == 1) 	 { pieceSelected = whiteQueen; }
				else if(colour == -1){ pieceSelected = blackQueen; }
				setVisible(false);
				dispose();
			}
		});
		GridBagConstraints gbc_btnQueen = new GridBagConstraints();
		gbc_btnQueen.insets = new Insets(0, 0, 5, 5);
		gbc_btnQueen.gridx = 1;
		gbc_btnQueen.gridy = 2;
		contentPanel.add(btnQueen, gbc_btnQueen);
		
		JButton btnKnight = new JButton();
		if(colour == 1) {
			btnKnight.setIcon(new ImageIcon(JDialogpawnPromotion.class.getResource("/images/_100x100/white-knight_100x100.png")));
		}else if(colour == -1) {
			btnKnight.setIcon(new ImageIcon(JDialogpawnPromotion.class.getResource("/images/_100x100/black-knight_100x100.png")));
		} 
		btnKnight.setBackground(new Color(99, 195, 230));
		btnKnight.setPreferredSize(new Dimension(100, 100));
		btnKnight.setMinimumSize(new Dimension(100, 100));
		btnKnight.setBorderPainted(false);	
		btnKnight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(colour == 1) 	 { pieceSelected = whiteKnight; }
				else if(colour == -1){ pieceSelected = blackKnight; }
				setVisible(false);
				dispose();
			}
		});
		GridBagConstraints gbc_btnKnight = new GridBagConstraints();
		gbc_btnKnight.insets = new Insets(0, 0, 5, 5);
		gbc_btnKnight.gridx = 2;
		gbc_btnKnight.gridy = 2;
		contentPanel.add(btnKnight, gbc_btnKnight);
		
		JButton btnRook = new JButton();
		if(colour == 1) {
			btnRook.setIcon(new ImageIcon(JDialogpawnPromotion.class.getResource("/images/_100x100/white-rook_100x100.png")));
		}else if(colour == -1) {
			btnRook.setIcon(new ImageIcon(JDialogpawnPromotion.class.getResource("/images/_100x100/black-rook_100x100.png")));
		} 
		btnRook.setBackground(new Color(99, 195, 230));
		btnRook.setPreferredSize(new Dimension(100, 100));
		btnRook.setMinimumSize(new Dimension(100, 100));
		btnRook.setBorderPainted(false);
		btnRook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(colour == 1) 	 { pieceSelected = whiteRook; }
				else if(colour == -1){ pieceSelected = blackRook; }
				setVisible(false);
				dispose();
			}
		});
		GridBagConstraints gbc_btnRook = new GridBagConstraints();
		gbc_btnRook.anchor = GridBagConstraints.NORTH;
		gbc_btnRook.insets = new Insets(0, 0, 5, 5);
		gbc_btnRook.gridx = 3;
		gbc_btnRook.gridy = 2;
		contentPanel.add(btnRook, gbc_btnRook);

		JButton btnBishop = new JButton();
		if(colour == 1) {
			btnBishop.setIcon(new ImageIcon(JDialogpawnPromotion.class.getResource("/images/_100x100/white-bishop_100x100.png")));
		}else if(colour == -1) {
			btnBishop.setIcon(new ImageIcon(JDialogpawnPromotion.class.getResource("/images/_100x100/black-bishop_100x100.png")));
		} 
		btnBishop.setBackground(new Color(99, 195, 230));
		btnBishop.setPreferredSize(new Dimension(100, 100));
		btnBishop.setMinimumSize(new Dimension(100, 100));
		btnBishop.setBorderPainted(false);	
		btnBishop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(colour == 1) 	 { pieceSelected = whiteBishop; }
				else if(colour == -1){ pieceSelected = blackBishop; }
				setVisible(false);
				dispose();
			}
		});
		GridBagConstraints gbc_btnBishop = new GridBagConstraints();
		gbc_btnBishop.insets = new Insets(0, 0, 5, 0);
		gbc_btnBishop.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_btnBishop.gridx = 4;
		gbc_btnBishop.gridy = 2;
		
		contentPanel.add(btnBishop, gbc_btnBishop);
		setModalityType(ModalityType.APPLICATION_MODAL);
		pack();
		setVisible(true);
	}
	
	public byte getPieceSelected() {
		return pieceSelected;
	}

}
