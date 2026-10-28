package ReverseCoffeeBeans

public class Spherical {
	public double r;
	public double theta;
	public double phi;
	public Spherical (double r, double theta, double phi) {
		this.r = r;
		this.theta = theta;
		this.phi = phi;
	}
	public Vector3 toVector3 () {
		return new Vector3(this.r * Math.sin(this.theta) * Math.cos(this.phi), r * Math.sin(theta) * Math.sin(this.phi), r * Math.cos(theta));
	}
}
