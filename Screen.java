package automaton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
//import java.awt.MouseInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Screen extends JFrame implements Runnable {

	/** fields */
	
	//pixel width of the buffer zone on each side
	int bufferZone = 100;
	
	
	private Cells cells;
	private boolean pause = true;

	// variable for hiding the cursor
	boolean hideCursor = false;

	// variable for painting the screen
	// boolean clicking = false;

	int footer = 50;
	
	
	int mouseX;
	int mouseY;

	int blockX;
	int blockY;

	// screen dimensions
	static int screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
	static int screenY = Toolkit.getDefaultToolkit().getScreenSize().height;

	// graphics double buffering
	private Image dbImage;
	private Graphics dbg;

	// thread delay
	long threadDelay = 100; // updates the thread 10 times per second

	// main constructor and window
	public Screen() {

		cells = new Cells((screenX - 10) / 12 + 2 * bufferZone, (screenY - 10) / 12 + 2 * bufferZone);
		// cells = new Cells();

		// adds key listener
		addKeyListener(new AL());
		// adds mouse listener
		addMouseListener(new Mouse());

		// defines the properties of the JFrame

		this.setSize(screenX, screenY);
		this.setTitle("Cellular Automaton: The Game of Life");
		this.setVisible(true);
		this.setResizable(false);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// the thread
	public void run() {

		try {

			while (true) {

				while (!pause) {
					cells.update();

					// pauses either when the screen is naturally or forcefully
					// cleared
					if (cells.isEmpty()) {
						pause = true;
					}
					Thread.sleep(threadDelay);
				}
				Thread.sleep(10);
			}
		} catch (Exception e) {
			System.out.println("Error in thread\n" + e.toString());
			System.exit(0);
		}

	}

	// key input
	public class AL extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == e.VK_Q) {
				System.exit(0);
			}
			if (key == e.VK_R) {
				Cells newCells = new Cells(cells.cells.length, cells.cells[0].length);
				cells = newCells;
			}
			if (key == e.VK_SPACE) {
				if (pause == true) {
					pause = false;
				} else {
					pause = true;
				}
			}
			if (key == e.VK_UP) {
				if (blockY > bufferZone) {
					blockY--;
				}
			}
			if (key == e.VK_DOWN) {
				if (blockY < cells.cells[0].length - 1 - bufferZone) {
					blockY++;
				}
			}
			if (key == e.VK_LEFT) {
				if (blockX > bufferZone) {
					blockX--;
				}
			}
			if (key == e.VK_RIGHT) {
				if (blockX < cells.cells.length - 1 - bufferZone) {
					blockX++;
				}
			}
			if (key == e.VK_Z) {
				if (cells.cells[blockX][blockY].isPopulated()) {
					cells.cells[blockX ][blockY].dePopulate();
				} else {
					cells.cells[blockX][blockY].populate();
				}
			}
			if (key == e.VK_EQUALS) {
				if (threadDelay < 1000) {
					threadDelay *= 10;
					// System.out.println(threadDelay);
				}
			}
			if (key == e.VK_MINUS) {
				if (threadDelay > 1) {
					threadDelay /= 10;
				}
			}
			if (key == e.VK_H) {
				if (hideCursor) {
					hideCursor = false;
				} else {
					hideCursor = true;
				}
			}
			// System.out.println(pause);

		}

		public void keyReleased(KeyEvent e) {

			int key = e.getKeyCode();

		}

	}

	// mouse input
	public class Mouse extends MouseAdapter {

		public void mousePressed(MouseEvent e) {

			mouseX = e.getX();
			mouseY = e.getY();

			// System.out.println("x: " + mouseX + " y: " + mouseY);
			draw();

		}

		public void mouseReleased(MouseEvent e) {

		}

	}

	// draws on the selected point
	public void draw() {

//		System.out.println("mouseX = " + mouseX + " mouseY = " + mouseY);
		
		blockX = (mouseX - 1) / 10 + bufferZone;
		blockY = (mouseY - 28) / 10 + bufferZone;

		if (blockX < bufferZone) {
			blockX = bufferZone;
		}
		if (blockY < bufferZone) {
			blockY = bufferZone;
		}

		if (blockX >= cells.cells.length - bufferZone) {
			blockX = cells.cells.length - 1 - bufferZone;
		}

		if (blockY >= cells.cells[0].length - bufferZone) {
			blockY = cells.cells[0].length - 1 - bufferZone;
		}

		// System.out.println("blockX : " + blockX + " blockY: " + blockY);

		if (cells.cells[blockX][blockY].isPopulated()) {
			cells.cells[blockX][blockY].dePopulate();
		} else {
			cells.cells[blockX][blockY].populate();
		}

	}

	// paints stuff on the screen
	public void paintComponent(Graphics g) {

		// paints the cells
		for (int r = 0; r < (screenX - 10) / 12; r++) {
			for (int c = 0; c < (screenY - 10) / 12; c++) {

				if (cells.cells[r + bufferZone][c + bufferZone].isPopulated()) {
					g.fillRect(r * 10, c * 10 + 25, 10, 10);
				}
				// unpopulated squares are hollow squares
				g.drawRect(r * 10, c * 10 + 25, 10, 10);
				
				g.setColor(Color.BLACK);
			}
		}

		// shows cursor
		if (!hideCursor) {
			g.setColor(Color.BLUE);
			g.drawRect((blockX - bufferZone) * 10 - 1, (blockY - bufferZone) * 10 - 1 + 25, 12, 12);
			g.drawRect((blockX - bufferZone) * 10 - 2, (blockY - bufferZone) * 10 - 2 + 25, 14, 14);
		}
		g.setColor(Color.BLACK);

		String stringpause;
		if (pause) {
			stringpause = "Resume";
		} else {
			stringpause = "Pause";
		}

		g.drawString(stringpause, 50, screenY - footer);

		g.drawString("Neighbors: " + cells.getNeighbors(blockX, blockY), 200, screenY - footer);

		// prints out the current block x and y
		g.drawString("X: " + blockX + " Y: " + blockY, screenX - 200, screenY - footer);

		g.drawString("Delay: " + threadDelay, 300, screenY - footer);

		repaint();
	}

	// enables visual double buffering
	public void paint(Graphics g) {

		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);

	}

}
