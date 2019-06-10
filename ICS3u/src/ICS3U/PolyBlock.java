package ICS3U;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;

/**
 *the polygon block
 */
public class PolyBlock {
	protected ArrayList<Vec2d> vertex = new ArrayList<Vec2d>();
	protected Polygon poly;
	protected int score;
	protected String s;
	protected int sides;
	protected Color color;
	protected int length;

	/**
	 * constructor
	 * @param x - one point
	 * @param y - one point
	 * @param side - number of sides
	 * @param angle - rotate angle
	 * @param round - which round is the game
	 */
	public PolyBlock(int x, int y, int side, double angle, int round) {
		this.sides=side;
		poly = new Polygon();
		generateScore(round);
		setColor();
		length=40;
		// assume the length of parameter vec2d is 1 (unit vector)
		// the rotate angle should between -90 and 90
		for (int i = 0; i < side; i++) {
			// create a vertex
			double newx = x+ length * Math.sin(angle);
			double newy = y+ length * Math.cos(angle);
			Vec2d vec = new Vec2d((float) newx, (float) newy);
			vertex.add(vec);
			poly.addPoint(vec.x, vec.y);
			angle += 2*Math.PI/side;
		}
	}
	
	/**
	 * set the location
	 */
	public void setColor() {
		if (score<=10) {
			this.color=new Color(247,167+score*8,71);
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
	 * set the location
	 * @param dy - the change of Y-coordinate
	 */
	public void setLocation(int dy) {
		for (int i=0;i<this.sides;i++) {
			this.vertex.get(i).y=this.vertex.get(i).y-dy;
		}
		poly.setCenterY(poly.getCenterY()-dy);
	}
	/**
	 * collide with the ball
	 * @param i - the mass of the ball
	 */
	public void collide(int i) {
		score-=i;
		s=Integer.toString(score);
		if(score <= 0)
			delete();
	}
	
	/**
	 * collide with the fireball
	 */
	public void collideWithFireball() {
		for (int i=0;i<this.sides;i++) {
			Vec2d a ;
			Vec2d b ;
			if (i==0) {
				a = this.vertex.get(this.sides-1);
				b = this.vertex.get(0);
			}else {
				a = this.vertex.get(i-1);
				b = this.vertex.get(i);
			}
			if(lineIntersect(a,b,new Vec2d(Game.fx,Game.fy),new Vec2d(Game.fx+30,Game.fy))) {
				this.delete();
				return;
			}
			if(lineIntersect(a,b,new Vec2d(Game.fx+30,Game.fy),new Vec2d(Game.fx+30,Game.fy+10))) {
				this.delete();
				return;
			}
			if(lineIntersect(a,b,new Vec2d(Game.fx+30,Game.fy+10),new Vec2d(Game.fx,Game.fy+10))) {
				this.delete();
				return;
			}
			if(lineIntersect(a,b,new Vec2d(Game.fx,Game.fy+10),new Vec2d(Game.fx,Game.fy))) {
				this.delete();
				return;
			}
		}
	}
	
	/**
	 * delete the block
	 */
	public void delete() {
		Game.disappear.add(0);
		Game.disappearPos.add(new Vec2d(this.poly.getCenterX(),this.poly.getCenterY()));
		Game.polyblocks.remove(this);
	}
	
	/**
	 * generate the score of the block
	 * @param round - the round of the game
	 */
	public void generateScore(int round){
		// generate score of the circle based on the current number of round.
		// because game will be harder and harder
		int range=3*(round/4)+2*(round/4)*(round/4)+8;
		score = (int)(Math.random()*range+range/2.3);
		s=Integer.toString(score);
	}
	
	/**
	 * check if it is collide with circle
	 * @param c - the circle block
	 * @return true if collide
	 */
	public boolean iscollideWithCircle(CircleBlock c) {
		for (int i = 0; i<sides; i++) {
			if (i > 0) {
				if (circleLine(vertex.get(i-1),vertex.get(i),c.x,c.y,c.RADIUS+5)) {
					return true;
				}
			}
			else {
				if (circleLine(vertex.get(sides-1),vertex.get(i),c.x,c.y,c.RADIUS+5)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * circle line collision
	 * @param s - start point of line
	 * @param e - end point of the line
	 * @param x - centerX of circle
	 * @param y - centerY of circle
	 * @param radius - radius of circle
	 * @return true if collide
	 */
	public boolean circleLine(Vec2d s, Vec2d e,float x, float y, int radius) {
		Vec2d center = new Vec2d(x-s.x,y-s.y);
		Vec2d line = new Vec2d(e.x-s.x,e.y-s.y);
		
		if (circlePoint(s,x,y,radius) || circlePoint(e,x,y,radius)) 
			return true;
		
		float closestX = s.x+((center.dot(line)/line.dot(line)))*line.x;
		float closestY = s.y+((center.dot(line)/line.dot(line)))*line.y;
		if (!(linePoint(s,e,closestX,closestY)))
			return false;

		float distenceX = closestX-x;
		float distanceY = closestY-y;
		if (Math.sqrt(distenceX*distenceX+distanceY*distanceY)<=radius)
			return true;
	
		return false;
	}
	
	/**
	 * circle point collition
	 * @param p - coordinate of the point
	 * @param x - centerX of circle
	 * @param y - centerY of circle
	 * @param r - radius of circel
	 * @return true if collide
	 */
	public boolean circlePoint(Vec2d p,float x,float y, int r) {
		  double distance = Math.sqrt( (p.x-x)*(p.x-x) + (p.y-y)*(p.y-y) );
		  if (distance <= r) 
			  return true;
		  return false;
	}
	
	/**
	 * line point collision
	 * @param s - start point of line
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
	 * check if collide with polygon
	 * @param p - the polygon block
	 * @return true if collide
	 */
	public boolean iscollideWithPolygon(PolyBlock p) {
		for (int i=0;i<this.sides;i++) {
			Vec2d a ;
			Vec2d b ;
			if (i==0) {
				a = this.vertex.get(this.sides-1);
				b = this.vertex.get(0);
			}else {
				a = this.vertex.get(i-1);
				b = this.vertex.get(i);
			}
			for (int i1=0;i1<p.sides;i1++) {
				if(i1==0) {
					if(lineIntersect(a,b,p.vertex.get(p.sides-1),p.vertex.get(0))) {
						return true;
					}
				}else {
					if(lineIntersect(a,b,p.vertex.get(i1-1),p.vertex.get(i1))) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * check if two lines intersects
	 * @param s1 - start point of line1
	 * @param e1 - end point of line1
	 * @param s2 - start point of line2
	 * @param e2 - end point of line2
	 * @return true if collide
	 */
	public boolean lineIntersect(Vec2d s1,Vec2d e1,Vec2d s2,Vec2d e2) {
		Vec2d a = e1.sub(s1);
		Vec2d b = e2.sub(s2);
		
		float d = a.cross(b);
		float u = ((s1.x - s2.x) * a.y - (s1.y - s2.y) * a.x) / d;
		float t = ((s2.x - s1.x) * b.y - (s2.y - s1.y) * b.x) / d;
		
		if (-1 <= u && u <= 1 && -1 <= t && t <= 1)
			return true;
		return false;
	}
}
