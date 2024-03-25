public class Circle {
    double radius;
    Vector center;

    public boolean collidesWithCircle(Circle other) {
        return this.center.getDistance(other.center) < this.radius + other.radius;
    }

    public void move(Vector shiftVector) {
        this.center.add(shiftVector);
    }
}
