package ICS3U;
import java.util.ArrayList;

import org.newdawn.slick.geom.*;

/**
 * the balls
 * @author alina
 */
public class Ball {
	protected float radius;
	protected int mass;
	protected float px;
	protected float py;
	protected double vx;
	protected double vy;
	protected Circle cir;
	protected boolean stop;
	protected boolean firstTime;
	protected boolean hasCollide;
	/**
	 * initialize
	 * @param x - the centerX
	 * @param y - teh centerY
	 * @param vx - the speed on x-axis
	 * @param vy - the speed on y-axis
	 */
	public Ball(int x, int y, double vx, double vy) {
		this.px=x;
		this.py=y;
		radius=15;
		mass=1;
		this.vx=vx;
		this.vy=vy;
		cir = new Circle(this.px, this.py, this.radius);
		stop=false;
		firstTime=true;
		hasCollide=false;
	}
	
	/**
	 * collide with ball and move apart these two collide balls
	 * @param ball - the ball
	 */
	public void collideWithBall(Ball ball) {
		double distance = Math.sqrt((ball.px-this.px)*(ball.px-this.px)+(ball.py-this.py)*(ball.py-this.py));
		if (distance<=this.radius+ball.radius+5) {
			// overlap
			double Overlap = (distance - ball.radius - this.radius-5);
			// Displace Balls
			this.px -= (Overlap * (this.px - ball.px) / distance);
			this.py -= (Overlap * (this.py - ball.py) / distance);
			ball.px += (Overlap * (this.px - ball.px) / distance);
			ball.py +=( Overlap * (this.py - ball.py) / distance);
			this.cir.setCenterX(this.px);
			this.cir.setCenterY(this.py);
			ball.cir.setCenterX(ball.px);
			ball.cir.setCenterY(ball.py);
			if (ball.stop) {
				this.stop=true;
			}
		}
	}
	
	/**
	 * deal with the collision
	 * @param l - the derection Vector of the line that collide with circle
	 * @param elastic - true if the ball slide on the line
	 */
	Vec2d tang;
	Vec2d v1 ;
	Vec2d vr1 ;
	Vec2d proj1 ;
	Vec2d N1;
	
	public void collide(Vec2d l, boolean elastic) {
		hasCollide=true;
		if (elastic) {
			double ratio = Math.sqrt(1/(l.x*l.x+l.y*l.y));
			tang = l.perpendicular();
			tang.x*=-1;
			tang.y*=-1;
			this.vx=tang.x*ratio;
			this.vy=tang.y*ratio;
		}
		else {
			v1 = new Vec2d((float)this.vx,(float)this.vy);
			vr1 = new Vec2d(-v1.x,-v1.y);
			proj1 = new Vec2d(l.x*vr1.dot(l)/l.dot(l),l.y*vr1.dot(l)/l.dot(l));//projection
			N1 = v1.add(proj1);
			N1=N1.add(proj1);
			double ratio = Math.sqrt(1/(N1.x*N1.x+N1.y*N1.y));
			this.vx=N1.x*ratio;
			this.vy=N1.y*ratio;
		}
	}
	
	/**
	 * collide with special block
	 * @param c - special block
	 * @return true if collide
	 */
	public boolean iscollideWithSpecial(SpecialBlock c) {
		double distance = Math.sqrt((c.x-this.px)*(c.x-this.px)+(c.y-this.py)*(c.y-this.py));
		if (distance<=this.radius+c.RADIUS) {
			return true;
		}
		return false;
	}
	
	/**
	 * collide with circle block
	 * @param c - circle block
	 * @return true if collide
	 */
	public boolean iscollideWithCircle(CircleBlock c) {
		double distance = Math.sqrt((c.x-this.px)*(c.x-this.px)+(c.y-this.py)*(c.y-this.py));
		if (distance<=this.radius+c.RADIUS) {
			Game.collision=new Vec2d(c.x-this.px,c.y-this.py);
			double overlap = this.radius+c.RADIUS-distance;
			double length = Math.sqrt(this.vx*this.vx+this.vy*this.vy);
			this.setLocation((float)(this.px-((overlap)*this.vx/length)),(float)(this.py-((overlap)*this.vy/length)));
			return true;
		}
		return false;
	}
	
