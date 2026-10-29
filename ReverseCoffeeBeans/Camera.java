package ReverseCoffeeBeans;

public class Camera {
	public Point3 position;
	public Vector3 rotation;
	public double fovX;
	public double fovY;
	public Film film;
	public Camera (Point3 position, Point3 lookAt, Film film) {
		this.position = position;
		this.rotation = lookAt.to(position);
		this.film = film;
		this.fovX = Math.PI / 2;
		this.fovY = Math.PI / 4;
	}
	public void lookAt (Point3 lookAt) {
		this.rotation = lookAt.to(this.position);
	}
}
