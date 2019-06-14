package ICS3U;

import java.util.concurrent.TimeUnit;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Funny extends BasicGameState{
	private boolean enter;
	private Music chillcity;
	private SpriteSheet[] video;
	private int page;

	public Funny(int funny) {
	}
 
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		enter=true;
		chillcity=new Music("./songs/ChillCity.ogg");
		page=0;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setColor(Color.black);
		video[page].draw(0,0,700,1200);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame sbg, int arg2) throws SlickException {
		if (enter) {
			chillcity.play();
			enter=false;
		}
		
			if(page<6) {
				page++;
				try {
					TimeUnit.MILLISECONDS.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				try {
					TimeUnit.MILLISECONDS.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Exit.scene=3;
				sbg.enterState(PinBall.exit, new FadeInTransition(), new FadeOutTransition());
			}
		//}
	}

	@Override
	public int getID() {
		return 8;
	}

}
