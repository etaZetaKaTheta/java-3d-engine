package testing;

import etazeta.engine.core.*;
import etazeta.engine.core.entity.Entity;
import etazeta.engine.core.entity.Model;
import etazeta.engine.core.entity.Texture;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic
{
    private static final float CAMERA_MOVE_SPEED = 0.05f;

    private final Renderer renderer;
    private final ObjectLoader loader;
    private final Window window;

    private Entity entity;
    private Camera camera;

    Vector3f cameraInc;

    public TestGame()
    {
        renderer = new Renderer();
        window = Launcher.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
    }

    @Override
    public void init() throws Exception
    {
        renderer.init();

        float[] vertices = new float[] {
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
        };
        float[] textureCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.0f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
                8, 10, 11, 9, 8, 11,
                12, 13, 7, 5, 12, 7,
                14, 15, 6, 4, 14, 6,
                16, 18, 19, 17, 16, 19,
                4, 6, 7, 5, 4, 7,
        };

        Model model = loader.loadModel(vertices, textureCoords, indices);
        model.setTexture(new Texture(loader.loadTexture("textures/Bricks076C_2K_Color.png")));
        entity = new Entity(model, new Vector3f(1, 0, -5), new Vector3f(0, 0, 0), 1);
    }

    @Override
    public void input()
    {
        cameraInc.set(0, 0, 0);
        if(window.isKeyPressed(GLFW.GLFW_KEY_W))
        {
            cameraInc.z = -1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_S))
        {
            cameraInc.z = 1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_A))
        {
            cameraInc.x = -1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_D))
        {
            cameraInc.x = 1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_I))
        {
            cameraInc.y = -1;
        }

        if(window.isKeyPressed(GLFW.GLFW_KEY_K))
        {
            cameraInc.y = 1;
        }
    }

    @Override
    public void update()
    {
        camera.movePosition(cameraInc.x * CAMERA_MOVE_SPEED, cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);
        entity.incRotation(0.0f, 0.05f, 0.0f);
    }

    @Override
    public void render()
    {
        if(window.isResize())
        {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        renderer.render(entity, camera);
    }

    @Override
    public void terminate()
    {
        renderer.terminate();
        loader.terminate();
    }
}
