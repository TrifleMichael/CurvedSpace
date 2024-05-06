package simulation;

import app.CircleSpriteHandler;
import app.CoordinateTransposer;
import simulation.NewtonPoint;
import utility.Vector;

public class CircleObject implements Cloneable{
    public CircleSpriteHandler circleSpriteHandler;
    public NewtonPoint newtonPoint;

    public CircleObject(double x, double y, double r, CoordinateTransposer coordinateTransposer) {
        newtonPoint = new NewtonPoint(new Vector(x, y));
        circleSpriteHandler = new CircleSpriteHandler(this, r, coordinateTransposer);
    }

    public void setSpeed(Vector speed) {
        newtonPoint.speed = speed;
    }

    public void changeDrawPrecision(int drawPrecision) {
        circleSpriteHandler.drawPrecision = drawPrecision;
    }
}
