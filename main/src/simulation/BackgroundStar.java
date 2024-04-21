package simulation;

import app.CoordinateTransposer;
import utility.Vector;

import java.util.ArrayList;

public class BackgroundStar {
    public CircleObject circleObject;

    public BackgroundStar(CircleObject circleObject) {
        this.circleObject = circleObject;
        this.circleObject.newtonPoint.movable = false;
    }

    public static ArrayList<BackgroundStar> spawnStars(int num, Vector upperLeft, Vector lowerRight, CoordinateTransposer coordinateTransposer) {
        ArrayList<BackgroundStar> stars = new ArrayList<>();
        while(num-- != 0) {
            double x = upperLeft.x + (lowerRight.x - upperLeft.x) * Math.random();
            double y = lowerRight.y + (upperLeft.y - lowerRight.y) * Math.random();
            double r = 1 + Math.random() * 3;
            BackgroundStar newStar = new BackgroundStar(new CircleObject(x, y, r, coordinateTransposer));
            newStar.circleObject.circleSpriteHandler.r = 0.4 + Math.random() * 0.5;
            newStar.circleObject.circleSpriteHandler.g = 0.4 + Math.random() * 0.1;
            newStar.circleObject.circleSpriteHandler.b = 0.4 + Math.random() * 0.5;
            newStar.circleObject.changeDrawPrecision(10);
            stars.add(newStar);
        }
        return stars;
    }
}