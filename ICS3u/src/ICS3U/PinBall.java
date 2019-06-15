package ICS3U;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * initialize of the game states
 */
public class PinBall extends StateBasedGame{
	public static final String pinball = "PinBall";
	public static final int menu = 0;
	public static final int game = 2;
	public static final int login = 1;
	public static final int gameend = 3;
	public static final int leaderboard = 4;
	public static final int exit = 5;
	public static final int foryou = 6;
	public static final int rule = 7;
	public static final int funny = 8;

	public static AppGameContainer appgc;

	/**
	 * add all the states to the game
	 * @param pinball - the name of the game
	 */
	public PinBall(String pinball) {
		super(pinball);
		this.addState(new Menu(menu));
		this.addState(new Game(game));
		this.addState(new Login(login));
		this.addState(new Leaderboard(leaderboard));
		this.addState(new GameEnd(gameend));
		this.addState(new Exit(exit));
		this.addState(new ForYou(foryou));
		this.addState(new Rule(rule));
		this.addState(new Funny(funny));
		Game.soundOn=true;
	}

	/**
	 * initialize every states
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		this.enterState(menu);
	}
	
	/**
	 * create a app game container with the scale 700*1200
	 * @param args - arguments
	 * @throws SlickException - slick2d
	 */
	public static void main (String[] args) throws SlickException {	
		appgc = new AppGameContainer(new PinBall(pinball));
		appgc.setDisplayMode(700, 1200, false);
		appgc.setIcon("./image/icon.gif");
		appgc.start();
	}
}
