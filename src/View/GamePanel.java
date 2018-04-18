package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import manager.GameStateManager;
import manager.InputHandler;

public class GamePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 480, HEIGHT = 480;
	
	private boolean running = false;
	private int fps = 35;
	private int targetTime = 1000 / fps;
	
	private Thread thread;
	private BufferedImage image;
	private Graphics2D g;
	private GameStateManager gsm;

	/**
	 * Construct the Runnable interface and all the relative fields
	 * to initiate the background picture and the Game manager.
	 */
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager();
		addKeyListener(new InputHandler(gsm));
	}
	
	/**
	 * Method called automatically to 
	 * start the thread and the main loop;
	 * 
	 */
	public void addNotify(){
		super.addNotify();
		if(thread == null && !running){
			thread = new Thread(this);
			thread.start();
			running = true;
		}
	}
	
	/**
	 * Main running method, looping through
	 * the logic and the rendering of the program;
	 */
	public void run(){
		long startTime;
		long elapsedTime;
		long waitTime;
		
		while(running){
			//current time
			startTime = System.nanoTime();
			tick();
			render();
			//how much time has passed
			elapsedTime = (System.nanoTime() - startTime) / 1000000;
			//frames to provide to your logic & render
			waitTime = targetTime - elapsedTime;
			//sleep the thread
			if(waitTime < 0){
				waitTime = -waitTime;
			}
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Game logic and functionality
	 */
	private void tick(){
		gsm.tick();
	}
	
	/**
	 * Game rendering and graphics
	 */
	private void render(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		gsm.render(g);
		g2.dispose();
	}
}
