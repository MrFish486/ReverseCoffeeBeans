package ReverseCoffeeBeans;

import ReverseCoffeeBeans.Geometry;

public class Sphere extends Geometry {
	public double radius;
	public Point3 position;
	public Vector3 rotation;
	public Sphere (Point3 position, double radius) {
		this.position = position;
		this.radius = radius;
	}
	public boolean intercepts (Point3 point) {
		Vector3 v = this.position.to(point);
		return (magnitude <= radius);
	}
}
