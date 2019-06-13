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

public class Exit extends BasicGameState{
	private boolean draw;
	private Image bye;
	private int scene;
	private Image text;
	private Image text1;
	private Image tell;
	private Image funny;
	private Rectangle rec;
	public static boolean enter;
	
	public Exit(int exit) {
	}

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

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		
		if (scene==0) {
			if (draw) {
				try {
					TimeUnit.MILLISECONDS.sleep(550);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				scene = 1;
			}
		}else if (scene==1){
			if (rec.getCenterY()+0.13<=1799) {
				rec.setCenterY((float) (rec.getCenterY()+0.13));
			}else {
				scene=2;
			}
		}else if (scene==2) {
			if (Mouse.isButtonDown(0) && Mouse.getX()<650 && Mouse.getX()>50) {
				if (1200-Mouse.getY()>380 && 1200-Mouse.getY()<610) {
					try {
						TimeUnit.MILLISECONDS.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sbg.enterState(PinBall.funny);
				}else if (1200-Mouse.getY()>730 && 1200-Mouse.getY()<960) {
					try {
						TimeUnit.MILLISECONDS.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sbg.enterState(PinBall.foryou);
				}
			}
		}
	}

	@Override
	public int getID() {
		return 5;
	}

}
