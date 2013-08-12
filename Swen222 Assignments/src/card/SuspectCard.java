package card;

import enums.Suspect;

public class SuspectCard extends Card {
	Suspect data;

	public Suspect getData() {
		return data;
	}

	public void setData(Suspect data) {
		this.data = data;
	}

	public SuspectCard(Suspect l) {
		data = l;
		super.setName(data.name());
		super.setMurder(false);
	}
}
