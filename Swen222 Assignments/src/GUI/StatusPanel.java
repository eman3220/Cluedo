package GUI;

import java.awt.Color;

import javax.swing.JPanel;

import main.Cluedo;

public class StatusPanel extends JPanel{
	
	private Cluedo host;
	
	public StatusPanel(Cluedo host){
		this.host = host;
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
		
		// don't forget to set bounds
	}
}
