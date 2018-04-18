package states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import enemies.Blob;
import manager.GameStateManager;
import player.Player;

public class TrainingState extends GameState{
	
	private String pathMap = "maps/test.txt";
	
	public TrainingState(GameStateManager gsm, Player player){
		super(gsm, player);
		super.setMapAttributes(pathMap);
		//this.map = new TileMap(player, pathMap, 32);
		//this.map.loadTiles(graphics);
		
		blobs = new ArrayList<Blob>();
		blobs.add(new Blob(player));
		//blobs.get(0).setTraining();
		blobs.get(0).setMap(map);
		blobs.get(0).setX(70.0);
		blobs.get(0).setY(50.0);
		blobs.add(new Blob(player));
		//blobs.get(1).setTraining();
		blobs.get(1).setMap(map);
		blobs.get(1).setX(110.0);
		blobs.get(1).setY(50.0);
	}

	@Override
	public void tick() {
		if(map.tick()){
			//gsm.setGameState(GameStateManager.LEVEL1STATE);
		}
		if(player.isDead()){
			reset();
		}
		player.tick();
		for(Blob b : blobs){
			b.tick();
		}
		cleanUpBlobs();
	}

	@Override
	public void render(Graphics2D g) {
		map.render(g);
		for (Blob e : blobs) {
			e.render(g);
		}
		player.render(g);
	}
	
	@Override
	public void reset(){
		player.setX(50.0);
		player.setY(350.0);
		blobs.get(0).setTraining();
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
