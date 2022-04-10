package windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import chessFunc.func;
import engine.game;
import engine.move;

public class MainWindow{
	private JFrame frame;
	private tile[][] boardTiles = new tile[8][8];
	private game game;
	private ArrayList<move> moves;
	int index = 0;
	tile startTile, endTile, recentTile; //Start and tile of the proposed move
	private ArrayList<move> legalMovesForPosition;
	JLabel lblBoardVal, lblPtoMoveVal, lblFENVal;
	
	/** Launch the application. */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainWindow window = new MainWindow();
				} catch (Exception e) { e.printStackTrace(); }
			}
		});
	}

	/** Construct the application. */
	public MainWindow() {
		initialize(); // Generate GUI
		newGame(); // Load starting FEN and set up other elements
	}

	private void newGame() {
		game = new game();
		loadGameToGUI();
		
		legalMovesForPosition = game.generateMoves();
		lblBoardVal.setText(game.getBoardasStr());
		lblPtoMoveVal.setText(Integer.toString(game.getPlayerToMove()));
		lblFENVal.setText(game.getFEN());
	}
	
	/** Uses the move created by the user using the GUI and plays the move on the game object,
	 * displays the new board, generates the next legal moves, and prints them
	 * @param MOVE
	 */
	private void makeMove(move MOVE) {
		game.playMove(MOVE);
		loadGameToGUI();
		
		legalMovesForPosition = game.generateMoves();
//		game.printPossibleMoves(legalMovesForPosition);
		
		lblBoardVal.setText(game.getBoardasStr());
		lblPtoMoveVal.setText(Integer.toString(game.getPlayerToMove()));
		lblFENVal.setText(game.getFEN());
	}
	
	/** Loads a FEN by displaying the corresponding board position of the game object
	 * on the board (GUI)
	 * to deprecated 
	 */
//	private boolean loadFENtoBoard(String FEN) {
//		String[] fenArray = FEN.split(" ");
//		int tilesCovered = 0, FENcount = 0;
//		while(tilesCovered < 64) {
//			String s = fenArray[FENcount];
//			if(isNumber(s)) {
//				int emptyTiles = Integer.parseInt(s);
//				for(emptyTiles = Integer.parseInt(s); emptyTiles>0; emptyTiles--) {
//					//if player is black have the black pieces of the board
//					// if(playerColour==1){}else if{ ==-1} else{}
//					writeIconToTile(tilesCovered/8, tilesCovered%8, "");		
//					tilesCovered++;
//				}
//				tilesCovered += emptyTiles;
//			}else{
//				if(s.equals("/")) {}
//				else {
//					writeIconToTile(tilesCovered/8, tilesCovered%8, s);		
//					tilesCovered++;
//				}
//			}
//			FENcount++;
//		}
//		return true;
//	}
	
	private void loadGameToGUI() {
		// Add a parameter of the players colour(to flip the board)
		for(int row = 0; row<8 ; row++) {
			for(int column = 0; column<8; column++) {
				writeIconToTile(row, column, game.getBoard()[row][column]);				
			}
		}
	}
