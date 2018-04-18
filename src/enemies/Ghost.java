package enemies;

import java.awt.Color;
import java.awt.Graphics2D;

import player.Player;

public class Ghost extends Enemy {

	public Ghost(Player player) {
		super(player);
		width = height = 25;
		moveSpeed = 0.5;
		maxSpeed = 3;
		maxFallingSpeed = 2;
		damage = 5;
		stopSpeed = 0.8;
		jumpStart = 3.0;
		gravity = 0.10;
	}
	
	@Override
	public void moveEnemy(int random){
		if (random > 100 && random < 105) {
			super.randomMovement(1);
			jumpStart = 3.0;
		} else if (random > 40 && random < 45) {
			super.randomMovement(2);
			jumpStart = -3.0;
		} else if (random > 150) {
			super.randomMovement(3);
		}
	}

	public void render(Graphics2D g) {
		int mapX = map.getX();
		int mapY = map.getY();
		g.setColor(Color.PINK);
		g.fillRect((int) (mapX + x - width / 2), (int) (mapY + y - height / 2), width, height);
	}
}
