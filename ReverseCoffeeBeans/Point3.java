package ReverseCoffeeBeans;

import ReverseCoffeeBeans.Double3;

public class Point3 extends Double3 {
	public Point3 (double x, double y, double z) {
		super(x, y, z);
	}
	public Vector3 to (Point3 other) {
		return Vector3.between(this, other);
	}
	public Point3 add (Vector3 other) {
		return new Point3(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	public Point3 add (Spherical other) {
		return this.add(other.toVector3());
	}
	public Point3 clone () {
		return new Point3(this.x, this.y, this.z);
	}
}
