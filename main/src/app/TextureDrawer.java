package app;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureDrawer {

    public HashMap<String, Integer> textureMap = new HashMap<>();

    public ArrayList<DrawEntry> drawQueue = new ArrayList<>();

    public ArrayList<Integer> removeQueue = new ArrayList<>();

    public void drawQueueContent() {
        for(DrawEntry entry : drawQueue) {
            int id = textureMap.get(entry.textureName);
            drawTexture(id, entry.xl, entry.xr, entry.yd, entry.yu);
            removeQueue.add(id);
        }
        drawQueue.clear();
    }

//    public void clearTextures() {
//        for(Integer id : removeQueue) {
//        glDeleteTextures(textureId);
//    }

    public void drawTexture(int textureId, int xl, int xr, int yd, int yu) {
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Render the textured quad
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(xl, yu);
        glTexCoord2f(1, 0);
        glVertex2f(xr, yu);
        glTexCoord2f(1, 1);
        glVertex2f(xr, yd);
        glTexCoord2f(0, 1);
        glVertex2f(xl, yd);
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }

    public void drawTexture(String textureTag, int xl, int xr, int yd, int yu) {
        int textureId = textureMap.get(textureTag);
        drawTexture(textureId, xl, xr, yd, yu);
    }

    public int loadTexture(String path) {
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
