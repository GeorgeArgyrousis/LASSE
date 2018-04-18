package map;
import java.awt.image.BufferedImage;

public class Animation {
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private BufferedImage images[];
	/**
	 * Default constructor;
	 */
	public Animation(){}
	
	/**
	 * Set the current set of images;
	 * @param images the frames.
	 */
	public void setFrames(BufferedImage images[]){
		this.images = images;
		if(currentFrame >= images.length){
			currentFrame = 0;
		}
	}
	
	/**
	 * Delay in between frames;
	 * @param delay the time.
	 */
	public void setDelay(long delay){
		this.delay = delay;
	}
	
	/**
	 * Wait for the frames to be shown;
	 */
	public void tick(){
		if(delay == -1){
			return;
		}
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == images.length){
			currentFrame = 0;
		}
	}
	
	/**
	 * Get the array of images;
	 * @return the images.
	 */
	public BufferedImage getImage(){
		return images[currentFrame];
	}
}
