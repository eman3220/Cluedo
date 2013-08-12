package main;

import java.util.ArrayList;
import java.util.Scanner;

import collection.Accusation;
import collection.Suggestion;

import card.Card;
import card.LocationCard;
import card.SuspectCard;
import card.WeaponCard;

import players.Player;

public class Turn {
	private Cluedo host;
	private int diceRoll;
	private Player current;
	private boolean accusing;
	private boolean suggesting;
	private Coordinates to;
	private Coordinates from;

	public Turn(Cluedo host, Player current) {
		this.current = current;
		this.host = host;
		if(current.isInRoom()){
			// ask the user which coordinates to exit
			System.out.println("You are currently in the "+current.getRoom().getName());
			// if we are in the corner rooms...
			Room currentRoom = current.getRoom();
			if(currentRoom.getName().equals("Spa") || currentRoom.getName().equals("Observatory") 
					|| currentRoom.getName().equals("Kitchen") || currentRoom.getName().equals("GuestHouse")){
				String input;
				while(true){
					System.out.println("Do you want to take the secret passage? (yes/no)");
					
					input = input();
					if(input.toUpperCase().equals("YES")){
						Room newRoom = takeSecretPassage();
						System.out.println("You are now in the "+newRoom.getName());
						//make a suggestion using the new room
						this.makeSuggestion(newRoom);
						return;
					} else if(input.toUpperCase().equals("NO")){
						// dont take secret passage, so we must leave room
						break;
					}
					System.out.println("Invalid input");
				}
				
			}
			
			int count = 1;
			for(Coordinates c : currentRoom.getEntrances()){
				System.out.println("("+(count++)+") "+c.getX()+" "+c.getY());
			}
			Coordinates exitCoord;
			while(true){
				System.out.println("\nSelect an exit");
				try{
					int exit = this.inputInt();
					exitCoord = currentRoom.getEntrances().get(exit-1);
					break;
				}catch(IndexOutOfBoundsException e){
					System.out.println("Invalid choice");
				}
			}
			current.setCoord(exitCoord, this.host.getGrid());
			currentRoom.leaveRoom(current);			
		}
		
		// main section of turn
		this.diceRoll = host.getDice().roll();
		System.out.println("You are at: (" + current.getCoord().getX() + ","
				+ current.getCoord().getY() + ")");
		System.out.println("You rolled a " + this.diceRoll);
		this.current.setMoveCount(diceRoll);
		for (int i = diceRoll; i > 0; i--) {

			System.out.println("You have " + i + " steps left");
			System.out
					.println("Type directions to move (UP/DOWN/LEFT/RIGHT): ");
			String input = input().toUpperCase();
			i = moveAround(input, i);
			
			if (roomCheck()) {
				i = 0;
				Coordinates c = host.getGrid()[current.getCoord().getX()][current
						.getCoord().getY()].getCoord();
				Room r = host.getCoordinatesToRoom().get(c);

				if (r.getName().equals("Pool")) {
					// prompt user for accusation
					System.out
							.println("Do you want to make a suggestion (sug), or an accusation (acc)?");
					String SOrA = input().toUpperCase();
					
					if (SOrA.equals("ACC")) {
						makeAccusation();
					} else {
						makeSuggestion(r);
					}

				} else {
					// prompt user for suggestion
					makeSuggestion(r);
				}
			}
		}
		// prompt user for viewing hand, making suggestion, and making
		// accusation
		System.out.println();
	}

	private Room takeSecretPassage() {
		// spa goes to guesthouse
		// observatory goes to kitchen
		Room r = this.current.getRoom();
		Room newRoom = null;
		if(r.getName().equals("Spa")){
			r.leaveRoom(current);
			newRoom = this.host.getRooms().get("GuestHouse");
			newRoom.enterRoom(current);
		}
		else if(r.getName().equals("GuestHouse")){
			r.leaveRoom(current);
			newRoom = this.host.getRooms().get("Spa");
			newRoom.enterRoom(current);
		}
		else if(r.getName().equals("Kitchen")){
			r.leaveRoom(current);
			newRoom = this.host.getRooms().get("Observatory");
			newRoom.enterRoom(current);
		}
		else if(r.getName().equals("Observatory")){
			r.leaveRoom(current);
			newRoom = this.host.getRooms().get("Kitchen");
			newRoom.enterRoom(current);
		}
		return newRoom;
	}

