package ReverseCoffeeBeans;

public class Camera {
	public Point3 position;
	public Vector3 rotation;
	public double fovX;
	public double fovY;
	public Film film;
	public Camera (Point3 position, Point3 lookAt, Film film) {
		this.position = position;
		this.rotation = look_at.to(lookAt);
		this.Film = film;
		this.fovX = Math.Pi / 2;
		this.fovY = Math.Pi / 4;
	}
	public lookAt (Point3 lookAt) {
		this.rotation = look_at.to(lookAt);
	}
}
