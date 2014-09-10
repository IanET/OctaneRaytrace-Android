package ianet.octaneraytrace.Flog;

import java.lang.Math;
import ianet.octaneraytrace.Flog.Shape.*;
import ianet.octaneraytrace.Flog.Material.*;

// The ray tracer code in this file was originally written by Adam Burmister.
public class RayTracer {

    public static void renderScene() {
        Scene scene = new Scene();

        scene.camera = new Camera(new Vector3D(0, 0, -15), new Vector3D(-0.2, 0, 5), new Vector3D(0, 1, 0));
        scene.background = new Background(new Color(0.5, 0.5, 0.5), 0.4);

        Sphere sphere = new Sphere(new Vector3D(-1.5, 1.5, 2), 1.5, new Solid(new Color(0, 0.5, 0.5), 0.3, 0.0, 0.0, 2.0));
        Sphere sphere1 = new Sphere(new Vector3D(1, 0.25, 1), 0.5, new Solid(new Color(0.9, 0.9, 0.9), 0.1, 0.0, 0.0, 1.5));
        Plane plane = new Plane(new Vector3D(0.1, 0.9, -0.5).normalize(), 1.2, new Chessboard(new Color(1, 1, 1), new Color(0, 0, 0), 0.2, 0.0, 1.0, 0.7));
        scene.shapes.add(plane);
        scene.shapes.add(sphere);
        scene.shapes.add(sphere1);

        Light light = new Light(new Vector3D(5, 10, -1), new Color(0.8, 0.8, 0.8));
        Light light1 = new Light(new Vector3D(-3, 5, -15), new Color(0.8, 0.8, 0.8), 100);
        scene.lights.add(light);
        scene.lights.add(light1);

        EngineOptions options = new EngineOptions();
        options.canvasWidth = 100;
        options.canvasHeight = 100;
        options.pixelWidth = 5;
        options.pixelHeight = 5;
        options.renderDiffuse = true;
        options.renderHighlights = true;
        options.renderShadows = true;
        options.renderReflections = true;
        options.rayDepth = 2;

        Engine raytracer = new Engine(options);
        raytracer.renderScene(scene, null);
    }
}
