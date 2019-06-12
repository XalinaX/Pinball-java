package ICS3U;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * special block
 * this class handles 3 special blocks:
 * 1. the sign that gives players an extra ball
 * 2. the sign that make the ball bigger
 * 3. boom: delete blocks in a certain radius
 *  
 */
public class SpecialBlock {
	protected float x;
	protected float y;
	protected int RADIUS;
	protected boolean add;
	protected boolean larger;

	protected Image largeri;
	protected Image addi;
	protected float xi;
	protected float yi;
	protected int width;
	protected int height;
	protected Image boom2;
	/**
	 * constructor: initialize special blocks
	 * @param x: the x position of the center of the block
	 * @param y: the y position of the center of the block
	 * @param add: if gives player an extra ball
	 * @param larger: if make the ball bigger
	 *  if both add and larger are true, it means boom
	 * @throws SlickException
	 */
	public SpecialBlock(float x, float y,boolean add,boolean larger) throws SlickException {
		this.RADIUS=30;
		this.x=x;
		this.y=y;
		width=60;
		height=60;
		this.add=add;
		this.larger=larger;
		if(this.add && this.larger) {
			width=70;
			height =96 ;
		}
		this.xi=x-this.RADIUS;
		this.yi=y-this.height/2;
		largeri = new Image("./image/larger.gif");
		addi = new Image("./image/add.gif");
		boom2 = new Image("./image/boom2.gif");
	}
	
	/**
	 * when collide with the ball
	 */
	public void collide() {
		if (this.add&&this.larger) {
			for ( int i=0;i<Game.polyblocks.size();i++) {
				PolyBlock p = Game.polyblocks.get(i);
				if(Math.sqrt((Math.pow(p.poly.getCenterX()-x,2)+ Math.pow(p.poly.getCenterY()-y, 2)))<=200) {
					p.delete();
				}
			}
			for(int i=0;i<Game.cirblocks.size();i++) {
				CircleBlock c = Game.cirblocks.get(i);
				if(Math.sqrt((Math.pow(c.cir.getCenterX()-x,2)+ Math.pow(c.cir.getCenterY()-y, 2)))<=200) {
					c.delete();
				}
			}
			Game.disappearPos.add(new Vec2d(this.x,this.y));
			Game.disappear.add(0);
		}
		Game.specialblocks.remove(this);
	}
	
	/**
	 * collide with the fireball
	 */
	public void collideWithFireball() {
		if(circleLine(new Vec2d(Game.fx,Game.fy),new Vec2d(Game.fx+30,Game.fy))) {
			this.collide();
			return;
		}
		if(circleLine(new Vec2d(Game.fx+30,Game.fy),new Vec2d(Game.fx+30,Game.fy+10))) {
			this.collide();
			return;
		}
		if(circleLine(new Vec2d(Game.fx+30,Game.fy+10),new Vec2d(Game.fx,Game.fy+10))) {
			this.collide();
			return;
		}
		if(circleLine(new Vec2d(Game.fx,Game.fy+10),new Vec2d(Game.fx,Game.fy))) {
			this.collide();
			return;
		}
	}
	
	/**
	 * check circle line collision
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
		if (Math.sqrt(distenceX*distenceX+distanceY*distanceY)<=this.RADIUS)
			return true;
		return false;
	}
	
	/**
	 * circle point collision
	 * @param p - coordinate of the point
	 * @return true if collide
	 */
	public boolean circlePoint(Vec2d p) {
		  double distance = Math.sqrt( (p.x-this.x)*(p.x-this.x) + (p.y-this.y)*(p.y-this.y) );

		  if (distance <= this.RADIUS) 
		    return true;
		  
		  return false;
	}
	
	/**
	 * line point collision
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
	 * set the location
	 * @param dy - change of y-coordinate
	 */
	public void setLocation(int dy) {
		this.y=this.y-dy;
		this.yi=this.yi-dy;
	}
}