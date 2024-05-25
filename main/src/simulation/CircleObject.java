package simulation;

import app.CircleSpriteHandler;
import app.CoordinateTransposer;
import app.TextureDrawer;
import simulation.NewtonPoint;
import utility.Vector;

public class CircleObject implements Cloneable{
    public CircleSpriteHandler circleSpriteHandler;
    public NewtonPoint newtonPoint;

    public boolean target = false;


    public void setRGB(double r, double g, double b) {
        circleSpriteHandler.r = r;
        circleSpriteHandler.g = g;
        circleSpriteHandler.b = b;
    }

    public CircleObject(double x, double y, double r, CoordinateTransposer coordinateTransposer) {
        newtonPoint = new NewtonPoint(new Vector(x, y));
        circleSpriteHandler = new CircleSpriteHandler(this, r, coordinateTransposer);
    }

    public void setSpeed(Vector speed) {
        newtonPoint.speed = speed;
    }

    public double getRadius() {
        return circleSpriteHandler.radius;
    }

    public void changeDrawPrecision(int drawPrecision) {
        circleSpriteHandler.drawPrecision = drawPrecision;
    }
}
