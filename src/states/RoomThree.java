package states;

import manager.GameStateManager;
import player.Player;
import types.StateType;

public class RoomThree extends RoomOne{

	public RoomThree(GameStateManager gsm, Player player) {
		super(gsm, player);
		super.setMapAttributes(StateType.ROOMTHREE.getPath());
	}
	
	@Override
	public void tick() {
		if(map.tick()){
			gsm.setGameState(StateType.ROOMFOUR.getState());
		}
		player.tick();
	}
}
