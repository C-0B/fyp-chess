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
import java.io.PrintWriter;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Main extends javax.swing.JFrame implements MouseInputListener{

	private JFrame frame;
	private JLabel lblPawn1;
	private tile[][] board = new tile[8][8];
	Point startPoint;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Main window = new Main();
					
					PrintWriter pw = new PrintWriter(System.out,true);
					pw.println('\u2600');
					System.out.println(String.valueOf('\u2600'));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		System.out.println(screenSize.getWidth());
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBackground(Color.DARK_GRAY);
		frame.getContentPane().setForeground(new Color(0, 102, 153));
		frame.setBounds(100, 100, 1080, 600);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH );
		//frame.setMinimumSize(new Dimension(1600, 900));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();		
		gridBagLayout.columnWidths = new int[]{ 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		frame.getContentPane().setLayout(gridBagLayout);
				
		//Left panel
		JPanel mainLeftPanel = new JPanel();
		mainLeftPanel.setBackground(Color.BLACK);
		GridBagConstraints mainLeftPanelGBC = new GridBagConstraints();
		mainLeftPanelGBC.insets = new Insets(10, 10, 10, 10);
		mainLeftPanelGBC.fill = GridBagConstraints.BOTH;
		mainLeftPanelGBC.gridx = 0;
		mainLeftPanelGBC.gridy = 1;
		mainLeftPanelGBC.weightx = 0.21875;
		mainLeftPanelGBC.weighty = 0.8;
		frame.getContentPane().add(mainLeftPanel, mainLeftPanelGBC);
		
		JButton btnResetBoard = new JButton("Reset Board");
		btnResetBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
				loadFENtoBoard(startFEN);
			} 
			});
		mainLeftPanel.add(btnResetBoard);
	
		//Middle Panel - Chess Board
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(null);
		
//		mainPanel.setBackground(new Color(108, 95, 217));
		mainPanel.setBackground(new Color(64, 64, 64));
//		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
//		mainPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
//			@Override
//			public void componentResized(ComponentEvent arg0) {
//			    int W = 1;  
//			    int H = 1;  
//			    Rectangle b = arg0.getComponent().getBounds();
//			    arg0.getComponent().setBounds(b.x, b.y, b.width, b.width*H/W);
//			}
//		  });
//		mainPanel.setPreferredSize(new Dimension(1600, 900));
		GridBagConstraints mainPanelGBC = new GridBagConstraints();

		mainPanelGBC.insets = new Insets(10, 10, 10, 10);
		mainPanelGBC.fill = GridBagConstraints.BOTH;
		mainPanelGBC.gridx = 1;
		mainPanelGBC.gridy = 1;
		mainPanelGBC.weightx = 0.5626;
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mainPanel.setLayout(gbl_mainPanel);
		
		frame.getContentPane().add(mainPanel, mainPanelGBC);
		
		//Set up board
		boolean colour = false; //false = white, true = black
		Dimension tileSize = new Dimension((mainPanel.getWidth()/10), (mainPanel.getWidth()/10));
		tileSize.getWidth();

		Integer tileNum = 0;
		for(int rank=0; rank<8; rank++) {
			for(int file=0; file<8; file++) {
				board[rank][file] = new tile(colour, rank, file, "");
				colour = !colour;
				GridBagConstraints gbc_tile = new GridBagConstraints();
				gbc_tile.gridx = file;
				gbc_tile.gridy = rank;
				gbc_tile.weightx = 0.111;
				gbc_tile.weighty = 0.111;
				gbc_tile.fill = GridBagConstraints.BOTH;
				board[rank][file].button.setText(tileNum.toString());
				tileNum++;
				board[rank][file].button.setFont(new Font("Arial", Font.BOLD, 18));
				mainPanel.add(board[rank][file].button, gbc_tile);
			}
			colour = !colour;
		}

		//lblPawn1.setIcon(new ImageIcon(home.class.getResource("/windows/pawn.png")));
		
		//Right panel
		SquarePanel rightPanel = new SquarePanel();
		rightPanel.setBackground(Color.BLACK);
		GridBagConstraints rightPanelGBC = new GridBagConstraints();
		rightPanelGBC.insets = new Insets(10, 10, 10, 10);
		rightPanelGBC.fill = GridBagConstraints.BOTH;
		rightPanelGBC.gridx = 2;
		rightPanelGBC.gridy = 1;
		rightPanelGBC.weightx = 0.21875;
		rightPanelGBC.weighty = 0.8;
		frame.getContentPane().add(rightPanel, rightPanelGBC);
		
		JLabel lblRightPanel = new JLabel("make it even");
		lblRightPanel.setForeground(Color.WHITE);
		rightPanel.add(lblRightPanel);
		
		
		//game game = new game();
		//setBoard(game.board);

		
		// Keep this at the end
		frame.pack();
		frame.setVisible(true);
	}
	
	private boolean loadFENtoBoard(String FEN) {
		String[] fenArray = FEN.split("(?!^)");
		int tilesCovered = 0, FENcount = 0;
		while(tilesCovered < 64) {
			String s = fenArray[FENcount];
//			System.out.println(tilesCovered+ " - "+s);
			if(isNumber(s)) {
				int emptyTiles = Integer.parseInt(s);
				for(emptyTiles = Integer.parseInt(s); emptyTiles>0; emptyTiles--) {
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
	private static boolean isNumber(String str) {
		try {  
			Integer.parseInt(str);  
		    return true;
		}catch(NumberFormatException e){  
		    return false;  
		}  
	}
	
	//Writes data to appear on the board
	private void writeToTile(int tilesCovered, String s) {
		int rank = tilesCovered / 8;
		int file = tilesCovered % 8;
		System.out.println(rank + ", "+file+" "+s);
		board[rank][file].button.setText(s);
	}

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
