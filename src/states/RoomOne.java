package states;

import java.awt.Graphics2D;

import manager.GameStateManager;
import player.Player;
import types.StateType;

public class RoomOne extends GameState{

	public RoomOne(GameStateManager gsm, Player player){
		super(gsm, player);
		super.setMapAttributes(StateType.ROOMONE.getPath());
	}

	@Override
	public void tick() {
		if(map.tick()){
			gsm.setGameState(StateType.ROOMTWO.getState());
		}
		if(player.isDead()){
			reset();
		}
		player.tick();
	}

	@Override
	public void render(Graphics2D g) {
		map.render(g);
		player.render(g);
	}

	@Override
	public void reset() {
		player.setX(50.0);
		player.setY(50.0);
	}
}
