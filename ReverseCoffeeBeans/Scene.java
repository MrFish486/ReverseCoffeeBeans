package ReverseCoffeeBeans;

public class Scene {
	public Geometry[] geometries;
	public PointLight[] lights;
	public Camera camera;
	public double stepIncrement = 0.1;
	public double lightStepIncrement = 0.1;
	public double farCutoff = 20;
	public double lightCastInitialDistance = 0;
	public boolean ignoreSelfOnLightCast = true;
	public Scene (Geometry[] geometries, PointLight[] lights, Camera camera) {
		this.geometries = geometries;
		this.lights = lights;
		this.camera = camera;
	}
	public void render () {
		for (int y = 0; y < this.camera.film.height; y ++) {
			for (int x = 0; x < this.camera.film.width; x ++) {
				double xProg = (double) (x - (this.camera.film.width / 2d)) / (this.camera.film.width);
				double yProg = (double) (y - (this.camera.film.height / 2d)) / (this.camera.film.width);
				
				Spherical cLook = this.camera.rotation.unit().toSpherical();

				Spherical castSpherical = cLook.add(new Spherical(0, xProg * this.camera.fovX, yProg * this.camera.fovY));
				castSpherical.r = this.stepIncrement;
				
				Vector3 castVector = castSpherical.toVector3();
				Point3 trace = this.camera.position.clone();
				
				boolean hit = false;

				for (int lZ = 0; lZ * this.stepIncrement < this.farCutoff; lZ ++) {
					trace = trace.add(castVector);
					for (int objN = 0; objN < this.geometries.length; objN ++) {
						if (this.geometries[objN].intercepts(trace)) {
							if (!this.geometries[objN].translucent) {
								hit = true;
								for (int lghtN = 0; lghtN < this.lights.length; lghtN ++) {
									Vector3 betweenLightAndHit = Vector3.between(trace, this.lights[lghtN].position);
									
									if (betweenLightAndHit.magnitude() > this.lights[lghtN].range) return;

									Spherical lCastSpherical = betweenLightAndHit.toSpherical();
									lCastSpherical.r = this.lightStepIncrement;

									Vector3 lCast = lCastSpherical.toVector3();

									Point3 lTrace = trace.clone();

									boolean hitL = false;

									lTrace = lTrace.add(lCast.multiply(this.lightCastInitialDistance));

									for (int lZL = 0; lZL * this.lightStepIncrement < betweenLightAndHit.magnitude(); lZL ++) {
										for (int objNL = 0; objNL < this.geometries.length; objNL ++) {
											if (objNL == objN && this.ignoreSelfOnLightCast) {
												//System.out.println("ignoring self.");
												continue;
											}
											if (this.geometries[objNL].intercepts(lTrace)) {
												hitL = true;
												//System.out.println("object hit while tracing to light. :C");
												System.out.println(this.geometries[objNL]);
												break;
											}
										}
										lTrace = lTrace.add(lCast);
										if (hitL) break;
									}

									if (!hitL) {
										double prog = (this.lights[lghtN].range - betweenLightAndHit.magnitude());
										if (prog < Byte.MIN_VALUE) prog = 0;
										prog /= this.lights[lghtN].range;
										//double brightness = ((betweenLightAndHit.magnitude() - this.lights[lghtN].range) / this.lights[lghtN].range) * this.lights[lghtN].intensity;
										//System.out.println(prog * this.lights[lghtN].intensity * this.camera.film.sensitivity);
										//System.out.println((byte) ((this.camera.film.sensitivity * prog * this.lights[lghtN].intensity) + 128));
										double brightness = this.camera.film.sensitivity * prog * this.lights[lghtN].intensity;
										if (brightness > 256) brightness = 255;
										this.camera.film.add(x, y, (byte) brightness);
									}
								}
								break;
							} else {
								this.camera.film.add(x, y, this.camera.film.sensitivity);
							}
						}
					}
					if (hit) break;
				}
			}
		}
	}
}
