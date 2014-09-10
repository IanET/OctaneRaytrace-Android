package ianet.octaneraytrace.Flog.Material;

import ianet.octaneraytrace.Flog.Color;

public class Chessboard extends BaseMaterial {
    Color colorEven;
    Color colorOdd;
    double density=  0.5;

    public Chessboard(Color colorEven, Color colorOdd, double reflection, double transparency, double gloss, double density) {
        this.colorEven = colorEven;
        this.colorOdd = colorOdd;
        this.reflection = reflection;
        this.transparency = transparency;
        this.gloss = gloss;
        this.density = density;
        this.hasTexture = true;
    }

    @Override
    public Color getColor(double u, double v) {
        double t = this.wrapUp(u * this.density) * this.wrapUp(v * this.density);
        if (t < 0.0)
            return this.colorEven;
        else
            return this.colorOdd;
    }

    @Override
    public String toString() {
        return "ChessMaterial [gloss=" + this.gloss + ", transparency=" + this.transparency + ", hasTexture=" + this.hasTexture + "]";
    }
}
