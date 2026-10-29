package ReverseCoffeeBeans;

import ReverseCoffeeBeans.Geometry;

public class Box extends Geometry {
	public Point3 position;
	public double width;
	public double height;
	public double depth;
	public Box (Point3 position, double width, double height, double depth) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	public Box (Point3 position, int width, int height, int depth) {
		this.position = position;
		this.width = (double) width;
		this.height = (double) height;
		this.depth = (double) depth;
	}
	public boolean intercepts (Point3 point) {
		return point.x > this.position.x - (this.width / 2) && point.x < this.position.x + (this.width / 2) && point.y > this.position.y - (this.height / 2) && point.y < this.position.y + (this.width / 2) && point.z > this.position.z - (this.height / 2) && point.z < this.position.z + (this.width / 2);
	}
}
