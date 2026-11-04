package ReverseCoffeeBeans;

public class PointLight {
	public Point3 position;
	public double intensity;
	public double range;
	public LightStyle style;
	public PointLight (Point3 position, double intensity, double range, LightStyle style) {
		this.position = position;
		this.intensity = intensity;
		this.range = range;
		this.style = style;
	}
}
