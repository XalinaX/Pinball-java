package ICS3U;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Line;

/**
 * the wall (grey lines)
 */
public class Wall {
	protected int sx;
	protected int sy;
	protected int ex;
	protected int ey;
	protected ArrayList<Vec2d> vertex;
	protected boolean elastic;
	protected Line line;
	protected Color color;

	/**
	 * initialize
	 * @param sx - the start pointX
	 * @param sy - the start pointY
	 * @param ex - the end pointX
	 * @param ey - teh end pointY
	 * @param elastic - true if the ball can slide on it
	 */
	public Wall(int sx, int sy, int ex, int ey, boolean elastic) {
		this.sx=sx;
		this.sy=sy;
		this.ex=ex;
		this.ey=ey;
		vertex = new ArrayList<Vec2d>();
		this.vertex.add(new Vec2d(this.sx,this.sy));
		this.vertex.add(new Vec2d(this.ex,this.ey));
		this.line = new Line(this.sx,this.sy,this.ex,this.ey);
		color = new Color(105,105,105);
		this.elastic = elastic;
	}
	
	
	
}
