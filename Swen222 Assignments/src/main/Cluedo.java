package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import GUI.MainFrame;

import collection.Envelope;

import card.Card;
import card.LocationCard;
import card.SuspectCard;
import card.WeaponCard;

import enums.Location;
import enums.Suspect;
import enums.Weapon;
import players.*;

public class Cluedo {

	private final int maxPlayers = 6;
	private final int minPlayers = 3;
	private int numPlayers;

	private Dice dice;

	private Envelope env;

	private Tile[][] grid;

	private Game game;

	private ArrayList<Coordinates> listOfCoordinates;
	private HashMap<Coordinates, Room> roomEntrances;
	private HashMap<String,Room> rooms;

	private ArrayList<Player> listOfPlayer;

	private ArrayList<Card> deck; // all cards - enveloped

	private ArrayList<WeaponCard> listOfWeaponCard;
	private ArrayList<LocationCard> listOfLocationCard;
	private ArrayList<SuspectCard> listOfSuspectCard;
	
	private MainFrame mf;

	public Cluedo(){
		this.mf = new MainFrame(this);
		
		System.out.println("Welcome to Cluedo! Made by E-Man and Imperial");		
		initialiseLists();

		this.dice = new Dice();

		// cards
		setupCharacters();
		setupWeapons();
		setupLocations();
		setupDeck();
		setupMurderComponents();
		setupGrid();

		setupNumberOfPlayers();

		pickCharacters();

		setupPlayerPosition();

		distributeCards();

		setTurnOrder();
		
		
		
		

		System.out.println("Game setup finished");
		MainThread mt = (MainThread) Thread.currentThread();

		// System.out.println("Dice Test: You rolled a " + this.dice.roll());
		System.out.println("Beginning game ...\n\n");
		mt.pause(500);

		System.out.println("** Game started **\n\n");
		this.game = new Game(this);
	}

	private void setTurnOrder() {
		
		for (int i = 0; i < listOfPlayer.size(); i++) {
			Player p = listOfPlayer.get(i);
			if (p.getSuspect().getData() == Suspect.KasandraScarlet) {
				p.setTurnOrder(6);
			}
			if (p.getSuspect().getData() == Suspect.JackMustard) {
				p.setTurnOrder(5);
			}
			if (p.getSuspect().getData() == Suspect.DianeWhite) {
				p.setTurnOrder(4);
			}
			if (p.getSuspect().getData() == Suspect.JacobGreen) {
				p.setTurnOrder(3);
			}
			if (p.getSuspect().getData() == Suspect.EleanorPeacock) {
				p.setTurnOrder(2);
			}
			if (p.getSuspect().getData() == Suspect.VictorPlum) {
				p.setTurnOrder(1);
			}
		}
		
	}

	private void distributeCards() {
		Collections.shuffle(deck);
		int i = 0;
		while (deck.size() != 0) {
			if (i == this.listOfPlayer.size()) {
				i = 0;
			}
			this.listOfPlayer.get(i).addCard(deck.remove(0));
			i++;
		}
		System.out.println("Cards distributed");
		
		/* Testing
		for(Player p :this.listOfPlayer){
			System.out.println(p.getSuspect().getName()+" has "+p.getHand().size()+" cards");
		}
		*/
	}

	private void setupDeck() {
		this.deck = new ArrayList<Card>();
		this.deck.addAll(listOfLocationCard);
		this.deck.addAll(listOfSuspectCard);
		this.deck.addAll(listOfWeaponCard);
		System.out.println("Deck made");
	}

