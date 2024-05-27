package simulation;

import app.CoordinateTransposer;
import utility.Vector;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class SpacePlane extends NewtonPoint {

    public boolean exploding = false;

    float r = 1;

    float g = 1;

    float b = 1;

    public double radius = 10;

    public ArrayList<Vector> speedStack = new ArrayList<>();

    public int speedStackLimit = 60000; // TODO Implement fully

    boolean visible = true;

    boolean frozen = false;

    @Override
    public void moveBackwards() {
        if (!frozen) {
            int lastIndex = speedStack.size() - 1;
            if (lastIndex != -1) {
                this.speed = speedStack.get(lastIndex);
                speedStack.remove(lastIndex);
            }
            this.x -= this.speed.x;
            this.y -= this.speed.y;
        }
    }

    @Override
    public void move() {
        if (!frozen) {
            super.move();
            speedStack.add(new Vector(speed));
        }
    }

    CoordinateTransposer coordinateTransposer;

    public SpacePlane(Vector center, CoordinateTransposer coordinateTransposer) {
        super(center);
        this.coordinateTransposer = coordinateTransposer;
    }

    public void draw() {
        if (visible) {
            // Plane dot
            Vector visualCenter = coordinateTransposer.physicalToVisual(this);

            glColor3f(r, g, b); // TODO Hardcoded color
            glBegin(GL_TRIANGLE_FAN);
            glVertex2d(visualCenter.x, visualCenter.y);
            for (int i = 0; i <= 360; i++) { // TODO Hardcoded polygon count
                double theta = Math.toRadians(i);
                double x = visualCenter.x + radius * Math.cos(theta);
                double y = visualCenter.y + radius * Math.sin(theta);
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

    }
}
