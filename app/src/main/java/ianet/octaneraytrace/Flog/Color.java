package ianet.octaneraytrace.Flog;

public class Color {
    public double red;
    public double green;
    public double blue;

    public Color(double r, double g, double b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public static Color add(Color c1, Color c2) {
        return new Color(c1.red + c2.red, c1.green + c2.green, c1.blue + c2.blue);
    }

    public static Color addScalar(Color c1, double s) {
        return new Color(c1.red + s, c1.green + s, c1.blue + s);
    }

    public static Color subtract(Color c1, Color c2) {
        return new Color(c1.red - c2.red, c1.green - c2.green, c1.blue - c2.blue);
    }

    public static Color multiply(Color c1, Color c2) {
        return new Color(c1.red * c2.red, c1.green * c2.green, c1.blue * c2.blue);
    }

    public static Color multiplyScalar(Color c1, double f) {
        return new Color(c1.red * f, c1.green * f, c1.blue * f);
    }

    public static Color divideFactor(Color c1, double f) {
        return new Color(c1.red / f, c1.green / f, c1.blue / f);
    }

    public void limit() {
        this.red = (this.red > 0.0) ? ((this.red > 1.0) ? 1.0 : this.red) : 0.0;
        this.green = (this.green > 0.0) ? ((this.green > 1.0) ? 1.0 : this.green) : 0.0;
        this.blue = (this.blue > 0.0) ? ((this.blue > 1.0) ? 1.0 : this.blue) : 0.0;
    }

    public double distance(Color color) {
        return Math.abs(this.red - color.red) + Math.abs(this.green - color.green) + Math.abs(this.blue - color.blue);
    }

    public static Color blend(Color c1, Color c2, double w) {
        return add(multiplyScalar(c1, 1 - w), multiplyScalar(c2, w));
    }

    public double brightness() {
        double r = Math.floor(this.red * 255);
        double g = Math.floor(this.green * 255);
        double b = Math.floor(this.blue * 255);
        return ((r * 77) + (g * 150) + (b * 29)) / 256;
    }

    @Override
    public String toString() {
        double r = Math.floor(this.red * 255);
        double g = Math.floor(this.green * 255);
        double b = Math.floor(this.blue * 255);

        return "rgb(" + r + "," + g + "," + b + ")";
    }

}