/** this was made but never used this idk really	*/
//	private void loadBoardTilestoGUI(int closeColourSide) {
//		String[][] tempBoardStrings = new String[8][8];
//		if(closeColourSide == -1) {// If the player is black
//			tempBoardStrings = reverseString2DArr(game.getBoard().clone());
//		}else {
//			tempBoardStrings = game.getBoard().clone();
//		}
//		for(int row = 0; row<8; row++) {
//			for(int column = 0; column<8; column++) {
//				writeIconToTile(row, column, tempBoardStrings[row][column]);
//			}
//		}
//	}
	
	/** Checks if a string can be converted to an Integer
	 */
	private static boolean isNumber(String str) {
		try {  
			Integer.parseInt(str);  
		    return true;
		}catch(NumberFormatException e){  
		    return false;  
		}  
	}
	
	/** Writes data for a single tile to appear on the board
	 */
	private void writeIconToTile(int row, int column, String pieceName) {
		String imgPathString = genIconImgPath(pieceName);
		boardTiles[row][column].button.setIcon(new ImageIcon(MainWindow.class.getResource(imgPathString)));
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
	
	/** 
	 * Reverse a String array
	 */
    static String[] reverseStrArr(String[] array){
        String reversedArray[] = new String[array.length];
        for(int index = 0; index<reversedArray.length; index++){
            reversedArray[index] = array[array.length-1-index];
        }
        return reversedArray;
    }
    /** Reverse a 2 dimenional string array, makes use of {@link #reverseStrArr()}
     */
    static String[][] reverseString2DArr(String[][] array2D){
        String[][] rev2D = new String[array2D.length][array2D[0].length];
        for(int row = 0; row<array2D.length; row++){
            rev2D[row] = reverseStrArr(array2D[array2D.length-1-row]).clone();
        }

        return rev2D;
    }
 
	/**
	 * Initialise the contents (elements) of the frame. */
	private void initialize() {
		// basics of the frame
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBackground(Color.DARK_GRAY);
		frame.getContentPane().setForeground(new Color(0, 102, 153));
		frame.setBounds(100, 100, 1600, 900); // This is just big enough to not be full screen but show all elements (not birng hidden)
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();		
		gridBagLayout.columnWidths = new int[]{ 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		frame.getContentPane().setLayout(gridBagLayout);
				
		// ----- Left panel -----
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.BLACK);
		GridBagConstraints lpGBC = new GridBagConstraints();
		lpGBC.insets = new Insets(10, 10, 10, 10);
		lpGBC.fill = GridBagConstraints.BOTH;
		lpGBC.gridx = 0;
		lpGBC.gridy = 1;
//		lpGBC.weightx = 0.21875;
//		lpGBC.weighty = 0.8;
		frame.getContentPane().add(leftPanel, lpGBC);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[]{83, 111, 85, 0};
		gbl_leftPanel.rowHeights = new int[]{23, 0, 0, 0};
		gbl_leftPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_leftPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		leftPanel.setLayout(gbl_leftPanel);
		
		// Button to clear the board and reset currentGame
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {newGame();}
		});
		GridBagConstraints gbc_btnNewGame = new GridBagConstraints();
		gbc_btnNewGame.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewGame.gridx = 0;
		gbc_btnNewGame.gridy = 0;
		leftPanel.add(btnNewGame, gbc_btnNewGame);
		
		JButton btnGenMoves = new JButton("Generate Moves");
		btnGenMoves.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moves = game.generateMoves();
				index = 0;
			}
		});
		GridBagConstraints gbc_btnGenMoves = new GridBagConstraints();
		gbc_btnGenMoves.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnGenMoves.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenMoves.gridx = 0;
		gbc_btnGenMoves.gridy = 1;
		leftPanel.add(btnGenMoves, gbc_btnGenMoves);
		
		JButton btnNextMove = new JButton("Next Move");
		btnNextMove.setIcon(new ImageIcon(MainWindow.class.getResource("/images/_100x100/white-bishop_100x100.png")));
		btnNextMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				move move = moves.get(index++);
				game tempGame = new game(game.getFEN());
				tempGame.playMove(move);
//				loadFENtoBoard(tempGame.getFEN());
			}
		});
		GridBagConstraints gbc_btnNextMove = new GridBagConstraints();
		gbc_btnNextMove.insets = new Insets(0, 0, 0, 5);
		gbc_btnNextMove.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNextMove.gridx = 0;
		gbc_btnNextMove.gridy = 2;
		leftPanel.add(btnNextMove, gbc_btnNextMove);
	
		// ----- Middle Panel ----- (chess board)
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(null);
		mainPanel.setBackground(new Color(64, 64, 64));
		GridBagConstraints mpGBC = new GridBagConstraints();

		mpGBC.insets = new Insets(10, 10, 10, 10);
		mpGBC.fill = GridBagConstraints.BOTH;
		mpGBC.gridx = 1;
		mpGBC.gridy = 1;
