package ianet.octaneraytrace.Flog;

import ianet.octaneraytrace.Flog.Shape.*;
import java.util.*;

public class Scene {
    public Camera camera;
    public Vector<BaseShape> shapes;
    public Vector<Light> lights;
    public Background background;

    public Scene() {
        this.camera = new Camera(new Vector3D(0, 0, -5), new Vector3D(0, 0, 1), new Vector3D(0, 1, 0));
        this.shapes = new Vector<BaseShape>();
        this.lights = new Vector<Light>();
        this.background = new Background(new Color(0, 0, 0.5), 0.2);
    }
}