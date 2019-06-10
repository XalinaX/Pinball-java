package ICS3U;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.*;

import org.lwjgl.input.*;

/**
 *all the operations in the game
 * @author alina,amy
 */
public class Game extends BasicGameState{
	/**the white balls that fly around the screen*/
	public static LinkedList<Ball> balls;
	public static LinkedList<PolyBlock> polyblocks;
	public static LinkedList<Wall> walls;
	public static LinkedList<CircleBlock> cirblocks;
	public static Vec2d collision;
	public static LinkedList<SpecialBlock> specialblocks;
	
	public static int userScore;
	public static String username;
	public static int num;
	
	public static Circle homeB;
	public static Circle musicB;
	protected Circle stopB;
	
	public static int xpos;
	public static int ypos;
	public static int start;
	public static int stopCount;
	public static int time;
	public static int round;
	public static boolean hasSpecial;

	private java.awt.Font awtFont;
	private TrueTypeFont font;
	private java.awt.Font awtFont1;
	private TrueTypeFont font1;
	
	public static Image exit;
	public static Image on;
	public static Image off;
	public static Image home;
	public static Image rank;
	public static Image restart;
	public static Image help;
	public static Image boom;
	public static Image shining;
	public static Image boom1;
	public static Image shining1;
	public static Image fireball;
	public static float fx;
	public static float fy;
	public static int fwidth;
	public static int fheight;
	
	public static Image[] disappears;
	public static LinkedList<Integer> disappear;
	public static LinkedList<Vec2d> disappearPos;
	
	public static boolean soundOn;
	public static boolean clicked;
	public static boolean boomOn;
	public static boolean shiningOn;
	public static boolean fireballOn;

	public static boolean gameover;
	public static boolean gameover1;
	public static boolean draw;
	public static Image over;
	public static Image over1;
	
	public Game(int game) {
		username="";
	}

