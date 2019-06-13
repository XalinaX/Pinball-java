package ICS3U;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * this calss is for Ms wong,
 * our BEST TEACHER!!!
 * 
 * @author alina, amy
 *
 */
public class ForYou extends BasicGameState{

	private ArrayList<Image> text;
	private int page;
	private Rectangle rec;
	private Music chengdu;
	private boolean enter;
	
	public ForYou(int foryou) {
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		text = new ArrayList<Image>();
		rec = new Rectangle(0,0,700,1200);
		for (int i=0;i<1;i++) {
			text.add(new Image("./image/forYou/text1.png"));
		}
		page=0;
		chengdu=new Music("./songs/chengdu.ogg");
		enter=true;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setColor(Color.black);
		text.get(page).draw(0,0,700,1200);
		g.fill(rec);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		if (enter) {
			chengdu.play();
			enter=false;
		}
		if (rec.getCenterY()+0.1<=1799) {
			rec.setCenterY((float) (rec.getCenterY()+0.1));
		}else {
			if(page<0) {
				page++;
				rec.setCenterY(600);
			}
		}
	}

	@Override
	public int getID() {
		return 6;
	}

}
