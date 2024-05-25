package app;

import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import simulation.*;
import utility.Settings;
import utility.Vector;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class SimulationHandler {

    long window;

    boolean runningInReverse = false;

    Vector cursorPosition = new Vector(0, 0);

    public SimulationHandler(long window) {
        this.window = window;
    }

    CoordinateTransposer coordinateTransposer = new CoordinateTransposer();


    GameState gameState = new GameState(coordinateTransposer);

    boolean inMainMenu = true;


    NewtonPoint[] getNewtonPoints() {
        return gameState.circleObjects.stream().map(co -> co.newtonPoint).toArray(NewtonPoint[]::new);
    }


    CircleSpriteHandler[] getCircleSprites() {
        return gameState.circleObjects.stream().map(co -> co.circleSpriteHandler).toArray(CircleSpriteHandler[]::new);
    }

    boolean leftButtonDown;

    public void checkCollisions() {
        if(!inMainMenu) {
            for (CircleObject co : gameState.circleObjects) {
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
    }

    public void callbackSetup() {
        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            cursorPosition = coordinateTransposer.visualToPhysical(new Vector(xpos, ypos));
//            System.out.println(cursorPosition.x + " " + cursorPosition.y);
        });

        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (inMainMenu) {
                    inMainMenu = false;
                    gameState.nextRound();
                }
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

        // Load the texture
        int textureId = loadTexture("E:\\Repos\\CurvedSpace\\main\\src\\textures\\test.png"); // TODO remove Hardcoding


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
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, textureId);

            // Render the textured quad
            glEnable(GL_TEXTURE_2D);
            glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f(100, 100);
            glTexCoord2f(1, 0);
            glVertex2f(300, 100);
            glTexCoord2f(1, 1);
            glVertex2f(300, 300);
            glTexCoord2f(0, 1);
            glVertex2f(100, 300);
            glEnd();
            glDisable(GL_TEXTURE_2D);

            simulatePhysics();
            drawFrame();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                System.out.println("Hello");
            }
        }

        glDeleteTextures(textureId);
    }

    private int loadTexture(String path) {
        int width, height;
        ByteBuffer image;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            // Load the image
            image = STBImage.stbi_load(path, w, h, comp, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + STBImage.stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        // Create a new OpenGL texture
        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Set the texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // Upload the texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        // Generate mipmaps
        glGenerateMipmap(GL_TEXTURE_2D); // TODO is this the good import?

        // Free the image memory
        STBImage.stbi_image_free(image);

        return textureId;
    }
}
