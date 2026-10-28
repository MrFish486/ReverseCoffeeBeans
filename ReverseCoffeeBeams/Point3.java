package ReverseCoffeeBeans

import ReverseCoffeeBeans.Double3

public class Point3 extends Double3 {
	public Vector3 to (Point3 other) {
		return Vector3.between(this, other);
	}
}
