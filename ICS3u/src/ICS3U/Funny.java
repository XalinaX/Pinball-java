package ICS3U;

import java.util.concurrent.TimeUnit;

import org.newdawn.slick.Animation;
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

/**
 * this class can make Ms Wong happy
 *
 */
public class Funny extends BasicGameState{
	private boolean enter;
	private Music chillcity;
	private SpriteSheet[] video;
	private Animation[] videoAnimation;
	private int[] time= {3280,3260,4450,6550,6300,6200,4700,4450,1800,2700,2700}; // the time of each video
	private int page;
	private int count;

	public Funny(int funny) {
	}
 
	/**
	 * initialize the happiness
	 */
	@Override 
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		enter=true;
		chillcity=new Music("./songs/ChillCity.ogg");
		page=0;
		video=new SpriteSheet[12];
		video[0] = new SpriteSheet("./image/funny/1.png",100,180);
		video[1] = new SpriteSheet("./image/funny/1 (2).png",100,160);
		video[2] = new SpriteSheet("./image/funny/2.png",200,320);
		video[3] = new SpriteSheet("./image/funny/2 (2).png",100,160);
		video[5] = new SpriteSheet("./image/funny/3 (2).png",100,160);
		video[4] = new SpriteSheet("./image/funny/3 (3).png",100,160);
		video[6] = new SpriteSheet("./image/funny/4.png",150,240);
		video[7] = new SpriteSheet("./image/funny/5.png",100,160);
		video[8] = new SpriteSheet("./image/funny/6.png",100,160);
		video[9] = new SpriteSheet("./image/funny/menu.png",100,160);
		video[10] = new SpriteSheet("./image/funny/login.png",100,160);
		videoAnimation = new Animation[12];
		for (int i=0;i<11;i++) {
			videoAnimation[i]=new Animation(video[i],40);
		}
		
	}

	/**
	 * display the funny videos 
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.black);
		System.out.println(page);
		if (page<11)
			videoAnimation[page].draw(0,0,200,320);
	}

	/**
	 * update the screen
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		if (enter) {
			chillcity.play();
			enter=false;
		}
		if (count<time[page]) {
			count++;
		}else {
			count=0;
			if(page<10) {
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				page++;
			}else {
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				chillcity.stop();
				sbg.getState(PinBall.exit).init(gc, sbg);
				Exit.scene=2;
				sbg.enterState(PinBall.exit, new FadeInTransition(), new FadeOutTransition());
			}
		}
	}

	@Override
	public int getID() {
		return 8;
	}

}
