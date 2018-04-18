package enemies;

import java.util.Random;

import player.Entity;
import player.Player;

public class Enemy extends Entity{

	protected boolean training;
	protected int random;
	
	protected Player player;
	protected Random ran;
	
	public Enemy(Player player){
		this.player = player;
		this.ran = new Random();
		training = false;
	}
	
	public void tick(){
		super.tick();
		intersecting = intersects();
		/** Animation **/
		animateEnemy();
		/** Stay Still **/
		if(training){
			return;
		}
		/** Random Movements **/
		int random = ran.nextInt(200);
		if (random > 185) {
			attacking = true;
		} else {
			attacking = false;
		}
		moveEnemy(random);
		enemyAttack();
	}
	
	public void randomMovement(int m){
		int maxMoves = 3;
		if(m > maxMoves + 1 || m < maxMoves - 3){
			return;
		}
		boolean array[] = {false, false, false};
		array[m-1] = true;
		left = array[0];
		right = array[1];
		if(!jumping){
			jumping = array[2];
		}
	}
	
	/** Move the enmy according to the random input */
	public void moveEnemy(int random){
		switch(random){
		case 10 :
			randomMovement(1);
			break;
		case 20 :
			randomMovement(2);
			break;
		case 30 :
			randomMovement(3);
			break;
		}
	}
	
	/** Animate the enemy externaly */
	public void animateEnemy(){}
	
	/** Actions according to the player being attacked by the blob
	 * or the blob attacking the player;
	 */
	private void enemyAttack(){
		if (isIntersecting() && isAttacking()) {
			setAttacking(false);
			int playerHealth = player.getHealth() - getDamage();
			player.setHealth(playerHealth);
		}
		if (attackIntersection() && player.isAttacking()) {
			player.destroyAttack();
			int enemyHealth = getHealth() - player.getDamage();
			if(enemyHealth <= 0){
				setDead();
			}else{
				setHealth(enemyHealth);
			}
		}
	}
	
	/** The enemy coming into contact with the Player */
	private boolean intersects() {
		int mapX = map.getX();
		int mapY = map.getY();
		int playerWidth = player.getWidth();
		int playerHeight = player.getHeight();
		Boolean leftBound = player.getX() + mapX + playerWidth / 2 >= mapX + x - playerWidth;
		Boolean rightBound = player.getX() + mapX - playerWidth / 2 <= mapX + x + playerWidth;
		Boolean topBound = player.getY() + mapY - playerHeight / 2 >= mapY + y - playerHeight;
		Boolean bottomBound = player.getY() + mapY - playerHeight / 2 <= mapY + y + playerHeight;
		if (leftBound && rightBound && topBound && bottomBound) {
			return true;
		}
		return false;
	}

	/** The enemy intersecting with a player's attack */
	private boolean attackIntersection() {
		int mapX = map.getX();
		int mapY = map.getY();
		if (player.getAttackSize() - 1 >= 0) {
			for (int i = player.getAttackSize() - 1; i != -1; i--) {
				int attackWidth = player.getAttack(i).getWidth();
				Boolean leftBound = player.getAttack(i).getX() + mapX + attackWidth / 2 >= mapX + x - attackWidth;
				Boolean rightBound = player.getAttack(i).getX() + mapX - attackWidth / 2 <= mapX + x + attackWidth;
				Boolean topBound = player.getAttack(i).getY() + mapY - attackWidth / 2 >= mapY + y - attackWidth;
				Boolean bottomBound = player.getAttack(i).getY() + mapY - attackWidth / 2 <= mapY + y + attackWidth;
				if (leftBound && rightBound && topBound && bottomBound) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void setTraining(){
		this.training = true;
	}
}
