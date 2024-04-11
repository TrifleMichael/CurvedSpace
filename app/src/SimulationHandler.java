import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class SimulationHandler {

    long window;

    Vector cursorPosition = new Vector(0, 0);

    public SimulationHandler(long window) {
        this.window = window;
    }

    ArrayList<CircleObject> circleObjects = new ArrayList<>();

    SpacePlane spacePlane;

    NewtonPoint[] getNewtonPoints() {
        return circleObjects.stream().map(co -> co.newtonPoint).toArray(NewtonPoint[]::new);
    }

    CircleSpriteHandler[] getCircleSprites() {
        return circleObjects.stream().map(co -> co.circleSpriteHandler).toArray(CircleSpriteHandler[]::new);
    }

    boolean leftButtonDown;

    public void gameSetup() {
        // TODO This isnt game setup
        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            cursorPosition = CoordinateTransposer.visualToPhysical(new Vector(xpos, ypos));
        });

        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_LEFT) {
                    if (action == GLFW_PRESS) {
                        leftButtonDown = true;
                        System.out.println("Left mouse button pressed");
                    } else if (action == GLFW_RELEASE) {
                        leftButtonDown = false;
                        System.out.println("Left mouse button released");
                    }
                }
            }
        });

        circleObjects.add(new CircleObject(300, 400, 30));
        circleObjects.add(new CircleObject(500, 400, 30));
        circleObjects.get(0).setSpeed(new Vector(0, 0.3));
        circleObjects.get(1).setSpeed(new Vector(0, -0.3));

        spacePlane = new SpacePlane(new NewtonPoint(new Vector(100, 100)));
    }

    public void drawFrame() {
        for (var sprite : getCircleSprites()) {
            sprite.drawCircle();
        }
        spacePlane.draw();
    }

    public void simulatePhysics() {
        var newtonPoints = getNewtonPoints();
        for (var point : newtonPoints) {
            point.move();
            point.applyGravity(newtonPoints);
        }
        spacePlane.center.applyGravity(newtonPoints);

        Vector spacePlaneSpeedFromCursor = cursorPosition.getDifference(spacePlane.center.position);
        spacePlaneSpeedFromCursor.normalize();
        spacePlaneSpeedFromCursor.multiplyByConstant(0.2);
        if (leftButtonDown) {
            spacePlane.center.speed.add(spacePlaneSpeedFromCursor);
        }
        spacePlane.center.move();
    }

    public void loop() {
        gameSetup();

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 1.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // Set up the projection matrix
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, 800, 0, 800, 1, -1); // Assuming your window size is 800x800

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
