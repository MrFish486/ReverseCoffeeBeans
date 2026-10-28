package ReverseCoffeeBeans

import ReverseCoffeeBeans.Double3

public class Vector3 extends Double3 {
	public static Vector3 between (Point3 a, Point3 b) {
		return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	public double magnitude () {
		return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
	}
	public Vector3 unit () {
		double mg = this.magnitude();
		return new Vector3(this.x / mg, this.y / mg, this.z / mg);
	}
	public Spherical toSpherical () {
		double r = this.magnitude();
		double theta = Math.acos(this.z / r);
		double phi = math.atan2(this.y, this.x);
		return new Spherical(r, theta, phi);
	}
}
