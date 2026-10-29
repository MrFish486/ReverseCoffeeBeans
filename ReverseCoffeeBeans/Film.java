package ReverseCoffeeBeans;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Film {
	public byte[][] pixels;
	public int height;
	public int width;
	public Film (int height, int width) {
		this.pixels = new byte[height][width];
		for (int y = 0; y < height; y ++) {
			for (int x = 0; x < width; x ++) this.pixels[y][x] = Byte.MIN_VALUE;
		}
		this.height = height;
		this.width = width;
	}
	public void add (int x, int y, byte value) {
		if (y < 0 || y > this.height - 1 || x < 0 || x > this.width - 1) return;
		this.pixels[y][x] += value;
	}
	public void save (File out) throws IOException {
		BufferedImage im = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < this.height; y ++) {
			for (int x = 0; x < this.width; x ++) {
				im.setRGB(x, y, ((this.pixels[y][x] + 128) << 16) | ((this.pixels[y][x] + 128) << 8) | this.pixels[y][x] + 128);
			}
		}
		ImageIO.write(im, "BMP", out);
	}
}