	/**
	 * initialize the game screen
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		exit = new Image("./image/exit.gif");
		on = new Image("./image/sound/on.gif");
		off = new Image("./image/sound/off.gif");
		clicked = false;
		rank=new Image("./image/rank.gif");
		home = new Image("./image/home.gif");
		restart = new Image("./image/new.gif");
		help = new Image("./image/help.gif");
		soundOn=true;
		gameover = false;
		gameover1 = false;
		over = new Image("./image/over.gif");
		over1 = new Image("./image/over1.gif");
		draw=false;
		boom = new Image("./image/boom.gif");
		shining = new Image("./image/shining.gif");
		boom1 = new Image("./image/boom1.gif");
		shining1 = new Image("./image/shining1.gif");
		boomOn = true;
		shiningOn = true;
		hasSpecial=false;
		fireball = new Image("./image/fireball.gif");
		fireballOn=false;
		fx=0;
		fy=990;
		fwidth = 130;
		fheight =60;
		disappear = new LinkedList<Integer>();
		disappearPos = new LinkedList<Vec2d>();
		disappears = new Image[8];
		for (int i=1;i<9;i++) {
			String s = "./image/disappear/"+i+".gif";
			disappears[i-1]=new Image(s);
		}
		
		awtFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 20);
		font = new TrueTypeFont(awtFont, false);
		awtFont1 = new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 35);
		font1 = new TrueTypeFont(awtFont1, false);
		userScore=0;
		//add the walls
		walls = new LinkedList<Wall>();
		walls.add(new Wall(50,100,50,1050,false));
		walls.add(new Wall(650,1050,650,100,false));
		walls.add(new Wall(50,220,650,220,false));
		walls.add(new Wall(50,100,310,200,true));
		walls.add(new Wall(650,100,390,200,true));
		walls.add(new Wall(50,1050,300,1150,true));
		walls.add(new Wall(650,1050,400,1150,true));
		walls.add(new Wall(0,0,100,0,true));
		walls.add(new Wall(700,0,600,0,true));
		walls.add(new Wall(130,0,130,10,true));
		walls.add(new Wall(570,0,570,10,true));
		walls.add(new Wall(1,1200,1,1080,true));
		walls.add(new Wall(699,1200,699,1080,true));
		walls.add(new Wall(350,1200,70,1200,true));
		walls.add(new Wall(350,1200,630,1200,true));

		//add the balls
		balls = new LinkedList<Ball>();
		for (int i=0;i<8;i++) {
			balls.add(new Ball(200+i*40,100,0,1.1));
		}
		
		round=0;
		//add the blocks(circle and polygon and special
		specialblocks = new LinkedList<SpecialBlock>();
		polyblocks = new LinkedList<PolyBlock>();
		cirblocks = new LinkedList<CircleBlock>();
		for (int i=0;i<2;i++) {
			num = (int)(Math.random()*4+1);
			generateBlocks(num, 860+i*110,905+i*110);
		}
		start=2;
	}

	/**
	 * draw all the component to the screen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(font1);
		g.setColor(new Color(200,210,250));
		g.drawString("player:",50,130);
		g.drawString(username,160-username.length()*8,180);
		g.drawString("score:",560,130);
		g.drawString(Integer.toString(userScore),540-Integer.toString(userScore).length()*8,180);

		//draw wall
		g.setLineWidth(4);
		for (int i=0; i<walls.size(); i++) {
			if (i!=2) {
				Wall w = walls.get(i);
				g.setColor(w.color);
				g.draw(w.line);
			}
		}
		g.drawLine(310,200,310,217);
		g.drawLine(390,200,390,217);
		
		//deadline.y=225
		g.setLineWidth(2);
		for(int i=0;i<20;i++) {
			if (i!=10&&i!=9)
				g.drawLine(53+i*30,223,i*30+72,223);
		}
		
		//draw balls
		for (int i=0; i<balls.size(); i++) {
			Ball c = balls.get(i);
			g.setColor(Color.white);
			if ((c.stop || c.px<40 ||c.px>660 || c.py>1165 || (10/27.0*c.px+2300/27.0>c.py+20&&350>=c.px) || (3100/9.0-10/27.0*c.px>c.py+20&&350<=c.px)) && start!=-1 &&start!=0) {
				g.setColor(Color.yellow);
			}
			g.draw(c.cir);
			g.fill(c.cir);
		}
		
		g.setFont(font);
		//draw polygons
		for (int i=0; i<polyblocks.size(); i++) {
			PolyBlock p = polyblocks.get(i);
			p.setColor();
			g.setColor(p.color);
			g.draw(p.poly);
			g.fill(p.poly);
			g.setColor(Color.black);
			g.drawString(p.s,p.poly.getCenterX()-3*(2+(int)Math.log10(p.score)), p.poly.getCenterY()-10);
		}
		
		for (int i=0; i<disappear.size(); i++) {
			int num = disappear.get(i);
			Vec2d pos = disappearPos.get(i);
			disappears[num/20].draw(pos.x-(80+num/4)/2,pos.y-(80+num/4)/2,80+num/4,80+num/4,new Color(255,255,0));
			disappear.remove(i);
			if ((num+1)/20<8) {
				disappear.add(i, num+1);
			}else {
				disappearPos.remove(i);
			}
		}
		
		//draw circles
		for (int i=0; i<cirblocks.size(); i++) {
			CircleBlock d = cirblocks.get(i);
			d.setColor();
			g.setColor(d.color);
			g.draw(d.cir);
			g.fill(d.cir);
			g.setColor(Color.black);
			g.drawString(d.s,d.cir.getCenterX()-3*(2+(int)Math.log10(d.score)), d.cir.getCenterY()-10);
		}
		
		for (int i=0; i<specialblocks.size(); i++) {
			SpecialBlock s = specialblocks.get(i);
			if (s.add && s.larger) {
				s.boom2.draw(s.xi, s.yi, s.width, s.height);
			} else if (s.add) {
				s.addi.draw(s.xi,s.yi,s.width,s.height);
			}else if (s.larger) {
				s.largeri.draw(s.xi,s.yi,s.width,s.height);
			}
		}
		
		//list of balls
		if (start==0) {
			g.setColor(new Color(190,220,240));
			for (float i=0;i<10;i++) {
				Circle circle = new Circle((xpos-350)*i/10+350,(ypos-235)*i/10+220,6);
				g.draw(circle);
				g.fill(circle);
			}
		}
		
		exit.draw(640,0,60,60);
		if (soundOn) {
			on.draw(0,0,60,60);
		}else {
			off.draw(0,0,60,60);
		}
		rank.draw(70,0,60,60);
		help.draw(140,0,60,60);
		home.draw(210,0,60,60);
		restart.draw(280,0,60,60);
		
		if (draw) {
			Leaderboard.write(username, userScore);
			GameEnd.score =Integer.toString(userScore);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sbg.enterState(PinBall.gameend);
		}
		if (gameover) {
			over.draw(25,500,650,140);
			draw=true;
		}
		if (gameover1) {
			over1.draw(10,300,70,70);
			over1.draw(620,300,70,70);
		}
		
		
		if (boomOn) {
			boom.draw(10,1080,80,110);
		}else {
			boom1.draw(10,1080,80,110);
		}
		
		if (shiningOn) {
			shining.draw(610,1110,80,80);
		}else {
			shining1.draw(610,1110,80,80);
		}
		
		if (fireballOn) {
			fireball.draw(fx,fy,fwidth,fheight);
		}
		g.setColor(Color.white);
		g.drawString(Integer.toString(stopCount), 70, 40);
		g.drawString(Integer.toString(start), 70, 60);
	}

	/**
	 * update the data of the game and call render again
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		gameover1=false;
		for (int ind=0;ind<polyblocks.size();ind++) {
			if (polyblocks.get(ind).poly.getCenterY()<260) {
				gameover=true;
			}
			if (polyblocks.get(ind).poly.getCenterY()<370) {
				gameover1=true;
			}
		}
		for (int ind=0;ind<cirblocks.size();ind++) {
			if (cirblocks.get(ind).cir.getCenterY()<260) {
				gameover=true;
			}
			if (cirblocks.get(ind).cir.getCenterY()<370) {
				gameover1=true;
			}
		}
		for (int ind=0;ind<specialblocks.size();ind++) {
			if (specialblocks.get(ind).yi<260) {
				specialblocks.remove(specialblocks.get(ind));
			}
		}
		xpos=Mouse.getX();
		ypos=1200-Mouse.getY();
		Input input = gc.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)&&((ypos>0 && ypos<60&&((xpos>0&&xpos<340)||(xpos>640&&xpos<700)))||
				(start!=1&&start!=2&&ypos>1110 && ypos<1190&&((xpos>10&&xpos<90)||(xpos<690&&xpos>610))))) {
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
			}else if (xpos>70 && xpos<130) {
				Leaderboard.lastState = PinBall.game;
				sbg.getState(PinBall.leaderboard).init(gc, sbg);
				sbg.enterState(PinBall.leaderboard);
			}else if (xpos>140 && xpos<200) {
				Rule.lastState=PinBall.game;
				sbg.enterState(PinBall.rule);
			}
			else if (xpos>210 && xpos<270) {
				sbg.enterState(PinBall.login);
			}else if (xpos>280 && xpos<340) {
				sbg.getState(PinBall.game).init(gc, sbg);
			}
			clicked=false;
		}else if (!Mouse.isButtonDown(0)&&clicked&& ypos>1110 && ypos<1190&&start!=1&&start!=2){
			if (xpos>10&&xpos<90) {
				if (boomOn) {
					boomOn=false;
					float x, y;
					int i = (int) (Math.random() * (polyblocks.size() + cirblocks.size() - 1));
					if (i < polyblocks.size()) {
						x = polyblocks.get(i).poly.getCenterX();
						y = polyblocks.get(i).poly.getCenterY();
						polyblocks.remove(i);
					} else {
						x = cirblocks.get(i - polyblocks.size()).cir.getCenterX();
						y = cirblocks.get(i - polyblocks.size()).cir.getCenterY();
						cirblocks.remove(i - polyblocks.size());
					}
					specialblocks.add(new SpecialBlock(x, y, true, true));
				}
			}else if (xpos<690&&xpos>610) {
				if (shiningOn) {
					shiningOn = false;
					fireballOn=true;
				}
			}
			clicked=false;
		}
		if (fireballOn) {
			if(fx>706) {
				fireballOn=false;
				for (int ind=0;ind<polyblocks.size();ind++) {
					PolyBlock p = polyblocks.get(ind);
					p.setLocation(-110);
				}
				
				for (int ind=0;ind<cirblocks.size();ind++) {
					CircleBlock d = cirblocks.get(ind);
					d.setLocation(-110);
				}
				for (int i1=0; i1<specialblocks.size(); i1++) {
					SpecialBlock s = specialblocks.get(i1);
					s.setLocation(-110);
				} 
				for (int i=0; i<disappear.size(); i++) {
					disappearPos.get(i).y-=110;
				}
			}
			fx+=2;
			deleteLine();
		}
		if (start==2) {
			stopCount=0;
			for (int i=0; i<balls.size(); i++) {
				Ball c = balls.get(i);
				c.px+=c.vx;
				c.py+=c.vy;
				c.cir.setCenterX(c.px);
				c.cir.setCenterY(c.py);
				for (int ind=0; ind<walls.size(); ind++) {
					Wall w = walls.get(ind);
					if (ind==2) {
						if (c.py>350) {
							if (c.iscollideWithWall(w.vertex)) {
								c.collide(collision,w.elastic);
								break;
							}
						}
					}else {
						if (c.iscollideWithWall(w.vertex)) {
							c.collide(collision,w.elastic);
							break;
						}
					}
				}
				for (int ind=0; ind<balls.size(); ind++) {
					Ball c1 = balls.get(ind);
					if ((10/27.0*c1.px+2300/27.0>=c1.py || 3100/9.0-10/27.0*c1.px>=c1.py) && c1.px>=55 && c1.px<=645) {
						if (c!=c1) {
							c.collideWithBall(c1);
						}
					}
					if (c.py>=180 && c.py<=220 && c.px<450 && c.px>250) {
						c.px-=c.vx;
						c.py-=c.vy;
						c.cir.setCenterX(c.px);
						c.cir.setCenterY(c.py);
					}
					if ((10/27.0*c.px+2300/27.0<c.py+c.radius&&350>=c.px&&50<c.px) || (3100/9.0-10/27.0*c.px<c.py+c.radius&&350<=c.px&&c.px<650)) {
						c.py-=2;
						c.cir.setCenterY(c.py);
					}
				}
				if (c.py<0) {
					c.stop=true;
				}
				if (c.px<c.radius) {
					c.px=c.radius;
					c.cir.setCenterX(c.px);
				}else if (c.px>700-c.radius) {
					c.px=700-c.radius;
					c.cir.setCenterX(c.px);
				}
				if (c.py<=c.radius) {
					c.py=c.radius;
					c.cir.setCenterY(c.py);
				}else if (c.py>1200-c.radius) {
					c.py=1200-c.radius;
					c.cir.setCenterY(c.py);
				}
				if (c.px==Float.NaN || c.py==Float.NaN) {
					c.setLocation(100, 50);
					c.setVelocity(0.6,0.6);
				}
				if (c.vx==Double.NaN || c.vy==Double.NaN) {
					c.setLocation(100, 100);
					c.setVelocity(0.6,0.6);
				}
				if (c.py<=80 && (c.vy<0.8 || (c.vx*c.vx+c.vy*c.vy<1))){
					c.vy+=0.0025;
				}
				if ((c.px<35 || c.px>665) && (c.vy>-0.8 || (c.vx*c.vx+c.vy*c.vy<1))) {
					c.vy-=0.0015;
				}
				if (c.stop) {
					stopCount++;
				}
			}
			if (stopCount==balls.size()) {
				start=-1;
				for (int ind=0;ind<polyblocks.size();ind++) {
					polyblocks.get(ind).setLocation(110);
				}
				
				for (int ind=0;ind<cirblocks.size();ind++) {
					cirblocks.get(ind).setLocation(110);
				}
				for (int i1=0; i1<specialblocks.size(); i1++) {
					specialblocks.get(i1).setLocation(110);
				}
				num = (int)(Math.random()*6+1);
				generateBlocks(num, 970,1015);
			}
		}
		//mouse clicked
		if (Mouse.isButtonDown(0) && start!=1&& start!=2) {
			start=-1;
			xpos=Mouse.getX();
			ypos=1200-Mouse.getY();
			if (ypos>225 && ypos<1050) {
				start=0;
				stopCount=0;
			}
		}
		//mouse released
		else if (start==0) {
			start=1;
			stopCount=0;
			for (int i=0; i<balls.size(); i++) {
				balls.get(i).setVelocity(xpos-350, ypos-200);
				balls.get(i).stop=false;
				balls.get(i).firstTime=true;
				time=0;
				num=300;
			}
		}
		//start the collisions
		if(start==1){
			stopCount=0;
			for (int i=0; i<balls.size(); i++) {
				time++;
				if(!(i*i*80+i*100<time)) {
					break;
				}
				Ball c = balls.get(i);
				if (c.firstTime) {
					c.setLocation(350,220+c.radius);
					c.firstTime=false;
				}
				if (c.vy<0 && c.px>55 && c.px<645) {
					c.vy+=0.003;
				}
				else if (c.vy>=0 && c.px>55 && c.px<645 && c.hasCollide) {
					if (c.py>=350 && c.py<=1140 && (c.vy<0.8 || (c.vx*c.vx+c.vy*c.vy<1))) {
						c.vy+=0.003;
					}else if (c.py<=80 && (c.vy<0.8 || (c.vx*c.vx+c.vy*c.vy<1))){
						c.vy+=0.0025;
					}else if(c.vy<0.8 || (c.vx*c.vx+c.vy*c.vy<1)) {
						c.vy+=0.0015;
					}
				}
				if ((c.px<35 || c.px>665) && (c.vy>-0.8 || (c.vx*c.vx+c.vy*c.vy<1))) {
					c.vy-=0.0015;
				}
				c.px+=c.vx;
				c.py+=c.vy;
				c.cir.setCenterX(c.px);
				c.cir.setCenterY(c.py);
				for (int ind=0; ind<walls.size(); ind++) {
					Wall w = walls.get(ind);
					if (ind==2) {
						if (c.py>220) {
							if (c.iscollideWithWall(w.vertex)) {
								c.collide(collision,w.elastic);
								break;
							}
						}
					}else {
						if (c.iscollideWithWall(w.vertex)) {
							c.collide(collision,w.elastic);
							break;
						}
					}
				}
				for (int ind=0;ind<polyblocks.size();ind++) {
					PolyBlock p = polyblocks.get(ind);
					if (c.iscollideWithPolygon(p.vertex)) {
						p.collide(c.mass);
						c.collide(collision,false);
						userScore++;
					}
				}
				
				for (int ind=0;ind<cirblocks.size();ind++) {
					CircleBlock d = cirblocks.get(ind);
					if (c.iscollideWithCircle(d)) {
						d.collide(c.mass);
						c.collide(collision,false);
						userScore++;
					}
				}
				for (int i1=0; i1<specialblocks.size(); i1++) {
					SpecialBlock s = specialblocks.get(i1);
					if (c.iscollideWithSpecial(s)) {
						s.collide();
						if (s.add&&s.larger) {
							//just make sure s is not the boom
						}else if (s.add) {
							balls.add(new Ball(200,50,0,1));
						}else if (s.larger) {
							c.setMass(20, 2);
						}
					}
				}
				if (c.stop) {
					stopCount++;
				}
				if (((10/27.0*c.px+2300/27.0>c.py&&350>=c.px) || (3100/9.0-10/27.0*c.px>c.py&&c.px>=350)) && c.px>55 && c.px<645) {
					
					if (c.py<=220 && c.px<450 && c.px>250) {
						c.px-=c.vx;
						c.py-=c.vy;
						c.cir.setCenterX(c.px);
						c.cir.setCenterY(c.py);
						c.stop=true;
					}
					if (10/27.0*c.px+2300/27.0<c.py+c.radius || 3100/9.0-10/27.0*c.px<c.py+c.radius) {
						c.py-=1;
						c.cir.setCenterY(c.py);
					}
					for (int ind=0; ind<balls.size(); ind++) {
						Ball c1 = balls.get(ind);
						if ((10/27.0*c1.px+2300/27.0>c1.py || 3100/9.0-10/27.0*c1.px>c1.py) && c1.px>55 && c1.px<645) {
							if (c!=c1) {
								c.collideWithBall(c1);
							}
						}
					}
				}		
				if (c.py<0) {
					c.stop=true;
				}
				if (c.px<c.radius) {
					c.px=c.radius;
					c.cir.setCenterX(c.px);
				}else if (c.px>700-c.radius) {
					c.px=700-c.radius;
					c.cir.setCenterX(c.px);
				}
				if (c.py<=c.radius) {
					c.py=c.radius;
					c.cir.setCenterY(c.py);
				}else if (c.py>1200-c.radius) {
					c.py=1200-c.radius;
					c.cir.setCenterY(c.py);
				}
				if (c.px==Float.NaN || c.py==Float.NaN) {
					c.setLocation(100, 100);
					c.setVelocity(0.6,0.6);
				}
				if (c.vx==Double.NaN || c.vy==Double.NaN) {
					c.setLocation(100, 100);
					c.setVelocity(0.6,0.6);
				}
			}
			if (stopCount==balls.size()) {
				start=2;
				for (int i=0; i<balls.size(); i++) {
					balls.get(i).stop=false;
					balls.get(i).setVelocity(0,1);
					balls.get(i).hasCollide=false;
				}
				
			}
		}
	}

	/**
	 * the ID of this game state
	 */
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	/**
	 * generate some blocks
	 * @param num - the number of blocks
	 * @param y1 - the start location of the blocks
	 * @param y2 - the end location of the blocks
	 * @throws SlickException 

	 */
	public static void generateBlocks(int num, int y1, int y2) throws SlickException {
		int n=(int)(Math.random()*num+1);
		hasSpecial=false;
		generatePolyBlocks(n, y1,y2);
		generateCirBlocks(num-n, y1,y2);
		Game.round++;
		if (round%2==1 && !hasSpecial && polyblocks.size()>0) {
			double n1 = Math.random();
			if(n1>=0.65) {
				specialblocks.add(new SpecialBlock(polyblocks.getLast().poly.getCenterX(),polyblocks.getLast().poly.getCenterY(),true,false));
				polyblocks.removeLast();
				hasSpecial = true;
			}else if (n1<=0.45) {
				specialblocks.add(new SpecialBlock(polyblocks.getLast().poly.getCenterX(),polyblocks.getLast().poly.getCenterY(),false,true));
				polyblocks.removeLast();
				hasSpecial = true;
			}
		}
	}
	
