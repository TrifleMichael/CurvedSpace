import static org.lwjgl.opengl.GL11.*;

public class SpacePlane {
    NewtonPoint center;


    float r = 1;

    float g = 1;

    float b = 1;

    public SpacePlane(NewtonPoint center) {
        this.center = center;
    }

    public void move() {
        center.move();
    }

    public void draw() {
        Vector visualCenter = CoordinateTransposer.physicalToVisual(center.position);

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
    }
}
