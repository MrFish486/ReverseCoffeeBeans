package ReverseCoffeeBeans;

public class Color {
	public double red;
	public double green;
	public double blue;
	public Color (double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public byte[] sample (byte value) {
		byte[] o = new byte[3];
		int v = (int) value;
		o[0] = (byte) (v * this.red);
		o[1] = (byte) (v * this.green);
		o[2] = (byte) (v * this.blue);
		if ((v * this.red) > Byte.MAX_VALUE) o[0] = Byte.MAX_VALUE;
		if ((v * this.green) > Byte.MAX_VALUE) o[1] = Byte.MAX_VALUE;
		if ((v * this.blue) > Byte.MAX_VALUE) o[2] = Byte.MAX_VALUE;
		return o;
	}
}