//		mpGBC.weightx = 0.5626;
		GridBagLayout mpGBL = new GridBagLayout();
		mpGBL.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mpGBL.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mainPanel.setLayout(mpGBL);
		
		frame.getContentPane().add(mainPanel, mpGBC);
		
		//Set up the chess board (inside the middle panel)
		boolean colour = false; //false = white, true = black
		Dimension tileSize = new Dimension((mainPanel.getWidth()/10), (mainPanel.getWidth()/10));
		tileSize.getWidth();

		Integer tileNum = 0;
		for(int rank=0; rank<8; rank++) {
			for(int file=0; file<8; file++) {
				boardTiles[rank][file] = new tile(colour, rank, file, "");
				/* Button action of each tile is defined here to be able to access 
				 */
				final Integer RANK = rank;
				final Integer FILE = file;
				boardTiles[rank][file].button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(boardTiles[RANK][FILE].PIECE != null) {
							System.out.println(boardTiles[RANK][FILE].PIECE.getNAME() +" @ "+func.sqNumToStr((RANK*8) + FILE));
						}
					}
				});
				
				// Board button actions
				boardTiles[rank][file].button.addMouseListener(new MouseListener() {					
					/* 
					 *  --- Create move by dragging ---
					 */
					@Override
					public void mouseEntered(MouseEvent e) {
						recentTile = boardTiles[RANK][FILE];
					}
					//Store recentTile to startTile
					@Override
					public void mousePressed(MouseEvent e) {
						startTile = boardTiles[RANK][FILE];
					}
					
					// Create move and see if its legal
					@Override
					public void mouseReleased(MouseEvent e) {
						boolean moveIsLegal = false;
						endTile = recentTile; //Determined by which tiles the mouse has entered during the press(drag)
						if(startTile.PIECE != null) {
							move thisMove = new move(game.getPlayerToMove(), startTile.PIECE, ((startTile.getRank()*8) + startTile.getFile()),((endTile.getRank()*8) + endTile.getFile()));
							for(move move : legalMovesForPosition) {
								if(thisMove.equals(move)){
									moveIsLegal = true;
									break;
								}// else isMoveLegal = false
							}
							
							//Play the move
							if(moveIsLegal) {
								System.out.println(game.isSquareAttacked(thisMove.getTARGET_SQUARE(), (0-thisMove.getPLAYER_TO_MOVE())));
								makeMove(thisMove);
							}else { JOptionPane.showMessageDialog(mainPanel, "That Move is invalid");}
						}
					}
					/*
					 * --- Create move by clicking 2 squares
					 * TODO
					 */
					@Override
					public void mouseClicked(MouseEvent e) {
						tile thisTile = boardTiles[RANK][FILE];
						if(startTile != thisTile) {// Start of a move
							move thisMove = new move(game.getPlayerToMove(), startTile.PIECE, ((startTile.getRank()*8)+startTile.getFile()) , ((RANK*8)+FILE));
						}else {
						}
						if(thisTile.PIECE != null) {						
						}
						startTile = boardTiles[RANK][FILE]; // Reset start tile for the next click
					}

					// Don't need - might delete later :)					
					@Override
					public void mouseExited(MouseEvent e) {}
				});
				
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
//		
		// ----- Right panel -----
		SquarePanel rightPanel = new SquarePanel();
		rightPanel.setBackground(Color.BLACK);

//		JLabel lblNewLabel = new JLabel("Player To Move:");

//		JLabel lblNewLabel_1 = new JLabel("start");
//		lblNewLabel_1.setForeground(Color.WHITE);
//		rightPanel.add(lblNewLabel_1, "4, 2");
//
		//Placing the right panel in the window
		GridBagConstraints rpGBC = new GridBagConstraints();
		rpGBC.insets = new Insets(10, 10, 10, 10);
		rpGBC.fill = GridBagConstraints.BOTH;
		rpGBC.gridx = 2;
		rpGBC.gridy = 1;
