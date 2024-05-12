package simulation;

import utility.Settings;
import utility.Vector;

import java.util.ArrayList;

public class NewtonPoint extends Vector {
    public Vector speed = new Vector(0, 0);
    public double mass = 1;

    public boolean movable = true;

    public NewtonPoint(Vector position) {
        super(position);
    }

    public void applyGravity(NewtonPoint[] newtonPoints) {
        if (movable) {
            for (var point : newtonPoints) {
                if (point != this) {
                    this.speed.add(getGravitationalContribution(point));
                }
            }
        }
    }

    public void subtractGravity(NewtonPoint[] newtonPoints) {
        if (movable) {
            for (var point : newtonPoints) {
                if (point != this) {
                    this.speed.subtract(getGravitationalContribution(point));
                }
            }
        }
    }

    public void move() {
        add(speed);
    }

    public void moveBackwards() {
        subtract(speed);
    }

    Vector getGravitationalContribution(NewtonPoint newtonPoint) {
        double dist = getDistance(newtonPoint);
        double gravityForceScalar = newtonPoint.mass * Settings.G / (dist * dist);
        double angle = getAngle(newtonPoint);
        return new Vector(
                Math.cos(angle) * gravityForceScalar,
                Math.sin(angle) * gravityForceScalar);
    }
}
