package player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import map.Animation;
import map.TileMap;
import types.AnimationType;

public class Attack{

	private String attackPath = AnimationType.ATTACK.getPath();
	
	private double x, y;
	// width and height always the same;
	private int width, damage;
	private double dx, dy, angle, speed;
	private boolean topLeft, topRight, bottomLeft, bottomRight;
	private boolean destroy;
	
	private TileMap map;
	private Animation animation;

	public Attack(double angle, double x, double y, int damage, TileMap map) {
		this.angle = Math.toRadians(angle);
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.destroy = false;
		this.map = map;
		this.width = 15;
		BufferedImage attackImages[] = new BufferedImage[AnimationType.ATTACK.getFrames()];
		BufferedImage subImage = null;
		try {
			subImage = ImageIO.read(new File(attackPath));
			for (int i = 0; i < attackImages.length; i++) {
				attackImages[i] = subImage.getSubimage(i * width, 0, width, width);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(attackImages);
		this.speed = 15;
		this.dx = Math.cos(this.angle) * speed;
		this.dy = Math.sin(this.angle) * speed;
	}

	public boolean tick() {
		// collision
		int currentCol = map.getColumnTile((int) x);
		int currentRow = map.getRowTile((int) y);
		// were we want to end up
		double destinationX = x + dx;
		double destinationY = y + dy;
		// our temporary position
		double tempX = x;
		double tempY = y;
		animation.setDelay(200);
		animation.tick();
		collisionDetection(x, destinationY);
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				tempY = currentRow * map.getTileSize() + width / 2;
				return false;
			} else {
				tempY += dy;
			}
		}
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				tempY = (currentRow + 1) * map.getTileSize() - width / 2;
				return false;
			} else {
				tempY += dy;
			}
		}
		collisionDetection(destinationX, y);
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				tempX = currentCol * map.getTileSize() + width / 2;
				return false;
			} else {
				tempX += dx;
			}
		}
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				tempX = (currentCol + 1) * map.getTileSize() - width / 2;
				return false;
			} else {
				tempX += dx;
			}
		}
		// set current position
		x = tempX;
		y = tempY;
		return true;
	}

	private void collisionDetection(double x, double y) {
		int leftTile = map.getColumnTile((int) (x - width / 2));
		int rightTile = map.getColumnTile((int) (x + width / 2) - 1);
		int topTile = map.getRowTile((int) (y - width / 2));
		int bottomTile = map.getRowTile((int) (y + width / 2) - 1);
		topLeft = map.isBlocked(topTile, leftTile);
		topRight = map.isBlocked(topTile, rightTile);
		bottomLeft = map.isBlocked(bottomTile, leftTile);
		bottomRight = map.isBlocked(bottomTile, rightTile);
	}

	public void render(Graphics2D g) {
		int mapX = map.getX();
		int mapY = map.getY();
		g.drawImage(animation.getImage(), (int) (x + mapX - width / 2), (int) (y + mapY - width / 2), null);
	}
	
	public int getDamage(){
		return this.damage;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void setDestroyed(){
		this.destroy = true;
	}
	
	public boolean isDestroeyd(){
		return this.destroy;
	}
}
