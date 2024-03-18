public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Point other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void subtract(Point other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void rotate(double radians) {
        double new_x = this.x * Math.cos(radians) - y * Math.sin(radians);
        double new_y = this.x * Math.sin(radians) + y * Math.cos(radians);
        this.x = new_x;
        this.y = new_y;
    }

    public void rotateAroundPoint(Point other, double radians) {
        this.subtract(other);
        this.rotate(radians);
        this.add(other);
    }

    public void multiplyByConstant(double constant) {
        this.x *= constant;
        this.y *= constant;
    }

    public Point getDifference(Point other) {
        return new Point(this.x - other.x, this.y-other.y);
    }

    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double getDistance(Point other) {
        return this.getDifference(other).getLength();
    }
}