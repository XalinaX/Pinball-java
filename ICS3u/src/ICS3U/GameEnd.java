package ICS3U;

import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *  the game end
 *  this class is called when the player loses 
 */
public class GameEnd extends BasicGameState {
	public static String score;
	protected java.awt.Font awtFont;
	protected TrueTypeFont font;
	public Input input;
	protected Polygon poly;
	private Animation animation;
	int picposition=0;
	private Image newgame;
	private Image rankk;
	
	public static Image exit;
	public static Image on;
	public static Image off;
	public static Image home;
	public static Image rank;
	public static boolean soundOn;
	public static boolean clicked;
	
	public GameEnd(int i) {
	}

	/**
	 * initialize the game screen
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		// initialize components
		newgame = new Image("./image/newgame.png");
		rankk = new Image("./image/rankk.png");
		awtFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 50);
		font = new TrueTypeFont(awtFont, false);
		poly = new Polygon();
		poly.addPoint(45, 55);
		poly.addPoint(65, 55);
		poly.addPoint(55, 45);
		
		// animation pics
		animation = new Animation(true);
		Image poke1 = new Image("./image/gameend/pokemon1.png");
		Image poke2 = new Image("./image/gameend/pokemon2.png");
		Image poke3 = new Image("./image/gameend/pokemon3.png");
		Image poke4 = new Image("./image/gameend/pokemon4.png");
		Image poke5 = new Image("./image/gameend/pokemon5.png");
		Image poke6 = new Image("./image/gameend/pokemon6.png");
		Image poke7 = new Image("./image/gameend/pokemon7.png");
		Image poke8 = new Image("./image/gameend/pokemon8.png");

		animation.addFrame(poke1, 200);
		animation.addFrame(poke2, 200);
		animation.addFrame(poke3, 200);
		animation.addFrame(poke4, 200);
		animation.addFrame(poke5, 200);
		animation.addFrame(poke6, 200);
		animation.addFrame(poke7, 200);
		animation.addFrame(poke8, 200);
		exit = new Image("./image/exit.gif");
		on = new Image("./image/sound/on.gif");
		off = new Image("./image/sound/off.gif");
		clicked = false;
		rank=new Image("./image/rank.gif");
		home = new Image("./image/home.gif");
		soundOn=true;
	}

	/**
	 * draw all the component to the screen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		// drawing methods
		// user's score
		font.drawString(150,150,"YOUR SCORE IS:",new Color(255,240,32));
		font.drawString(360-score.length()*15,340,score,new Color(255,215,0));
		// start a new game
		newgame.draw(200,530,300,90);
		// ranking
		rankk.draw(100,770,500,90);
		//animation
		animation.draw((float) (picposition/3),990,120,120);
		// exit
		exit.draw(640,0,60,60);
		// mute button
		if (soundOn) {
			on.draw(0,0,60,60);
		}else {
			off.draw(0,0,60,60);
		}
		rank.draw(70,0,60,60);
		home.draw(140,0,60,60);
	}

	/**
	 * update the data of the game and call render again
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		// position of animation
		picposition++;
		if(picposition>2200)
			picposition=0;
		input = gc.getInput();
		int xpos=Mouse.getX();
		int ypos=1200-Mouse.getY();
		// check any button is clicked
		boolean clicked1=input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		if (clicked1&&ypos>0 && ypos<60&&((xpos>0&&xpos<270)||(xpos>640&&xpos<700))) {
			clicked=true;
		}else if (!Mouse.isButtonDown(0)&&clicked&& ypos>0 && ypos<60) {
			// exit button
			if (xpos>640 && xpos<700) {
				sbg.enterState(PinBall.exit);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else if (xpos>0 && xpos<60){
				// mute button
				if (soundOn) {
					soundOn=false;
				}else {
					soundOn=true;
				}
			}else if (xpos>70 && xpos<130) {
				// leaderboard button
				Leaderboard.lastState = PinBall.gameend;
				sbg.getState(PinBall.leaderboard).init(gc, sbg);
				sbg.enterState(PinBall.leaderboard);
			}else if (xpos>140 && xpos<200) {
				// new game button
				sbg.enterState(PinBall.login);
			}
			clicked=false;
		}
		if (clicked1) {
			if (Mouse.getX() >= 200 && Mouse.getX() <= 500 && 1200-Mouse.getY() <= 640 && 1200-Mouse.getY() >= 550) {
				// start a new game button (on the middle)
				sbg.getState(PinBall.game).init(gc, sbg);
				sbg.enterState(PinBall.game);
			} else if (Mouse.getX() >= 100 && Mouse.getX() <= 600 && 1200-Mouse.getY() <= 840 && 1200-Mouse.getY() >= 750) {
				// looking at ranking button
				Leaderboard.lastState = PinBall.gameend;
				sbg.getState(PinBall.leaderboard).init(gc, sbg);
				sbg.enterState(PinBall.leaderboard);
			}
		}
	}

	@Override
	public int getID() {
		return 3;
	}

}