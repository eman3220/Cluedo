package main;

public class Coordinates {
	private int x;
	private int y;
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean matches(Coordinates other){
		if(this.x==other.getX() && this.getY()==other.getY()){
			return true;
		}
		return false;
	}
}
