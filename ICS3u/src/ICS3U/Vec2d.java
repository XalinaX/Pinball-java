package ICS3U;

/**
 * 2 dimentional vector
 *
 */
public class Vec2d {
	protected float x;
	protected float y;
	/**
	 * generate vectors
	 * @param a: the x position of the head of this vector
	 * @param b: the y position of the head of this vector
	 */
	public Vec2d(float a, float b) {
		this.x = a;
		this.y = b;
	}
	/**
	 * doing math "dot product" of this vector and parameter
	 * @param a: vector that needs to be dotted
	 * @return dot product (scalar)
	 */
	public float dot(Vec2d a) {
		return this.x*a.x+this.y*a.y;
	}
	/**
	 * doing math "cross product" of this vector and parameter vector
	 * @param a: vector that does cross product with this vector
	 * @return the length of the produced vector
	 */
	public float cross(Vec2d a) {
		return this.x*a.y-this.y*a.x;
	}
	/**
	 * generate a perpendicular vector of this vector
	 * @return the perpendicular vector
	 */
	public Vec2d perpendicular() {
		return new Vec2d(-this.y, this.x);
	}
	/**
	 * add two vectors
	 * @param a the vector that needs to be added
	 * @return the sum vector
	 */
	public Vec2d add(Vec2d a) {
		return new Vec2d(this.x+a.x, this.y+a.y);
	}
	/**
	 * Subtract two vectors
	 * @param a subtrahend
	 * @return difference vector
	 */
	public Vec2d sub(Vec2d a) {
		return new Vec2d(this.x-a.x, this.y-a.y);
	}

//	
//	public static void main(String[] args) {
//		Vec2d demo = new Vec2d(3,4);
//		System.out.println(demo.dot(new Vec2d(3,4)));
//		System.out.println(demo.cross(new Vec2d(3,4)));
//		System.out.println(demo.perpendicular().x+" "+demo.perpendicular().y);
//		
//	}
}