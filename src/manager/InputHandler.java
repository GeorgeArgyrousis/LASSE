package manager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{
	
	private GameStateManager gsm;
	
	public InputHandler(GameStateManager gsm){
		this.gsm = gsm;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}
		
	@Override
	public void keyTyped(KeyEvent e) {}
}
