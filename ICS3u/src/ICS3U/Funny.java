package ICS3U;

import java.util.concurrent.TimeUnit;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	// {6550,6300,6200,4700,4450,1800,2700,2700};
	private int[] time= {3500,3500,3420,4100,4290,6200,5020,4600,2080,3100,3500};// the time of each video
	private int page;
	private int count;
	private Image[] text;
	private Image[] other;

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
		video[4] = new SpriteSheet("./image/funny/3 (3).png",100,160);
		video[5] = new SpriteSheet("./image/funny/3 (2).png",100,160);
		video[6] = new SpriteSheet("./image/funny/4.png",150,240);
		video[7] = new SpriteSheet("./image/funny/5.png",100,160);
		video[8] = new SpriteSheet("./image/funny/6.png",100,160);
		video[9] = new SpriteSheet("./image/funny/menu.png",100,160);
		video[10] = new SpriteSheet("./image/funny/login.png",100,160);
		videoAnimation = new Animation[12];
		for (int i=0;i<11;i++) {
			videoAnimation[i]=new Animation(video[i],40);
		}
		text = new Image[14];
		for (int i=1;i<=14;i++) {
			String s = "./image/funny/t"+Integer.toString(i)+".png";
			text[i-1] = new Image(s);
		}
		other = new Image[9];
		other[0] = new Image("./image/funny/1.JPG");
		other[1] = new Image("./image/funny/2.JPG");
		other[2] = new Image("./image/funny/3.JPG");
		other[3] = new Image("./image/funny/6.JPG");
		other[4] = new Image("./image/funny/leaderboard.JPG");
		other[5] = new Image("./image/funny/waiting.png");
		other[6] = new Image("./image/funny/word.JPG");
		other[7] = new Image("./image/funny/word.png");
		other[8] = new Image("./image/funny/words.png");
	}

	/**
	 * display the funny videos 
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (page==0) {
			text[13].draw(50,400,600,400);
		}else if (page==1) {
			other[0].draw(100,100,450,700);
			other[0].setRotation(5);
			text[12].draw(100,900,500,200);
			text[12].setRotation(355);
		}else if (page==2) {
			text[0].draw(50,800,600,180);
			text[0].setRotation(5);
			videoAnimation[0].draw(200,200,300,480);
		}else if (page==3) {
			text[1].draw(50,800,600,180);
			text[1].setRotation(355);
			videoAnimation[1].draw(200,200,300,480);
		}else if (page==4) {
			text[2].draw(50,1000,600,180);
			text[2].setRotation(5);
			videoAnimation[2].draw(350,20,300,480);
			videoAnimation[7].draw(50,520,300,480);
		}else if (page==5) {
			text[8].draw(50,800,600,400);
			text[8].setRotation(355);
			videoAnimation[3].draw(200,200,300,500);
		}else if (page==6) {
			text[5].draw(50,800,600,230);
			text[5].setRotation(5);
			videoAnimation[4].draw(200,200,300,480);
		}else if (page==7) {
			text[4].draw(50,990,600,210);
			text[4].setRotation(355);
			videoAnimation[5].draw(50,20,300,480);
			videoAnimation[6].draw(350,500,300,480);
		}else if (page==8) {
			text[6].draw(50,970,600,230);
			text[6].setRotation(5);
			videoAnimation[8].draw(350,500,300,480);
			other[2].draw(30,20,320,480);
		}else if (page==9) {
			text[9].draw(50,980,600,220);
			text[9].setRotation(355);
			videoAnimation[9].draw(50,20,300,480);
			videoAnimation[10].draw(350,500,300,480);

		}else if (page==10) {
			text[10].draw(30,950,640,260);
			text[10].setRotation(5);
			other[3].draw(40,0,400,200);
			other[3].setRotation(357);
			other[6].draw(400,0,300,500);
			other[6].setRotation(3);
			other[5].draw(10,230,480,390);
			other[5].setRotation(357);
			other[7].draw(-10,650,360,240);
			other[7].setRotation(3);
			other[8].draw(355,590,380,260);
			other[8].setRotation(357);
		}
			
			
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
