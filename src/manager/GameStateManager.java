package manager;

import java.awt.Graphics2D;
import java.util.ArrayList;

import player.Player;
import states.GameState;
import states.LevelOne;
import states.Menu;
import states.RoomFour;
import states.RoomOne;
import states.RoomThree;
import states.RoomTwo;

public class GameStateManager {

	private ArrayList<GameState> states;

	private int currentState;

	private Player player;

	public GameStateManager() {
		player = new Player();
		states = new ArrayList<GameState>();
		states.add(new Menu(this));
		states.add(new RoomOne(this, player));
		states.add(new RoomTwo(this, player));
		states.add(new RoomThree(this, player));
		states.add(new RoomFour(this, player));
		states.add(new LevelOne(this, player));
		currentState = 0;
	}

	public void tick() {
		states.get(currentState).tick();
	}

	public void render(Graphics2D g) {
		states.get(currentState).render(g);
	}

	public void keyPressed(int key) {
		states.get(currentState).keyPressed(key);
	}

	public void keyReleased(int key) {
		states.get(currentState).keyReleased(key);
	}

	public void setGameState(int state) {
		this.currentState = state;
		if (this.currentState > 0) {
			player.setMap(states.get(currentState).getMap());
			states.get(currentState).reset();
		}
		// cannot go more than max;
	}
}
