package ReverseCoffeeBeans;

import ReverseCoffeeBeans.Double3;

public class Vector3 extends Double3 {
	public Vector3 (double x, double y, double z) {
		super(x, y, z);
	}
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
		double phi = Math.atan2(this.y, this.x);
		return new Spherical(r, theta, phi);
	}
	public Vector3 add (Vector3 other) {
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	public Vector3 add (Spherical other) {
		return this.add(other.toVector3());
	}
	public Vector3 multiply (double s) {
		return new Vector3(this.x * s, this.y * s, this.z * s);
	}
	public Vector3 clone () {
		return new Vector3(this.x, this.y, this.z);
	}
}
