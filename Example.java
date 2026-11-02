import ReverseCoffeeBeans.*;

import java.io.File;
import java.io.IOException;

public class Example {
	public static void main (String[] args) {
		
		Film film = new Film(200, 200, (byte) 5);
		Camera cam = new Camera(new Point3(3, 3, 3), new Point3(0, 0, 0), film);
		cam.fovX = Math.PI / 4;
		cam.fovY = Math.PI / 4;

		Sphere sph = new Sphere(new Point3(0, 0, 1), 1);
		Box bx = new Box(new Point3(0, 0, -1), 1, 1, 1);

		PointLight lght = new PointLight(new Point3(2, 3, 1), 2200, 10);

		Scene sc = new Scene(new Geometry[] {bx, sph}, new PointLight[] {lght}, cam);
		sc.stepIncrement = 0.05;

		sc.multiThreadedRender(20, 20);

		try {
			film.save(new File("out.bmp"));
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
