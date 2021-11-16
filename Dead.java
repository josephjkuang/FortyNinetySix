import java.awt.image.BufferedImage;
import java.awt.Image;

public class Dead extends Number { // Uncrossable Spots on the Board

	private int number;
	private Image image;
	private boolean combined;

	public Dead() {
		number = -1;
		image = null;
		combined = false;
	}

	public Dead(int number, Image image, boolean combined) {
		super(number, image, combined);
	}

	public String toString() {
		return "X";
	}
}