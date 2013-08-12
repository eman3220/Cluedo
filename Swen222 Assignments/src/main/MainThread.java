package main;

public class MainThread extends Thread{
	private Cluedo host;
	
	public MainThread(){
		
	}
	
	public void run(){
		try {
			host = new Cluedo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pause(int i){
		try {
			Thread.currentThread().sleep(i);
		} catch (InterruptedException e) {
			
		}
	}
	
	public static void main(String [] args){
		new MainThread().start();
	}
}
