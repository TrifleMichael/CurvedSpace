import static org.lwjgl.opengl.GL11.*;

public class CircleSpriteHandler {
    public Vector center;
    double radius;

    float r, g, b;

    public CircleSpriteHandler(Vector center, double radius) {
        this.center = center;
        this.radius = radius;
        r = 0;
        g = 1;
        b = 0;
    }

    public void drawCircle() {
        glColor3f(r, g, b); // TODO Hardcoded color
        glBegin(GL_TRIANGLE_FAN);
        glVertex2d(center.x, center.y);
        for (int i = 0; i <= 360; i++) { // TODO Hardcoded polygon count
            double theta = Math.toRadians(i);
            double x = center.x + radius * Math.cos(theta);
            double y = center.y + radius * Math.sin(theta);
            glVertex2d(x, y);
        }
        glEnd();
    }
}
