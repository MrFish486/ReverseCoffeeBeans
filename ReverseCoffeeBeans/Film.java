package ReverseCoffeeBeans;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Film {
	public byte[][] red;
	public byte[][] green;
	public byte[][] blue;
	public int height;
	public int width;
	public byte sensitivity;
	public Film (int height, int width, byte sensitivity) {
		this.red = new byte[height][width];
		this.green = new byte[height][width];
		this.blue = new byte[height][width];
		for (int y = 0; y < height; y ++) {
			for (int x = 0; x < width; x ++) this.red[y][x] = Byte.MIN_VALUE;
			for (int x = 0; x < width; x ++) this.green[y][x] = Byte.MIN_VALUE;
			for (int x = 0; x < width; x ++) this.blue[y][x] = Byte.MIN_VALUE;
		}
		this.height = height;
		this.width = width;
		this.sensitivity = sensitivity;
	}
	public void add (int x, int y, byte value, Color tint) {
		if (y < 0 || y > this.height - 1 || x < 0 || x > this.width - 1) return;
		byte[] s = tint.sample(value);
		this.red[y][x] = s[0];
		this.green[y][x] = s[1];
		this.blue[y][x] = s[2];
	}
	public void add (int x, int y, byte value) {
		if (y < 0 || y > this.height - 1 || x < 0 || x > this.width - 1) return;
		if (((int) this.red[y][x] + (int) value) > Byte.MAX_VALUE) {
			System.out.println(String.format("Combination too high. To add = %d, existing = %d", (int) value, (int) this.red[y][x]));
			this.red[y][x] = Byte.MAX_VALUE;
			this.green[y][x] = Byte.MAX_VALUE;
			this.blue[y][x] = Byte.MAX_VALUE;
		} else {
			this.red[y][x] += value;
			this.green[y][x] += value;
			this.blue[y][x] += value;
		}
	}
	public void save (File out) throws IOException {
		BufferedImage im = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < this.height; y ++) {
			for (int x = 0; x < this.width; x ++) {
				im.setRGB(x, y, ((this.red[y][x] + 128) << 16) | ((this.green[y][x] + 128) << 8) | this.blue[y][x] + 128);
			}
		}
		ImageIO.write(im, "BMP", out);
	}
}
