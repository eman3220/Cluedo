package GUI;

import java.awt.BorderLayout;

import javax.swing.*;

import main.Cluedo;

public class MainFrame extends JFrame{
	
	private Cluedo host;
	private GamePanel gp;
	private ButtonPanel bp;
	
	public MainFrame(Cluedo host){
		this.host = host;
		
		initialiseFrame();
		
		this.gp = new GamePanel();
		this.bp = new ButtonPanel();
		this.add(gp);
		this.add(bp, BorderLayout.SOUTH);
		
		this.show();
	}

	private void initialiseFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
	}
}
