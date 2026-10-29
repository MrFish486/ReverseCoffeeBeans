import ReverseCoffeeBeans.*;

import java.io.File;
import java.io.IOException;

public class Example {
	public static void main (String[] args) {
		Film film = new Film(50, 50);
		Camera cam = new Camera(new Point3(4, 4, 4), new Point3(0, 0, 0), film);
		Sphere sph = new Sphere(new Point3(0, 0, 0), 1);
		Scene sc = new Scene(new Geometry[] {sph}, new PointLight[] {}, cam);

		sc.render();

		try {
			film.save(new File("out.bmp"));
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
