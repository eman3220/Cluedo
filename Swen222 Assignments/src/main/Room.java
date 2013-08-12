package main;

import java.util.ArrayList;

import card.LocationCard;

import players.Player;

public class Room {
	private String name;
	private LocationCard card;
	private ArrayList<Player> playersInRoom;
	private ArrayList<Coordinates> entry;
	
	public Room(String name, LocationCard lc, ArrayList<Coordinates> entry){
		this.name = name;
		this.card = lc;
		this.entry = entry;
		this.playersInRoom = new ArrayList<Player>();
	}
	
	public ArrayList<Coordinates> getEntrances(){
		return this.entry;
	}
	
	/**
	 *  player can only enter the room if he/she is standing on
	 *  the entry coordinates. 
	 */
	public boolean enterRoom(Player p){
		this.playersInRoom.add(p);
		p.toggleInRoom();
		p.setRoom(this);
		return true;
	}
	
	/**
	 * Player can only leave the room if he/she is actuall
	 * in the room.
	 * @param p
	 */
	public void leaveRoom(Player p){
		if(this.playersInRoom.contains(p)){
			this.playersInRoom.remove(p);
			p.toggleInRoom();
			p.setRoom(null);
		}
	}
	
	public ArrayList<Player> getPlayersInRoom(){
		return this.playersInRoom;
	}
	
	public String getName(){
		return this.name;
	}
	
	public LocationCard getCard(){
		return this.card;
	}
}
