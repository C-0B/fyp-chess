package windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.imageio.stream.IIOByteBuffer;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import org.hamcrest.CustomTypeSafeMatcher;

import chessFunc.func;
import engine.game;
import engine.move;
import engine.promotionMove;
import opponents.PlayOpponents;

public class MainWindow{
	private JFrame frmChess;
	private tile[][] boardTiles = new tile[8][8];
	private game game;
	private ArrayList<move> moves;
	int colourOfPlayer = 1; // 1 = white, -1 = black
	tile startTile, endTile, recentTile; //Start and tile of the proposed move
	JLabel lblPtoMoveVal;
	private JTextField tfDisplayFEN;
	private JTextField tfLoadFEN;
	
	int gamemode = 0;
	/* 0 - solo play
	 * 1 - white v random
	 * 2 - random v black */
	
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
	}

	private void newGame() {
		game = new game();
		moves = game.generateMoves();
		loadGameToGUI();
		updateMetadata();
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
    
    private void updateMetadata() {
    	if(game.getPlayerToMove() == 1) {
    		lblPtoMoveVal.setText("White");
    	}else {
    		lblPtoMoveVal.setText("Black");
    	}
		tfDisplayFEN.setText(game.getFEN());
		
    }
	/** Initialise the contents (elements) of the frame. */
	private void initialize() {
	
		// basics of the frame
		frmChess = new JFrame();
		frmChess.setTitle("Chess");
		frmChess.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/icons/titleIcon256x256.png")));
		frmChess.getContentPane().setBackground(Color.DARK_GRAY);
		frmChess.setBackground(Color.DARK_GRAY);
		frmChess.getContentPane().setForeground(new Color(0, 102, 153));
		frmChess.setBounds(100, 100, 1700, 900); // This is just big enough to not be full screen but show all elements (not birng hidden)
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0, 0, 0};
		gridBagLayout.rowWeights = new double[]{0};
		frmChess.getContentPane().setLayout(gridBagLayout);
						
		// ----- Left panel -----
		JPanel leftPanel = new JPanel();
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.fill = GridBagConstraints.VERTICAL;
		gbc_leftPanel.gridx = 0;
		gbc_leftPanel.gridy = 0;
		frmChess.getContentPane().add(leftPanel, gbc_leftPanel);
		leftPanel.setBackground(Color.BLACK);
		GridBagLayout gbl_leftPanel = new GridBagLayout();
		gbl_leftPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_leftPanel.rowHeights = new int[]{0, 0, 0};
		gbl_leftPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_leftPanel.rowWeights = new double[]{0.0, 0.0, 0.0};
		leftPanel.setLayout(gbl_leftPanel);
		
		JLabel lblGameMode = new JLabel("Gamemode:    ");
		lblGameMode.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblGameMode = new GridBagConstraints();
		gbc_lblGameMode.insets = new Insets(0, 10, 5, 5);
		gbc_lblGameMode.anchor = GridBagConstraints.EAST;
		gbc_lblGameMode.gridx = 0;
		gbc_lblGameMode.gridy = 2;
		leftPanel.add(lblGameMode, gbc_lblGameMode);
		
		JComboBox comboMode = new JComboBox();
		comboMode.setModel(new DefaultComboBoxModel(new String[] {"Solo Game", "V Random", "V Checkmate"}));
		comboMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamemode = comboMode.getSelectedIndex();
			}
		});
		GridBagConstraints gbc_comboMode = new GridBagConstraints();
		gbc_comboMode.insets = new Insets(5, 0, 5, 10);
		gbc_comboMode.gridx = 1;
		gbc_comboMode.gridy = 2;
		leftPanel.add(comboMode, gbc_comboMode);
		
		
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
		
		// Button to clear the board and reset currentGame
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {newGame();}
		});
		GridBagConstraints gbc_btnNewGame = new GridBagConstraints();
		gbc_btnNewGame.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewGame.insets = new Insets(5, 0, 5, 10);
		gbc_btnNewGame.gridx = 1;
		gbc_btnNewGame.gridy = 3;
		leftPanel.add(btnNewGame, gbc_btnNewGame);
		
		JButton btnNextMove = new JButton("Next Move");
		btnNextMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( !(gamemode==0) ) {
					playOpponentsMove();
				}else {
					JOptionPane.showMessageDialog(null, "You are currently playing alone");
				}
				
			}
		});
		GridBagConstraints gbc_btnNextMove = new GridBagConstraints();
		gbc_btnNextMove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNextMove.insets = new Insets(5, 0, 5, 10);
		gbc_btnNextMove.gridx = 1;
		gbc_btnNextMove.gridy = 4;
		leftPanel.add(btnNextMove, gbc_btnNextMove);
		GridBagConstraints gbc_btnFlipBoard = new GridBagConstraints();
		gbc_btnFlipBoard.anchor = GridBagConstraints.SOUTH;
		gbc_btnFlipBoard.insets = new Insets(5, 0, 5, 10);
		gbc_btnFlipBoard.gridx = 1;
		gbc_btnFlipBoard.gridy = 5;
		leftPanel.add(btnFlipBoard, gbc_btnFlipBoard);
		
		Component horizontalStrut = Box.createHorizontalStrut(50);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 0;
		frmChess.getContentPane().add(horizontalStrut, gbc_horizontalStrut);
						
							
		// ----- Middle Panel ----- (chess board)
		JPanel mainPanel = new JPanel();
		GridBagConstraints gbc_mainPanel = new GridBagConstraints();
		gbc_mainPanel.gridx = 2;
		gbc_mainPanel.gridy = 0;
		frmChess.getContentPane().add(mainPanel, gbc_mainPanel);
		mainPanel.setBorder(null);
		mainPanel.setBackground(new Color(64, 64, 64));
		GridBagLayout mpGBL = new GridBagLayout();
		mpGBL.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mpGBL.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mainPanel.setLayout(mpGBL);
		Dimension tileSize = new Dimension((mainPanel.getWidth()/10), (mainPanel.getWidth()/10));
		
		//Set up the chess board (inside the middle panel)
