public class Circle {
    double radius;
    Point center;

    public boolean collidesWithCircle(Circle other) {
        return this.center.getDistance(other.center) < this.radius + other.radius;
    }

    public void move(Point shiftVector) {
        this.center.add(shiftVector);
    }
}
