package simulation;

import app.CoordinateTransposer;
import utility.Vector;

import java.util.ArrayList;

public class GameState implements Cloneable {
    public ArrayList<BackgroundStar> backgroundStars;
    public ArrayList<CircleObject> circleObjects;
    public SpacePlane spacePlane;
    public ArrayList<ParametricPoint> parametricPoints;

    CoordinateTransposer coordinateTransposer;

    public GameState(CoordinateTransposer coordinateTransposer) {
        this.coordinateTransposer = coordinateTransposer;
    }

    public void gameState1() {
        resetGameState();
        backgroundStars = BackgroundStar.spawnStars(3000, new Vector(-2000, -2000), new Vector(2000, 2000), coordinateTransposer);

        circleObjects.add(new CircleObject(0, 0, 30, coordinateTransposer));
        circleObjects.get(0).newtonPoint.mass = 10;
        circleObjects.get(0).circleSpriteHandler.r = 1;
        circleObjects.get(0).circleSpriteHandler.g = 1;
        circleObjects.get(0).circleSpriteHandler.b = 0;
        circleObjects.add(new CircleObject(700, 0, 15, coordinateTransposer));
        circleObjects.get(1).newtonPoint.mass = 1.5;
        circleObjects.get(1).newtonPoint.speed = new Vector(0, 0.83);

        spacePlane = new SpacePlane(new Vector(250, 0), coordinateTransposer);
        spacePlane.speed.y = 1;
    }

    public void gameState2() {
        resetGameState();
        backgroundStars = new ArrayList<>();
        circleObjects = new ArrayList<>();
        parametricPoints = new ArrayList<>();


        spacePlane = new SpacePlane(new Vector(100, 100), coordinateTransposer);
        circleObjects.add(new CircleObject(300, 400, 30, coordinateTransposer));
        circleObjects.add(new CircleObject(500, 400, 30, coordinateTransposer));
        circleObjects.get(0).newtonPoint.speed = new Vector(0, 0.5);
        circleObjects.get(1).newtonPoint.speed = new Vector(0, -0.5);
        parametricPoints.add(new ParametricPoint(new Vector(400,400), 0.01, 300, 100, 60, coordinateTransposer));
    }

    public void resetGameState() {
        backgroundStars = new ArrayList<>();
        circleObjects = new ArrayList<>();
        parametricPoints = new ArrayList<>();
        spacePlane = new SpacePlane(new Vector(100, 100), coordinateTransposer);
    }

    @Override
    public GameState clone() {
        try {
            GameState clone = (GameState) super.clone();
            clone.backgroundStars = new ArrayList<>(this.backgroundStars);
            clone.circleObjects = new ArrayList<>(this.circleObjects);
            clone.parametricPoints = new ArrayList<>(this.parametricPoints);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
