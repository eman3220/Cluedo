package card;

import enums.Location;

public class LocationCard extends Card{
	Location data;
	
	public Location getData() {
	return data;
	}

	public void setData(Location data) {
	this.data = data;
	}

	public LocationCard (Location l){
		data = l;
		super.setName(data.name());
		super.setMurder(false);
	}

}
