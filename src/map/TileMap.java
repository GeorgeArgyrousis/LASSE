package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Items.Dialog;
import Items.Pill;
import View.GamePanel;
import player.Player;
import types.BackgroundType;

public class TileMap {

	private final int PORTAL = 55;
	private final int DIALOG = 56;
	private final int PILL = 53;

	private String t = BackgroundType.TEST.getPath();

	private int x, y;
	private int tileSize;
	private int map[][];
	private int width, height;
	private int minX, minY;
	private int maxX = 0, maxY = 0;

	private BufferedImage tileSet;
	private Player player;
	private Tile tiles[][];

	private Background bg;
	private Dialog dialog;

	private Portal portal;
	private ArrayList<Pill> pills;

	public TileMap(Player player, String path, int tileSize) {
		this.player = player;
		this.tileSize = tileSize;
		bg = new Background(t, this);
		pills = new ArrayList<Pill>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			// the width of the map
			width = Integer.parseInt(reader.readLine());
			// the height of the map
			height = Integer.parseInt(reader.readLine());
			// create the array
			map = new int[height][width];

			minX = GamePanel.WIDTH - width * tileSize;
			minY = GamePanel.HEIGHT - height * tileSize;

			for (int i = 0; i < height; i++) {
				String line = reader.readLine();
				String bits[] = line.split("\\s+");
				for (int j = 0; j < width; j++) {
					int block = Integer.parseInt(bits[j]);
					switch (block) {
					default:
						map[i][j] = block;
						break;
					case PORTAL:
						portal = new Portal(this, player, j, i);
						break;
					case PILL:
						pills.add(new Pill(this, player, j, i));
						break;
					case DIALOG:
						dialog = new Dialog(this, player, j, i);
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO :: fix
	public void loadTiles(String path) {
		try {
			tileSet = ImageIO.read(new File(path));
			int width = (tileSet.getWidth() + 1) / (tileSize + 1);
			tiles = new Tile[4][width];
			BufferedImage subImage;
			for (int i = 0; i < width; i++) {
				subImage = tileSet.getSubimage(i * tileSize + i, 0, tileSize, tileSize);
				tiles[0][i] = new Tile(subImage, false);
				subImage = tileSet.getSubimage(i * tileSize + i, tileSize + 1, tileSize, tileSize);
				tiles[1][i] = new Tile(subImage, true);
				subImage = tileSet.getSubimage(i * tileSize + i, 2 * tileSize + 2, tileSize, tileSize);
				tiles[2][i] = new Tile(subImage, true);
				subImage = tileSet.getSubimage(i * tileSize + i, 3 * tileSize + 3, tileSize, tileSize);
				tiles[3][i] = new Tile(subImage, true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean tick() {
		if(bg != null){
			bg.tick();
		}
		// portal logic
		if (portal != null) {
			portal.tick();
		}
		if (portal != null && portal.intersects()) {
			return true;
		}
		for(Pill p : pills){
			if(p.intersects()){
				player.addPill(p);
				p.setDestroy();
			}
		}
		pillCleanUp();
		return false;
	}

	public void render(Graphics2D g) {
		bg.render(g);
		// Rows
		for (int i = 0; i < height; i++) {
			// Columns
			for (int j = 0; j < width; j++) {
				int block = map[i][j];
				int row = block / tiles[0].length;
				int column = block % tiles[0].length;
				// draw all tiles to the screen;
				g.drawImage(tiles[row][column].getImage(), x + j * tileSize, y + i * tileSize, null);
			}
		}
		// draw all pills to the screen;
		for (Pill pill : pills) {
			pill.render(g);
		}
		// draw the portal to the screen;
		portal.render(g);
		// draw the dialog to the screen;
		if (dialog != null && dialog.intersects()) {
			dialog.render(g);
		}
	}

	public boolean isBlocked(int row, int column) {
		int rc = map[row][column];
		int r = rc / tiles[0].length;
		int c = rc % tiles[0].length;
		return tiles[r][c].isBlocked();
	}

	public int getTile(int row, int column) {
		return map[row][column];
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getColumnTile(int x) {
		return x / tileSize;
	}

	public int getRowTile(int y) {
		return y / tileSize;
	}

	public void setX(int x) {
		this.x = x;
		if (x < minX) {
			this.x = minX;
		}
		if (x > maxX) {
			this.x = maxX;
		}
	}

	public void setY(int y) {
		this.y = y;
		if (y < minY) {
			this.y = minY;
		}
		if (y > maxY) {
			this.y = maxY;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void pillCleanUp(){
		for (int i = 0; i < pills.size(); i++) {
			if (pills.get(i).isDestroyed()) {
				pills.remove(i);
			}
		}
	}

	public void setNewBackground(String path) {
		bg.setBackground(path);
	}
}
