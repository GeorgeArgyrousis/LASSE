package View;
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final String TITLE = "LASSE";
	
	/**
	 * Construct the JFrame and JPanel class. 
	 */
	public GameFrame(){
		setTitle(TITLE);
		setLayout(new BorderLayout());
		add(new GamePanel(), BorderLayout.CENTER);
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}
