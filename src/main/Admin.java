package main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Admin extends JFrame {
	
	private final String TITLE = "Administrator Mode";
	
	private int defaultHeight = 710; // 600
	private int defaultWidth = 990; // 1000
	private int minHeight = 710; // 710
	private int minWidth = 990; // 990


	public Admin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		
		
		
		setVisible(true);
	}

}
