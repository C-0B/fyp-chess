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
	int colourOfPlayer = -1;
	tile startTile, endTile, recentTile; //Start and tile of the proposed move
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
		newGame(); // create new game, load it to GUI and set up other elements
		
//		promotion = new JDialogpawnPromotion(1);
	}

	private void newGame() {
		game = new game();
		moves = game.generateMoves();
		loadGameToGUI();
		
		lblBoardVal.setText(game.getBoardasStr());
		lblPtoMoveVal.setText(Integer.toString(game.getPlayerToMove()));
		lblFENVal.setText(game.getFEN());
	}
	
	private void loadGameToGUI() {
		// Add a parameter of the players colour(to flip the board)
		if( colourOfPlayer == 1 ) {
			for(int row = 0; row<8 ; row++) {
				for(int column = 0; column<8; column++) {
					boardTiles[row][column].setCurrent(game.getBoard()[row][column]);
					boardTiles[row][column].setChessIcon();
					boardTiles[row][column].setRankandFile(row, column);
				}
			}
		}else if ( colourOfPlayer == -1 ) {
			for(int row = 0; row<8 ; row++) {
				for(int column = 0; column<8; column++) {
					boardTiles[7-row][7-column].setCurrent(game.getBoard()[row][column]);
					boardTiles[7-row][7-column].setChessIcon();
					boardTiles[7-row][7-column].setRankandFile(row, column);
				}
			}
		}
	}
	
	
	/** Reverse a String array */
    static String[] reverseStrArr(String[] array){
        String reversedArray[] = new String[array.length];
        for(int index = 0; index<reversedArray.length; index++){
            reversedArray[index] = array[array.length-1-index];
        }
        return reversedArray;
    }
    /** Reverse a 2 dimenional string array, makes use of {@link #reverseStrArr()} */
    static String[][] reverseString2DArr(String[][] array2D){
        String[][] rev2D = new String[array2D.length][array2D[0].length];
        for(int row = 0; row<array2D.length; row++){
            rev2D[row] = reverseStrArr(array2D[array2D.length-1-row]).clone();
        }
        return rev2D;
    }
    
    private void showPossibleMoves(String startSqStr, ArrayList<move> legalMoves) {
    	
    	int startSqInt = func.sqStrToInt(startSqStr);
    	int startSqRow = startSqInt / 8;
    	int startSqColumn = startSqInt % 8;
    	
		if( colourOfPlayer == 1 ) {
	    	boardTiles[startSqRow][startSqColumn].setCurrentTile();
		}else if( colourOfPlayer == -1 ) {
	    	boardTiles[7-startSqRow][7-startSqColumn].setCurrentTile();
		}
		
    	for(move legalMove : legalMoves) {
    		if(legalMove.getStartSquareStr().equals(startSqStr)) {
    			String tgtSqStr = legalMove.getTargetSquareStr();
    			int tgtSqInt = func.sqStrToInt(tgtSqStr);
    			
    			int tgtSqRow = tgtSqInt / 8;
    			int tgtSqColumn = tgtSqInt % 8;
    			if( colourOfPlayer == 1 ) {
    				boardTiles[tgtSqRow][tgtSqColumn].setSelectedColour();
    		    	boardTiles[startSqRow][startSqColumn].setCurrentTile();
    			}else if( colourOfPlayer == -1 ) {
    		    	boardTiles[7-startSqRow][7-startSqColumn].setCurrentTile();
    				boardTiles[7-tgtSqRow][7-tgtSqColumn].setSelectedColour();
    			}
    		}
    	}
    }
    
    private void resetTileCololurs() {
    	for(tile[]rowTiles : boardTiles ) {
    		for(tile tile : rowTiles) {
    			tile.resetColour();
    		}
    	}
    }
	/** Initialise the contents (elements) of the frame. */
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
		frame.getContentPane().add(leftPanel, lpGBC);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[]{0, 0, 0};
		gbl_leftPanel.rowHeights = new int[]{0, 0, 0};
		gbl_leftPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_leftPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
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
		
		JButton btnFlipBoard = new JButton();
		btnFlipBoard.setBackground(new Color(64, 64, 64));
		btnFlipBoard.setPreferredSize(new Dimension(80, 80));
		btnFlipBoard.setMinimumSize(new Dimension(80, 80));
		btnFlipBoard.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/rotate-black_64x64.png")));
		btnFlipBoard.setToolTipText("Rotate the board");
		btnFlipBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colourOfPlayer -= 2*(colourOfPlayer);
				loadGameToGUI();
			}
		});
		GridBagConstraints gbc_btnFlipBoard = new GridBagConstraints();
		gbc_btnFlipBoard.insets = new Insets(0, 0, 0, 5);
		gbc_btnFlipBoard.gridx = 0;
		gbc_btnFlipBoard.gridy = 1;
		leftPanel.add(btnFlipBoard, gbc_btnFlipBoard);

	
		// ----- Middle Panel ----- (chess board)
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(null);
		mainPanel.setBackground(new Color(64, 64, 64));
		GridBagConstraints mpGBC = new GridBagConstraints();

		mpGBC.insets = new Insets(10, 10, 10, 10);
		mpGBC.fill = GridBagConstraints.BOTH;
		mpGBC.gridx = 1;
		mpGBC.gridy = 1;
		GridBagLayout mpGBL = new GridBagLayout();
		mpGBL.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mpGBL.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mainPanel.setLayout(mpGBL);
		
		frame.getContentPane().add(mainPanel, mpGBC);
		
		//Set up the chess board (inside the middle panel)
