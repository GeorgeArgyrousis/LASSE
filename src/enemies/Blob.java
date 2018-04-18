package enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import map.Animation;
import player.Player;
import types.AnimationType;

public class Blob extends Enemy {

	private String walkingPath = AnimationType.BLOB.getPath();

	private BufferedImage walkingSprite[];
	
	public Blob(Player player) {
		super(player);
		width = 19;
		height = 32;
		moveSpeed = 0.5;
		maxSpeed = 2.5;
		maxFallingSpeed = 12.0;
		health = 5;
		maxHealth = 5;
		damage = 5;
		stopSpeed = 0.8;
		jumpStart = -9.0;
		walkingSprite = new BufferedImage[AnimationType.BLOB.getFrames()];
		super.loadImages(walkingPath, walkingSprite);
		animation = new Animation();
		//TODO :: normally, it should be idle
		animation.setFrames(walkingSprite);
	}
	
	@Override
	public void animateEnemy(){
		if (left || right) {
			animation.setFrames(walkingSprite);
			animation.setDelay(60);
		}
		animation.tick();
	}

	public void render(Graphics2D g) {
		int mapX = map.getX();
		int mapY = map.getY();
		if (left) {
			g.drawImage(animation.getImage(), (int) (mapX + x - width / 2 + width), (int) (mapY + y - height / 2), -width, height, null);
		} else {
			g.drawImage(animation.getImage(), (int) (mapX + x - width / 2), (int) (mapY + y - height / 2), null);
		}
		g.setColor(Color.red);
		g.drawRect((int) (mapX + x - width / 2), (int) (mapY + y - height / 2), width, height);
	}
}
