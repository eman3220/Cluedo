package main;
public class Dice {
	public Dice() {

	}

	public int roll() {
		return (int)Math.ceil(Math.random()*12);
	}
}
