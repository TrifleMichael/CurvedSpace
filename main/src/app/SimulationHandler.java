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

    boolean runningInReverse = false;

    Vector cursorPosition = new Vector(0, 0);

    public SimulationHandler(long window) {
        this.window = window;
    }

    CoordinateTransposer coordinateTransposer = new CoordinateTransposer();


    GameState gameState = new GameState(coordinateTransposer);


    NewtonPoint[] getNewtonPoints() {
        return gameState.circleObjects.stream().map(co -> co.newtonPoint).toArray(NewtonPoint[]::new);
    }


    CircleSpriteHandler[] getCircleSprites() {
        return gameState.circleObjects.stream().map(co -> co.circleSpriteHandler).toArray(CircleSpriteHandler[]::new);
    }

    boolean leftButtonDown;

    public void checkCollisions() {
        for(CircleObject co : gameState.circleObjects) {
            double distance = co.newtonPoint.getDistance(gameState.spacePlane);
            double combinedRadius = co.getRadius() + gameState.spacePlane.radius;
            if (distance < combinedRadius) {
                if (co.target) {
                    System.out.println("You won!");
                    gameState.nextRound();
                } else {
                    gameState.spacePlane.exploding = true;
                    System.out.println("Explosion scheduled"); // Todo: actually explode ship
                    runningInReverse = true;
                }
                break;
            }
        }
    }

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
                if (button == GLFW_MOUSE_BUTTON_RIGHT) {
//                if (button == GLFW_KEY_SPACE) {
                    if (action == GLFW_PRESS) {
                        runningInReverse = !runningInReverse;
                    }
                }
            }
        });
    }

    public void drawFrame() {
        for (var bs : gameState.backgroundStars) {
            bs.circleObject.circleSpriteHandler.drawCircle();
        }
        for (var sprite : getCircleSprites()) {
            sprite.drawCircle();
        }
        for (var point : gameState.parametricPoints) {
            point.draw();
        }
        gameState.spacePlane.draw();
    }

    public void simulatePhysics() {
        coordinateTransposer.shipPosition = gameState.spacePlane;

        // Other points
        var newtonPoints = getNewtonPoints();
        checkCollisions();

        if (!runningInReverse) {
            for (var point : newtonPoints) {
                if (point.movable) {
                    point.move();
                    point.applyGravity(newtonPoints);
                }
            }
            for (var point : gameState.parametricPoints) {
                point.nextStep();
            }

            // Spaceplane
            gameState.spacePlane.applyGravity(newtonPoints);
            Vector spacePlaneSpeedFromCursor = cursorPosition.getDifference(gameState.spacePlane);
            spacePlaneSpeedFromCursor.normalize();
            spacePlaneSpeedFromCursor.multiplyByConstant(0.03);
            if (leftButtonDown) {
                gameState.spacePlane.speed.add(spacePlaneSpeedFromCursor);
            }
            gameState.spacePlane.move();
        } else {
            for (var point : newtonPoints) {
                if (point.movable) {
                    point.moveBackwards();
                    point.subtractGravity(newtonPoints);
                }
            }
            for (var point : gameState.parametricPoints) {
                point.previousStep();
            }

            // Spaceplane
            gameState.spacePlane.subtractGravity(newtonPoints);
            gameState.spacePlane.moveBackwards();
        }
    }

    public void loop() {
        callbackSetup();
//        gameSetup();
        gameState.nextRound();


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
