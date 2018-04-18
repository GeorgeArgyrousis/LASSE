package Items;

import map.TileMap;
import player.Player;

public class Dialog extends MapObject{
	
	private String path = "graphics/dialog1.png";
	
	public Dialog(TileMap map, Player player, int x, int y){
		super(map, player, x, y);
		loadTile(path);
	}
}
