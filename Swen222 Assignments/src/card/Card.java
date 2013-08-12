package card;

public abstract class Card {
	
	private String name;
	private boolean isMurder;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isMurder() {
		return isMurder;
	}
	public void setMurder(boolean isMurder) {
		this.isMurder = isMurder;
	}
	
}
