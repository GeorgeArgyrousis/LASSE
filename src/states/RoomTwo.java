package states;

import manager.GameStateManager;
import player.Player;
import types.StateType;

public class RoomTwo extends RoomOne{

	public RoomTwo(GameStateManager gsm, Player player) {
		super(gsm, player);
		super.setMapAttributes(StateType.ROOMTWO.getPath());
	}
	
	@Override
	public void tick() {
		if(map.tick()){
			gsm.setGameState(StateType.ROOMTHREE.getState());
		}
		if(player.isDead()){
			reset();
		}
		player.tick();
	}
}
