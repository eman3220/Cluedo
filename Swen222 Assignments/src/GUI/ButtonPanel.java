package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ButtonPanel extends JPanel implements ActionListener{
	
	private JButton aButton;
	private JButton bButton;
	private JButton cButton;
	
	
	public ButtonPanel(){
		this.setSize(WIDTH, 100);
		this.setBackground(Color.DARK_GRAY);
		buttonSetup();
		
	}


	private void buttonSetup() {
		this.aButton = new JButton("One");
		this.bButton = new JButton("Two");
		this.cButton = new JButton("Three");
		aButton.addActionListener(this);
		bButton.addActionListener(this);
		cButton.addActionListener(this);
		this.add(aButton);
		this.add(bButton);
		this.add(cButton);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==aButton){
			
		} else if(e.getSource()==bButton){
			
		} else if(e.getSource()==cButton){
			
		}
	}
}
