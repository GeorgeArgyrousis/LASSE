package player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import View.GamePanel;
import map.Animation;
import map.TileMap;
/**
 * Something that can be moved on the map,
 * either by the player or by the game itself.
 * 
 * @author georgeargyrousis
 *
 */
public abstract class Entity {

	protected double x, y;
	protected double dx, dy;
	protected int width, height;

	protected boolean left, right;
	protected boolean jumping, falling;

	protected double maxSpeed, moveSpeed;
	protected double maxFallingSpeed, stopSpeed;
	protected double jumpStart, gravity;

	protected boolean topLeft, topRight, bottomLeft, bottomRight, attacking, intersecting, dead;
	protected int health, maxHealth, damage, damageLength;
	
	protected TileMap map;
	protected Animation animation;

	public Entity() {
		this(0.65, false, false, false);
	}
	
	public Entity(double gravity, boolean dead, boolean attacking, boolean intersecting){
		this.gravity = gravity;
		this.dead = dead;
		this.attacking = attacking;
		this.intersecting = intersecting;
	}
	
	/** Load the images into their designated arrays */
	public void loadImages(String path, BufferedImage image[]) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
			for (int i = 0; i < image.length; i++) {
				//if(i + 1 == image.length) break;
				image[i] = img.getSubimage(i * width + i, 0, width, height);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		this.move();
		this.jump();
		this.fall();
		// collision
		int currentCol = map.getColumnTile((int) x);
		int currentRow = map.getRowTile((int) y);
		// were we want to end up
		double destinationX = x + dx;
		double destinationY = y + dy;
		// our temporary position
		double tempX = x;
		double tempY = y;
		collisionDetection(x, destinationY);
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				tempY = currentRow * map.getTileSize() + height / 2;
			} else {
				tempY += dy;
			}
		}
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				tempY = (currentRow + 1) * map.getTileSize() - height / 2;
			} else {
				tempY += dy;
			}
		}
		collisionDetection(destinationX, y);
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				tempX = currentCol * map.getTileSize() + width / 2;
			} else {
				tempX += dx;
			}
		}
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				tempX = (currentCol + 1) * map.getTileSize() - width / 2;
			} else {
				tempX += dx;
			}
		}
		if (!falling) {
			collisionDetection(x, y + 1);
			if (!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		// set current position
		x = tempX;
		y = tempY;
	}
	
	/** Move the Entity to the left or Right */
	private void move(){
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} else {
			// Stopping
			if (dx > 0) {
				// friction
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				// friction
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}
	}
	
	/** Make the entity jump */
	private void jump(){
		if (jumping) {
			dy = jumpStart;
			falling = true;
			jumping = false;
		}
	}
	
	/** Make the entity fall */
	private void fall(){
		if (falling) {
			dy += gravity;
			if (dy > maxFallingSpeed) {
				dy = maxFallingSpeed;
			}
		} else {
			// stopping
			dy = 0;
		}
	}

	/** Colide based on the dimensions of the map tiles */
	private void collisionDetection(double x, double y) {
		int leftTile = map.getColumnTile((int) (x - width / 2));
		int rightTile = map.getColumnTile((int) (x + width / 2) - 1);
		int topTile = map.getRowTile((int) (y - height / 2));
		int bottomTile = map.getRowTile((int) (y + height / 2) - 1);
		topLeft = map.isBlocked(topTile, leftTile);
		topRight = map.isBlocked(topTile, rightTile);
		bottomLeft = map.isBlocked(bottomTile, leftTile);
		bottomRight = map.isBlocked(bottomTile, rightTile);
	}
	
	public void moveMap(){
		map.setX((int) (GamePanel.WIDTH / 2 - x));
		map.setY((int) (GamePanel.HEIGHT / 2 - y));
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setJumping(boolean jumping) {
		if (!falling) {
			this.jumping = jumping;
		}
	}

	public boolean isIntersecting() {
		return intersecting;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public int getDamage() {
		return damage;
	}
	
	public void setDead(){
		this.dead = true;
	}

	public boolean isDead() {
		return dead;
	}

	public int getHealth() {
		return this.health;
	}

	public void setHealth(int health) {
		this.health = health;
		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
		if (health <= 0) {
			this.health = 0;
			dead = true;
		}
	}

	public void setMap(TileMap map) {
		this.map = map;
	}
}
