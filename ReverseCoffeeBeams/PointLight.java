package ReverseCoffeeBeans;

public class PointLight {
	public Point3 position;
	public double intensity;
	public double range;
	public PointLight (Point3 position, double intensity, double range) {
		this.position = position;
		this.intensity = intensity;
		this.range = range;
	}
}
