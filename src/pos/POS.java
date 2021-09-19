package pos;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import utils.Gallery;
import utils.RoundedPanel;
import utils.Utility;
import utils.VerticalLabelUI;


/**
 * To be done by: Glenn
 */
@SuppressWarnings("serial")
public class POS extends JFrame {

	private JPanel mainPanel, navigationPanel, displayPanel, 
					posPanel, transactionPanel, reportPanel,
					cartPanel;
	private JLabel lblDashboardNav, lblTransactionNav, lblReportNav,
					lblTransactionNo, lblDateTime;
	private SpringLayout sl_mainPanel, sl_posPanel, sl_cartPanel;
	private CardLayout cardLayout;
	
	private int height = 600;
	private int width = 1000;

	private Gallery gallery;
	private Utility utility;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					POS frame = new POS();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public POS() {
		gallery = new Gallery();
		utility = new Utility();
		
		/**
		 *  	After designing, change all Panel to Rounded Panel like this:
		 * panelVariableExample = new RoundedPanel(Gallery.BLUE);
		 *     
		 * 	 	To set the default font, first set the size then set the font:
		 * utility.setFontSize(20f);
		 * lblNewLabel.setFont(gallery.font);
		 */
		
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		
		mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setBackground(Gallery.BLACK);
		sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		cardLayout = new CardLayout(0, 0);
		
		navigationPanel = new RoundedPanel(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationPanel, 426, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationPanel, 60, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationPanel, -30, SpringLayout.WEST, mainPanel);
		mainPanel.add(navigationPanel);

		displayPanel = new RoundedPanel(Gallery.GRAY);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, displayPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, displayPanel, 15, SpringLayout.EAST, navigationPanel);
		SpringLayout sl_navigationPanel = new SpringLayout();
		navigationPanel.setLayout(sl_navigationPanel);
		
		lblDashboardNav = new JLabel("POS");
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblDashboardNav, 35, SpringLayout.NORTH, navigationPanel);
		lblDashboardNav.setBorder(new EmptyBorder(10, 10, 0, 10));
		lblDashboardNav.setFont(utility.getFont(15f));
		lblDashboardNav.setForeground(Color.WHITE);
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblDashboardNav, 40, SpringLayout.WEST, navigationPanel);
		lblDashboardNav.setUI(new VerticalLabelUI(false));
		lblDashboardNav.setIcon(utility.getImage("pos.png", 15));
		sl_navigationPanel.putConstraint(SpringLayout.EAST, lblDashboardNav, -10, SpringLayout.EAST, navigationPanel);
		navigationPanel.add(lblDashboardNav);
		
		lblTransactionNav = new JLabel("TRANSACTIONS");
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblTransactionNav, 10, SpringLayout.SOUTH, lblDashboardNav);
		lblTransactionNav.setBorder(new EmptyBorder(10, 10, 0, 10));
		lblTransactionNav.setFont(utility.getFont(15f));
		lblTransactionNav.setForeground(Color.WHITE);
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblTransactionNav, 0, SpringLayout.WEST, lblDashboardNav);
		sl_navigationPanel.putConstraint(SpringLayout.EAST, lblTransactionNav, 0, SpringLayout.EAST, lblDashboardNav);
		lblTransactionNav.setUI(new VerticalLabelUI(false));
		lblTransactionNav.setIcon(utility.getImage("transaction.png", 15));
		navigationPanel.add(lblTransactionNav);
		
		lblReportNav = new JLabel("REPORTS");
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblReportNav, 10, SpringLayout.SOUTH, lblTransactionNav);
		lblReportNav.setForeground(Color.WHITE);
		lblReportNav.setBorder(new EmptyBorder(10, 10, 0, 10));
		lblReportNav.setFont(utility.getFont(15f));
		lblReportNav.setUI(new VerticalLabelUI(false));
		lblReportNav.setIcon(utility.getImage("report.png", 15));
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblReportNav, 0, SpringLayout.WEST, lblDashboardNav);
		sl_navigationPanel.putConstraint(SpringLayout.EAST, lblReportNav, 0, SpringLayout.EAST, lblDashboardNav);
		navigationPanel.add(lblReportNav);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, displayPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, displayPanel, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(displayPanel);
		displayPanel.setLayout(cardLayout);
		
		posPanel = new RoundedPanel(Gallery.GRAY);
		displayPanel.add(posPanel, "pos");
		sl_posPanel = new SpringLayout();
		posPanel.setLayout(sl_posPanel);
		
		lblTransactionNo = new JLabel("Transaction No.81273");
		lblTransactionNo.setFont(utility.getFont(23f));
		sl_posPanel.putConstraint(SpringLayout.NORTH, lblTransactionNo, 20, SpringLayout.NORTH, posPanel);
		sl_posPanel.putConstraint(SpringLayout.WEST, lblTransactionNo, 20, SpringLayout.WEST, posPanel);
		posPanel.add(lblTransactionNo);
		
		cartPanel = new RoundedPanel(Color.WHITE);
		sl_posPanel.putConstraint(SpringLayout.NORTH, cartPanel, 20, SpringLayout.NORTH, posPanel);
		sl_posPanel.putConstraint(SpringLayout.WEST, cartPanel, -250, SpringLayout.EAST, posPanel);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, cartPanel, -20, SpringLayout.SOUTH, posPanel);
		sl_posPanel.putConstraint(SpringLayout.EAST, cartPanel, -20, SpringLayout.EAST, posPanel);
		posPanel.add(cartPanel);
		
		sl_cartPanel = new SpringLayout();
		cartPanel.setLayout(sl_cartPanel);
		
		lblDateTime = new JLabel(utility.getTime());
		sl_posPanel.putConstraint(SpringLayout.SOUTH, lblDateTime, -1, SpringLayout.SOUTH, lblTransactionNo);
		sl_posPanel.putConstraint(SpringLayout.EAST, lblDateTime, -20, SpringLayout.WEST, cartPanel);
		lblDateTime.setFont(utility.getFont(15f));
		posPanel.add(lblDateTime);
		
		transactionPanel = new RoundedPanel(Color.BLUE); // Gallery.GRAY
		displayPanel.add(transactionPanel, "transaction");
		transactionPanel.setLayout(new SpringLayout());
		
		reportPanel = new RoundedPanel(Color.GREEN); // Gallery.GRAY
		displayPanel.add(reportPanel, "report");
		reportPanel.setLayout(new SpringLayout());
		
		
		
		
		
		
		
		
		


		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
			}
		});
		lblDashboardNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(lblDashboardNav); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblDashboardNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "pos");
			}
		});
		lblTransactionNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(lblTransactionNav); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblTransactionNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "transaction");
			}
		});
		lblReportNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(lblReportNav); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblReportNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "report");
			}
		});
		
		
		
		setLocationRelativeTo(null);
	}
	
	public void mouseEnter(JLabel label) {
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label.setBackground(Gallery.DARK_BLUE);
		label.setOpaque(true);
	}
	
	public void mouseExit(JLabel label) {
		label.setBackground(Gallery.BLUE);
	}
}
