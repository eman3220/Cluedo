package collection;

import card.LocationCard;
import card.SuspectCard;
import card.WeaponCard;

public class Envelope {
	private SuspectCard suspect;
	private LocationCard location;
	private WeaponCard weapon;
	
	public SuspectCard getSuspect() {
		return suspect;
	}

	public LocationCard getLocation() {
		return location;
	}

	public WeaponCard getWeapon() {
		return weapon;
	}

	public Envelope(SuspectCard s, LocationCard l, WeaponCard w){
		this.suspect = s;
		this.location = l;
		this.weapon = w;		
	}
	

}
