package automaton;
public class Cell {

	private boolean isPopulated;
	
	public Cell() {
		this.isPopulated = false;
	}
	
	public boolean isPopulated() {
		return this.isPopulated;
	}
	
	public void populate() {
		this.isPopulated = true;
	}
	
	public void dePopulate() {
		this.isPopulated = false;
	}
	
}
