

public class NewtonPoint {
    Vector position;
    Vector speed = new Vector(0, 0);
    public double mass = 1;

    public NewtonPoint(Vector position) {
        this.position = position;
    }

    public void applyGravity(NewtonPoint[] newtonPoints) {
        for(var point : newtonPoints) {
            if (point != this) {
                this.speed.add(getGravitationalContribution(point));
            }
        }
    }

    public void move() {
        position.add(speed);
    }

    Vector getGravitationalContribution(NewtonPoint newtonPoint) {
        double dist = position.getDistance(newtonPoint.position);
        double gravityForceScalar = this.mass * newtonPoint.mass * Settings.G / (dist * dist);
        double angle = position.getAngle(newtonPoint.position);
        return new Vector(
                Math.cos(angle) * gravityForceScalar,
                Math.sin(angle) * gravityForceScalar);
    }
}
