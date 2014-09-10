package ianet.octaneraytrace.Flog;

public class Vector3D {
    public double x;
    public double y;
    public double z;

    public Vector3D(double x, double y, double z) {
        this.x = Double.isNaN(x) ? 0.0 : x;
        this.y = Double.isNaN(y) ? 0.0 : y;
        this.z = Double.isNaN(z) ? 0.0 : z;
    }

    public void copy (Vector3D vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public Vector3D normalize() {
        double m = this.magnitude();
        return new Vector3D(this.x / m, this.y / m, this.z / m);
    }

    public double magnitude() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
    }

    public Vector3D cross (Vector3D w) {
        return new Vector3D(-this.z * w.y + this.y * w.z, this.z * w.x - this.x * w.z, -this.y * w.x + this.x * w.y);
    }

    public double dot(Vector3D w) {
        return this.x * w.x + this.y * w.y + this.z * w.z;
    }

    public static Vector3D add(Vector3D v, Vector3D w) {
        return new Vector3D(w.x + v.x, w.y + v.y, w.z + v.z);
    }

    public static Vector3D subtract(Vector3D v, Vector3D w) {
        if (w == null || v == null) throw new RuntimeException("Vectors must be defined [" + v + "," + w + "]");
        return new Vector3D(v.x - w.x, v.y - w.y, v.z - w.z);
    }

    public static Vector3D multiplyVector(Vector3D v, Vector3D w) {
        return new Vector3D(v.x * w.x, v.y * w.y, v.z * w.z);
    }

    public static Vector3D multiplyScalar(Vector3D v, double w) {
        return new Vector3D(v.x * w, v.y * w, v.z * w);
    }

    @Override
    public String toString() {
        return "Vector [" + this.x + "," + this.y + "," + this.z + "]";
    }
}