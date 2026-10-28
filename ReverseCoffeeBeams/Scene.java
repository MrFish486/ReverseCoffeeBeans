package ReverseCoffeeBeans;

public class Scene {
	public Geometry[] geometries;
	public PointLight[] lights;
	public Camera camera;
	public Scene (Geometry[] geometries, PointLight[] lights, Camera camera) {
		this.geometries = geometries;
		this.lights = lights;
	}
	public render () {
		// ...
	}
}
