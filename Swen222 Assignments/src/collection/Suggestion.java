package collection;

import card.LocationCard;
import card.SuspectCard;
import card.WeaponCard;

public class Suggestion {
	private SuspectCard suspect;
	private LocationCard location;
	private WeaponCard weapon;
	private boolean suspectRefuted = false;
	private boolean locationRefuted = false;
	private boolean weaponRefuted = false;
	
	public SuspectCard getSuspect() {
		return suspect;
	}

	public LocationCard getLocation() {
		return location;
	}

	public WeaponCard getWeapon() {
		return weapon;
	}
	
	public boolean isLocationRefuted() {
		return locationRefuted;
	}

	public void setLocationRefuted(boolean locationRefuted) {
		this.locationRefuted = locationRefuted;
	}

	public boolean isWeaponRefuted() {
		return weaponRefuted;
	}

	public void setWeaponRefuted(boolean weaponRefuted) {
		this.weaponRefuted = weaponRefuted;
	}

	public boolean isSuspectRefuted(){
		return this.suspectRefuted;
	}
	
	public void setSuspectRefuted(boolean suspectRefuted) {
		this.suspectRefuted = suspectRefuted;
	}
	

	public Suggestion(LocationCard lc, WeaponCard wc, SuspectCard sc){
		this.location = lc;
		this.weapon = wc;
		this.suspect = sc;		
	}
}
