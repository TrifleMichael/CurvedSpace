public class Vector {
    double x;
    double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void subtract(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void rotate(double radians) {
        double new_x = this.x * Math.cos(radians) - y * Math.sin(radians);
        double new_y = this.x * Math.sin(radians) + y * Math.cos(radians);
        this.x = new_x;
        this.y = new_y;
    }

    public void rotateAroundPoint(Vector other, double radians) {
        this.subtract(other);
        this.rotate(radians);
        this.add(other);
    }

    public void multiplyByConstant(double constant) {
        this.x *= constant;
        this.y *= constant;
    }

    public Vector pointToPointMultiply(Vector other) {
        return new Vector(this.x * other.x, this.y * other.y);
    }

    public Vector getDifference(Vector other) {
        return new Vector(this.x - other.x, this.y-other.y);
    }

    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double getDistance(Vector other) {
        return this.getDifference(other).getLength();
    }

    public double getAngle(Vector other) {
        return Math.atan2(other.y - y, other.x - x);
    }
    public void normalize() {
        double length = getLength();
        this.x /= length;
        this.y /= length;
    }
}