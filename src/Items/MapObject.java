package Items;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import map.Animation;
import map.TileMap;
import player.Player;

public abstract class MapObject {

	protected int x, y;
	protected int playerWidth, playerHeight;
	protected int tileSize;
	protected boolean destroy;
	
	private BufferedImage image;
	private Animation animation;
	private BufferedImage images[];
	
	protected TileMap map;
	protected Player player;

	/** A map object needs to have some specific attributes */
	public MapObject(TileMap map, Player player, int x, int y) {
		this.map = map;
		this.player = player;
		this.x = x;
		this.y = y;
		destroy = false;
		playerWidth = player.getWidth();
		playerHeight = player.getHeight();
		this.tileSize = map.getTileSize();
	}

	public void loadTile(String path) {
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(){
		if(image == null){
			return null;
		}
		return image;
	}

	public void loadTiles(String path, int frames, int delay) {
		images = new BufferedImage[frames];
		BufferedImage subImage;
		try {
			subImage = ImageIO.read(new File(path));
			for (int i = 0; i < images.length; i++) {
				// i * tileSize + i if there is a pixel in between; also remove
				// the -8, -4
				images[i] = subImage.getSubimage(i * tileSize, 0, tileSize - 8, tileSize - 4);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// when using animation, you need all the methods of the class;
		animation = new Animation();
		animation.setFrames(images);
		animation.setDelay(delay);
	}

	public boolean intersects() {
		int mapX = map.getX();
		int mapY = map.getY();
		Boolean left = player.getX() + mapX - playerWidth / 2 >= mapX + x * tileSize - playerWidth;
		Boolean right = player.getX() + mapX - playerWidth / 2 <= mapX + x * tileSize + playerWidth;
		Boolean top = player.getY() + mapY - playerHeight / 2 >= mapY + y * tileSize - playerHeight;
		Boolean bottom = player.getY() + mapY - playerHeight / 2 <= mapY + y * tileSize + playerHeight;
		if (left && right && top && bottom) {
			return true;
		}
		return false;
	}

	public void tick() {
		if (animation != null) {
			animation.tick();
		}
	}

	public void render(Graphics2D g) {
		if (image != null && intersects()) {
			//480x480
			g.drawImage(getImage(), 1, 329, 478, 150, null);
		}
		if (animation != null) {
			g.drawImage(animation.getImage(), map.getX() + x * tileSize, map.getY() + y * tileSize, tileSize, tileSize, null);
		}
	}
	
	public void setDestroy(){
		this.destroy = true;
	}
	
	public boolean isDestroyed(){
		return this.destroy;
	}
}
