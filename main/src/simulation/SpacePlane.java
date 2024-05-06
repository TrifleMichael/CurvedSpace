package simulation;

import app.CoordinateTransposer;
import utility.Vector;

import static org.lwjgl.opengl.GL11.*;

public class SpacePlane extends NewtonPoint implements Cloneable {


    float r = 1;

    float g = 1;

    float b = 1;

    CoordinateTransposer coordinateTransposer;

    public SpacePlane(Vector center, CoordinateTransposer coordinateTransposer) {
        super(center);
        this.coordinateTransposer = coordinateTransposer;
    }

    public void draw() {
        // Plane dot
        Vector visualCenter = coordinateTransposer.physicalToVisual(this);

        glColor3f(r, g, b); // TODO Hardcoded color
        glBegin(GL_TRIANGLE_FAN);
        glVertex2d(visualCenter.x, visualCenter.y);
        for (int i = 0; i <= 360; i++) { // TODO Hardcoded polygon count
            double theta = Math.toRadians(i);
            double x = visualCenter.x + 10 * Math.cos(theta); // TODO hardcoded radiuss
            double y = visualCenter.y + 10 * Math.sin(theta);
            glVertex2d(x, y);
        }
        glEnd();

        // Speed arrow
        Vector endPoint = new Vector(visualCenter.x + speed.x * 10, visualCenter.y + speed.y * 10);
        Vector sidePoint = new Vector(visualCenter.x + 4, visualCenter.y + 4);

        glColor3f(1, 0, 0); // TODO Hardcoded color
        glBegin(GL_TRIANGLE_FAN);
        glVertex2d(visualCenter.x, visualCenter.y);
        glVertex2d(endPoint.x, endPoint.y);
        glVertex2d(sidePoint.x, sidePoint.y);
        glEnd();

    }

    @Override
    public SpacePlane clone() {
        try {
            SpacePlane clone = (SpacePlane) super.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
