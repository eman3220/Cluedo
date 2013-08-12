package main;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JFrame;

import enums.Suspect;

import players.Player;

/**
 * This is where all the gameplay stuff happens. 
 * 
 * @author Emmanuel and Alvin
 * 
 */
public class Game {

	private Cluedo host;
	public static int state; // 0 for game finished, 1 for still playing

	public Game(Cluedo host){
		super();
		this.host = host;
		this.state = 1;

		runGame();
	}

	private Turn takeTurn(Player currentPlayer){

		System.out.println("It is " + currentPlayer.getSuspect().getName()
				+ "'s turn.");
		Turn t = new Turn(host, currentPlayer);
		return t;
	}

	private void runGame(){
		int player = 0;
		while (state != 0) {
			Player currentPlayer = this.host.getListOfPlayer().get(player);
			takeTurn(currentPlayer);
			
			// change player turn
			player++;
			if (player >= this.host.getListOfPlayer().size()) {
				player = 0;
			}
		}
		System.out.println("Thanks for playing ;-)");
	}
	
	public void gameFinished(){
		this.state = 0;
	}

}
