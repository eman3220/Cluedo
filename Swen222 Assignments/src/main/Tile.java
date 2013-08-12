package main;

import players.Player;

public class Tile {
	private Player player;
	private boolean walkable;
	private Coordinates coord;
	
	public Tile(boolean walkable, Coordinates coord){
		this.player = null;
		this.walkable = walkable;
		this.coord = coord;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public Coordinates getCoord() {
		return coord;
	}
}