//		boolean colour = false; //false = white, true = black
		int colour = 1;//1 = white, -1 = black
		Dimension tileSize = new Dimension((mainPanel.getWidth()/10), (mainPanel.getWidth()/10));
		tileSize.getWidth();

		Integer tileNum = 0;
		for(int rank=0; rank<8; rank++) {
			for(int file=0; file<8; file++) {
				boardTiles[rank][file] = new tile(colour, rank, file, " ");
				/* Button action of each tile is defined here to be able to access  */
				final Integer RANK = rank; // rank of the current tile/button
				final Integer FILE = file; // file of the current tile/button
				boardTiles[rank][file].button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) { }
				});
				
				// Board button actions
				boardTiles[rank][file].button.addMouseListener(new MouseListener() {					
					/** Used to get the grid of the tile the mouse1
					 *  is over currently<p>
					 *  Occurs when the mouse is dragged/moved into
					 *  /onto the element */
					@Override
					public void mouseEntered(MouseEvent e) {
						recentTile = boardTiles[RANK][FILE];
					}
					/** Store recentTile to startTile<p>
					 *  Occurs when the mouse button is first
					 *  pressed on the element, does not need to
					 *  be released */
					@Override
					public void mousePressed(MouseEvent e) {
						startTile = boardTiles[RANK][FILE];
						showPossibleMoves(startTile.getSqName(), moves);
					}
					
					/** Create move and see if its legal<p>
					 *  Occurs when the mouse button is released 
					 *  on the element */
					@Override
					public void mouseReleased(MouseEvent e) {
						endTile = recentTile; // Determined by which tiles the mouse has entered during the press(drag)
						String startSquareStr = startTile.getSqName();
						String targetSquareStr = endTile.getSqName();
						move userMove = new move(startSquareStr, targetSquareStr);
						if( game.isMoveLegal(userMove)) {
							game.playMove(userMove, 1);
							loadGameToGUI();
							moves = game.generateMoves();
							
							lblBoardVal.setText(game.getBoardasStr());
							lblPtoMoveVal.setText(Integer.toString(game.getPlayerToMove()));
							lblFENVal.setText(game.getFEN());
						}else {
							System.out.println("invalid move: "+userMove);
						}
						resetTileCololurs();
						
						if(moves.size() == 0){
							if( game.isPlayerInCheck(game.getPlayerToMove())){
								JOptionPane.showMessageDialog(null, "Checkmate");
							}else {
								JOptionPane.showMessageDialog(null, "StaleMate");
							}
						}
					}
					
					/** Occurs when press and release on the same button
					 *  without moving the mouse to another element
					 *  <p>
					 *  Used for the user two click two seperate tiles on the board*/
					@Override
					public void mouseClicked(MouseEvent e) { }

					// Don't need - might delete later :)					
					@Override
					public void mouseExited(MouseEvent e) {}
				});
				colour -= 2*colour;
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
			colour -= 2*colour;
		}

		// ----- Right panel -----
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLACK);

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
				String strFEN = tfLoadFEN.getText();
				try {
					game tempGame = new game(strFEN);
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "There was an error reading the FEN");
					return;
				}
				game = new game(strFEN);
				moves = game.generateMoves();
				loadGameToGUI(); //loadFENtoBoard(game.getFEN());
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
	
	/** IS NEVER USED LOCALLY <p>
	 * Checks if a string can be converted to an Integer */
	private static boolean isNumber(String str) {
		try {  
			Integer.parseInt(str);  
		    return true;
		}catch(NumberFormatException e){  
		    return false;  
		}  
	}
	
}