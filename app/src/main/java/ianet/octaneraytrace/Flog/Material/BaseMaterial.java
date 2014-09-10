package ianet.octaneraytrace.Flog.Material;

import ianet.octaneraytrace.Flog.Color;

public class BaseMaterial {
    public double gloss = 2.0;             // [0...infinity] 0 = matt
    public double transparency = 0.0;      // 0=opaque
    public double reflection = 0.0;        // [0...infinity] 0 = no reflection
    public double refraction = 0.50;
    public boolean hasTexture = false;

    public Color getColor(double u, double v) {
        return null;
    }

    public double wrapUp(double t) {
        t = t % 2.0;
        if (t < -1) t += 2.0;
        if (t >= 1) t -= 2.0;
        return t;
    }

    @Override
    public String toString() {
        return "Material [gloss=" + this.gloss + ", transparency=" + this.transparency + ", hasTexture=" + this.hasTexture + "]";
    }
}
