package simulation;

import app.CoordinateTransposer;
import app.TextureDrawer;
import utility.CoordinateHasher;
import utility.Vector;

import java.util.ArrayList;

public class BackgroundStar {
    public CircleObject circleObject;

    public BackgroundStar(CircleObject circleObject) {
        this.circleObject = circleObject;
        this.circleObject.newtonPoint.movable = false;
    }

    public static ArrayList<BackgroundStar> spawnStars(int num, Vector upperLeft, Vector lowerRight, CoordinateTransposer coordinateTransposer, TextureDrawer textureDrawer) {
        ArrayList<BackgroundStar> stars = new ArrayList<>();
        while(num-- != 0) {
            double x = upperLeft.x + (lowerRight.x - upperLeft.x) * Math.random();
            double y = lowerRight.y + (upperLeft.y - lowerRight.y) * Math.random();
            double r = 1 + Math.random() * 3;
            BackgroundStar newStar = new BackgroundStar(new CircleObject(x, y, r, coordinateTransposer, textureDrawer));
            newStar.circleObject.circleSpriteHandler.r = 0.4 + Math.random() * 0.5;
            newStar.circleObject.circleSpriteHandler.g = 0.4 + Math.random() * 0.1;
            newStar.circleObject.circleSpriteHandler.b = 0.4 + Math.random() * 0.5;
            newStar.circleObject.changeDrawPrecision(10);
            stars.add(newStar);
        }
        return stars;
    }

    public static ArrayList<BackgroundStar> spawnStars2(Vector upperLeft, Vector lowerRight, int spawnChance, CoordinateTransposer coordinateTransposer, TextureDrawer textureDrawer) {
        ArrayList<BackgroundStar> stars = new ArrayList<>();
        for(int x = (int)upperLeft.x; x < lowerRight.x; x++) {
            for(int y = (int)upperLeft.y; y < lowerRight.y; y++) {
                long v = CoordinateHasher.hashIntegers(x, y);
                if (v % spawnChance == 0) {
//                    double r = 1 + Math.random() * 3;
                    double r = 1 + CoordinateHasher.hashIntegers(x+1, y+1) % 4;
                    BackgroundStar newStar = new BackgroundStar(new CircleObject(x, y, r, coordinateTransposer, textureDrawer));
                    newStar.circleObject.circleSpriteHandler.r = 0.4 + Math.random() * 0.5;
                    newStar.circleObject.circleSpriteHandler.g = 0.4 + Math.random() * 0.1;
                    newStar.circleObject.circleSpriteHandler.b = 0.4 + Math.random() * 0.5;
                    newStar.circleObject.changeDrawPrecision(10);
                    stars.add(newStar);
                }
            }
        }
        return stars;
    }
}
