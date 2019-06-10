package ICS3U;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * handling the leaderboard
 *
 */
public class Leaderboard extends BasicGameState{
	
	public static int lastState=0;
	
	private java.awt.Font awtFont;
	private TrueTypeFont font;
	
	private java.awt.Font awtFont1;
	private TrueTypeFont font1;

	private int xs;
	private int ys;
	private int ye;
	private int lastY;
	private float lastCenterY;
	public static int player;
	private boolean clicked;
	private boolean mouseOn;
	private boolean move;
	private boolean stopMove;
	private boolean firstClick;
	
	private LinkedList<Line> lines;
	private float[] lastLineY;
	private Map<String,Integer> board;
	
	private Color gray = new Color(80,80,80);
	private Color gray1 = new Color(55,55,55);
	private Color gray2 = new Color(220,220,220);
	private Color gray3 = new Color(250,250,250);

	private Scanner sc;
	private static PrintWriter out;
	
	private Image[] number = new Image[15];
	private float[] lastNumberY = new float[15];

	private int start;
	private int end;
	
	private Rectangle rec;
	private Image back;
	private boolean clicked1;
	
	public Leaderboard(int leaderboard){
	}
	
	/**
	 * find the last key of the map
	 * @return the last key
	 */
	public String getLastKey() {
        final long count = board.entrySet().stream().count();
        return board.entrySet().stream().skip(count - 1).findFirst().get().getKey();
    }
	
