package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;

import main.Cluedo;

public class GamePanel extends JPanel{
	
	private Cluedo host;
	
	public void paint(Graphics gr){
		Graphics2D g = (Graphics2D)gr.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.LIGHT_GRAY);
		g.fill3DRect(0, 0, WIDTH, HEIGHT, true);
	}
	
	public GamePanel(Cluedo host){
		this.host = host;
	}
}
