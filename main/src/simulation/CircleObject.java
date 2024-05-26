package simulation;

import app.CircleSpriteHandler;
import app.CoordinateTransposer;
import app.TextureDrawer;
import simulation.NewtonPoint;
import utility.Vector;

public class CircleObject implements Cloneable {
    public CircleSpriteHandler circleSpriteHandler;
    public NewtonPoint newtonPoint;

    public boolean target = false;

    TextureDrawer textureDrawer;

    String textureName;


    public void setRGB(double r, double g, double b) {
        circleSpriteHandler.r = r;
        circleSpriteHandler.g = g;
        circleSpriteHandler.b = b;
    }

    public CircleObject(double x, double y, double r, CoordinateTransposer coordinateTransposer, TextureDrawer textureDrawer, String textureName) {
        newtonPoint = new NewtonPoint(new Vector(x, y));
        circleSpriteHandler = new CircleSpriteHandler(this, r, coordinateTransposer, textureDrawer);
        this.textureDrawer = textureDrawer;
        this.textureName = textureName;
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