	private void makeAccusation() {
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(host.getListOfSuspectCard());
		allCards.addAll(host.getListOfWeaponCard());
		allCards.addAll(host.getListOfLocationCard());

		int count = 1;

		for (Card c : allCards) {
			System.out.println("(" + (count++) + ") " + c.getName());
		}

		int oneInt;
		int twoInt;
		int threeInt;

		LocationCard lc;
		WeaponCard wc;
		SuspectCard sc;

		while (true) {
			System.out.println("\nSelect a location, a weapon, and a suspect.");
			System.out.println("Make your choice... who do you accuse?");
			try {
				oneInt = inputInt();
				lc = (LocationCard) allCards.get(oneInt - 1);
				System.out.println("You chose: "
						+ allCards.get(oneInt - 1).getName());

				twoInt = inputInt();
				wc = (WeaponCard) allCards.get(twoInt - 1);
				System.out.println("You chose: "
						+ allCards.get(twoInt - 1).getName());

				threeInt = inputInt();
				sc = (SuspectCard) allCards.get(threeInt - 1);
				System.out.println("You chose: "
						+ allCards.get(threeInt - 1).getName());
			} catch (ClassCastException cce) {
				System.out.println("Pick a location, weapon, and suspect... in that order please");
				continue;
			}

			if (lc == null || wc == null || sc == null) {
				System.out.println("Pick again...");
				continue;
			}
			break;
		}
		// TODO still working on this...
		Accusation acc = new Accusation(lc, wc, sc);
		// compare this to the envelope
		if (acc.match(host.getEnv())) {
			System.out.println(current.getSuspect().getName()
					+ " wins the game!");
			// the game is over and

			Game.state = 0;
			return;
		} else {
			System.out.println("You made a false accusation... you're out!");
			// remove player from the playerList
			host.getListOfPlayer().remove(current);
			return;
		}
	}

	private int inputInt() {
		Scanner scan = new Scanner(System.in);
		String input = scan.next();
		int i = Integer.parseInt(input);
		return i;
	}

	private void makeSuggestion(Room r) {
		// TODO suggestion
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(host.getListOfSuspectCard());
		allCards.addAll(host.getListOfWeaponCard());

		for (Card c : current.getHand()) {
			allCards.remove(c);
		}

		int count = 1;
		System.out.println("\nWeapons");
		for (Card c : allCards) {
			System.out.println("(" + (count++) + ") " + c.getName());
		}
		System.out.println("Make a suggestion using the above cards");

		int oneInt;
		int twoInt;

		LocationCard lc = r.getCard();
		WeaponCard wc;
		SuspectCard sc;

		while (true) {
			System.out.println("\nSelect a weapon and a suspect.");
			System.out.println("Make your choice... who do you suggest?");
			
			try{
			oneInt = inputInt();
			wc = (WeaponCard) allCards.get(oneInt - 1);
			System.out.println("You picked " + wc.getName());
			twoInt = inputInt();
			sc = (SuspectCard) allCards.get((twoInt - 1));
			System.out.println("You picked " + sc.getName());
			} catch (ClassCastException cce){
				System.out.println("Pick a weapon and suspect... in that order please");
				continue;
			} catch (IndexOutOfBoundsException e){
				System.out.println("Please select a number within the range");
				continue;
			}

			if (lc == null || wc == null || sc == null) {
				System.out.println("Pick again...");
				System.out.println("Weapon first... then Suspect");
				continue;
			}
			break;
		}
		System.out.println("Your suggestion is made up of...");
		System.out.println(lc.getName() + " " + wc.getName() + " "
				+ sc.getName());
		// what do we do with the suggestion?
		Suggestion sug = new Suggestion(lc, wc, sc);
		refuting(sug);
	}