	/**
	 * generate some circle blocks
	 * @param num - the number of blocks
	 * @param y1 - the start location of the blocks
	 * @param y2 - the end location of the blocks
	 * @throws SlickException 
	 */
	public static void generatePolyBlocks(int num, int y1, int y2) throws SlickException {
		int i=0;
		if(num>4) {
			num=4;
		}
		int i2=0;
		while (i<num && i2<500) {
			boolean collidee=false;
			int y=(int)(Math.random()*(y2-y1+1)+y1);
			int x = (int)(Math.random()*441+130);
			int side = (int)(Math.random()*4+3);
			double angle  = Math.random()*180-90;
			PolyBlock p = new PolyBlock(x,y,side,angle,round);
			for (int i1=(polyblocks.size()-6-i+Math.abs(polyblocks.size()-6-i))/2;i1<polyblocks.size();i1++) {
				if(p.iscollideWithPolygon(polyblocks.get(i1))){
					collidee=true;
					break;
				}
			}
			if (!collidee) {
				for (int i1=(cirblocks.size()-6+Math.abs(cirblocks.size()-6))/2;i1<cirblocks.size();i1++) {
					if(p.iscollideWithCircle(cirblocks.get(i1))){
						collidee=true;
						break;
					}
				}
			}
			if (!collidee) {
				polyblocks.add(p);
				i++;
			}
			i2++;
		}
	}
	
