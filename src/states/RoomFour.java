package states;

import manager.GameStateManager;
import player.Player;
import types.StateType;

public class RoomFour extends RoomOne{

	public RoomFour(GameStateManager gsm, Player player) {
		super(gsm, player);
		super.setMapAttributes(StateType.ROOMFOUR.getPath());
	}
	
	@Override
	public void tick() {
		if(map.tick()){
			gsm.setGameState(StateType.LEVELONE.getState());
		}
		player.tick();
	}

}
