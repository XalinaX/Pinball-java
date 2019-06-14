package ICS3U;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

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
	private double speed;
	private SpriteSheet happy;
	private Animation happyAnimation;
	
	public ForYou(int foryou) {
	}

	/**
	 * initailize (how wonderful Ms Wong is)
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		text = new ArrayList<Image>();
		rec = new Rectangle(0,50,700,1200);
		text.add(new Image("./image/forYou/first.png"));
		text.add(new Image("./image/forYou/alina1.png"));
		text.add(new Image("./image/forYou/alina2.png"));
		text.add(new Image("./image/forYou/alina3.png"));
		text.add(new Image("./image/forYou/amy1.png"));
		text.add(new Image("./image/forYou/amy2.png"));
		page=0;
		chengdu=new Music("./songs/chengdu.wav");
		enter=true;
		happy = new SpriteSheet("./image/forYou/happyEveryday.png",100,160);
		happyAnimation = new Animation(happy,60);
	}

	/**
	 * display the letter for Ms Wong
	 */
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		if (page==6) {
			happyAnimation.draw(0,0,700,1200);
		}else if(page<6){
			g.setColor(Color.black);
			text.get(page).draw(0,0,700,1200);
			g.fill(rec);
		}
	}

	/**
	 * check if Ms Wong is happy
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		if (enter) {
			chengdu.play();
			enter=false;
		}
		if (page==0) {
			speed=0.2;
		}else {
			speed=0.03;
		}
		if (rec.getCenterY()+speed<=1799) {
			rec.setCenterY((float) (rec.getCenterY()+speed));
		}else {
			if(page<6) {
				page++;
				rec.setCenterY(650);
				try {
					TimeUnit.MILLISECONDS.sleep(2050);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				chengdu.stop();
				sbg.getState(PinBall.exit).init(gc, sbg);
				Exit.scene=2;
				sbg.enterState(PinBall.exit, new FadeOutTransition(), new FadeInTransition());
			}
		}
	}

	@Override
	public int getID() {
		return 6;
	}

}