	private void refuting(Suggestion sug) {
		System.out.println();
		LocationCard lc = sug.getLocation();
		WeaponCard wc = sug.getWeapon();
		SuspectCard sc = sug.getSuspect();

		for (Player p : host.getListOfPlayer()) {
			ArrayList<Card> hand = p.getHand();
			if (hand.contains(lc)) {
				sug.setLocationRefuted(true);
				System.out.println(lc.getName() + " has been refuted");
			}
			if (hand.contains(wc)) {
				sug.setWeaponRefuted(true);
				System.out.println(wc.getName() + " has been refuted");
			}
			if (hand.contains(sc)) {
				sug.setSuspectRefuted(true);
				System.out.println(sc.getName() + " has been refuted");
			}
		}
	}

	private boolean roomCheck() {
		Coordinates c = host.getGrid()[current.getCoord().getX()][current
				.getCoord().getY()].getCoord();
		Room r = host.getCoordinatesToRoom().get(c);
		if (r != null) {
			// ask user if they want to enter the room
			while (true) {
				System.out.println("Do you want to enter the " + r.getName()
						+ " (yes/no)");
				Scanner sc = new Scanner(System.in);
				String in = sc.next().toUpperCase();
				if (in.equals("YES")) {
					// TODO put player in room

					System.out.println(current.getSuspect().getName()
							+ " entered the " + r.getName());
					
					r.enterRoom(current);
					//current.setCoord(new Coordinates(-1,-1), host.getGrid());
					return true;
				} else if (in.equals("NO")) {
					// just leave the player be and carry on moving
					return false;
				}
				System.out.println("Invalid input... try again");
			}
		}
		return false;
	}

	private int moveAround(String input, int i) {

		if (input.equals("UP")) {
			// System.out.println("you said up");

			// check if the tile is walkable too
			Tile[][] grid = this.host.getGrid();
			if (this.current.getCoord().getY() == 0) {
				System.out.println("Can't walk here");
				return i++;
			}
			Tile t = grid[this.current.getCoord().getX()][(this.current
					.getCoord().getY() - 1)];
			if (t.isWalkable() && t.getPlayer() == null) {
				current.up(grid);

			} else {
				System.out.println("Can't walk here");
				i++;
			}
		} else if (input.equals("DOWN")) {
			// System.out.println("you said down");
			Tile[][] grid = this.host.getGrid();
			if (this.current.getCoord().getY() == 29) {
				System.out.println("Can't walk here");
				return i++;
			}
			Tile t = grid[this.current.getCoord().getX()][this.current
					.getCoord().getY() + 1];
			if (t!=null && t.isWalkable() && t.getPlayer() == null) {
				current.down(grid);

			} else {
				System.out.println("Can't walk here");
				i++;
			}
		} else if (input.equals("LEFT")) {
			// System.out.println("you said left");
			Tile[][] grid = this.host.getGrid();
			if (this.current.getCoord().getX() == 0) {
				System.out.println("Can't walk here");
				return i++;
			}
			Tile t = grid[this.current.getCoord().getX() - 1][(this.current
					.getCoord().getY())];
			if (t.isWalkable() && t.getPlayer() == null) {
				current.left(grid);

			} else {
				System.out.println("Can't walk here");
				i++;
			}
		} else if (input.equals("RIGHT")) {
			// System.out.println("you said right");
			Tile[][] grid = this.host.getGrid();
			if (this.current.getCoord().getX() == 24) {
				System.out.println("Can't walk here");
				return i++;
			}
			Tile t = grid[this.current.getCoord().getX() + 1][this.current
					.getCoord().getY()];
			if (t.isWalkable() && t.getPlayer() == null) {
				current.right(grid);

			} else {
				System.out.println("Can't walk here");
				i++;
			}
		} else {
			System.out.println("Invalid Command");
			i++;
		}
		return i;
	}

	private String input() {
		Scanner scan = new Scanner(System.in);
		return scan.next().toUpperCase();
	}
}
