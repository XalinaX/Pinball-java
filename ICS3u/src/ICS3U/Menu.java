package ICS3U;

import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * first screen
 */
public class Menu extends BasicGameState {
	protected Ball ball = new Ball(550, 700, 0,0);
	protected static boolean add = true;
	protected Input input;
	private boolean change;
	private java.awt.Font awtFont;
	private TrueTypeFont font;
	public static Image exit;
	public static Image on;
	public static Image off;
	public static Image hi;
	public static boolean soundOn;
	public static boolean clicked;
	public static boolean clicked1;
	
	public Menu(int menu) {
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(font);
		g.setColor(Color.orange);
		g.drawString("Welcome to Physics Pinball", 70, 230);
		g.setColor(Color.pink);
		g.drawString("Click the ball to continue", 90, 380);
		g.setColor(Color.white);
		if (change) {
			g.setColor(Color.yellow);
		}
		g.fill(ball.cir);
		g.draw(ball.cir);
		exit.draw(640,0,60,60);
		if (soundOn) {
			on.draw(0,0,60,60);
		}else {
			off.draw(0,0,60,60);
		}
		hi.draw(230,850,240,220);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame sbg) throws SlickException {
		awtFont = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 40);
		font = new TrueTypeFont(awtFont, false);
		ball.setMass(20, 1);
		change=false;
		exit = new Image("./image/exit.gif");
		on = new Image("./image/sound/on.gif");
		off = new Image("./image/sound/off.gif");
		hi = new Image("./image/hi.gif");
		clicked = false;
		soundOn=true;
		clicked1=false;
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg1) throws SlickException {
		int xpos=Mouse.getX();
		int ypos=1200-Mouse.getY();
		input = gc.getInput();
		clicked1=input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		if (clicked1) {
			Vec2d point = new Vec2d(xpos,ypos);
			if (ball.circlePoint(point,true)) {
				change=true;
				sbg.enterState(PinBall.login);
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (clicked1&&ypos>0 && ypos<60&&((xpos>0&&xpos<60)||(xpos>640&&xpos<700))) {
			clicked=true;
		}else if (!Mouse.isButtonDown(0)&&clicked&& ypos>0 && ypos<60) {
			if (xpos>640 && xpos<700) {
				sbg.enterState(PinBall.exit);
			}else if (xpos>0 && xpos<60){
				if (soundOn) {
					soundOn=false;
				}else {
					soundOn=true;
				}
			}
			clicked = false;
		}
		if (ball.px >= 548) {
			add = false;
		} else if (ball.px <= 152) {
			add = true;
		}
		if (add)
			ball.px+=0.15;
		else
			ball.px-=0.15;			
		ball.py = (float) (100 * (-(Math.sqrt(10 - 0.00025 * Math.pow(ball.px - 350, 2)))
					+ Math.pow(0.0005 * Math.pow(ball.px - 350, 2), (1 / 3.0))) + 857);

		ball.cir.setCenterX(ball.px);
		ball.cir.setCenterY(ball.py);
		
		
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}