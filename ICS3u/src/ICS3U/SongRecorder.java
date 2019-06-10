package ICS3U;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * this class is use for generate songs' information
 * it will be stored in a file and when the game starts, the program will read in and store them in an 2D-Array
 */
public class SongRecorder extends JPanel implements KeyListener {
	static boolean ctrlOn = false;
	static boolean shiftOn = false;
	public SongRecorder() {
        super();
		setPreferredSize (new Dimension (100,100));
		setFocusable (true);
		addKeyListener(this);
		
	}
	public static void main(String[] args) throws IOException{

		JFrame j = new JFrame ("song recorder");//create the screen
		SongRecorder p = new SongRecorder();

		j.add (p);
		j.pack ();
		j.setVisible (true);
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
	}
	
	/**
	 * when the key is pressed, get the number and display them
	 * @param e - the KeyEvent
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
		ctrlOn = true;
		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
		shiftOn=true;
		if (e.getKeyCode()-49>=0&&e.getKeyCode()-49<=6) {
		if(ctrlOn) {
			System.out.println("1 "+(e.getKeyCode()-49));
			ctrlOn = false;
		}else if(shiftOn) {
			System.out.println("2 "+(e.getKeyCode()-49));
			shiftOn=false;
		}else {
			System.out.println("0 "+(e.getKeyCode()-49));
		}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
