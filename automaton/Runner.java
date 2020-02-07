package automaton;

public class Runner {
	
	public static void main(String[] args) {
		
		Screen screen = new Screen();
		Thread t1 = new Thread(screen);
		t1.start();
		
	}
	
}