//		rpGBC.weightx = 0.21875;
//		rpGBC.weighty = 0.8;
		frame.getContentPane().add(rightPanel, rpGBC);

		// The layout of elements in the right panel
		GridBagLayout gbl_RP = new GridBagLayout();
		gbl_RP.columnWidths = new int[]{0, 0};
		gbl_RP.rowHeights = new int[]{0, 0, 0};
		gbl_RP.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_RP.rowWeights = new double[]{0.0, 0.0, 0.0};
		rightPanel.setLayout(gbl_RP);
		
		JLabel lblPtoMove = new JLabel("Player To Move:   ");
		lblPtoMove.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		lblPtoMove.setForeground(Color.WHITE);
		GridBagConstraints gbc_RP = new GridBagConstraints();
		gbc_RP.insets = new Insets(0, 0, 5, 0);
		gbc_RP.fill = GridBagConstraints.BOTH;
		gbc_RP.gridx = 0;
		gbc_RP.gridy = 0;
		rightPanel.add(lblPtoMove, gbc_RP);
		
		lblPtoMoveVal = new JLabel("start - white?");
		lblPtoMoveVal.setForeground(Color.WHITE);
		lblPtoMoveVal.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPtoMoveVal = new GridBagConstraints();
		gbc_lblPtoMoveVal.insets = new Insets(0, 0, 5, 0);
		gbc_lblPtoMoveVal.fill = GridBagConstraints.BOTH;
		gbc_lblPtoMoveVal.gridx = 1;
		gbc_lblPtoMoveVal.gridy = 0;
		rightPanel.add(lblPtoMoveVal, gbc_lblPtoMoveVal);
		
		JLabel lblFEN = new JLabel("FEN:   ");
		lblFEN.setForeground(Color.WHITE);
		lblFEN.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFEN = new GridBagConstraints();
		gbc_lblFEN.insets = new Insets(0, 0, 5, 0);
		gbc_lblFEN.gridx = 0;
		gbc_lblFEN.gridy = 1;
		rightPanel.add(lblFEN, gbc_lblFEN);
		
		lblFENVal = new JLabel("start - white?");
		lblFENVal.setForeground(Color.WHITE);
		lblFENVal.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFENVal = new GridBagConstraints();
		gbc_lblFENVal.insets = new Insets(0, 0, 0, 10);
		gbc_lblFENVal.fill = GridBagConstraints.BOTH;
		gbc_lblFENVal.gridx = 1;
		gbc_lblFENVal.gridy = 1;
		rightPanel.add(lblFENVal, gbc_lblFENVal);
		
		JLabel lblBoard = new JLabel("Board:   ");
		lblBoard.setForeground(Color.WHITE);
		lblBoard.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		rightPanel.add(lblBoard, gbc_lblNewLabel);
		
		lblBoardVal = new JLabel("some baord?");
		lblBoardVal.setForeground(Color.WHITE);
		lblBoardVal.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		GridBagConstraints gbc_lblBoardVal = new GridBagConstraints();
		gbc_lblBoardVal.insets = new Insets(0, 0, 5, 0);
		gbc_lblBoardVal.fill = GridBagConstraints.BOTH;
		gbc_lblBoardVal.gridx = 1;
		gbc_lblBoardVal.gridy = 2;
		rightPanel.add(lblBoardVal, gbc_lblBoardVal);
		
		// Load Game from FEN GUI elements in right panel	
		JTextField tfLoadFEN = new JTextField();
		GridBagConstraints gbc_tfLoadFEN = new GridBagConstraints();
		gbc_tfLoadFEN.insets = new Insets(0, 0, 5, 0);
		gbc_tfLoadFEN.fill = GridBagConstraints.BOTH;
		gbc_tfLoadFEN.gridx = 1;
		gbc_tfLoadFEN.gridy = 3;
		rightPanel.add(tfLoadFEN, gbc_tfLoadFEN);
		
		JButton btnLoadFromFEN = new JButton("Load Game From FEN");
		btnLoadFromFEN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Creates and loads a new game to the GUI using the FEN given by the user.
				String stFEN = tfLoadFEN.getText();
				game = new game(stFEN);
				loadGameToGUI(); //loadFENtoBoard(game.getFEN());
				legalMovesForPosition = game.generateMoves();	
				lblBoardVal.setText(game.getBoardasStr());
				lblPtoMoveVal.setText(Integer.toString(game.getPlayerToMove()));
				lblFENVal.setText(game.getFEN());
			}
		});
		GridBagConstraints gbc_btnLoadFromFEN = new GridBagConstraints();
		gbc_btnLoadFromFEN.fill = GridBagConstraints.BOTH;
		gbc_btnLoadFromFEN.gridx = 0;
		gbc_btnLoadFromFEN.gridy = 3;
		rightPanel.add(btnLoadFromFEN, gbc_btnLoadFromFEN);

		// End of right panel + and it's elements
//		frame.pack(); // Keep this at the end
		frame.setVisible(true);
	}
}