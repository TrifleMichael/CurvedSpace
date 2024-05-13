package simulation;

import app.CoordinateTransposer;
import utility.Vector;

import java.util.ArrayList;

public class GameState implements Cloneable {

    int gameStateCounter = 0;
    public ArrayList<BackgroundStar> backgroundStars;
    public ArrayList<CircleObject> circleObjects;
    public SpacePlane spacePlane;
    public ArrayList<ParametricPoint> parametricPoints;

    CoordinateTransposer coordinateTransposer;

    public GameState(CoordinateTransposer coordinateTransposer) {
        this.coordinateTransposer = coordinateTransposer;
    }

    public void nextRound() {
        resetGameState();
        if (gameStateCounter == 0) {
            gameState1();
        } else if (gameStateCounter == 1){
            gameState2();
        } else {
            gameState1();
        }
        gameStateCounter++;
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
        circleObjects.get(1).target = true;
        circleObjects.get(1).circleSpriteHandler.r = 1;
        circleObjects.get(1).circleSpriteHandler.g = 0;
        circleObjects.get(1).circleSpriteHandler.b = 1;

        circleObjects.add(new CircleObject(740, 0, 5, coordinateTransposer));
        circleObjects.get(2).newtonPoint.mass = 0.01;
        circleObjects.get(2).newtonPoint.speed = new Vector(0, 2.2);

        spacePlane = new SpacePlane(new Vector(250, 0), coordinateTransposer);
        spacePlane.speed.y = 1;
    }

    public void gameState2() {
        resetGameState();
        backgroundStars = BackgroundStar.spawnStars(3000, new Vector(-2000, -2000), new Vector(2000, 2000), coordinateTransposer);
        circleObjects = new ArrayList<>();

        spacePlane = new SpacePlane(new Vector(400, 100), coordinateTransposer);
        spacePlane.speed = new Vector(0, -1);

        circleObjects.add(new CircleObject(-100, 0, 30, coordinateTransposer));
        circleObjects.get(0).newtonPoint.speed = new Vector(0, 1.7);
        circleObjects.get(0).newtonPoint.mass = 25;
        circleObjects.get(0).setRGB(0.6, 0.6, 1);

        circleObjects.add(new CircleObject(100, 0, 30, coordinateTransposer));
        circleObjects.get(1).newtonPoint.speed = new Vector(0, -1.7);
        circleObjects.get(1).newtonPoint.mass = 25;
        circleObjects.get(1).setRGB(1, 0.3, 0.3);

        circleObjects.add(new CircleObject(0, 0, 5, coordinateTransposer));
        circleObjects.get(2).newtonPoint.speed = new Vector(0, 0);
        circleObjects.get(2).newtonPoint.mass = 0;
        circleObjects.get(2).target = true;
        circleObjects.get(2).newtonPoint.movable = false;

        circleObjects.add(new CircleObject(400, 0, 10, coordinateTransposer));
        circleObjects.get(3).newtonPoint.speed = new Vector(0, -2.3);
        circleObjects.get(3).newtonPoint.mass = 0;



//        circleObjects.add(new CircleObject(400, 0, 10, coordinateTransposer));
//        circleObjects.get(2).newtonPoint.speed = new Vector(0, -0.3);
//        circleObjects.get(1).newtonPoint.mass = 0.5;
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
            clone.circleObjects = new ArrayList<>(this.circleObjects); // TODO clone subparts
            clone.parametricPoints = new ArrayList<>(this.parametricPoints);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
