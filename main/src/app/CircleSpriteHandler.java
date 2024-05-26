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

    TextureDrawer textureDrawer;

    String textureName;

    public CircleSpriteHandler(CircleObject circleObject, double radius, CoordinateTransposer coordinateTransposer, TextureDrawer textureDrawer, String textureName) {
        this.circleObject = circleObject;
        this.radius = radius;
        r = 0;
        g = 1;
        b = 0;
        this.coordinateTransposer = coordinateTransposer;
        drawPrecision = 360;
        this.textureDrawer = textureDrawer;
        this.textureName = textureName;
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
    }

    public void createDrawQueueEntry() {
        int radius = (int)circleObject.getRadius();
        NewtonPoint center = circleObject.newtonPoint;
        Vector topLeft = coordinateTransposer.physicalToVisual(new Vector(center.x - radius, center.y + radius));
        Vector bottomRight = coordinateTransposer.physicalToVisual(new Vector(center.x + radius, center.y - radius));

        textureDrawer.drawQueue.add(new DrawEntry(textureName,
                (int)topLeft.x,
                (int)bottomRight.x,
                (int)bottomRight.y,
                (int)topLeft.y));
    }
}
