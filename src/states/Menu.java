package states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import View.GamePanel;
import manager.GameStateManager;
import types.MenuType;
import types.StateType;

public class Menu extends GameState {

	//** s for start, a for about, o for options, q for quit; */
	private final int s = MenuType.START.getType(),
			          a = MenuType.ABOUT.getType(),
			          o = MenuType.OPTIONS.getType(),
			          q = MenuType.QUIT.getType();
	private final String arr[] = {MenuType.START.getName(),
								  MenuType.ABOUT.getName(),
								  MenuType.OPTIONS.getName(),
								  MenuType.QUIT.getName()};
	private final int size = 4;
	private int current;
	
	private BufferedImage images[];

	public Menu(GameStateManager gsm) {
		super(gsm);
		current = s;
		images = new BufferedImage[size];
		try {
			images[s-1] = ImageIO.read(new File(MenuType.START.getPath()));
			images[a-1] = ImageIO.read(new File(MenuType.ABOUT.getPath()));
			images[o-1] = ImageIO.read(new File(MenuType.OPTIONS.getPath()));
			images[q-1] = ImageIO.read(new File(MenuType.QUIT.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics2D g) {
		if(images != null){
			g.drawImage(images[current - 1], 0, 0, null);
		}else{
			epilepsy(g);
		}
	}
	
	/** Just don't...alternate menu just in case */
	private void epilepsy(Graphics2D g){
		String s = "error";
		g.setColor(Color.WHITE);
		for(int i = 0; i < GamePanel.HEIGHT / 10; i++){
			for(int j = 0; j < 20; j++){
				g.setColor(new Color(new Random().nextInt(0xFFFFFF)));
				g.drawString(s, j * 30, i * 10);
			}
		}
		int x = 215, y = 275;
		g.setColor(Color.BLACK);
		g.fillRect(x - 5, y - 17, 60, 70);
		for(int i = 0; i < arr.length; i++){
			g.setColor(Color.WHITE);
			g.drawString(arr[i], x, y);
			if(i == current - 1){
				g.setColor(Color.CYAN);
				g.drawString(arr[current - 1], x, y);
			}
			y += 15;
		}
	}

	@Override
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_DOWN) {
			current++;
			if (current == 5) {
				current = 1;
			}
		}
		if (key == KeyEvent.VK_UP) {
			current--;
			if (current == 0) {
				current = 4;
			}
		}
		if (key == KeyEvent.VK_ENTER) {
			if (current == s) {
				gsm.setGameState(StateType.ROOMONE.getState());
			} else if (current == a) {
				//TODO :: GameState
				System.out.println("ABOUT");
			} else if (current == o) {
				//TODO :: GameState
				System.out.println("OPTIONS");
			} else if (current == q) {
				System.exit(-1);
			}
		}
	}

	@Override
	public void keyReleased(int key) {}
	
	@Override
	public void reset(){};
}
