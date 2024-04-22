package app;

import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;
import simulation.*;
import utility.Settings;
import utility.Vector;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class SimulationHandler {

    long window;



    Vector cursorPosition = new Vector(0, 0);

    public SimulationHandler(long window) {
        this.window = window;
    }

    ArrayList<CircleObject> circleObjects = new ArrayList<>();

    ArrayList<ParametricPoint> parametricPoints = new ArrayList<>();

    SpacePlane spacePlane;

    CoordinateTransposer coordinateTransposer = new CoordinateTransposer();

    NewtonPoint[] getNewtonPoints() {
        return circleObjects.stream().map(co -> co.newtonPoint).toArray(NewtonPoint[]::new);
    }

    CircleSpriteHandler[] getCircleSprites() {
        return circleObjects.stream().map(co -> co.circleSpriteHandler).toArray(CircleSpriteHandler[]::new);
    }

    ArrayList<BackgroundStar> backgroundStars;

    boolean leftButtonDown;


    public void callbackSetup() {
        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            cursorPosition = coordinateTransposer.visualToPhysical(new Vector(xpos, ypos));
//            System.out.println(cursorPosition.x + " " + cursorPosition.y);
        });

        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_LEFT) {
                    if (action == GLFW_PRESS) {
                        leftButtonDown = true;
//                        System.out.println("Left mouse button pressed");
                    } else if (action == GLFW_RELEASE) {
                        leftButtonDown = false;
//                        System.out.println("Left mouse button released");
                    }
                }
            }
        });
    }

    public void gameSetup() {
        circleObjects.add(new CircleObject(300, 400, 30, coordinateTransposer));
        circleObjects.add(new CircleObject(500, 400, 30, coordinateTransposer));
        circleObjects.get(0).setSpeed(new Vector(0, 0.5));
        circleObjects.get(1).setSpeed(new Vector(0, -0.5));

        spacePlane = new SpacePlane(new NewtonPoint(new Vector(100, 100)), coordinateTransposer);

        parametricPoints.add(new ParametricPoint(new Vector(400,400), 0.01, 300, 100, 60, coordinateTransposer));
    }

    public void singleStarGameSetup() {
        backgroundStars = BackgroundStar.spawnStars(3000, new Vector(-2000, -2000), new Vector(2000, 2000), coordinateTransposer);

        circleObjects.add(new CircleObject(0, 0, 30, coordinateTransposer));
        circleObjects.get(0).newtonPoint.mass = 10;
        circleObjects.get(0).circleSpriteHandler.r = 1;
        circleObjects.get(0).circleSpriteHandler.g = 1;
        circleObjects.get(0).circleSpriteHandler.b = 0;


        circleObjects.add(new CircleObject(700, 0, 15, coordinateTransposer));
        circleObjects.get(1).newtonPoint.mass = 1.5;
        circleObjects.get(1).setSpeed(new Vector(0, 0.83));

        spacePlane = new SpacePlane(new NewtonPoint(new Vector(250, 0)), coordinateTransposer);
        spacePlane.center.speed.y = 1;
    }

    public void drawFrame() {
        for (var bs : backgroundStars) {
            bs.circleObject.circleSpriteHandler.drawCircle();
        }
        for (var sprite : getCircleSprites()) {
            sprite.drawCircle();
        }
        for (var point : parametricPoints) {
            point.draw();
        }
        spacePlane.draw();
    }

    public void simulatePhysics() {
        coordinateTransposer.shipPosition = spacePlane.center.position;

        // Other points
        var newtonPoints = getNewtonPoints();
        for (var point : newtonPoints) {
            point.move();
            point.applyGravity(newtonPoints);
        }
        for (var point : parametricPoints) {
            point.nextStep();
        }

        // Spaceplane
        spacePlane.center.applyGravity(newtonPoints);
        Vector spacePlaneSpeedFromCursor = cursorPosition.getDifference(spacePlane.center.position);
        spacePlaneSpeedFromCursor.normalize();
        spacePlaneSpeedFromCursor.multiplyByConstant(0.03);
        if (leftButtonDown) {
            spacePlane.center.speed.add(spacePlaneSpeedFromCursor);
        }
        spacePlane.center.move();
    }

    public void loop() {
        callbackSetup();
//        gameSetup();
        singleStarGameSetup();


        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.1f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // Set up the projection matrix
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Settings.windowX, 0, Settings.windowY, 1, -1); // Assuming your window size is 800x800

            // Set up the modelview matrix
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            // https://stackoverflow.com/questions/20394727/gl-triangle-strip-vs-gl-triangle-fan

            simulatePhysics();
            drawFrame();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}
