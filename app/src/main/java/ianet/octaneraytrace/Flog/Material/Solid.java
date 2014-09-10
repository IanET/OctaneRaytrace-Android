package ianet.octaneraytrace.Flog.Material;

import ianet.octaneraytrace.Flog.Color;

public class Solid extends BaseMaterial {
    Color color;

    public Solid(Color color, double reflection, double refraction, double transparency, double gloss) {
        this.color = color;
        this.reflection = reflection;
        this.transparency = transparency;
        this.gloss = gloss;
        this.hasTexture = false;
    }

    @Override
    public Color getColor(double u, double v) {
        return this.color;
    }

    @Override
    public String toString() {
        return "SolidMaterial [gloss=" + this.gloss + ", transparency=" + this.transparency + ", hasTexture=" + this.hasTexture + "]";
    }
}

