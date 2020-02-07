package automaton;

public class Cells {
	
	public Cell[][] cells;
	public Cell[][] temp;
	
	public Cells() {
		
		cells = new Cell[10][10];
		temp = new Cell[10][10];
		fillBlank();
		
	}
	
	public Cells(int x, int y) {
		
		cells = new Cell[x][y];
		temp = new Cell[x][y];
		fillBlank();
		
	}
	
	private void fillBlank() {
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
//				System.out.println(r + " " + c);
				Cell cell = new Cell();
				Cell cell2 = new Cell();
				cell.dePopulate();
				cell.dePopulate();
				cells[r][c] = cell;
				temp[r][c] = cell2;
			}
		}
	}
	
	//returns true if the cells 2d array is empty
	public boolean isEmpty() {
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				if (cells[r][c].isPopulated()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void update() {
		
		//updates temp to be equivalent to the cells list
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				if (cells[r][c].isPopulated()) {
					temp[r][c].populate();
				}
			}
		}
		
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				
//				long timer = 0;
//				while (timer < 10000000) {
//					timer ++;
//				}
				
				int neighbors = getNeighbors(r, c);
				if ((neighbors < 2 || neighbors > 3) && cells[r][c].isPopulated()) {
//					System.out.println("Block removed at r: " + r + " c: " + c);
//					System.out.println(neighbors);
					temp[r][c].dePopulate();
				}
				if (neighbors == 3 && !cells[r][c].isPopulated()){
//					System.out.println("Block placed at r: " + r + " c: " + c);
//					System.out.println(neighbors);
					temp[r][c].populate();
				}
				
//				rule1(r, c);
//				rule2(r, c);
//				rule3(r, c);
//				rule4(r, c);
			}
		}
//		System.out.println(this);
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				if (temp[r][c].isPopulated()) {
					cells[r][c].populate();
				}
				else {
					cells[r][c].dePopulate();
				}
			}
		}
		
	}
	
	
	public int getNeighbors(int r, int c) {
		
		
		int neighbors = 0;
		
		if (r > 0 && c > 0 && cells[r - 1][c - 1].isPopulated()) {
			neighbors ++;
		}
		
		if (r > 0 && cells[r - 1][c].isPopulated()) {
			neighbors ++;
		}
		
		if (c > 0 && cells[r][c - 1].isPopulated()) {
			neighbors ++;
		}
		
		if (r < cells.length - 1 && c < cells[0].length - 1 && cells[r + 1][c + 1].isPopulated()) {
			neighbors ++;
		}
		
//		if (r == 81) {System.out.println("YEET");}
		
		if (r < cells.length - 1 && cells[r + 1][c].isPopulated()) {
			neighbors ++;
		}
		
		if (c < cells[0].length - 1 && cells[r][c + 1].isPopulated()) {
			neighbors ++;
		}
		
		if (c > 0 && r < cells.length - 1 && cells[r + 1][c - 1].isPopulated()) {
			neighbors ++;
		}
		
		if (r > 0 && c < cells[0].length - 1 && cells[r - 1][c + 1].isPopulated()) {
			neighbors ++;
		}
		/*
		}
//		try {
			
		//checks to the immediate right
		try {
			if (cells[r][c + 1].isPopulated()) {
				neighbors ++;
			}
		} catch(Exception e) {}
		
		//checks upper right
		try {
			if (cells[r - 1][c + 1].isPopulated()) {
				neighbors ++;
			}
		} catch(Exception e) {}
		
		//checks lower right
		try {
			if (cells[r + 1][c + 1].isPopulated()) {
				neighbors ++;
			}
		} catch(Exception e) {}
		
		//checks to the immediate left
		try {
			if (cells[r][c - 1].isPopulated()) {
				System.out.println("row" + r + " col" + c);
				neighbors ++;
			}
		} catch(Exception e) {}
		
		//checks upper left
		try {
			if (cells[r - 1][c - 1].isPopulated()) {
				neighbors ++;
			}
		} catch(Exception e) {}
		
		//checks lower left
		try {
			if (cells[r + 1][c - 1].isPopulated()) {
				neighbors ++;
			}
		} catch(Exception e) {}
		
		//checks immediately below
		try {
			if (cells[r + 1][c].isPopulated()) {
				neighbors ++;
			}
		} catch(Exception e) {}
		
		//checks immediately above
		try {
			if (cells[r - 1][c].isPopulated()) {
				neighbors ++;
			}
		} catch(Exception e) {}
		
		*/
		return neighbors;
		
	}
	
	public String toString() {
		
		String word = "";
		word += "\n\nCells\n";
		for (int r = 0; r < this.cells.length; r++) {
			
			for (int c = 0; c < this.cells[0].length; c++) {
				
				word += this.cells[c][r].isPopulated() + " ";
				if (this.cells[c][r].isPopulated()) {
					word += " ";
				}
				
			}
			word += "\n";
			
		}
		word += "\n\nTemp\n";
		for (int r = 0; r < this.temp.length; r++) {
			
			for (int c = 0; c < this.temp[0].length; c++) {
				
				word += this.temp[c][r].isPopulated() + " ";
				if (this.temp[c][r].isPopulated()) {
					word += " ";
				}
				
			}
			word += "\n";
			
		}
		
		return word;
		
	}
	
}
