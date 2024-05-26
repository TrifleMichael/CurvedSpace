package simulation;

import app.CoordinateTransposer;
import app.TextureDrawer;
import utility.Vector;

import java.util.ArrayList;

public class GameState implements Cloneable {

    int gameStateCounter = 0;
    public ArrayList<BackgroundStar> backgroundStars;
    public ArrayList<CircleObject> circleObjects;
    public SpacePlane spacePlane;
    public ArrayList<ParametricPoint> parametricPoints;

    TextureDrawer textureDrawer;

    CoordinateTransposer coordinateTransposer;

    public GameState(CoordinateTransposer coordinateTransposer, TextureDrawer textureDrawer) {
        this.coordinateTransposer = coordinateTransposer;
        this.textureDrawer = textureDrawer;
    }


    public void nextRound() {
        gameStateCounter++;
        resetGameState();
        if (gameStateCounter == 1) {
            mainMenu();
        } else if (gameStateCounter == 2){
            gameState3();
        } else if (gameStateCounter == 3) {
            gameState2();
        } else if (gameStateCounter == 4) {
            gameState1();
        }
    }

    public void mainMenu() {
        resetGameState();
        backgroundStars = BackgroundStar.spawnStars2(new Vector(-2000, -2000), new Vector(2000, 2000), 3000, coordinateTransposer, textureDrawer);
        spacePlane = new SpacePlane(new Vector(0, 0), coordinateTransposer);
        spacePlane.visible = false;
        spacePlane.frozen = true;


        circleObjects.add(new CircleObject(-300, 0, 5, coordinateTransposer, textureDrawer, "earth"));
        circleObjects.get(0).setSpeed(new Vector(-0.1, 1.4));
        circleObjects.get(0).newtonPoint.mass = 0.3;
        circleObjects.get(0).circleSpriteHandler.r = 0.6;
        circleObjects.get(0).circleSpriteHandler.g = 0.6;
        circleObjects.get(0).circleSpriteHandler.b = 0.6;


        circleObjects.add(new CircleObject(-330, 0, 9, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(1).setSpeed(new Vector(-0.1, 2.3));
        circleObjects.get(1).newtonPoint.mass = 0.3;
        circleObjects.get(1).circleSpriteHandler.r = 0;
        circleObjects.get(1).circleSpriteHandler.g = 0.6;
        circleObjects.get(1).circleSpriteHandler.b = 1;


        circleObjects.add(new CircleObject(300, 0, 11, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(2).setSpeed(new Vector(-0.1, -1.4));
        circleObjects.get(2).newtonPoint.mass = 0.3;
        circleObjects.get(2).circleSpriteHandler.r = 0.6;
        circleObjects.get(2).circleSpriteHandler.g = 0.2;
        circleObjects.get(2).circleSpriteHandler.b = 0.2;


        circleObjects.add(new CircleObject(330, 0, 8, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(3).setSpeed(new Vector(-0.1, -2.3));
        circleObjects.get(3).newtonPoint.mass = 0.3;
        circleObjects.get(3).circleSpriteHandler.r = 0.6;
        circleObjects.get(3).circleSpriteHandler.g = 0.6;
        circleObjects.get(3).circleSpriteHandler.b = 0.6;



        circleObjects.add(new CircleObject(-20, 0, 20, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(4).setSpeed(new Vector(-0.1, -2.5));
        circleObjects.get(4).newtonPoint.mass = 10;
        circleObjects.get(4).circleSpriteHandler.r = 1;
        circleObjects.get(4).circleSpriteHandler.g = 0;
        circleObjects.get(4).circleSpriteHandler.b = 0;


        circleObjects.add(new CircleObject(20, 0, 14, coordinateTransposer, textureDrawer, "star2"));
        circleObjects.get(5).setSpeed(new Vector(-0.1, 2.5));
        circleObjects.get(5).newtonPoint.mass = 10;
        circleObjects.get(5).circleSpriteHandler.r = 0.7;
        circleObjects.get(5).circleSpriteHandler.g = 0.9;
        circleObjects.get(5).circleSpriteHandler.b = 1;
    }

    public void gameState1() {
        resetGameState();
        backgroundStars = BackgroundStar.spawnStars2(new Vector(-2000, -2000), new Vector(2000, 2000), 3000, coordinateTransposer, textureDrawer);

        circleObjects.add(new CircleObject(0, 0, 30, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(0).newtonPoint.mass = 10;
        circleObjects.get(0).circleSpriteHandler.r = 1;
        circleObjects.get(0).circleSpriteHandler.g = 1;
        circleObjects.get(0).circleSpriteHandler.b = 0;

        circleObjects.add(new CircleObject(700, 0, 15, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(1).newtonPoint.mass = 1.5;
        circleObjects.get(1).newtonPoint.speed = new Vector(0, 0.83);
        circleObjects.get(1).target = true;
        circleObjects.get(1).circleSpriteHandler.r = 1;
        circleObjects.get(1).circleSpriteHandler.g = 0;
        circleObjects.get(1).circleSpriteHandler.b = 1;

        circleObjects.add(new CircleObject(740, 0, 5, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(2).newtonPoint.mass = 0.01;
        circleObjects.get(2).newtonPoint.speed = new Vector(0, 2.2);

        spacePlane = new SpacePlane(new Vector(250, 0), coordinateTransposer);
        spacePlane.speed.y = 1;
    }

    public void gameState2() {
        resetGameState();
        backgroundStars = BackgroundStar.spawnStars2(new Vector(-2000, -2000), new Vector(2000, 2000), 3000, coordinateTransposer, textureDrawer);
        circleObjects = new ArrayList<>();

        spacePlane = new SpacePlane(new Vector(400, 100), coordinateTransposer);
        spacePlane.speed = new Vector(0, -1);

        circleObjects.add(new CircleObject(-100, 0, 30, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(0).newtonPoint.speed = new Vector(0, 1.7);
        circleObjects.get(0).newtonPoint.mass = 25;
        circleObjects.get(0).setRGB(0.6, 0.6, 1);

        circleObjects.add(new CircleObject(100, 0, 30, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(1).newtonPoint.speed = new Vector(0, -1.7);
        circleObjects.get(1).newtonPoint.mass = 25;
        circleObjects.get(1).setRGB(1, 0.3, 0.3);

        circleObjects.add(new CircleObject(0, 0, 5, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(2).newtonPoint.speed = new Vector(0, 0);
        circleObjects.get(2).newtonPoint.mass = 0;
        circleObjects.get(2).target = true;
        circleObjects.get(2).newtonPoint.movable = false;

        circleObjects.add(new CircleObject(400, 0, 10, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(3).newtonPoint.speed = new Vector(0, -2.5);
        circleObjects.get(3).newtonPoint.mass = 0;
    }

    public void gameState3() {
        resetGameState();
        backgroundStars = BackgroundStar.spawnStars2(new Vector(-2000, -2000), new Vector(2000, 2000), 3000, coordinateTransposer, textureDrawer);
        circleObjects = new ArrayList<>();

        spacePlane = new SpacePlane(new Vector(200, 0), coordinateTransposer);
        spacePlane.speed = new Vector(0, 1.6);

        circleObjects.add(new CircleObject(0, 0, 30, coordinateTransposer, textureDrawer, "star1"));
        circleObjects.get(0).newtonPoint.speed = new Vector(0, 0);
        circleObjects.get(0).newtonPoint.mass = 20;
        circleObjects.get(0).setRGB(1, 0.3, 0.3);

        circleObjects.add(new CircleObject(-450, 0, 15, coordinateTransposer, textureDrawer, "earth"));
        circleObjects.get(1).newtonPoint.speed = new Vector(0, -1.5);
        circleObjects.get(1).newtonPoint.mass = 1;
        circleObjects.get(1).setRGB(1, 1, 1);
        circleObjects.get(1).target = true;

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
