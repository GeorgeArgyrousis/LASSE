package states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enemies.Blob;
import enemies.Ghost;
import manager.GameStateManager;
import map.TileMap;
import player.Attack;
import player.Player;
import types.StateType;

public abstract class GameState {
	
	protected String graphics = "graphics/tileset.png";
	
	protected ArrayList<Blob> blobs;
	protected ArrayList<Ghost> ghosts;

	protected GameStateManager gsm;
	protected Player player;
	protected TileMap map;
	
	public GameState(GameStateManager gsm, Player player){
		this.gsm = gsm;
		this.player = player;
	}
	
	public GameState(GameStateManager gsm){
		this.gsm = gsm;
	}

	public abstract void tick();

	public abstract void render(Graphics2D g);
	
	/** Move the player according to input */
	public void keyPressed(int key) {
		if(player == null){
			return;
		}
		switch(key){
		case KeyEvent.VK_LEFT :
			player.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT :
			player.setRight(true);
			break;
		case KeyEvent.VK_UP : 
			player.setJumping(true);
			break;
		case KeyEvent.VK_Z :
			if(player.getPillSize() <= 0){
				break;
			}
			player.setAttacking(true);
			if(player.getFacingLeft()){
				player.addAttack(new Attack(180, player.getX() - 2, player.getY(), player.getDamage(), map));
			}else{
				player.addAttack(new Attack(0, player.getX() - 2, player.getY(), player.getDamage(), map));
			}
			player.removePill();
			break;
		case KeyEvent.VK_X :
			player.teleportToAttack();
			break;
		case KeyEvent.VK_ESCAPE :
			gsm.setGameState(StateType.MENU.getState());
			break;
		}
	}

	/** Reset the movement from the keyPressed method */
	public void keyReleased(int key) {
		if(player == null){
			return;
		}
		switch(key){
		case KeyEvent.VK_LEFT :
			player.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT :
			player.setRight(false);
			break;
		}
	}
	
	public abstract void reset();

	public void setMapAttributes(String path){
		if(this.player != null){
			this.map = new TileMap(player, path, 32);
			this.map.loadTiles(graphics);
		}else{
			System.out.println("ERROR IN GAMESTATE");
		}
	}
	
	public TileMap getMap() {
		return this.map;
	}
	
	public void cleanUpBlobs(){
		if(blobs != null){
			for(int i = 0; i < blobs.size(); i++){
				if(blobs.get(i).isDead()){
					blobs.remove(i);
				}
			}
		}
	}
	
	public void cleanUpGhosts(){
		if(ghosts != null){
			for(int i = 0; i < ghosts.size(); i++){
				if(ghosts.get(i).isDead()){
					ghosts.remove(i);
				}
			}
		}
	}
}