	private void setupGrid() {
		this.grid = new Tile[25][30];
		int x = 0;
		int y = 0;
		Tile t = null;
		// setup tiles
		try {
			Scanner scan = new Scanner(new File("map.txt"));
			while (scan.hasNext()) {
				String line = scan.nextLine();
				String[] l = line.split("");

				for (String s : l) {
					if (s.equals("")) {
						continue;
					}
					if (s.equals(".")) {
						t = new Tile(true, new Coordinates(x, y));
					} else if (s.equals("x")) {
						t = new Tile(false, new Coordinates(x, y));
					}
					// tile t should never be null
					this.grid[x][y] = t;
					x++;
				}
				y++;
				x = 0;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Where's the map.txt file???");
		}
		setupRooms();
		System.out.println("\nGrid setup complete");
		// runGridTest();
	}

	private void runGridTest() {
		System.out.println("Grid draw test Starting...\n");
		for (int j = 0; j < 30; j++) {
			for (int i = 0; i < 25; i++) {
				Tile ti = this.grid[i][j];
				if (ti == null) {
					continue;
				}
				if (ti.isWalkable()) {
					System.out.print(".");
				} else {
					System.out.print("x");
				}
			}
			System.out.println();
		}
		System.out.println("Grid test finished");
	}

	private void setupRooms() {
		// setup lists of entrances to each room
		this.listOfCoordinates = new ArrayList<Coordinates>();
		this.rooms = new HashMap<String,Room>();
		
		ArrayList<Coordinates> spaEntrances = new ArrayList<Coordinates>();
		spaEntrances.add(grid[5][6].getCoord());

		ArrayList<Coordinates> theatreEntrances = new ArrayList<Coordinates>();
		theatreEntrances.add(grid[13][2].getCoord());
		theatreEntrances.add(grid[10][8].getCoord());

		ArrayList<Coordinates> livingRoomEntrances = new ArrayList<Coordinates>();
		livingRoomEntrances.add(grid[13][5].getCoord());
		livingRoomEntrances.add(grid[16][9].getCoord());

		ArrayList<Coordinates> observatoryEntrances = new ArrayList<Coordinates>();
		observatoryEntrances.add(grid[21][8].getCoord());

		ArrayList<Coordinates> patioEntrances = new ArrayList<Coordinates>();
		patioEntrances.add(grid[5][10].getCoord());
		patioEntrances.add(grid[8][12].getCoord());
		patioEntrances.add(grid[8][16].getCoord());
		patioEntrances.add(grid[5][18].getCoord());

		// ...
		ArrayList<Coordinates> poolEntrances = new ArrayList<Coordinates>();
		poolEntrances.add(grid[10][17].getCoord());
		poolEntrances.add(grid[17][17].getCoord());
		poolEntrances.add(grid[14][10].getCoord());

		ArrayList<Coordinates> hallEntrances = new ArrayList<Coordinates>();
		hallEntrances.add(grid[22][10].getCoord());
		hallEntrances.add(grid[18][13].getCoord());
		hallEntrances.add(grid[18][14].getCoord());

		ArrayList<Coordinates> kitchenEntrances = new ArrayList<Coordinates>();
		kitchenEntrances.add(grid[6][21].getCoord());

		ArrayList<Coordinates> diningRoomEntrances = new ArrayList<Coordinates>();
		diningRoomEntrances.add(grid[12][18].getCoord());
		diningRoomEntrances.add(grid[16][21].getCoord());

		ArrayList<Coordinates> guestHouseEntrances = new ArrayList<Coordinates>();
		guestHouseEntrances.add(grid[20][20].getCoord());

		// setup entrances map
		this.roomEntrances = new HashMap<Coordinates, Room>();
		for (LocationCard lc : this.listOfLocationCard) {
			String name = lc.getName();
			Room r;
			if (name.equals("Spa")) {
				r = new Room(name, lc, spaEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : spaEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("Theatre")) {
				r = new Room(name, lc,theatreEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : theatreEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("LivingRoom")) {
				r = new Room(name, lc,livingRoomEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : livingRoomEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("Observatory")) {
				r = new Room(name, lc,observatoryEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : observatoryEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("Patio")) {
				r = new Room(name,lc, patioEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : patioEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("Pool")) {
				r = new Room(name,lc,poolEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : poolEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("Hall")) {
				r = new Room(name, lc,hallEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : hallEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("Kitchen")) {
				r = new Room(name, lc,kitchenEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : kitchenEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("DiningRoom")) {
				r = new Room(name, lc,diningRoomEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : diningRoomEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			} else if (name.equals("GuestHouse")) {
				r = new Room(name, lc,guestHouseEntrances);
				this.rooms.put(name, r);
				for (Coordinates c : guestHouseEntrances) {
					this.roomEntrances.put(c, r);
					this.listOfCoordinates.add(c);
				}
			}
		}
	}

	private void setupPlayerPosition() {
		// need a map of player start positions
		HashMap<Suspect, Coordinates> startPositions = new HashMap<Suspect, Coordinates>();
		startPositions.put(Suspect.KasandraScarlet, new Coordinates(18, 28));
		startPositions.put(Suspect.JackMustard, new Coordinates(7, 28));
		startPositions.put(Suspect.DianeWhite, new Coordinates(0, 19));
		startPositions.put(Suspect.JacobGreen, new Coordinates(0, 9));
		startPositions.put(Suspect.EleanorPeacock, new Coordinates(6, 0));
		startPositions.put(Suspect.VictorPlum, new Coordinates(20, 0));

		for (int i = 0; i < this.listOfPlayer.size(); i++) {
			Player p = this.listOfPlayer.get(i);
			Suspect sus = p.getSuspect().getData();
			boolean b = p.setCoord(startPositions.get(sus), this.grid);
			// should always be able to put character on starting position
		}
		System.out.println("Initial player starting positions set");
	}

	private void setupMurderComponents() {
		int one = (int) Math.floor(Math.random()
				* this.listOfLocationCard.size());
		int two = (int) Math.floor(Math.random()
				* this.listOfSuspectCard.size());
		int three = (int) Math.floor(Math.random()
				* this.listOfWeaponCard.size());

		LocationCard lc = this.listOfLocationCard.get(one);
		SuspectCard sc = this.listOfSuspectCard.get(two);
		WeaponCard wc = this.listOfWeaponCard.get(three);

		//System.out.println("Deck size: " + this.deck.size());

		this.deck.remove(lc);
		this.deck.remove(sc);
		this.deck.remove(wc);

		//System.out.println("Deck size: " + this.deck.size());

		this.env = new Envelope(sc, lc, wc);
		System.out.println("Envelope setup complete");

		// TODO get rid of this print
		//System.out.println("Envelope made using " + sc.getName() + " "
				//+ lc.getName() + " " + wc.getName());
	}

	private void initialiseLists() {
		System.out.print("Initialising lists... ");
		this.listOfLocationCard = new ArrayList<LocationCard>();
		this.listOfSuspectCard = new ArrayList<SuspectCard>();
		this.listOfWeaponCard = new ArrayList<WeaponCard>();
		this.listOfPlayer = new ArrayList<Player>();
		System.out.println("List initialisation Finished");
	}

	// incomplete
	private void pickCharacters() {
		ArrayList<SuspectCard> availableCharacters = new ArrayList<SuspectCard>();
		// load characters into list
		for (int i = 0; i < this.listOfSuspectCard.size(); i++) {
			availableCharacters.add(this.listOfSuspectCard.get(i));
		}

		for (int i = 0; i < this.listOfPlayer.size(); i++) {
			System.out.println("Player " + (i + 1) + "... pick a character");
			for (int j = 0; j < availableCharacters.size(); j++) {
				System.out.println((j + 1) + ". "
						+ availableCharacters.get(j).getName());
			}
			while (true) {
				Scanner scan = new Scanner(System.in);
				int i1 = Integer.parseInt(scan.next());
				if (i1 <= availableCharacters.size()) {
					if (i1 == 1) {
						System.out.println("Player " + (i + 1) + " is: "
								+ availableCharacters.get(1 - 1).getName());
						this.listOfPlayer.get(i).setSuspect(
								availableCharacters.remove(1 - 1));
						break;
					} else if (i1 == 2) {
						System.out.println("Player " + (i + 1) + " is: "
								+ availableCharacters.get(2 - 1).getName());
						this.listOfPlayer.get(i).setSuspect(
								availableCharacters.remove(2 - 1));
						break;
					} else if (i1 == 3) {
						System.out.println("Player " + (i + 1) + " is: "
								+ availableCharacters.get(3 - 1).getName());
						this.listOfPlayer.get(i).setSuspect(
								availableCharacters.remove(3 - 1));
						break;
					} else if (i1 == 4) {
						System.out.println("Player " + (i + 1) + " is: "
								+ availableCharacters.get(4 - 1).getName());
						this.listOfPlayer.get(i).setSuspect(
								availableCharacters.remove(4 - 1));
						break;
					} else if (i1 == 5) {
						System.out.println("Player " + (i + 1) + " is: "
								+ availableCharacters.get(5 - 1).getName());
						this.listOfPlayer.get(i).setSuspect(
								availableCharacters.remove(5 - 1));
						break;
					} else if (i1 == 6) {
						System.out.println("Player " + (i + 1) + " is: "
								+ availableCharacters.get(6 - 1).getName());
						this.listOfPlayer.get(i).setSuspect(
								availableCharacters.remove(6 - 1));
						break;
					}

				}
				System.out.println("Pick again...");
			}

		}
	}

	private void setupWeapons() {
		System.out.print("Setting up weapon cards... ");
		Weapon[] values = Weapon.values();
		for (int i = 0; i < values.length; i++) {
			Weapon s = values[i];
			WeaponCard sc = new WeaponCard(s);
			this.listOfWeaponCard.add(sc);
		}
		System.out.println("Finished");
	}

	private void setupLocations() {
		System.out.print("Setting up location cards... ");
		Location[] values = Location.values();
		for (int i = 0; i < values.length; i++) {
			Location s = values[i];
			LocationCard sc = new LocationCard(s);
			this.listOfLocationCard.add(sc);
		}
		System.out.println("Finished");
	}

	private void setupCharacters() {
		System.out.print("Setting up character cards... ");
		Suspect[] values = Suspect.values();
		for (int i = 0; i < values.length; i++) {
			Suspect s = values[i];
			SuspectCard sc = new SuspectCard(s);
			this.listOfSuspectCard.add(sc);
		}
		System.out.println("Finished");
	}

	private void setupNumberOfPlayers() {
		while (true) {
			try {
				System.out.println("How many players will there be: ");
				Scanner scan = new Scanner(System.in);
				String line = scan.next();
				this.numPlayers = Integer.parseInt(line);
				if (this.numPlayers > this.maxPlayers) {
					System.out
							.println("There is a maximum of six players foo! Enter again!\n");
					throw new IllegalArgumentException();
				} else if (this.numPlayers < this.minPlayers) {
					System.out
							.println("There is a minimum of three players foo! Enter again!\n");
					throw new IllegalArgumentException();
				}
				System.out.println("Cool!");
				break;
			} catch (IllegalArgumentException e) {

			}
		}

		// make the human players
		for (int i = 0; i < this.numPlayers; i++) {
			Player p = new Player();
			this.listOfPlayer.add(p);
		}

	}

	public Dice getDice() {
		return dice;
	}

	public Envelope getEnv() {
		return env;
	}

	public Tile[][] getGrid() {
		return grid;
	}

	public ArrayList<Player> getListOfPlayer() {
		return listOfPlayer;
	}

	public ArrayList<WeaponCard> getListOfWeaponCard() {
		return listOfWeaponCard;
	}

	public ArrayList<LocationCard> getListOfLocationCard() {
		return listOfLocationCard;
	}

	public ArrayList<SuspectCard> getListOfSuspectCard() {
		return listOfSuspectCard;
	}
	
	public HashMap<Coordinates,Room> getCoordinatesToRoom(){
		return this.roomEntrances;
	}
	
	public ArrayList<Coordinates> getListOfCoordinates(){
		return this.listOfCoordinates;
	}
	
	public Game getGame(){
		return this.game;
	}

	public HashMap<String, Room> getRooms() {
		return rooms;
	}
}
