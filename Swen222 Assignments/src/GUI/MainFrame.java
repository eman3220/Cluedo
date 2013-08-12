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
		
		this.add(gp);
		this.add(bp, BorderLayout.SOUTH);
		
		
		
		
	}

	private void initialiseFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 650);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.gp = new GamePanel();
		this.bp = new ButtonPanel();
		this.show();
	}
}
