package ICS3U;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * display the rule of the game
 *
 */
public class Rule extends BasicGameState {
	int i = 1200;
	public static int lastState;
	private java.awt.Font awtFont;
	private TrueTypeFont font;
	Input input;
	public static Image rule;
	public static Image back;
	public static Image boom;
	public static Image shining;
	protected Image largeri;
	protected Image addi;
	
	public Rule(int i) {

	}

	/**
	 * initialize
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		awtFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 31);
		font = new TrueTypeFont(awtFont, false);
		rule = new Image("./image/rules.png");
		back = new Image("./image/back.png");
		boom = new Image("./image/boom.gif");
		shining = new Image("./image/shining.gif");
		largeri = new Image("./image/larger.gif");
		addi = new Image("./image/add.gif");



	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		//print the rules
		rule.draw(220,33,250,100);

		font.drawString(40, i,"START");
		font.drawString(40, i+30,"- Press the mouse left button down to",Color.lightGray);
		font.drawString(40, i+60,"  see the route of balls.",Color.lightGray);
		font.drawString(40, i+90,"- Release the mouse to let balls drop.",Color.lightGray);
		
		font.drawString(40, i+125, "GAME OVER");
		font.drawString(40, i+155, "- When any block hits the dotted line on", Color.lightGray);
		font.drawString(40, i+185, "  the top, game over.", Color.lightGray);
		

		font.drawString(40, i+220,"BALL");
		font.drawString(40, i+250, "- The mass of the small ball is one,", Color.lightGray);
		font.drawString(40, i+280, "  the mass of the large ball if two.", Color.lightGray);
		font.drawString(40, i+310, "- There are 8 balls at the beginning.", Color.lightGray);
		
		font.drawString(40, i+345,"BLOCK");
		font.drawString(40, i+375, "- When collide with balls,", Color.lightGray);
		font.drawString(40, i+405, "  the number on blocks will decrease", Color.lightGray);
		font.drawString(40, i+435, "  (subtract with the mass of the ball.", Color.lightGray);
		font.drawString(40, i+465, "- When the number on blocks become zero", Color.lightGray);
		font.drawString(40, i+495, "  the block will disappear.", Color.lightGray);

		font.drawString(40, i+530,"SPECIAL BLOCK");
		font.drawString(40, i+560, "- There are 2 types of special block.", Color.lightGray);
		font.drawString(80, i+590, "  can add a ball to the game,", Color.lightGray);
		font.drawString(80, i+620, "  can increase the mass of the ball to", Color.lightGray);
		font.drawString(40, i+650, "  two (if the mass is one).", Color.lightGray);

		
		font.drawString(40, i+685, "BOOM");
		font.drawString(40, i+715, "- Able to remove the blocks in an area with", Color.lightGray);
		font.drawString(40, i+745, "  certain radius around the boom.", Color.lightGray);

		font.drawString(40, i+780, "LIGHTENING BUTTON");
		font.drawString(40, i+810, "- Able to remove the blocks on the last line.", Color.lightGray);
		font.drawString(40, i+850, "** The boom and the lightening button", Color.lightGray);
		font.drawString(40, i+880, "can only be used once. **", Color.lightGray);

		boom.draw(150,i+676,35,50);
		shining.draw(385,i+778,40,40);
		addi.draw(57,i+595,31,31);
		largeri.draw(57,i+629,31,31);
		
		if (i <= 142) {
			back.draw(250,1085,200,83);

		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		// return to the last page		
		if (i > 142)
			i-=2;
		input = gc.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			if (Mouse.getX() < 450 && Mouse.getX() > 250 && 1200-Mouse.getY()> 1085 && 1200-Mouse.getY() < 1168) {
				sbg.enterState(lastState);
			}
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 7;
	}

}
