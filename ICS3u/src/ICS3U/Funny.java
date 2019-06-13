package ICS3U;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Funny extends BasicGameState{
	private boolean enter;
	private Music chillcity;

	public Funny(int funny) {
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		enter=true;
		chillcity=new Music("./songs/ChillCity.ogg");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		if (enter) {
			chillcity.play();
			enter=false;
		}
	}

	@Override
	public int getID() {
		return 8;
	}

}
