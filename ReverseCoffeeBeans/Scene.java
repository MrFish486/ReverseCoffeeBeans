package ReverseCoffeeBeans;

public class Scene {
	private class RendererJob implements Runnable {
		public int sX; // start x coord
		public int eX; // end   x coord
		public int sY; // start y coord
		public int eY; // end   y coord
		public Scene target;
		public RendererJob (int sX, int eX, int sY, int eY, Scene target) {
			this.sX = sX;
			this.eX = eX;
			this.sY = sY;
			this.eY = eY;
			this.target = target;
		}
		public void run () {
			for (int y = this.sY; y < this.eY && y < this.target.camera.film.height; y ++) {
				for (int x = this.sX; x < this.eX && x < this.target.camera.film.width; x ++) {
					double xProg = (double) (x - (this.target.camera.film.width / 2d)) / (this.target.camera.film.width);
					double yProg = (double) (y - (this.target.camera.film.height / 2d)) / (this.target.camera.film.width);
					
					Spherical cLook = this.target.camera.rotation.unit().toSpherical();

					Spherical castSpherical = cLook.add(new Spherical(0, xProg * this.target.camera.fovX, yProg * this.target.camera.fovY));
					castSpherical.r = this.target.stepIncrement;
					
					Vector3 castVector = castSpherical.toVector3();
					Point3 trace = this.target.camera.position.clone();
					
					boolean hit = false;

					for (int lZ = 0; lZ * this.target.stepIncrement < this.target.farCutoff; lZ ++) {
						trace = trace.add(castVector);
						for (int objN = 0; objN < this.target.geometries.length; objN ++) {
							if (this.target.geometries[objN].intercepts(trace)) {
								if (!this.target.geometries[objN].translucent) {
									hit = true;
									for (int lghtN = 0; lghtN < this.target.lights.length; lghtN ++) {
										Vector3 betweenLightAndHit = Vector3.between(this.target.lights[lghtN].position, trace);
										
										if (betweenLightAndHit.magnitude() > this.target.lights[lghtN].range) return;

										Spherical lCastSpherical = betweenLightAndHit.toSpherical();
										lCastSpherical.r = this.target.lightStepIncrement;

										Vector3 lCast = lCastSpherical.toVector3();

										Point3 lTrace = trace.clone();

										boolean hitL = false;

										lTrace = lTrace.add(lCast.multiply(this.target.lightCastInitialDistance));

										for (int lZL = 0; lZL * this.target.lightStepIncrement < betweenLightAndHit.magnitude(); lZL ++) {
											for (int objNL = 0; objNL < this.target.geometries.length; objNL ++) {
												if (objNL == objN && this.target.ignoreSelfOnLightCast) {
													continue;
												}
												if (this.target.geometries[objNL].intercepts(lTrace)) {
													hitL = true;
													break;
												}
											}
											lTrace = lTrace.add(lCast);
											if (hitL) break;
										}
										if (!hitL) {
											if (this.target.lights[lghtN].style == LightStyle.REALISTIC) {
												Vector3 betweenCameraAndHit = Vector3.between(this.target.camera.position, trace);
												double totalDistance = betweenLightAndHit.magnitude() + betweenCameraAndHit.magnitude();
												double brightness = this.target.camera.film.sensitivity * (this.target.lights[lghtN].intensity / (totalDistance * totalDistance));
												if (brightness > 256) brightness = 255;
												this.target.camera.film.add(x, y, (byte) brightness);//, this.target.lights[lghtN].tint);
											} else if (this.target.lights[lghtN].style == LightStyle.LINEAR) {
												double prog = (this.target.lights[lghtN].range - betweenLightAndHit.magnitude());
												if (prog < Byte.MIN_VALUE) prog = 0;
												prog /= this.target.lights[lghtN].range;
												double brightness = this.target.camera.film.sensitivity * prog * this.target.lights[lghtN].intensity;
												if (brightness > 256) brightness = 255;
												this.target.camera.film.add(x, y, (byte) brightness);//, this.target.lights[lghtN].tint);
											} else if (this.target.lights[lghtN].style == LightStyle.SIMPLE) {
												Vector3 betweenCameraAndHit = Vector3.between(this.target.camera.position, trace);
												double totalDistance = betweenLightAndHit.magnitude() + betweenCameraAndHit.magnitude();
												double brightness = this.target.camera.film.sensitivity * (this.target.lights[lghtN].intensity / totalDistance);
												if (brightness > 256) brightness = 255;
												this.target.camera.film.add(x, y, (byte) brightness);//, this.target.lights[lghtN].tint);
											}
										}
									}
									break;
								} else {
									this.target.camera.film.add(x, y, this.target.camera.film.sensitivity);
								}
							}
						}
						if (hit) break;
					}
				}
			}
		}
	}
	public Geometry[] geometries;
	public PointLight[] lights;
	public Camera camera;
	public double stepIncrement = 0.1;
	public double lightStepIncrement = 0.1;
	public double farCutoff = 20;
	public double lightCastInitialDistance = 1;
	public boolean ignoreSelfOnLightCast = false;
	public Scene (Geometry[] geometries, PointLight[] lights, Camera camera) {
		this.geometries = geometries;
		this.lights = lights;
		this.camera = camera;
	}
	public void multiThreadedRender (int xThreadSize, int yThreadSize) {
		for (int y = 0; y * yThreadSize < this.camera.film.height; y ++) {
			for (int x = 0; x * xThreadSize < this.camera.film.width; x ++) {
				Scene.RendererJob job = new Scene.RendererJob(x * xThreadSize, (x + 1) * xThreadSize, y * yThreadSize, (y + 1) * yThreadSize, this);
				Thread thr = new Thread(job);
				thr.start();
				try {
					thr.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void render () {
		Scene.RendererJob j = new Scene.RendererJob(0, 0, this.camera.film.width, this.camera.film.height, this);
		j.run();
	}
}
