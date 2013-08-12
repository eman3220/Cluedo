package card;

import enums.Weapon;

public class WeaponCard extends Card {
	Weapon data;
	
	public Weapon getData() {
		return data;
	}

	public void setData(Weapon data) {
		this.data = data;
	}

	public WeaponCard (Weapon w){
		data = w;
		super.setName(data.name());
		super.setMurder(false);
	}

}
