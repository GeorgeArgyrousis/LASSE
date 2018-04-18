package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import View.GamePanel;

public class Background {

	private BufferedImage image;
	private TileMap map;
	
	private int x, y;
	private int width, height;
	private float smoothScroll;
	
	public Background(String path, TileMap map){
		this.map = map;
		smoothScroll = 0.015f;
		width = GamePanel.WIDTH;
		height = GamePanel.HEIGHT;
		setBackground(path);
	}
	
	public void tick(){
		x = (int)(map.getX() * smoothScroll % width);
		y = (int)(map.getY() * smoothScroll % height);
	}
	
	public void render(Graphics2D g){
		g.drawImage(image, x, y, null);
	}
	
	public void setBackground(String path){
		image = null;
		try{
			image = ImageIO.read(new File(path));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
