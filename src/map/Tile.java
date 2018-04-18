package map;
import java.awt.image.BufferedImage;

public class Tile {

	private boolean blocked;
	
	private BufferedImage image;

	/** Tile with an image and wheather it's blocked or not */
	public Tile(BufferedImage image, boolean blocked) {
		this.image = image;
		this.blocked = blocked;
	}

	public BufferedImage getImage() {
		return image;
	}

	public boolean isBlocked() {
		return blocked;
	}
}