	/**
	 * initialize the game screen
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		back = new Image("./image/return.gif");
		for (int i=1;i<=15;i++) {
			String s = "./image/number/"+i+".gif";
			number[i-1]=new Image(s);
			lastNumberY[i-1]=250+(i-1)*160;
		}
		try {
			sc = new Scanner (new File ("./leaderboard/leaderboard.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		awtFont = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 35);
		font = new TrueTypeFont(awtFont, false);		
		awtFont1 = new java.awt.Font("Arial", java.awt.Font.BOLD, 60);
		font1 = new TrueTypeFont(awtFont1, false);	
		
		board = new HashMap<>();
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			String s1= sc.nextLine();
			while (board.containsKey(s)) {
				s+=" ";
			}
			board.put(s, Integer.parseInt(s1));
		}
		sc.close();
		
		board = sortBoard(board);
		if (board.size()>15) {
			board.remove(getLastKey());
		}
		if (board.size()>5) {
			player = board.size();
		}else {
			player=5;
		}
		
		try {
			out = new PrintWriter (new FileWriter("./leaderboard/leaderboard.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Entry<String, Integer> line : board.entrySet()) {
			out.println(line.getKey().trim());
			out.println(Integer.toString(line.getValue()));
		}
		out.close();
		
		start=0;
		end=player;
		
		lines = new LinkedList<Line>();
		lastLineY = new float[15];
		for (int i=0;i<15;i++) {
			lines.add(new Line(50,370+i*160,650,370+i*160));
			lastLineY[i]=lines.get(i).getY();
		}
		
		clicked=false;
		rec = new Rectangle(620,240,30,(float) (730*800.0/(160*player)));
		lastCenterY=rec.getCenterY();
		mouseOn=false;
		move=false;
		ye=0;
		lastY=0;
		stopMove=false;
		firstClick=true;
		clicked1=false;
	}
	
	/**
	 * draw all the component to the screen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) throws SlickException {
	
		if (move) {
			if (Math.round(lastCenterY+ye-lastY-rec.getHeight()/2)>=240 && Math.round(lastCenterY+ye-lastY+rec.getHeight()/2)<=970) {
				rec.setCenterY(lastCenterY+ye-lastY);
				stopMove=false;
			}
			else if (!stopMove) {
				for (int i=0;i<15;i++) {
					lastNumberY[i]=lastNumberY[i]-(ye-lastY)*16*player/73;
				}
				for (int i=0;i<lastLineY.length;i++) {
					lastLineY[i]=lastLineY[i]-(ye-lastY)*16*player/73;
				}
				lastCenterY=rec.getCenterY();
				stopMove=true;

			}
		}
		
		gr.setColor(Color.lightGray);
		for (int i=0;i<lines.size();i++) {
			if (move && !stopMove) {
				lines.get(i).set(50,lastLineY[i]-(ye-lastY)*16*player/73,650,lastLineY[i]-(ye-lastY)*16*player/73);
			}else {
				lines.get(i).set(50,lastLineY[i],650,lastLineY[i]);
			}
			gr.draw(lines.get(i));
		}
		int i=0;
		for (Entry<String, Integer> line : board.entrySet()) {
			if (i>=start && i<end) {
				String s1=line.getKey().trim();
				String s2=Integer.toString(line.getValue());
				if (move&&!stopMove) {	
					font.drawString(274-s1.length()*9, lastNumberY[i]-(ye-lastY)*16*player/73+10, s1);
					font.drawString(494-s2.length()*9, lastNumberY[i]-(ye-lastY)*16*player/73+10, s2);
				}
				else {
					font.drawString(274-s1.length()*9, lastNumberY[i]+10, s1);
					font.drawString(494-s2.length()*9, lastNumberY[i]+10, s2);
				}
			}
			i++;
		}
		
		for (int i1=start;i1<end;i1++) {
			if (move&&!stopMove) {
				number[i1].draw(50,lastNumberY[i1]-(ye-lastY)*16*player/73,80,80);	
			}else {
				number[i1].draw(50,lastNumberY[i1],80,80);
			}
		}
		gr.setColor(Color.black);
		gr.fillRect(50, 0, 600, 160);
		gr.fillRect(50, 1010, 600, 190);

		gr.setColor(gray1);
		gr.fillRect(620, 160, 30, 900);
		font.drawString(620,205,"^",Color.lightGray);
		font.drawString(624,965,"v",Color.lightGray);
		if (clicked) {
			gr.setColor(gray3);
		}else if (mouseOn) {
			gr.setColor(gray2);
		}else {
			gr.setColor(Color.lightGray);
		}
		
		gr.fill(rec);
		
		gr.setColor(Color.white);
		gr.drawRect(50, 160, 600, 900);
		font1.drawString(110,50,"LEADER BOARD",Color.yellow);
		gr.setLineWidth(3);
		gr.fillRect(50, 160, 600, 50);
		font.drawString(230, 160, "player", gray);
		font.drawString(460, 160, "score", gray);
		gr.fillRect(50, 1010, 600, 50);
		
		back.draw(20,20,60,60);
	}
	
	/**
	 * update the data of the game and call render again
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		
		int ypos=1200-Mouse.getY();
		int xpos=Mouse.getX();
		Input input=gc.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)&&ypos>20 && ypos<80&&xpos>20&&xpos<80) {
			clicked1=true;
		}else if (!Mouse.isButtonDown(0)&&clicked1&& ypos>0 && ypos<60&&xpos>20&&xpos<80) {
			sbg.enterState(lastState);
		}
		move=false;
		xs=Mouse.getX();
		ys=1200-Mouse.getY();
		if (!clicked) {
			lastY=ys;
		}
		if (xs>=610&&xs<=650&&ys<=rec.getCenterY()+rec.getHeight()/2&&ys>=rec.getCenterY()-rec.getHeight()/2) {
			mouseOn=true;
		}else{
			mouseOn=false;
		}
		if(Mouse.isButtonDown(0)&&mouseOn) {
			clicked=true;
		}
		if (Mouse.isButtonDown(0)&&clicked) {
			if(firstClick) {
				stopMove=false;
				firstClick=false;
			}
			if (!stopMove) {
				ye=1200-Mouse.getY();
			}
			move=true;
		}
		if(!Mouse.isButtonDown(0)&&clicked){
			if (!stopMove) {
				for (int i=0;i<15;i++) {
					lastNumberY[i]=lastNumberY[i]-(ye-lastY)*16*player/73;
				}
				for (int i=0;i<lastLineY.length;i++) {
					lastLineY[i]=lastLineY[i]-(ye-lastY)*16*player/73;
				}
				lastCenterY=rec.getCenterY();
			}
			ye=0;
			lastY=0;
			clicked=false;
			move=false;
			stopMove=true;
			firstClick=true;
		}
		
	}
	@Override
	public int getID() {
		return 4;
	}
	
	/**
	 * sort the leaderboard
	 */
	public static Map<String, Integer> sortBoard(Map<String, Integer> board) {
	        return board.entrySet()
	                .stream()
	                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
	                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
	
	public static void write(String s, int i) {
		try {
			out = new PrintWriter (new FileWriter("./leaderboard/leaderboard.txt",true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(s);
		out.println(i);
		out.close();
	}

	
}