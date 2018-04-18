package states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import enemies.Blob;
import enemies.Ghost;
import manager.GameStateManager;
import player.Player;
import types.StateType;

public class LevelOne extends GameState {

	public LevelOne(GameStateManager gsm, Player player) {
		super(gsm, player);

		super.setMapAttributes(StateType.LEVELONE.getPath());
		//this.map = new TileMap(player, pathMap, 32);
		//this.map.loadTiles(graphics);

		blobs = new ArrayList<Blob>();
		ghosts = new ArrayList<Ghost>();
		
		blobs.add(new Blob(player));
		ghosts.add(new Ghost(player));
		blobs.get(0).setMap(map);
		blobs.get(0).setX(270.0);
		blobs.get(0).setY(50.0);
		
		ghosts.get(0).setMap(map);
		ghosts.get(0).setX(220.0);
		ghosts.get(0).setY(50.0);
	}

	@Override
	public void tick() {
		map.tick();
		if(player.isDead()){
			reset();
		}
		player.tick();
		for(Blob b : blobs){
			b.tick();
		}
		cleanUpBlobs();
		for(Ghost g : ghosts){
			g.tick();
		}
		cleanUpGhosts();
	}

	@Override
	public void render(Graphics2D g) {
		map.render(g);
		for (Blob e : blobs) {
			e.render(g);
		}
		for(Ghost gh : ghosts){
			gh.render(g);
		}
		player.render(g);
	}
	
	public void reset(){
		player.setX(50.0);
		player.setY(50.0);
		if(blobs.size() > 0){
			blobs.get(0).setX(270.0);
			blobs.get(0).setY(50.0);
		}else{
			blobs.add(new Blob(player));
			blobs.get(0).setMap(map);
			blobs.get(0).setX(270.0);
			blobs.get(0).setY(50.0);
		}
	}
}
