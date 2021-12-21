package windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;

import engine_1.*;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class MainWindow extends javax.swing.JFrame implements MouseInputListener{

	private JFrame frame;
	private JLabel lblPawn1;
	private tile[][] boardTiles = new tile[8][8];
	Point startPoint;
	private game currentGame = new game();
	private ArrayList<move> moves;
	int index = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					MainWindow window = new MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBackground(Color.DARK_GRAY);
		frame.getContentPane().setForeground(new Color(0, 102, 153));
		frame.setBounds(100, 100, 1080, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH );
		//frame.setMinimumSize(new Dimension(1600, 900));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();		
		gridBagLayout.columnWidths = new int[]{ 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		frame.getContentPane().setLayout(gridBagLayout);
				
		//Left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.BLACK);
		GridBagConstraints lpGBC = new GridBagConstraints();
		lpGBC.insets = new Insets(10, 10, 10, 10);
		lpGBC.fill = GridBagConstraints.BOTH;
		lpGBC.gridx = 0;
		lpGBC.gridy = 1;
		lpGBC.weightx = 0.21875;
		lpGBC.weighty = 0.8;
		frame.getContentPane().add(leftPanel, lpGBC);
		
		
		
		// Button to clear the board and reset currentGame
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentGame = new game();
				loadFENtoBoard(currentGame.getFEN());
			} 
			});
		leftPanel.add(btnNewGame);
		
		
		// Clear Board fucntion
		JButton btnClearBoard = new JButton("Clear Board");
		btnClearBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearBoard();
			}
		});
		leftPanel.add(btnClearBoard);
		
		
		
		JButton btnGenMoves = new JButton("Generate Moves");
		btnGenMoves.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moves = currentGame.generateMoves();
			}
		});
		leftPanel.add(btnGenMoves);
		
		
		
		
		JButton btnNextMove = new JButton("Next Move");
		btnNextMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				move move = moves.get(index++);
				game tempGame = new game();
				tempGame.readFEN(currentGame.getFEN());
				tempGame.playMove(move);
				loadFENtoBoard(tempGame.getFEN());
			}
		});
		leftPanel.add(btnNextMove);
	
		//Middle Panel - Chess Board
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(null);
		mainPanel.setBackground(new Color(64, 64, 64));
		GridBagConstraints mpGBC = new GridBagConstraints();

		mpGBC.insets = new Insets(10, 10, 10, 10);
		mpGBC.fill = GridBagConstraints.BOTH;
		mpGBC.gridx = 1;
		mpGBC.gridy = 1;
		mpGBC.weightx = 0.5626;
		GridBagLayout mpGBL = new GridBagLayout();
		mpGBL.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mpGBL.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mainPanel.setLayout(mpGBL);
		
		frame.getContentPane().add(mainPanel, mpGBC);
		
		//Set up board
		boolean colour = false; //false = white, true = black
		Dimension tileSize = new Dimension((mainPanel.getWidth()/10), (mainPanel.getWidth()/10));
		tileSize.getWidth();

		Integer tileNum = 0;
		for(int rank=0; rank<8; rank++) {
			for(int file=0; file<8; file++) {
				boardTiles[rank][file] = new tile(colour, rank, file, "");
				colour = !colour;
				GridBagConstraints gbc_tile = new GridBagConstraints();
				gbc_tile.gridx = file;
				gbc_tile.gridy = rank;
				gbc_tile.weightx = 0.111;
				gbc_tile.weighty = 0.111;
				//gbc_tile.fill = GridBagConstraints.BOTH;
				tileNum++;
				boardTiles[rank][file].button.setFont(new Font("Arial", Font.BOLD, 18));
				mainPanel.add(boardTiles[rank][file].button, gbc_tile);
			}
			colour = !colour;
		}
		
		//Right panel
		SquarePanel rightPanel = new SquarePanel();
		rightPanel.setBackground(Color.BLACK);
		GridBagConstraints rpGBC = new GridBagConstraints();
		rpGBC.insets = new Insets(10, 10, 10, 10);
		rpGBC.fill = GridBagConstraints.BOTH;
		rpGBC.gridx = 2;
		rpGBC.gridy = 1;
		rpGBC.weightx = 0.21875;
		rpGBC.weighty = 0.8;
		frame.getContentPane().add(rightPanel, rpGBC);
		
		JButton btnNewButton = new JButton("New button");
		rightPanel.add(btnNewButton);
		
		//game game = new game();
		//setBoard(game.board);
		// Keep this at the end
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Loads a FEN and displays the corresponding board position on the board
	 */
	private boolean loadFENtoBoard(String FEN) {
		String[] fenArray = FEN.split("(?!^)");
		int tilesCovered = 0, FENcount = 0;
		while(tilesCovered < 64) {
			String s = fenArray[FENcount];
			if(isNumber(s)) {
				int emptyTiles = Integer.parseInt(s);
				for(emptyTiles = Integer.parseInt(s); emptyTiles>0; emptyTiles--) {
					//if player is black have the black pieces of the board
					// if(playerColour==1){}else if{ ==-1} else{}
					writeToTile(tilesCovered++, "");			
				}
				tilesCovered += emptyTiles;
			}else{
				if(s.equals("/")) {}
				else {
					writeToTile(tilesCovered, s);		
					tilesCovered++;
				}
			}
			FENcount++;
		}
		return true;
	}
	
	private void clearBoard() {
		for(int i = 0; i<64; i++) {
			writeToTile(i, "");
		}
	}
	
	/**
	 * Checks if a string can be converted to an Integer
	 */
	private static boolean isNumber(String str) {
		try {  
			Integer.parseInt(str);  
		    return true;
		}catch(NumberFormatException e){  
		    return false;  
		}  
	}
	
	/**
	 * Writes data to appear on the board
	 */
	private void writeToTile(int tilesCovered, String s) {
		int rank = tilesCovered / 8;
		int file = tilesCovered % 8;
		String imgPathString = "/images/_100x100/"; 
		if(!s.equals("")) {
			if(s.equals(s.toUpperCase())) {
				imgPathString += "white-";
			}else if(s.equals(s.toLowerCase())) {
				imgPathString += "black-";
				colour = -1; // why on earth was this 0?
			}else { System.out.println("img colour error");}
		}
		
		if(s.toUpperCase().equals("P")) {
			imgPathString += "pawn";
		}else if(s.toUpperCase().equals("R")) {
			imgPathString += "rook";
		}else if(s.toUpperCase().equals("N")) {
			imgPathString += "knight";
		}else if(s.toUpperCase().equals("B")) {
			imgPathString += "bishop";
		}else if(s.toUpperCase().equals("Q")) {
			imgPathString += "queen";
		}else if(s.toUpperCase().equals("K")) {
			imgPathString += "king";
		}else {
			imgPathString += "blank";
		}
		imgPathString += "_100x100.png";
		boardTiles[rank][file].button.setIcon(new ImageIcon(MainWindow.class.getResource(imgPathString)));
	}
		
	
	/* ---------------------------------------------------------------
	 *  Event Handlers 
	 * ---------------------------------------------------------------
	 */
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
