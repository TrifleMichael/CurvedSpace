import static org.lwjgl.opengl.GL11.*;

public class ParametricPoint {

    Vector center;

    double t = 0;

    double a;

    double b;

    double dt;

    double angle = 0;

    double radius = 10;

    Vector elipseCenter;

    CoordinateTransposer coordinateTransposer;

    public ParametricPoint(Vector elipseCenter, double dt, double a, double b, double angle, CoordinateTransposer coordinateTransposer) {
        this.elipseCenter = elipseCenter;
        this.a = a;
        this.b = b;
        this.dt = dt;
        this.angle = angle;
        this.coordinateTransposer = coordinateTransposer;
    }

    public void nextStep() { // TODO Constant steps give unrealistic acceleration for elliptic orbits
        Vector centerOffsetVector = new Vector(a * Math.cos(t), b * Math.sin(t));
        centerOffsetVector.rotate(angle / 180 * Math.PI);
        this.center = new Vector(elipseCenter);
        this.center.add(centerOffsetVector);
        t += dt;
        t = t % (2*Math.PI);
    }

    public void draw() {
        Vector center = coordinateTransposer.physicalToVisual(this.center);

        glColor3f(0, 0, 0); // TODO Hardcoded color
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
