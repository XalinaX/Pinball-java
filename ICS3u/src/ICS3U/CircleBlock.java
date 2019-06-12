package ICS3U;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

/**
 * generating circle block
 */
public class CircleBlock {
	protected final int RADIUS = 34; // fixed radius
	protected int score;
	protected String s;
	protected float x;// horizontal position of the centre
	protected float y;// vertical position of the centre
	protected Circle cir;
	protected Color color;
	
	/**
	 * constructor
	 * @param x - centerX of the circle
	 * @param y - centerY of the circle
	 * @param round - the round of the game
	 */
	public CircleBlock(float x, float y, int round) {
		this.x = x;
		this.y = y;
		setColor();
		cir = new Circle(this.x, this.y, this.RADIUS);
		generateScore(round);
	}
	
	/**
	 * set the color
	 * Color will change based on its score
	 */
	public void setColor() {
		if (score<=10) {
			this.color=new Color(247,207+score*4,71);
		}else if (score<=32) {
			this.color=new Color(247-(score-10)*8,247,71);
		}else if (score<=54) {
			this.color=new Color(71,247,71+(score-32)*8);
		}else if (score<=76) {
			this.color=new Color(71,247-(score-54)*8,247);
		}else if (score<=98) {
			this.color=new Color(71+(score-76)*8,71,247);
		}else if (score<=120) {
			this.color=new Color(247,71,247-(score-98)*8);
		}else {
			this.color=new Color(247,30,30);
		}
	}
	
	/**
	 * check if it collides with circle
	 * @param c - teh circle
	 * @return true if collide
	 */
	public boolean iscollideWithCircle(CircleBlock c) {
		//calculate the distance between the centers of cicle blocks
		double distance = Math.sqrt((c.x-this.x)*(c.x-this.x)+(c.y-this.y)*(c.y-this.y));
		if (distance<=this.RADIUS+c.RADIUS+5) {
			return true;
		}
		return false;
	}

	/**
	 * collide with the fireball after clicking the lighitning button
	 */
	public void collideWithFireball() {
		if(circleLine(new Vec2d(Game.fx,Game.fy),new Vec2d(Game.fx+30,Game.fy))) {
			this.delete();
			return;
		}
		if(circleLine(new Vec2d(Game.fx+30,Game.fy),new Vec2d(Game.fx+30,Game.fy+20))) {
			this.delete();
			return;
		}
		if(circleLine(new Vec2d(Game.fx+30,Game.fy+20),new Vec2d(Game.fx,Game.fy+20))) {
			this.delete();
			return;
		}
		if(circleLine(new Vec2d(Game.fx,Game.fy+20),new Vec2d(Game.fx,Game.fy))) {
			this.delete();
			return;
		}
	}
	
	/**
	 * check if it is collide with the polygon
	 * @param p - polygon
	 * @return true if collide
	 */
	public boolean iscollideWithPolygon(ArrayList<Vec2d> p) {
		for (int i = 0; i<p.size(); i++) {
			if (i > 0) {
				if (circleLine(p.get(i-1),p.get(i))) {
					return true;
				}
			}
			else {
				if (circleLine(p.get(p.size()-1),p.get(i))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * check if a circle block and a line collide
	 * @param s - start point of the line
	 * @param e - end point of the line
	 * @return true if collide
	 */
	public boolean circleLine(Vec2d s, Vec2d e) {
		Vec2d center = new Vec2d(this.x-s.x,this.y-s.y);
		Vec2d line = new Vec2d(e.x-s.x,e.y-s.y);
		
		if (circlePoint(s) || circlePoint(e)) 
			return true;
		
		float closestX = s.x+((center.dot(line)/line.dot(line)))*line.x;
		float closestY = s.y+((center.dot(line)/line.dot(line)))*line.y;
		if (!(linePoint(s,e,closestX,closestY)))
			return false;

		float distenceX = closestX-this.x;
		float distanceY = closestY-this.y;
		if (Math.sqrt(distenceX*distenceX+distanceY*distanceY)<=this.RADIUS+5)
			return true;
		return false;
	}
	
	/**
	 * check circle point collision
	 * @param p - coordinate of the point
	 * @return true if collide
	 */
	public boolean circlePoint(Vec2d p) {
		  double distance = Math.sqrt( (p.x-this.x)*(p.x-this.x) + (p.y-this.y)*(p.y-this.y) );

		  if (distance <= this.RADIUS+5) 
		    return true;
		  
		  return false;
	}
	
	/**
	 * check line point collision
	 * @param s - start point of the line
	 * @param e - end point of the line
	 * @param x - x-coordinate of the point
	 * @param y - y-coordinate of the point
	 * @return true if collide
	 */
	public boolean linePoint(Vec2d s, Vec2d e, float x, float y) {
		double d1 = Math.sqrt((x-s.x)*(x-s.x)+(y-s.y)*(y-s.y));
		double d2 = Math.sqrt((x-e.x)*(x-e.x)+(y-e.y)*(y-e.y));
		double d3 = Math.sqrt((e.x-s.x)*(e.x-s.x)+(e.y-s.y)*(e.y-s.y));
		
	    if (d1+d2 >= d3-0.1 && d1+d2 <= d3+0.1) 
		    return true;
		  
		return false;
		
	}

	/**
	 * generate the score of the block
	 * @param round - the round of the game
	 */
	public void generateScore(int round){
		// generate score of the circle based on the current number of round.
		// because game will be harder and harder
		int range=3*(round/4)+2*(round/4)*(round/4)+10;
		score = (int)(Math.random()*range+range/4.5);
		s=Integer.toString(score);
	}

	/**
	 * things happened for circle block after collide with the ball
	 * @param i - the mass of the ball
	 */
	public void collide(int i) {
		score-=i;
		s=Integer.toString(score);
		if(score <= 0)
			delete();
	}
	
	/**
	 * delete the block
	 */
	public void delete() {
		Game.disappear.add(0);
		Game.disappearPos.add(new Vec2d(this.cir.getCenterX(),this.cir.getCenterY()));
		Game.cirblocks.remove(this);
		
	}
	/**
	 * set the location
	 * @param dy - change of y-coordinate
	 */
	public void setLocation(int dy) {
		this.y=this.y-dy;
		cir.setCenterY(cir.getCenterY()-dy);
	}
	}
