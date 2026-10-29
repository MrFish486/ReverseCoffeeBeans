package ReverseCoffeeBeans;

public class Scene {
	public Geometry[] geometries;
	public PointLight[] lights;
	public Camera camera;
	public static double stepIncrement = 0.1;
	public static double farCutoff = 100;
	public Scene (Geometry[] geometries, PointLight[] lights, Camera camera) {
		this.geometries = geometries;
		this.lights = lights;
		this.camera = camera;
	}
	public void render () {
		for (int y = 0; y < this.camera.film.height; y ++) {
			for (int x = 0; x < this.camera.film.width; x ++) {
				double xProg = x - (this.camera.film.width / 2);
				double yProg = y - (this.camera.film.height / 2);

				Spherical cLook = this.camera.rotation.unit().toSpherical();

				Spherical castSpherical = cLook.add(new Spherical(0, xProg * this.camera.fovX, yProg * this.camera.fovY));
				castSpherical.r = Scene.stepIncrement;
				
				Vector3 castVector = castSpherical.toVector3();
				Point3 trace = this.camera.position.clone();

				for (int lZ = 0; lZ * Scene.stepIncrement < Scene.farCutoff; lZ ++) {
					trace = trace.add(castVector);
					for (int objN = 0; objN < this.geometries.length; objN ++) {
						if (this.geometries[objN].intercepts(trace)) {
							this.camera.film.add(x, y, (byte) 255);
							System.out.println(String.format("Exposed pixel at %d, %d.", x, y));
						}
					}
				}
			}
		}
	}
}
