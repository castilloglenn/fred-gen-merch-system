package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import utils.Database;
import utils.Gallery;
import utils.Utility;


/**
 *  @author Allen Glenn E. Castillo
 */
public class Main extends JFrame {
	
	private final String TITLE = "Login";
	
	private Gallery gallery;
	private Database database;
	private Utility utility;

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}

	public Main() {
		gallery = new Gallery();
		database = new Database();
		utility = new Utility();
		

		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE + Utility.TITLE_SEPARATOR + Utility.APP_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);

		setLocationRelativeTo(null);
		setVisible(true);
	}

}