//		boolean colour = false; //false = white, true = black
		int colour = 1;//1 = white, -1 = black
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
						frmChess.setCursor(Cursor.HAND_CURSOR);
						startTile = boardTiles[RANK][FILE];
						showPossibleMoves(startTile.getSqName(), moves);
					}
					
					/** Create move and see if its legal<p>
					 *  Occurs when the mouse button is released 
					 *  on the element */
					@Override
					public void mouseReleased(MouseEvent e) {
						frmChess.setCursor(null);
						endTile = recentTile; // Determined by which tiles the mouse has entered during the press(drag)
						String startSquareStr = startTile.getSqName();
						String targetSquareStr = endTile.getSqName();
						move userMove = new move(startSquareStr, targetSquareStr);
						if( game.isMoveLegal(userMove)) {
							if( game.isPromotionMove(userMove)) {
				    			JDialogpawnPromotion p = new JDialogpawnPromotion(game.getPlayerToMove());
				    			if( !p.getPieceSelected().equals(" ") ){
				    				game.playMove(userMove, 1, p.getPieceSelected());
				    			}
							}else{
								game.playMove(userMove, 1, "");
							}

							loadGameToGUI();
							moves = game.generateMoves();
							updateMetadata();
							resetTileCololurs();


							if( !(gamemode == 0) ) {
								playOpponentsMove();
							}
						}// else in valid move
						resetTileCololurs();

						if(game.isGameFinshed()){
							JOptionPane.showMessageDialog(null, "The has finished\n"+game.getEndCondition());
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
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(50);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.gridx = 3;
		gbc_horizontalStrut_1.gridy = 0;
		frmChess.getContentPane().add(horizontalStrut_1, gbc_horizontalStrut_1);
		

		// ----- Right panel -----
		JPanel rightPanel = new JPanel();
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.fill = GridBagConstraints.VERTICAL;
		gbc_rightPanel.gridx = 4;
		gbc_rightPanel.gridy = 0;
		frmChess.getContentPane().add(rightPanel, gbc_rightPanel);
		rightPanel.setBackground(Color.BLACK);
		
		// The layout of elements in the right panel
		GridBagLayout gbl_RP = new GridBagLayout();
		gbl_RP.columnWidths = new int[] {0, 0, 0};
		gbl_RP.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
		gbl_RP.columnWeights = new double[]{0.0, 1.0};
		gbl_RP.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		rightPanel.setLayout(gbl_RP);
		
		JLabel lblFEN = new JLabel("FEN:   ");
		lblFEN.setForeground(Color.WHITE);
		lblFEN.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFEN = new GridBagConstraints();
		gbc_lblFEN.insets = new Insets(0, 0, 5, 5);
		gbc_lblFEN.gridx = 0;
		gbc_lblFEN.gridy = 0;
		rightPanel.add(lblFEN, gbc_lblFEN);
		
		tfDisplayFEN = new JTextField();
		tfDisplayFEN.setToolTipText("FEN of the current board position");
		tfDisplayFEN.setEditable(false);
		GridBagConstraints gbc_tfDisplayFEN = new GridBagConstraints();
		gbc_tfDisplayFEN.insets = new Insets(0, 0, 5, 10);
		gbc_tfDisplayFEN.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDisplayFEN.gridx = 1;
		gbc_tfDisplayFEN.gridy = 0;
		rightPanel.add(tfDisplayFEN, gbc_tfDisplayFEN);
		tfDisplayFEN.setColumns(500);
		
		JLabel lblPtoMove = new JLabel("Player To Move:   ");
		lblPtoMove.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		lblPtoMove.setForeground(Color.WHITE);
		GridBagConstraints gbc_RP = new GridBagConstraints();
		gbc_RP.insets = new Insets(0, 0, 5, 5);
		gbc_RP.fill = GridBagConstraints.BOTH;
		gbc_RP.gridx = 0;
		gbc_RP.gridy = 1;
		rightPanel.add(lblPtoMove, gbc_RP);
		
		lblPtoMoveVal = new JLabel("start - white?");
		lblPtoMoveVal.setForeground(Color.WHITE);
		lblPtoMoveVal.setFont(new Font("CaskaydiaCove Nerd Font Mono", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPtoMoveVal = new GridBagConstraints();
		gbc_lblPtoMoveVal.insets = new Insets(0, 0, 5, 5);
		gbc_lblPtoMoveVal.fill = GridBagConstraints.BOTH;
		gbc_lblPtoMoveVal.gridx = 1;
		gbc_lblPtoMoveVal.gridy = 1;
		rightPanel.add(lblPtoMoveVal, gbc_lblPtoMoveVal);
		
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
				updateMetadata();
			}
		});
		GridBagConstraints gbc_btnLoadFromFEN = new GridBagConstraints();
		gbc_btnLoadFromFEN.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadFromFEN.fill = GridBagConstraints.VERTICAL;
		gbc_btnLoadFromFEN.gridx = 0;
		gbc_btnLoadFromFEN.gridy = 3;
		rightPanel.add(btnLoadFromFEN, gbc_btnLoadFromFEN);
		
		// Load Game from FEN GUI elements in right panel	
		tfLoadFEN = new JTextField();
		GridBagConstraints gbc_tfLoadFEN = new GridBagConstraints();
		gbc_tfLoadFEN.gridheight = 2;
		gbc_tfLoadFEN.insets = new Insets(0, 0, 5, 10);
		gbc_tfLoadFEN.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfLoadFEN.gridx = 1;
		gbc_tfLoadFEN.gridy = 3;
		rightPanel.add(tfLoadFEN, gbc_tfLoadFEN);
		
		JButton btnCleartfFEN = new JButton("Clear FEN");
		btnCleartfFEN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfLoadFEN.setText("");
			}
		});
		GridBagConstraints gbc_btnCleartfFEN = new GridBagConstraints();
		gbc_btnCleartfFEN.insets = new Insets(0, 0, 5, 5);
		gbc_btnCleartfFEN.gridx = 0;
		gbc_btnCleartfFEN.gridy = 4;
		rightPanel.add(btnCleartfFEN, gbc_btnCleartfFEN);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(400);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_2.gridx = 1;
		gbc_horizontalStrut_2.gridy = 6;
		rightPanel.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		JMenuBar menuBar = new JMenuBar();
		frmChess.setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		mnGame.setBackground(Color.DARK_GRAY);
		menuBar.add(mnGame);
		
		JMenuItem mnItmSolo = new JMenuItem("New Solo Game");
		mnItmSolo.setBackground(Color.DARK_GRAY);
		mnGame.add(mnItmSolo);
		
		JMenuItem nmItmNewGameRandom = new JMenuItem("New Game as black v Random");
		nmItmNewGameRandom.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/question.png")));
		mnGame.add(nmItmNewGameRandom);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New Game as white V Random");
		mntmNewMenuItem.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/white-question.png")));
		mnGame.add(mntmNewMenuItem);
		
		JMenuItem mnItmGameVChecks = new JMenuItem("New Game v Checks");
		mnItmGameVChecks.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/black-king.png")));
		mnGame.add(mnItmGameVChecks);

		// End of right panel + and it's elements
//		frmChess.pack(); // Keep this at the end
		frmChess.setVisible(true);
	}
	
	private void playOpponentsMove() {
		if( !game.isGameFinshed() ) {
			switch (gamemode) {
				case 1: // v random opponent
					move randomMove = PlayOpponents.getRandomMove(game);
					if( randomMove instanceof promotionMove ) {
						promotionMove pMove = (promotionMove)randomMove;
						game.playMove(randomMove, 1, pMove.getPromotionPiece());
					}else {
						game.playMove(randomMove, 1, "");
					}
					break;
				case 2: // 1 depth 
					move checkmateMove = PlayOpponents.getRandomMove(game);
					if( checkmateMove instanceof promotionMove ) {
						promotionMove pMove = (promotionMove)checkmateMove;
						game.playMove(pMove, 1, pMove.getPromotionPiece());
					}else {
						game.playMove(checkmateMove, 1, "");
					}
					break;
				default:
					break;
			}
			moves = game.generateMoves();
			loadGameToGUI();
			updateMetadata();
		}
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