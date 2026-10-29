import ReverseCoffeeBeans.*;

import java.io.File;
import java.io.IOException;

public class Example {
	public static void main (String[] args) {
		
		Film film = new Film(200, 200, (byte) 5);
		Camera cam = new Camera(new Point3(4, 4, 4), new Point3(0, 0, 0), film);
		cam.fovX = 0.3;
		cam.fovY = 0.3;
		//Sphere sph = new Sphere(new Point3(0, 0, 0), 0.5);
		Box sph = new Box(new Point3(0, 0, 0), 1, 1, 1);

		PointLight lght = new PointLight(new Point3(3, 3, 3), 200, 10);

		Scene sc = new Scene(new Geometry[] {sph}, new PointLight[] {lght}, cam);
		sc.stepIncrement = 0.01;

		sc.render();

		try {
			film.save(new File("out.bmp"));
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
