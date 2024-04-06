import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class SimulationHandler {

    long window;

    public SimulationHandler(long window) {
        this.window = window;
    }

    SpriteHandler spriteHandler = new SpriteHandler();

    ArrayList<CircleObject> circleObjects = new ArrayList<>();

    NewtonPoint[] getNewtonPoints() {
        return circleObjects.stream().map(co -> co.newtonPoint).toArray(NewtonPoint[]::new);
    }

    CircleSpriteHandler[] getCircleSprites() {
        return circleObjects.stream().map(co -> co.circleSpriteHandler).toArray(CircleSpriteHandler[]::new);
    }
    public void loop() {
        circleObjects.add(new CircleObject(300, 400, 30));
        circleObjects.add(new CircleObject(500, 400, 30));
        circleObjects.get(0).setSpeed(new Vector(0, 0.3));
        circleObjects.get(1).setSpeed(new Vector(0, -0.3));
//        NewtonPoint p1 = new NewtonPoint(new Vector(300, 400));
//        NewtonPoint p2 = new NewtonPoint(new Vector(500, 400));
//
//        p1.speed.y += 0.3;
//        p2.speed.y -= 0.3;
//
//        CircleSpriteHandler circleSpriteHandler1 = new CircleSpriteHandler(p1.position, 30);
//        circleSpriteHandler1.r = 1f;
//        CircleSpriteHandler circleSpriteHandler2 = new CircleSpriteHandler(p2.position, 30);


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

            // Simulate physics
            var newtonPoints = getNewtonPoints();
            for (var point : newtonPoints) {
                point.move();
                point.applyGravity(newtonPoints);
            }
            for (var sprite : getCircleSprites()) {
                sprite.drawCircle();
            }

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}
