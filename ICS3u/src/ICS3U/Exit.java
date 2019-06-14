package ICS3U;

import java.util.concurrent.TimeUnit;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

/**
* for exit page
*/
public class Exit extends BasicGameState{
	private boolean draw;
	private Image bye;
	public static int scene;
	private Image text;
	private Image text1;
	private Image tell;
	private Image funny;
	private Rectangle rec;
	public static boolean enter;
	
	public Exit(int exit) {
	}

	/**
	 * initialize the screen
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// initialize components
		draw=false;
		bye = new Image("./image/bye.gif");
		scene=0;
		text=new Image("./image/forYou/text1.png");
		text1=new Image("./image/forYou/text2.png");
		rec = new Rectangle(0,0,700,1200);
		tell=new Image("./image/forYou/tell.png");
		funny=new Image("./image/forYou/funny.png");
	}

	/**
	* draw components on the screen
	*/
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

		if(scene==0) {
			bye.draw(150,430,400,320);
			draw=true;
		}else if (scene==1) {
			g.setColor(Color.black);
			text.draw(0,0,700,1200);
			g.fill(rec);
		}else if (scene==2) {
			text1.draw(20,100,660,230);
			tell.draw(50,730,600,230);
			funny.draw(50,380,600,230);
		}
	}

	/**
	 * update the data of the game and call render again
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		//different scenes
		if (scene==0) {
			if (draw) {//bye picture
				try {
					TimeUnit.MILLISECONDS.sleep(550);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				scene = 1;
			}
		}else if (scene==1){//the words
			if (rec.getCenterY()+2.5<=1799) {
				rec.setCenterY((float) (rec.getCenterY()+0.13));
			}else {
				scene=2;
			}
		}else if (scene==2) {//choose one to watch
			if (Mouse.isButtonDown(0) && Mouse.getX()<650 && Mouse.getX()>50) {
				if (1200-Mouse.getY()>380 && 1200-Mouse.getY()<610) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sbg.getState(PinBall.funny).init(gc, sbg);
					sbg.enterState(PinBall.funny,new FadeOutTransition(), new FadeInTransition());
				}else if (1200-Mouse.getY()>730 && 1200-Mouse.getY()<960) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sbg.getState(PinBall.foryou).init(gc, sbg);
					sbg.enterState(PinBall.foryou,new FadeOutTransition(), new FadeInTransition());
				}
			}
		}
	}

	@Override
	public int getID() {
		return 5;
	}

}
