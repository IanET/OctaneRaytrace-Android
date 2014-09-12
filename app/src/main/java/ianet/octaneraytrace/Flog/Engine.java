package ianet.octaneraytrace.Flog;

import ianet.octaneraytrace.Flog.Shape.*;

public class Engine {
    public Canvas canvas; /* 2d context we can render to */
    public EngineOptions options;
    public int checkNumber;

    public Engine(EngineOptions options) {
        this.options = options != null ? options : new EngineOptions();
        this.options.canvasHeight /= this.options.pixelHeight;
        this.options.canvasWidth /= this.options.pixelWidth;

        /* TODO: dynamically include other scripts */
    }

    public void setPixel(int x, int y, Color color) {
        int pxW, pxH;

        pxW = this.options.pixelWidth;
        pxH = this.options.pixelHeight;

        if (this.canvas != null) {
            this.canvas.fillStyle = color;
            this.canvas.fillRect(x * pxW, y * pxH, pxW, pxH);
        } else {
            if (x == y) {
                checkNumber += color.brightness();
            }
            // print(x * pxW, y * pxH, pxW, pxH);
        }
    }

    public void renderScene(Scene scene, Canvas canvas) {
        this.checkNumber = 0;
        this.canvas = canvas;

        int canvasHeight = this.options.canvasHeight;
        int canvasWidth = this.options.canvasWidth;

        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                double yp = y * 1.0 / canvasHeight * 2 - 1;
                double xp = x * 1.0 / canvasWidth * 2 - 1;

                Ray ray = scene.camera.getRay(xp, yp);

                Color color = this.getPixelColor(ray, scene);

                this.setPixel(x, y, color);
            }
        }
        if (this.canvas == null && checkNumber != 2321) {
            throw new RuntimeException("Scene rendered incorrectly");
        }
    }

    public Color getPixelColor(Ray ray, Scene scene) {
        IntersectionInfo info = this.testIntersection(ray, scene, null);
        if (info.isHit) {
            Color color = this.rayTrace(info, ray, scene, 0);
            return color;
        }
        return scene.background.color;
    }

    public IntersectionInfo testIntersection(Ray ray, Scene scene, BaseShape exclude) {
        int hits = 0;
        IntersectionInfo best = new IntersectionInfo();
        best.distance = 2000;

        for (int i = 0; i < scene.shapes.size(); i++) {
            BaseShape shape = scene.shapes.elementAt(i);

            if (shape != exclude) {
                IntersectionInfo info = shape.intersect(ray);
                if (info.isHit && info.distance >= 0 && info.distance < best.distance) {
                    best = info;
                    hits++;
                }
            }
        }
        best.hitCount = hits;
        return best;
    }

    public Ray getReflectionRay(Vector3D P, Vector3D N, Vector3D V) {
        double c1 = -N.dot(V);
        Vector3D R1 = Vector3D.add(Vector3D.multiplyScalar(N, 2 * c1), V);
        return new Ray(P, R1);
    }

    public Color rayTrace(IntersectionInfo info, Ray ray, Scene scene, int depth) {
        // Calc ambient
        Color color = Color.multiplyScalar(info.color, scene.background.ambience);
        Color oldColor = color;
        double shininess = Math.pow(10, info.shape.material.gloss + 1);

        for (int i = 0; i < scene.lights.size(); i++) {
            Light light = scene.lights.elementAt(i);

            // Calc diffuse lighting
            Vector3D v = Vector3D.subtract(light.position, info.position).normalize();

            if (this.options.renderDiffuse) {
                double L = v.dot(info.normal);
                if (L > 0.0) {
                    color = Color.add(color, Color.multiply(info.color, Color.multiplyScalar(light.color, L)));
                }
            }

            // The greater the depth the more accurate the colours, but
            // this is exponentially (!) expensive
            if (depth <= this.options.rayDepth) {
                // calculate reflection ray
                if (this.options.renderReflections && info.shape.material.reflection > 0) {
                    Ray reflectionRay = this.getReflectionRay(info.position, info.normal, ray.direction);
                    IntersectionInfo refl = this.testIntersection(reflectionRay, scene, info.shape);

                    if (refl.isHit && refl.distance > 0) {
                        refl.color = this.rayTrace(refl, reflectionRay, scene, depth + 1);
                    } else {
                        refl.color = scene.background.color;
                    }

                    color = Color.blend(color, refl.color, info.shape.material.reflection);
                }

                // Refraction
            /* TODO */
            }

            /* Render shadows and highlights */

            IntersectionInfo shadowInfo = new IntersectionInfo();

            if (this.options.renderShadows) {
                Ray shadowRay = new Ray(info.position, v);

                shadowInfo = this.testIntersection(shadowRay, scene, info.shape);
                if (shadowInfo.isHit && shadowInfo.shape != info.shape /*&& shadowInfo.shape.type != 'PLANE'*/) {
                    Color vA = Color.multiplyScalar(color, 0.5);
                    double dB = (0.5 * Math.pow(shadowInfo.shape.material.transparency, 0.5));
                    color = Color.addScalar(vA, dB);
                }
            }

            // Phong specular highlights
            if (this.options.renderHighlights && !shadowInfo.isHit && info.shape.material.gloss > 0) {
                Vector3D Lv = Vector3D.subtract(info.shape.position, light.position).normalize();

                Vector3D E = Vector3D.subtract(scene.camera.position, info.shape.position).normalize();

                Vector3D H = Vector3D.subtract(E, Lv).normalize();

                double glossWeight = Math.pow(Math.max(info.normal.dot(H), 0), shininess);
                color = Color.add(Color.multiplyScalar(light.color, glossWeight), color);
            }
        }
        color.limit();
        return color;
    }
}