	/**
	 * collide with polygon block
	 * @param p - polygon block
	 * @return true if collide
	 */
	public boolean iscollideWithPolygon(ArrayList<Vec2d> p) {
		for (int i = 0; i<p.size(); i++) {
			if (i > 0) {
				if (circleLine(p.get(i-1),p.get(i),false)) {
					Vec2d tang = new Vec2d(p.get(i).x-p.get(i-1).x,p.get(i).y-p.get(i-1).y);
				
					Game.collision = tang.perpendicular();
					return true;
					
				}
			}
			else {
				if (circleLine(p.get(p.size()-1),p.get(i),false)) {
					Vec2d tang = new Vec2d(p.get(i).x-p.get(p.size()-1).x,p.get(i).y-p.get(p.size()-1).y);
					Game.collision = tang.perpendicular();
					return true;
				}
			}
		}
		return false;
	
	}
	
	/**
	 * collide with wall
	 * @param p - vertexes of the line
	 * @return true if collide
	 */
	Vec2d tang1;
	public boolean iscollideWithWall(ArrayList<Vec2d> p) {
		if (circleLine(p.get(0),p.get(1),true)) {
			tang1 = new Vec2d(p.get(1).x-p.get(0).x,p.get(1).y-p.get(0).y);
			Game.collision = tang1.perpendicular();
			if (p.get(1).y==200) {
				this.stop=true;
			}
			if (p.get(0).x==130) {
				this.stop=true;
			}
			if (p.get(0).x==570) {
				this.stop=true;
			}
			if (Math.abs(p.get(1).y-p.get(0).y)==950) {
				if (this.px<350) {
					this.px=(float) (50.1+this.radius);
				}else {
					this.px=(float)(649.9-this.radius);
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * circle line collision
	 * @param s - start point of line
	 * @param e - end point of line
	 * @param isWall - true if the line is a wall
	 * @return true if collide
	 */
	public boolean circleLine(Vec2d s, Vec2d e, boolean isWall) {
		Vec2d velo = new Vec2d((float)this.vx,(float)this.vy);
		Vec2d center = new Vec2d(this.px-s.x,this.py-s.y);
		Vec2d line = new Vec2d(e.x-s.x,e.y-s.y);
		if (velo.dot(line.perpendicular())<0 || isWall) {
			if (circlePoint(s,isWall) || circlePoint(e,isWall)) {
				return true;
			}
			float closestX = s.x+((center.dot(line)/line.dot(line)))*line.x;
			float closestY = s.y+((center.dot(line)/line.dot(line)))*line.y;
			if (!(linePoint(s,e,closestX,closestY)))
				return false;
	
			float distenceX = closestX-this.px;
			float distanceY = closestY-this.py;
			double overlap=Math.sqrt(distenceX*distenceX+distanceY*distanceY);
			if (overlap<=this.radius) {
				if (!isWall) {
					double length = Math.sqrt(this.vx*this.vx+this.vy*this.vy);
					this.setLocation((float)(this.px-((overlap)*this.vx/length)),(float)(this.py-((overlap)*this.vy/length)));
				}
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * circle point collision
	 * @param p - coordinate of the point
	 * @param isWall - true if the point is on a wall
	 * @return true if collide
	 */
	public boolean circlePoint(Vec2d p,boolean isWall) {
		  double distance = Math.sqrt( (p.x-this.px)*(p.x-this.px) + (p.y-this.py)*(p.y-this.py) );
		  if (distance <= this.radius) {
			  if (!isWall) {
				  double overlap = this.radius-distance+5;
				  double length = Math.sqrt(this.vx*this.vx+this.vy*this.vy);
				  this.setLocation((float)(this.px-((overlap)*this.vx/length)),(float)(this.py-((overlap)*this.vy/length)));
			  }
			  return true;
		  }
		  return false;
	}
	
	/**
	 * check if the point is on the line
	 * @param s - start point of the line
	 * @param e - end point of the line
	 * @param x - pointX
	 * @param y - pointY
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
	 * set the mass of the circle
	 * @param r - current radius
	 * @param m - current mass
	 */
	public void setMass(int r, int m) {
		this.radius=r;
		this.mass=m;
		this.cir.setRadius(r);
	}
	
	/**
	 * set the velocity of the circle
	 * @param vx - the speed on X-axis
	 * @param vy - the speed on Y-axis
	 */
	public void setVelocity(double vx,double vy) {
		double ratio = Math.sqrt(1/(vx*vx+vy*vy));
		this.vx=vx*ratio;
		this.vy=vy*ratio;

	}
	
	/**
	 * set the location of the circle
	 * @param x - centerX
	 * @param y - centerY
	 */
	public void setLocation (float x, float y) {
		this.px=x;
		this.py=y;
		this.cir.setCenterX(this.px);
		this.cir.setCenterY(this.py);

	}
	
}
