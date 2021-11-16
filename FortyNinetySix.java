import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyAdapter.*;
import java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.applet.Applet;
import java.lang.Math;

public class FortyNinetySix extends JPanel implements KeyListener, ActionListener {

	private static Number[][] board;
	public static JFrame frame = new JFrame("Joseph's 4096 Game");
	private static final String imagePath = "./Images/"; 
	private static JButton button;
	private static Image[] images;
	private static int scoreboard;
	private static boolean game_over;
	private static boolean moved;
	private static boolean reset;

	public FortyNinetySix() { // Default Constructor where Key Listener is added
		frame.repaint();
		frame.addKeyListener(this);
		scoreboard = 0;
		game_over = false;
		moved = false;
		reset = false;
	}

	public static void main(String[] args) { // Main Method that Starts the Game
		images = new Image[15]; 
		images[1] = loadImage("2.png");
		images[2] = loadImage("4.png");
		images[3] = loadImage("8.png");
		images[4] = loadImage("16.png");
		images[5] = loadImage("32.png");
		images[6] = loadImage("64.png");
		images[7] = loadImage("128.png");
		images[8] = loadImage("256.png");
		images[9] = loadImage("512.png");
		images[10] = loadImage("1024.png");
		images[11] = loadImage("2048.png");
		images[12] = loadImage("4096.png");
		images[13] = loadImage("8192.png");
		images[14] = loadImage("16384.png");
		images[0] = loadImage("32768.png");

		createNewGame();
		spawn();
		System.out.println("\n");
		printBoard();
	}

