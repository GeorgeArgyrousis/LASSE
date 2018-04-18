package Items;

import java.awt.Graphics2D;

import map.TileMap;
import player.Player;

public class Pill extends MapObject{
	
	private String path = "graphics/pill.png";

	public Pill(TileMap map, Player player, int x, int y){
		super(map, player, x, y);
		super.loadTile(path);
	}
	
	public void render(Graphics2D g){
		if (getImage() != null) {
			int resize = 4;
			//g.setColor(Color.red);
			//g.drawLine(map.getX() + x * tileSize + resize / 2, map.getY() + y * tileSize + resize, map.getX() + x * tileSize + resize / 2, -(map.getY() + y * tileSize + resize));
			g.drawImage(getImage(), map.getX() + x * tileSize + resize / 2, map.getY() + y * tileSize + resize, tileSize - resize, tileSize - resize, null);
		}
	}
}
