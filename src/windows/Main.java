package windows;

import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;

import javax.swing.JLabel;
import java.awt.Point;
import javax.swing.JButton;

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
					Main window = new Main();
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
	
		//Middle Panel - Chess Board
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(null);
		
//		mainPanel.setBackground(new Color(108, 95, 217));
		mainPanel.setBackground(Color.DARK_GRAY);
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
//		mainPanelGBC.insets = new Insets(0, 0, 5, 5);
		mainPanelGBC.fill = GridBagConstraints.BOTH;
		mainPanelGBC.gridx = 1;
		mainPanelGBC.gridy = 1;
		mainPanelGBC.weightx = 0.5626;
//		mainPanelGBC.weighty = 0.5;
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		mainPanel.setLayout(gbl_mainPanel);
		

		frame.getContentPane().add(mainPanel, mainPanelGBC);
		
		//Set up board
		boolean colour = false; //false = white, true = black
		Dimension tileSize = new Dimension((mainPanel.getWidth()/10), (mainPanel.getWidth()/10));
		tileSize.getWidth();
		for(int rank=0; rank<8; rank++) {
			for(int file=0; file<8; file++) {
				board[rank][file] = new tile(colour, rank, file, 'p');
				colour = !colour;
				GridBagConstraints gbc_tile = new GridBagConstraints();
				gbc_tile.gridx = file;
				gbc_tile.gridy = rank;
				gbc_tile.weightx = 0.111;
				gbc_tile.weighty = 0.111;
				gbc_tile.fill = GridBagConstraints.BOTH;
				mainPanel.add(board[rank][file].label, gbc_tile);
				
				//board[rank][file].label.setPreferredSize(tileSize);
			}

			colour = !colour;
		}
		
		/*
		JLabel lblPawn1 = new JLabel("");
		lblPawn1.setBackground(Color.WHITE);
		lblPawn1.setIcon(new ImageIcon(home.class.getResource("/windows/pawn.png")));
		lblPawn1.addMouseListener(this);
		lblPawn1.addMouseMotionListener(this);
		GridBagConstraints gbc_lblPawn1 = new GridBagConstraints();
		gbc_lblPawn1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPawn1.gridx = 1;
		gbc_lblPawn1.gridy = 1;
		mainPanel.add(lblPawn1, gbc_lblPawn1);
		*/
		
		
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

		
		// Keep this at the end
		frame.pack();
		frame.setVisible(true);
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
