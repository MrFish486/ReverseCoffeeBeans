package ReverseCoffeeBeans;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {
	public int xThreadSize;
	public int yThreadSize;
	public File from;
	public Scene scene;
	public Camera camera;
	public Film film;
	public PointLight[] lightCollection;
	public Geometry[] geometryCollection;
	public Loader (File from) {
		this.from = from;
		try {
			Scanner sc = new Scanner(from);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				System.err.println(line);
				if (line.equals("opt")) {
					System.out.println("options found.");
				}
			}
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		}
	}
	public static void main (String[] args) {
		if (args.length == 1) {
			File f = new File(args[0]);
			new Loader(f);
		}
	}
}
