package ICS3U;

import java.util.concurrent.TimeUnit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Exit extends BasicGameState{
	private boolean draw;
	private Image bye;
	
	public Exit(int exit) {
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// initialize components
		draw=false;
		bye = new Image("./image/bye.gif");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		// draw a picture
		bye.draw(150,430,400,320);
		draw=true;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		// enter another page
		if (draw) {
			sbg.enterState(PinBall.foryou);
			try {
				TimeUnit.MILLISECONDS.sleep(550);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getID() {
		return 5;
	}

}
