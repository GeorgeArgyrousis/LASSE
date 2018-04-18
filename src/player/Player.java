package player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Items.Pill;
import map.Animation;
import types.AnimationType;

public class Player extends Entity {

	private String idlePath = AnimationType.PIDLE.getPath();
	private String walkingPath = AnimationType.PWALK.getPath();
	private String jumpingPath = AnimationType.PJUMP.getPath();
	private String airPath = AnimationType.PAIR.getPath();
	private String fallingPath = AnimationType.PFALL.getPath();
	private String attackingPath = AnimationType.PATTACK.getPath();

	private boolean facingLeft;

	private BufferedImage idleSprite[];
	private BufferedImage walkingSprite[];
	private BufferedImage jumpingSprite[];
	private BufferedImage airSprite[];
	private BufferedImage fallingSprite[];
	private BufferedImage attackingSprite[];

	private ArrayList<Pill> pills;
	private ArrayList<Attack> attacks;

	/**
	 * Construct the player with all respected fields;
	 */
	public Player() {
		super();
		//used to be 19, now it is 21
		width = 21;
		height = 32;
		// directional variables
		moveSpeed = 0.5;
		maxSpeed = 4.0;
		maxFallingSpeed = 12.0;
		// health system
		health = 15;
		maxHealth = 15;
		damage = 5;
		damageLength = 32;
		// friction
		stopSpeed = 0.3;
		// -12
		jumpStart = -12.0;
		facingLeft = false;
		idleSprite = new BufferedImage[AnimationType.PIDLE.getFrames()];
		walkingSprite = new BufferedImage[AnimationType.PWALK.getFrames()];
		jumpingSprite = new BufferedImage[AnimationType.PJUMP.getFrames()];
		airSprite = new BufferedImage[AnimationType.PAIR.getFrames()];
		fallingSprite = new BufferedImage[AnimationType.PFALL.getFrames()];
		attackingSprite = new BufferedImage[AnimationType.PATTACK.getFrames()];

		//loadImages(idlePath, idleSprite);
		loadImages(walkingPath, walkingSprite);
		//loadImages(jumpingPath, jumpingSprite);
		//loadImages(airPath, airSprite);
		//loadImages(fallingPath, fallingSprite);
		//loadImages(attackingPath, attackingSprite);

		pills = new ArrayList<Pill>();
		attacks = new ArrayList<Attack>();
		animation = new Animation();
		animation.setFrames(idleSprite);
	}

	public void tick() {
		super.tick();
		super.moveMap();
		animatePlayer();
		attackingPlayer();
		if (dx < 0) {
			facingLeft = true;
		}
		if (dx > 0) {
			facingLeft = false;
		}
		if (health <= 0) {
			dead = true;
		}
		if (dead) {
			dead = false;
			health = 15;
		}
	}

	private void animatePlayer() {
		if (left || right) {
			animate(walkingSprite, 45);
			animation.tick();
		} else if (attacking) {
			//animate(attackingSprite, 40);
		} else {
			//animate(idleSprite, 75);
		}
		if (dy < 0) {
			//animate(jumpingSprite, 55);
		}
		if (dy > 0) {
			//animate(fallingSprite, 55);
		}
		//animation.tick();
	}

	/** Animate a set of images and loop throught them based on a delay */
	private void animate(BufferedImage images[], int delay) {
		animation.setFrames(images);
		animation.setDelay(delay);
	}
	
	/** Animate the attacking action and remove it after it is done */
	private void attackingPlayer(){
		if (attacking) {
			for(Attack a : attacks){
				System.out.println("Attacking");
				if (!a.tick()) {
					a.setDestroyed();
				}
			}
			attacksCleanUp();
			if (attacks.size() == 0) {
				System.out.println("Done attacking");
				attacking = false;
			}
		}
	}

	public void render(Graphics2D g) {
		/** player **/
		int mapX = map.getX();
		int mapY = map.getY();
		if (facingLeft) {
			g.drawImage(animation.getImage(), (int) (mapX + x - width / 2 + width), (int) (mapY + y - height / 2), -width, height, null);
		} else if (attacking) {
			g.drawImage(animation.getImage(), (int) (mapX + x - width / 2), (int) (mapY + y - height / 2), null);
		} else {
			g.drawImage(animation.getImage(), (int) (mapX + x - width / 2), (int) (mapY + y - height / 2), null);
		}
		g.setColor(Color.red);
		g.drawRect((int)(mapX + x - width / 2), (int)(mapY + y - height / 2), width, height);
		g.setColor(Color.black);
		g.drawRect((int)(mapX + x - 15 / 2), (int)(mapY + y - 15 / 2), 15, 15);
		/** health **/
		g.setColor(Color.GREEN);
		for (int i = 0; i < health / 5; i++) {
			g.drawRect(15 * i + 5, 5, 10, 10);
		}
		/** pills **/
		if (pills.size() != 0) {
			for (int i = 0; i < pills.size(); i++) {
				int width = 15;
				int height = 15;
				Image img = pills.get(i).getImage();
				g.drawImage(img, (i * width) + (i * 5), 16, width, height, null);
			}
		}
		/** pills**/
		for (Attack a : attacks) {
			a.render(g);
		}
	}

	public void addPill(Pill p) {
		if (pills.size() < 3) {
			pills.add(p);
		}
	}

	public void addAttack(Attack a) {
		if (pills.size() != 0) {
			attacks.add(a);
		}
	}

	public Attack getAttack(int index) {
		return attacks.get(index);
	}

	public void destroyAttack() {
		if (attacks.size() > 0) {
			attacks.get(attacks.size() - 1).setDestroyed();
		}
	}

	public int getAttackSize() {
		return attacks.size();
	}

	public void removePill() {
		if (pills.size() > 0) {
			pills.remove(pills.size() - 1);
		}
	}

	public void teleportToAttack() {
		if (attacks.size() > 0) {
			double x = attacks.get(attacks.size() - 1).getX();
			double y = attacks.get(attacks.size() - 1).getY() + 17;
			setX(x);
			setY(y);
			destroyAttack();
		}
	}

	public int getPillSize() {
		return pills.size();
	}

	public boolean getFacingLeft() {
		return facingLeft;
	}
	
	public void attacksCleanUp(){
		for(int i = 0; i < attacks.size(); i++){
			if(attacks.get(i).isDestroeyd()){
				attacks.remove(i);
			}
		}
	}
}
