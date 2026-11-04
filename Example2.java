import ReverseCoffeeBeans.*;

import java.io.File;
import java.io.IOException;

public class Example2 {
	public static void main (String[] args) {
		
		Film film = new Film(200, 200, (byte) 5);
		Camera cam = new Camera(new Point3(3, 3, 3), new Point3(0, 0, 0), film);
		cam.fovX = Math.PI / 4;
		cam.fovY = Math.PI / 4;

		Sphere sph = new Sphere(new Point3(0, 0, 0), 1);

		PointLight lght = new PointLight(new Point3(0, 0, 2), 400, 5, LightStyle.SIMPLE);
		PointLight lght2 = new PointLight(new Point3(0, 0, -2), 400, 5, LightStyle.SIMPLE);

		Scene sc = new Scene(new Geometry[] {sph}, new PointLight[] {lght, lght2}, cam);
		sc.stepIncrement = 0.02;

		sc.multiThreadedRender(50, 50);

		try {
			film.save(new File("out.bmp"));
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
