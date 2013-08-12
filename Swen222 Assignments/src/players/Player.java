package players;

import java.util.ArrayList;

import card.Card;
import card.SuspectCard;
import main.Coordinates;
import main.Room;
import main.Tile;

/**
 * Player class WIll be able to make suggestions make accusation
 * 
 * and set starting position
 * 
 * @author Emmanuel
 * 
 */
public class Player {

	private Tile[][] grid = null;
	
	private SuspectCard suspect;
	private Coordinates coord;
	private ArrayList<Card> hand;
	private int moveCount = 0;
	private int turnOrder;
	private boolean inRoom = false;
	private Room room = null;
	
	public boolean isInRoom() {
		return inRoom;
	}
	
	public Room getRoom(){
		return room;
	}

	public void setRoom(Room r){
		this.room = r;
	}
	
	public void toggleInRoom(){
		if(inRoom==true){
			inRoom = false;
		} else {
			inRoom = true;
		}
	}

	public int getTurnOrder() {
		return turnOrder;
	}

	public void setTurnOrder(int turnOrder) {
		this.turnOrder = turnOrder;
	}

	// private Tile[][] grid;

	public Player() {
		this.coord = new Coordinates(-1, -1);
		this.hand = new ArrayList();
		// this.grid = grid;
	}

	public SuspectCard getSuspect() {
		return suspect;
	}

	public void setSuspect(SuspectCard suspect) {
		this.suspect = suspect;
	}

	public Coordinates getCoord() {
		return coord;
	}

	public boolean setCoord(Coordinates coord, Tile[][] grid) {
		if(this.grid==null){
			this.grid = grid;
		}
		
		if (grid[coord.getX()][coord.getY()].isWalkable()) {
			this.coord = coord;
			
			grid[coord.getX()][coord.getY()].setPlayer(this);
			
			return true;
		} else {
			System.out.println("Can't walk here!");
			return false;
		}
	}

	// TODO movement stuff
	public void up(Tile[][] grid) {
		if(this.coord.getY()==0){
			System.out.println("Can't move up anymore");
			return;
		}
		if (moveCount > 0) {			
			grid[this.getCoord().getX()][this.getCoord().getY()].setPlayer(null);
			grid[this.getCoord().getX()][this.getCoord().getY()-1].setPlayer(this);
			this.coord.setY(this.coord.getY() - 1);
			moveCount--;
			System.out.println("You moved UP : ("+this.getCoord().getX()+","+this.getCoord().getY()+")");
		}
	}

	public void down(Tile[][] grid) {
		if(this.coord.getY()==29){
			System.out.println("Can't move down anymore");
			return;
		}
		if (moveCount > 0) {
			grid[this.getCoord().getX()][this.getCoord().getY()].setPlayer(null);
			grid[this.getCoord().getX()][this.getCoord().getY()+1].setPlayer(this);
			
			this.coord.setY(this.coord.getY() + 1);
			moveCount--;
			System.out.println("You moved DOWN : ("+this.getCoord().getX()+","+this.getCoord().getY()+")");
		}
	}

	public void left(Tile[][] grid) {
		if(this.coord.getX()==0){
			System.out.println("Can't move left anymore");
			return;
		}
		if (moveCount > 0) {
			grid[this.getCoord().getX()][this.getCoord().getY()].setPlayer(null);
			grid[this.getCoord().getX()-1][this.getCoord().getY()].setPlayer(this);
			this.coord.setX(this.coord.getX() - 1);
			moveCount--;
			
			System.out.println("You moved LEFT : ("+this.getCoord().getX()+","+this.getCoord().getY()+")");
		}

	}

	public void right(Tile[][] grid) {
		if(this.coord.getY()==24){
			System.out.println("Can't move right anymore");
			return;
		}
		if (moveCount > 0) {
			grid[this.getCoord().getX()][this.getCoord().getY()].setPlayer(null);
			grid[this.getCoord().getX()+1][this.getCoord().getY()].setPlayer(this);
			
			this.coord.setX(this.coord.getX() + 1);
			moveCount--;
			System.out.println("You moved RIGHT : ("+this.getCoord().getX()+","+this.getCoord().getY()+")");
		}
	}

	// TODO hand stuff
	public void addCard(Card c) {
		this.hand.add(c);
	}

	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
}