	/**
	 * generate some polygon blocks
	 * @param num - the number of blocks
	 * @param y1 - the start location of the blocks
	 * @param y2 - the end location of the blocks
	 * @throws SlickException 
	 */
	public static void generateCirBlocks(int num, float y1, float y2) throws SlickException {
		int i=0;
		if(num>4) {
			num=4;
		}
		int i2=0;
		while (i<num&&i2<500) {
			boolean collidee=false;
			int y=(int)(Math.random()*(y2-y1+1)+y1);
			int x = (int)(Math.random()*451+125);
			CircleBlock c = new CircleBlock(x,y,round);
			for (int i1=(polyblocks.size()-6+Math.abs(polyblocks.size()-6))/2;i1<polyblocks.size();i1++) {
				if(c.iscollideWithPolygon(polyblocks.get(i1).vertex)){
					collidee=true;
					break;
				}
			}
			if (!collidee) {
				for (int i1=(cirblocks.size()-6-i+Math.abs(cirblocks.size()-6-i))/2;i1<cirblocks.size();i1++) {
					if(c.iscollideWithCircle(cirblocks.get(i1))){
						collidee=true;
						break;
					}
				}
			}
			if (!collidee) {
				cirblocks.add(c);
				i++;
			}
			i2++;
		}
		
	}
	
	/**
	 * delete the blocks on the last line
	 */
	public static void deleteLine() {
		for (int ind=0;ind<polyblocks.size();ind++) {
			PolyBlock p = polyblocks.get(ind);
			p.collideWithFireball();
		}
		
		for (int ind=0;ind<cirblocks.size();ind++) {
			CircleBlock d = cirblocks.get(ind);
			d.collideWithFireball();
		}
		for (int i1=0; i1<specialblocks.size(); i1++) {
			SpecialBlock s = specialblocks.get(i1);
			s.collideWithFireball();
		} 
	}

}