	public static void createNewGame() { // Sets the Board with Empty and Barracaded Spaces
		board = new Number [4][9]; // Two 2048 Boards with a Middle Isle to Connect the Two Boards
		Number empty = new Number(0, null, false);
		Number dead = new Dead(-1, null, false);

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				board[i][j] = empty;
			}
			for(int j2 = 5; j2 < 9; j2++) {
				board[i][j2] = empty;
			}
		}

		board[0][4] = dead;
		board[1][4] = empty;
		board[2][4] = dead;
		board[3][4] = dead;

		frame.setSize(1300, 600);
		frame.setVisible(true);
		// frame.setBackground(Color.WHITE);
		frame.setBackground( new Color(255, 236, 207) );

		if(reset == false) {
			FortyNinetySix gui = new FortyNinetySix();
			frame.add(gui);
			frame.getContentPane().add(gui);
		}
	}

	public static void printBoard() { // Prints Board into Terminal
		for(int r = 0; r < board.length; ++r) {
			for(int c = 0; c < board[0].length; ++c) {
				if(board[r][c] == null) {
					System.out.print(". ");
				}
				else
					System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	Timer tm = new Timer(5, this);
	int x = 9, velX = 2;

	public void actionPerformed(ActionEvent e) {
		x = x + velX;
		frame.repaint();
	}

	@Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) { // Method that determines which way you want to move and changes the board for that movement
    	int direction = -1;
    	int movement = -1;
    	int count = 0;
    	moved = false;
    	Number empty = new Number(0, null, false);
    	boolean combine = false;

        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) { // Moving Right
        	System.out.println("Right Key Pressed");
            direction = 1;

            for(int i = 0; i < 4; i ++) { // Combining Tiles
            	for(int j = 8; j >= 0; j--) {
            		combinable(i, j, direction);
            	}
            }
            notcombined(); // Resets Combined Value

            while(count != 8) { // Moving Tiles
            	count++;
	            for(int i = 0; i < 4; i++) {
	            	for(int j = 8; j >= 0; j--) {
	            		if(board[i][j].getNumber() > 0) {
	            			movement = move(i, j, direction);
	            			board[i][movement] = board[i][j]; 
	            			if(j != movement) { // Deleting Tiles
	            				board[i][j] = empty;
	            				moved = true;
	            				movement = -1;
	            			}
	            		}
	            	}
	            }
	        }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) { // Moving Left
        	System.out.println("Left Key Pressed");
            direction = 3;

			for(int i = 0; i < 4; i++) { // Combining Tiles
	           	for(int j = 0; j < 9; j++) {
	           		combinable(i, j, direction);
           		}
           	}
           	notcombined(); // Resets Combined Value

            while(count != 8) { // Moving Tiles
            	count++;
	            for(int i = 0; i < 4; i++) {
	            	for(int j = 0; j < 9; j++) {
	            		if(board[i][j].getNumber() > 0) {
	            			movement = move(i, j, direction);
	            			board[i][movement] = board[i][j];
	            			if(j != movement) { // Deleting Tiles
	            				board[i][j] = empty;
	            				moved = true;
	            				movement = -1;
	            			}
	            		}
	            	}
	            }
	        }
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) { // Moving Up
        	System.out.println("Up Key Pressed");
            direction = 0;

            for(int i = 0; i < 4; i++) { // Combining Tiles
            	for(int j = 0; j < 9; j++) {
            		combinable(i, j, direction);
            	}
            }
			notcombined(); // Resets Combined Value

            while(count != 3) { // Moving Tiles
            	count ++;
	            for(int i = 3; i >= 0; i--) {
	            	for(int j = 0; j < 9; j++) {
	            		if(board[i][j].getNumber() > 0) {
	            			movement = move(i, j, direction);
	            			board[movement][j] = board[i][j];
	            			if(i != movement) { // Deleting Tiles
	            				board[i][j] = empty;
	            				moved = true;
	            			}
	            		}
	            	}
	            }
	        }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) { // Moving Down
        	System.out.println("Down Key Pressed");
            direction = 2;

            for(int i = 3; i >= 0; i--) { // Combining Tiles
            	for(int j = 0; j < 9; j++) {
            		combinable(i, j, direction);
            	}
            }
            notcombined(); // Resets Combined Value

            while(count != 3) { // Moving Tiles
            	count++;
	            for(int i = 0; i < 4; i++) {
	            	for(int j = 0; j < 9; j++) {
	            		if(board[i][j].getNumber() > 0) {
	            			movement = move(i, j, direction);
	            			board[movement][j] = board[i][j];
	            			if(i != movement) { // Deleting Tiles
	            				board[i][j] = empty;
	            				moved = true;
	            				movement = -1;
	            			}
	            		}
	            	}
	            }
	        }
        }

        if(e.getKeyCode() == 81) { // Hit Q on keyboard
        	frame.dispose(); // Closes JFrame
        }

        if(e.getKeyCode() == 82) { // Hit R on keyboard
        	endgame();
        }

        frame.repaint();
        if(moved) { // If the keystroke has no action on the tiles
        	System.out.println("The Score is now " + scoreboard);
       		spawn();
       		moved = false;
       	}
    }

	public void paintComponent(Graphics g) {
		Graphics2D gui = (Graphics2D) g;

		gui.setColor(Color.LIGHT_GRAY); // Board
		gui.fillRect(200, 200, 900, 400); 

		gui.setColor(Color.DARK_GRAY); // Dividing Lines
		for(int r = 200; r <= 1100; r = r + 100) {
			gui.fillRect(r, 200, 10, 400);
		}
		for(int c = 200; c <= 600; c = c + 100) {
			gui.fillRect(200, c, 910, 10);
		}

		gui.setColor(Color.BLACK); // Barracades
		gui.fillRect(610, 210, 90, 90);
		gui.fillRect(610, 410, 90, 90);
		gui.fillRect(610, 510, 90, 90);

		for(int r = 0; r < 4; r++ ) { // Blocks
			for(int c = 0; c < 9; c++) {
				gui.drawImage(board[r][c].getImage(), 210 + c*100, 210 + r*100, 90, 90, null);
			}
		}
		gui.drawImage(loadImage("shortcuts.png"), 220, 40, 200, 140, null);
		gui.drawImage(loadImage("logo.png"), 480, 40, 340, 140, null);
		gui.drawImage(loadImage("arrow.png"), 880, 40, 200, 140, null);
		gui.drawImage(loadImage("directions.png"), 200, 620, 600, 75, null);

		Image black = loadImage("black.png"); // Purpose is to cover the game over logo after hitting restart
		gui.drawImage(black, 610, 210, 90, 90, null);
		gui.drawImage(black, 610, 410, 90, 90, null);
		gui.drawImage(black, 610, 510, 90, 90, null);

		if(game_over) {
			System.out.println("\n Game Over \n YOU LOST!!! \n");
			Image gameover = loadImage("gameover.png");
			gui.drawImage(gameover, 610, 210, 90, 90, null);
			gui.drawImage(gameover, 610, 410, 90, 90, null);
			gui.drawImage(gameover, 610, 510, 90, 90, null);
		}
		// tm.start();
	}

	private static Image loadImage(String filename) { // Loads images into frame
		Image tempImage = null;
		if (filename != null && filename != "") {
			try {
				tempImage = Toolkit.getDefaultToolkit().getImage(imagePath + filename);
			}
			catch (Exception e) {
				System.out.println("error getImage with toolkit unable to load filename " + filename);
			}
		}
		return tempImage;
	}

	public static int count() { // Counts the numver of open spaces
		int count = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 9; j++) {
				if(board[i][j].getNumber() == 0){
					count++;
				}
			}
		}
		return count;
	}

	public static void spawn() { // Spawns tiles after a move
		int count = count();

		if(count != 1) { // If there are two or more open space
			while(true) {
				int number = (int)(Math.random() * 36); // Random Space on the Board
				int i = number / 9;
				int j = number % 9;

				if(board[i][j].getNumber() == 0) { // If the space is open
					int tile = tile();
					if(tile == 2) {
						Number two = new Number(2, images[1], false);
						board[i][j] = two;
					}
					if(tile == 4) {
						Number four = new Number(4, images[2], false);
						board[i][j] = four;
					}
					if(tile == 8) {
						Number eight = new Number(8, images[3], false);
						board[i][j] = eight;
					}
					break;
				}
			}
		}

		while(true) { // If there are one or more spaces
			int number = (int)(Math.random() * 36);
			int i = number / 9;
			int j = number % 9;
			if(board[i][j].getNumber() == 0) {
				int tile = tile();
				if(tile == 2) {
					Number two = new Number(2, images[1], false);
					board[i][j] = two;
				}
				if(tile == 4) {
					Number four = new Number(4, images[2], false);
					board[i][j] = four;
				}
				if(tile == 8) {
					Number eight = new Number(8, images[3], false);
					board[i][j] = eight;
				}
				break;
			}
		}

		count = count();
		if(count == 0) { // Checks to see if any moves are remaining
			game_over = true;
			for(int i = 0; i < 3; i ++) {
				for(int j = 0; j < 8; j++) {
					if(board[i][j].getNumber() == board[i+1][j].getNumber()) {
						if(board[i][j].getNumber() != -1) {
							game_over = false;
						}
					}
					if(board[i][j].getNumber() == board[i][j+1].getNumber()) {
						game_over = false;
					}
				}
			}
			for(int r = 0; r < 3; r++) {
				if(board[r][8].getNumber() == board[r+1][8].getNumber()) {
					game_over = false;
				}
			}
			for(int c = 0; c < 8; c++) {
				if(board[3][c].getNumber() == board[3][c+1].getNumber()) {
					game_over = false;
				}
			}
		}

		frame.repaint(); // Changing the frame for new pieces
	}

	public static int tile() { // Chooses to spawn a 2, 4, or 8 tile
		int number = (int)(Math.random() * 100); // Randomly chooses a number 0-99
		if(number == 0) { 
			return 8;
		}
		if(number <= 10) {
			return 4;
		}
		else {
			return 2;
		}
	}

	public static int move(int x, int y, int direction) { // Determines where the blocks will move
		int[][] new_board = new int[4][9]; // Creating a board of ints rather than Numbers
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				new_board[i][j] = board[i][j].getNumber();
			}
		}
		int movement = board[x][y].movement(x, y, direction, new_board); // Calling Number's movement method
		return movement;
	}

	public static void combinable(int x, int y, int direction) { // Determining if two tiles will combine
		int[][] new_board = new int[4][9]; // Creating a board of ints rather than Numbers
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				new_board[i][j] = board[i][j].getNumber();
			}
		}

		if (direction == 2) { // Down Movement
			for(int i = x - 1; i >= 0; i--) {
				if(y == 4) {
					break;
				}
				if(new_board[i][y] == new_board[x][y]) {
					combine(x, y, i, y);
					moved = true;
					break;
				}
				if(new_board[i][y] != 0) {
					break;
				}
			}
		}

		 else if (direction == 3) { // Left Movement
			for(int i = y + 1; i <= 8; i++) {
				if(i == 4 && x != 1) {
					break;
				}
				if(new_board[x][i] == new_board[x][y]) {
					combine(x, y, x, i);
					moved = true;
					break;
				}
				if(new_board[x][i] != 0) {
					break;
				}
			}
		}

		else if (direction == 0) { // Up Movement
			for(int i = x + 1; i <= 3; i++) {
				if(y == 4) {
					break;
				}
				if(new_board[i][y] == new_board[x][y]) {
					combine(x, y, i, y);
					moved = true;
					break;
				}
				if(new_board[i][y] != 0) {
					break;
				}
			}
		}

		else { // Right Movement
			for(int i = y - 1; i >= 0; i--) {
				if(i == 4 && x != 1) {
					break;
				}
				if(new_board[x][i] == new_board[x][y]) {
					combine(x, y, x, i);
					moved = true;
					break;
				}
				if(new_board[x][i] != 0) {
					break;
				}
			}
		}
	}

	public static void combine(int x1, int y1, int x2, int y2) { // Combines two tiles
		if(board[x1][y1].getCombined() == false && board[x2][y2].getCombined() == false) {
			Number empty = new Number(0, null, false);
			board[x1][y1] = empty;

			int number = board[x2][y2].getNumber() * 2;
			board[x2][y2].setCombined(true);

			if(number == 4) {
				board[x2][y2].setNumber(4);
				board[x2][y2].setImage(images[2]);
				scoreboard = scoreboard + 4;
			}
			if(number == 8) {
				board[x2][y2].setNumber(8);
				board[x2][y2].setImage(images[3]);
				scoreboard = scoreboard + 8;
			}
			if(number == 16) {
				board[x2][y2].setNumber(16);
				board[x2][y2].setImage(images[4]);
				scoreboard = scoreboard + 16;
			}
			if(number == 32) {
				board[x2][y2].setNumber(32);
				board[x2][y2].setImage(images[5]);
				scoreboard = scoreboard + 32;
			}
			if(number == 64) {
				board[x2][y2].setNumber(64);
				board[x2][y2].setImage(images[6]);
				scoreboard = scoreboard + 64;
			}
			if(number == 128) {
				board[x2][y2].setNumber(128);
				board[x2][y2].setImage(images[7]);
				scoreboard = scoreboard + 128;
			}
			if(number == 256) {
				board[x2][y2].setNumber(256);
				board[x2][y2].setImage(images[8]);
				scoreboard = scoreboard + 256;
			}
			if(number == 512) {
				board[x2][y2].setNumber(512);
				board[x2][y2].setImage(images[9]);
				scoreboard = scoreboard + 512;
			}
			if(number == 1024) {
				board[x2][y2].setNumber(1024);
				board[x2][y2].setImage(images[10]);
				scoreboard = scoreboard + 1024;
			}
			if(number == 2048) {
				board[x2][y2].setNumber(2048);
				board[x2][y2].setImage(images[11]); 
				scoreboard = scoreboard + 2048;
			}
			if(number == 4096) {
				board[x2][y2].setNumber(4096);
				board[x2][y2].setImage(images[12]);
				scoreboard = scoreboard + 4096;
			}
			if(number == 8192) {
				board[x2][y2].setNumber(8192);
				board[x2][y2].setImage(images[13]);
				scoreboard = scoreboard + 8192;
			}
			if(number == 16384) {
				board[x2][y2].setNumber(16384);
				board[x2][y2].setImage(images[14]);
				scoreboard = scoreboard + 16384;
			}
			if(number == 32768) {
				board[x2][y2].setNumber(32768);
				board[x2][y2].setImage(images[0]);
				scoreboard = scoreboard + 32768;
			}
			frame.repaint();
		}
	}

	private static void notcombined() {  // Changes combined attribute to false
		for(int i = 0; i < 4; i ++ ) {
	    	for(int j = 0; j < 9; j++) {
	    		board[i][j].setCombined(false);
	    	}
	    }
	}

	public static void endgame() { // Ends the game if no open spaces left
		reset = true;
		createNewGame();
		game_over = false;
		frame.repaint();
		spawn();
		System.out.println();
		System.out.println("You have started a new game. GOOD LUCK!");
		System.out.println();
		printBoard();
	}
}