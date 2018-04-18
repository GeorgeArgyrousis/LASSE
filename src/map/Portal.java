package map;

import Items.MapObject;
import player.Player;
import types.AnimationType;

public class Portal extends MapObject{
	/**
	 * Construct a portal at a specific location;
	 * @param map the map that the portal exists.
	 * @param player the player.
	 * @param x coordinate.
	 * @param y coordinate.
	 */
	public Portal(TileMap map, Player player, int x, int y) {
		super(map, player, x, y);
		loadTiles(AnimationType.PORTAL.getPath(), AnimationType.PORTAL.getFrames(), 50);
	}
}