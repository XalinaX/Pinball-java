package ICS3U;

import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.gui.*;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * enter the user name
 *
 */
public class Login extends BasicGameState implements ComponentListener{
	
	private Input input;
	protected TextField text;
	protected Ball ball1;
	protected Ball ball2;
	protected Polygon poly1;
	protected Polygon poly2;
	protected Polygon poly3;
	protected Polygon poly4;
	private java.awt.Font awtFont;
	private TrueTypeFont font;
	private java.awt.Font awtFont1;
	private TrueTypeFont font1;
	private String str;

	public static Image exit;
	public static Image home;
	public static Image rank;
	public static Image enter;
	public static Image help;
	
	public static boolean clicked;
	public static boolean clicked1;


	public Login(int login) {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * draw each component on the screen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.setFont(font);
		font.drawString(140, 300,"What is your name?", new Color(200,200,200));
		g.setColor(new Color(147,112,219));
		g.setFont(font1);
		g.drawString(str, 180,600);
		
		text.render(gc,new Graphics());
		
		g.setColor(Color.white);
		g.fill(ball1.cir);
		g.draw(ball1.cir);
		g.fill(ball2.cir);
		g.draw(ball2.cir);
		g.setColor(Color.red);
		g.fill(poly1);
		g.setColor(Color.orange);
		g.fill(poly2);
		g.setColor(Color.blue);
		g.fill(poly3);
		g.setColor(Color.cyan);
		g.fill(poly4);
		
		exit.draw(640,0,60,60);
		rank.draw(70,0,60,60);
		enter.draw(200,750,300,110);
		help.draw(140,0,60,60);
	}
	/**
	 * initialize each component
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		exit = new Image("./image/exit.gif");
		clicked = false;
		rank=new Image("./image/rank.gif");
		enter = new Image("./image/enter.png");
		help = new Image("./image/help.gif");
		clicked1=false;
		
		str="";
		awtFont = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 45);
		font = new TrueTypeFont(awtFont, false);
		awtFont1 = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 27);
		font1 = new TrueTypeFont(awtFont1, false);

		text = new TextField(gc, font,200, 510, 300, 60,this);
		text.setFocus(true);
		text.setCursorVisible(true);
        text.setTextColor(Color.black);
		text.setBackgroundColor(new Color(240,240,240));
		text.setBorderColor(Color.yellow);
        text.isAcceptingInput();
        text.setMaxLength(10);
        

		ball1 = new Ball(100, 100, 0, 0);
		ball2 = new Ball(600, 700, 0, 0);
		poly1 = new Polygon();
		poly1.addPoint(70, 70);
		poly1.addPoint(70,130);
		poly1.addPoint(130,70);
		poly2 = new Polygon();
		poly2.addPoint(630, 70);
		poly2.addPoint(630, 130);
		poly2.addPoint(570, 70);
		poly3 = new Polygon();
		poly3.addPoint(630, 1130);
		poly3.addPoint(630, 1070);
		poly3.addPoint(570, 1130);
		poly4 = new Polygon();
		poly4.addPoint(70, 1130);
		poly4.addPoint(70, 1070);
		poly4.addPoint(130, 1130);


	}
/**
 * update position
 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg1) throws SlickException {
		// get mouse position
		int xpos=Mouse.getX();
		int ypos=1200-Mouse.getY();
		input = gc.getInput();
		clicked1=input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		if (clicked1&&ypos>0 && ypos<60&&((xpos>0&&xpos<200)||(xpos>640&&xpos<700))) {
			clicked=true;
		}else if (!Mouse.isButtonDown(0)&&clicked&& ypos>0 && ypos<60) {
			if (xpos>640 && xpos<700) {
				// exit button
				sbg.enterState(PinBall.exit);
			}else if (xpos>70 && xpos<130) {
				// leaderboard button
				Leaderboard.lastState = PinBall.login;
				sbg.getState(PinBall.leaderboard).init(gc, sbg);
				sbg.enterState(PinBall.leaderboard);
			}else if (xpos>140 && xpos<200) {
				// rule button
				Rule.lastState=PinBall.login;
				sbg.enterState(PinBall.rule);
			}
			clicked = false;
		}
		text.setFocus(true);
		text.setCursorVisible(true);
        text.isAcceptingInput();

		if(clicked1) {
			//enter game
			if(xpos>=200&&xpos<=500&&ypos<=860&&ypos>=750) {
				Game.username = text.getText();
				if (!Game.username.equals("")) {
					sbg.enterState(PinBall.game);
				}else{
					str="please enter your name";
				}
				try {
					TimeUnit.MILLISECONDS.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if(input.isKeyPressed(Input.KEY_ENTER)) {
			if (!Game.username.equals("")) {
				sbg.getState(PinBall.game).init(gc, sbg);
				sbg.enterState(PinBall.game);
			}
			else {
				str="please enter your name";
			}
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// update balls' movement
		if(ball1.px<=600&&ball1.py<=100)
			ball1.px++;
		else if(ball1.px>=100 && ball1.py>=1100)
			ball1.px--;
		else if (ball1.px<=100 && ball1.py>=100)
			ball1.py--;
		else if (ball1.px>=600 && ball1.py<=1100)
			ball1.py++;
		
		if(ball2.px<=600&&ball2.py<=100)
			ball2.px++;
		else if(ball2.px>=100 && ball2.py>=1100)
			ball2.px--;
		else if (ball2.px<=100 && ball2.py>=100)
			ball2.py--;
		else if (ball2.px>=600 && ball2.py<=1100)
			ball2.py++;
		ball1.cir.setCenterX(ball1.px);
		ball1.cir.setCenterY(ball1.py);
		ball2.cir.setCenterX(ball2.px);
		ball2.cir.setCenterY(ball2.py);
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void componentActivated(AbstractComponent value) {
		// TODO Auto-generated method stub
		Game.username = text.getText();
	}
	
}
