package tests;
import java.util.Scanner;

import main.Dice;


import enums.Weapon;


public class TestDice {
	
	public TestDice(){
		Dice d = new Dice();
		test(d);
	}
	
	private void test(Dice d){
		System.out.println("Anything to roll, q to quit\n");
		while(true){
			Scanner s = new Scanner(System.in);
			if(s.next().equals("q")){
				System.exit(1);
			}
			
			System.out.println("Rolled: "+d.roll());
		}
	}
	
	public static void main(String[]args){
		new TestDice();
	}
}
