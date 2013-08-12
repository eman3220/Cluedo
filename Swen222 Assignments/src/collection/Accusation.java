package collection;

import card.LocationCard;
import card.SuspectCard;
import card.WeaponCard;

public class Accusation {
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

	public Accusation(LocationCard lc, WeaponCard wc, SuspectCard sc){
		this.suspect = sc;
		this.location = lc;
		this.weapon = wc;	
	}
	
	public boolean match(Envelope env){
		if(this.suspect.getData()==env.getSuspect().getData()){
			if(this.location.getData()==env.getLocation().getData()){
				if(this.weapon.getData()==env.getWeapon().getData()){
					return true;
				}
			}
		}
		return false;
	}
}
