import java.awt.image.BufferedImage;
import java.awt.Image;

public class Number {
	
	private int number;
	private Image image;
	private boolean combined;

	public Number() {
		number = 0;
		image = null;
		combined = false;
	}

	public Number(int number, Image image, boolean combined) {
		this.number = number;
		this.image = image;
		this.combined = combined;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public String toString() {
		return "" + number;
	}

	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	public boolean getCombined() {
		return combined;
	}

	public int movement(int x, int y, int direction, int[][] new_board) {
		int movement = 0;
		if (direction == 0) { // Up Movement
			movement = x;
			for(int i = x - 1; i >= 0; i--) {
				if(y == 4) {
					break;
				}
				if(new_board[i][y] != 0) {
					break;
				}
				movement = i;
			}
		}

		 else if (direction == 1) { // Right Movement
			movement = y;
			for(int i = y + 1; i <= 8; i++) {
				if(i == 4 && x != 1) {
					break;
				}
				if(new_board[x][i] != 0) {
					break;
				}
				movement = i;
			}
		}

		else if (direction == 2) { // Down Movement
			movement = x;
			for(int i = x + 1; i <= 3; i++) {
				if(y == 4) {
					break;
				}
				if(new_board[i][y] != 0) {
					break;
				}
				movement = i;
			}
		}

		else { // Left Movement
			movement = y;
			for(int i = y - 1; i >= 0; i--) {
				if(i == 4 && x != 1) {
					break;
				}
				if(new_board[x][i] != 0) {
					break;
				}
				movement = i;
			}
		}
		
		return movement;
	}
}