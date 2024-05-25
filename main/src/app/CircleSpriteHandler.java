package app;

import simulation.CircleObject;
import simulation.NewtonPoint;
import utility.*;

import static org.lwjgl.opengl.GL11.*;

public class CircleSpriteHandler {
    public double radius;

    public double r, g, b;

    CircleObject circleObject;

    CoordinateTransposer coordinateTransposer;

    public int drawPrecision;

    private int textureId;

    public CircleSpriteHandler(CircleObject circleObject, double radius, CoordinateTransposer coordinateTransposer) {
        this.circleObject = circleObject;
        this.radius = radius;
        r = 0;
        g = 1;
        b = 0;
        this.coordinateTransposer = coordinateTransposer;
        drawPrecision = 360;
        textureId = TextureDrawer.loadTexture("E:\\Repos\\CurvedSpace\\main\\src\\textures\\star1.png");
    }

    public void drawCircle() {
        Vector center = coordinateTransposer.physicalToVisual(circleObject.newtonPoint);

        glColor3f((float)r, (float)g, (float)b); // TODO Hardcoded color
        glBegin(GL_TRIANGLE_FAN);
        glVertex2d(center.x, center.y);
        for (int i = 0; i <= drawPrecision; i++) { // TODO Hardcoded polygon count
            double theta = Math.toRadians((double)i*360/drawPrecision);
            double x = center.x + radius * Math.cos(theta);
            double y = center.y + radius * Math.sin(theta);
            glVertex2d(x, y);
        }
        glEnd();
//        int radius = (int)circleObject.getRadius();
//        NewtonPoint center = circleObject.newtonPoint;
//        TextureDrawer.drawTexture(textureId, (int)center.x - radius, (int)center.x + radius, (int)center.y - radius, (int)center.y + radius);
    }
}
