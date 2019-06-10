package ICS3U;

/**
 * 2 dimentional vector
 *
 */
public class Vec2d {
	protected float x;
	protected float y;

	public Vec2d(float a, float b) {
		this.x = a;
		this.y = b;
	}
	
	public float dot(Vec2d a) {
		return this.x*a.x+this.y*a.y;
	}
	
	public float cross(Vec2d a) {
		return this.x*a.y-this.y*a.x;
	}
	
	public Vec2d perpendicular() {
		return new Vec2d(-this.y, this.x);
	}

	public Vec2d add(Vec2d a) {
		return new Vec2d(this.x+a.x, this.y+a.y);
	}

	public Vec2d sub(Vec2d a) {
		return new Vec2d(this.x-a.x, this.y-a.y);
	}

	
	public static void main(String[] args) {
		Vec2d demo = new Vec2d(3,4);
		System.out.println(demo.dot(new Vec2d(3,4)));
		System.out.println(demo.cross(new Vec2d(3,4)));
		System.out.println(demo.perpendicular().x+" "+demo.perpendicular().y);
		
	}
}
